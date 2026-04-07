package com.nexushr.employee.dto;

import com.nexushr.employee.model.LeaveStatus;
import com.nexushr.employee.model.LeaveType;

import java.time.Instant;
import java.time.LocalDate;

public record LeaveRequestResponse(
        Long id,
        Long employeeId,
        String employeeCode,
        String employeeName,
        LeaveType leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String reason,
        LeaveStatus status,
        Instant requestedAt,
        Instant reviewedAt,
        String reviewedBy,
        String reviewComments
) {
}
