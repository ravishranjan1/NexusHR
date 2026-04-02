package com.nexushr.auth.config;

import com.nexushr.auth.model.Employee;
import com.nexushr.auth.model.Role;
import com.nexushr.auth.model.RoleName;
import com.nexushr.auth.repository.EmployeeRepository;
import com.nexushr.auth.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedInitialData(RoleRepository roleRepository,
                                      EmployeeRepository employeeRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.ADMIN)));
            Role hrManagerRole = roleRepository.findByName(RoleName.HR_MANAGER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.HR_MANAGER)));
            roleRepository.findByName(RoleName.EMPLOYEE)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.EMPLOYEE)));

            if (!employeeRepository.existsByEmail("admin@nexushr.local")) {
                Employee admin = new Employee(
                        "System",
                        "Administrator",
                        "admin@nexushr.local",
                        passwordEncoder.encode("Admin@123")
                );
                admin.setRoles(Set.of(adminRole, hrManagerRole));
                employeeRepository.save(admin);
            }
        };
    }
}
