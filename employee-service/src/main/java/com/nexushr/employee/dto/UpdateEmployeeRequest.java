package com.nexushr.employee.dto;

import com.nexushr.employee.model.EmploymentStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record UpdateEmployeeRequest(
        @NotBlank @Size(max = 80) String firstName,
        @NotBlank @Size(max = 80) String lastName,
        @NotBlank @Email String email,
        @Size(max = 20) String phone,
        @NotBlank @Size(max = 120) String jobTitle,
        @NotNull LocalDate hireDate,
        @NotNull EmploymentStatus status,
        @NotBlank String departmentCode,
        @NotEmpty Set<String> roles
) {
}
