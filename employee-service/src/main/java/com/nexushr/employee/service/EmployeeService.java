package com.nexushr.employee.service;

import com.nexushr.employee.dto.CreateEmployeeRequest;
import com.nexushr.employee.dto.EmployeeResponse;
import com.nexushr.employee.dto.UpdateEmployeeRequest;
import com.nexushr.employee.model.Department;
import com.nexushr.employee.model.Employee;
import com.nexushr.employee.model.Role;
import com.nexushr.employee.repository.DepartmentRepository;
import com.nexushr.employee.repository.EmployeeRepository;
import com.nexushr.employee.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        return toResponse(findEmployee(id));
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmployeeCode(request.employeeCode())) {
            throw new IllegalArgumentException("Employee code already exists.");
        }
        if (employeeRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Employee email already exists.");
        }

        Employee employee = new Employee();
        employee.setEmployeeCode(request.employeeCode());
        applyValues(
                employee,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone(),
                request.jobTitle(),
                request.hireDate(),
                request.status(),
                request.departmentCode(),
                request.roles()
        );

        return toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = findEmployee(id);

        if (!employee.getEmail().equalsIgnoreCase(request.email()) && employeeRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Employee email already exists.");
        }

        applyValues(
                employee,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone(),
                request.jobTitle(),
                request.hireDate(),
                request.status(),
                request.departmentCode(),
                request.roles()
        );

        return toResponse(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        Employee employee = findEmployee(id);
        employeeRepository.delete(employee);
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
    }

    private void applyValues(Employee employee,
                             String firstName,
                             String lastName,
                             String email,
                             String phone,
                             String jobTitle,
                             java.time.LocalDate hireDate,
                             com.nexushr.employee.model.EmploymentStatus status,
                             String departmentCode,
                             Set<String> roleNames) {
        Department department = departmentRepository.findByCode(departmentCode)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + departmentCode));

        List<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.size() != roleNames.size()) {
            throw new IllegalArgumentException("One or more roles are invalid.");
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setJobTitle(jobTitle);
        employee.setHireDate(hireDate);
        employee.setStatus(status);
        employee.setDepartment(department);
        employee.setRoles(new LinkedHashSet<>(roles));
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getJobTitle(),
                employee.getHireDate(),
                employee.getStatus(),
                employee.getDepartment().getCode(),
                employee.getDepartment().getName(),
                employee.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new)),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}
