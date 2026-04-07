package com.nexushr.employee.dto;

import com.nexushr.employee.model.AttendanceStatus;

import java.time.Instant;
import java.time.LocalDate;

public record AttendanceResponse(
        Long id,
        Long employeeId,
        String employeeCode,
        String employeeName,
        LocalDate attendanceDate,
        Instant checkInTime,
        Instant checkOutTime,
        AttendanceStatus status,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
