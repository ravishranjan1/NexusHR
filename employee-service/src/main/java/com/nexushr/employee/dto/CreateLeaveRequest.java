package com.nexushr.employee.dto;

import com.nexushr.employee.model.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateLeaveRequest(
        @NotNull Long employeeId,
        @NotNull LeaveType leaveType,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull @FutureOrPresent LocalDate endDate,
        @NotBlank @Size(max = 255) String reason
) {
}
