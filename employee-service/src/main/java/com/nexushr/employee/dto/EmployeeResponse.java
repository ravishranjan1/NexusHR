package com.nexushr.employee.dto;

import com.nexushr.employee.model.EmploymentStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record EmployeeResponse(
        Long id,
        String employeeCode,
        String firstName,
        String lastName,
        String email,
        String phone,
        String jobTitle,
        LocalDate hireDate,
        EmploymentStatus status,
        String departmentCode,
        String departmentName,
        Set<String> roles,
        Instant createdAt,
        Instant updatedAt
) {
}
