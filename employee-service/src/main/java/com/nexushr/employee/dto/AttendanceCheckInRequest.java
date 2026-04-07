package com.nexushr.employee.dto;

import com.nexushr.employee.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;

public record AttendanceCheckInRequest(
        @NotNull Long employeeId,
        LocalDate attendanceDate,
        Instant checkInTime,
        @NotNull AttendanceStatus status,
        @Size(max = 255) String notes
) {
}
