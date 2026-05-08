package com.nexushr.employee.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePerformanceReviewRequest(
        @NotNull Long employeeId,
        @NotNull Long reviewerId,
        @NotBlank String reviewPeriod,
        String goalsSummary,
        String strengths,
        String improvementAreas,
        String managerComments,
        @NotNull @Min(1) @Max(5) Integer goalAchievementRating,
        @NotNull @Min(1) @Max(5) Integer teamworkRating,
        @NotNull @Min(1) @Max(5) Integer punctualityRating
) {
}
