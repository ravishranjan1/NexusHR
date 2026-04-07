package com.nexushr.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LeaveDecisionRequest(
        @NotBlank @Size(max = 120) String reviewerName,
        @Size(max = 255) String comments
) {
}
