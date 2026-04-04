package com.nexushr.auth.service;

import com.nexushr.auth.dto.AuthResponse;
import com.nexushr.auth.dto.LoginRequest;
import com.nexushr.auth.dto.RefreshTokenRequest;
import com.nexushr.auth.dto.SignupRequest;
import com.nexushr.auth.model.Employee;
import com.nexushr.auth.model.RefreshToken;
import com.nexushr.auth.model.Role;
import com.nexushr.auth.model.RoleName;
import com.nexushr.auth.repository.EmployeeRepository;
import com.nexushr.auth.repository.RoleRepository;
import com.nexushr.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final long accessTokenExpiration;

    public AuthenticationService(EmployeeRepository employeeRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 RefreshTokenService refreshTokenService,
                                 @Value("${security.jwt.access-token-expiration}") long accessTokenExpiration) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public AuthResponse signup(SignupRequest request) {
        if (employeeRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("An employee with this email already exists.");
        }

        Role employeeRole = roleRepository.findByName(RoleName.EMPLOYEE)
                .orElseThrow(() -> new IllegalStateException("Default EMPLOYEE role is missing."));

        Employee employee = new Employee(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        employee.setRoles(Set.of(employeeRole));

        Employee savedEmployee = employeeRepository.save(employee);
        return buildAuthResponse(savedEmployee);
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails principal = (UserDetails) authentication.getPrincipal();
            Employee employee = employeeRepository.findByEmail(principal.getUsername())
                    .orElseThrow(() -> new IllegalStateException("Authenticated employee was not found."));

            return buildAuthResponse(employee);
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyActiveToken(request.refreshToken());
        Employee employee = refreshToken.getEmployee();
        refreshTokenService.revoke(request.refreshToken());
        return buildAuthResponse(employee);
    }

    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }

    private AuthResponse buildAuthResponse(Employee employee) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(employee.getEmail())
                .password(employee.getPasswordHash())
                .disabled(!employee.isActive())
                .authorities(employee.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                        .toList())
                .build();

        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.create(employee);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                accessTokenExpiration / 1000,
                employee.getEmail(),
                employee.getRoles().stream().map(Role::getName).map(Enum::name).toList()
        );
    }
}
