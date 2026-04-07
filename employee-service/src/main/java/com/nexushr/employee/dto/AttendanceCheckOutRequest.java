package com.nexushr.employee.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AttendanceCheckOutRequest(
        Instant checkOutTime,
        @Size(max = 255) String notes
) {
}
