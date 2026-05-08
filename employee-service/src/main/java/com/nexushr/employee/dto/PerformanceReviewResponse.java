package com.nexushr.employee.dto;

import com.nexushr.employee.model.PerformanceReviewStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PerformanceReviewResponse(
        Long id,
        Long employeeId,
        String employeeCode,
        String employeeName,
        Long reviewerId,
        String reviewerCode,
        String reviewerName,
        String reviewPeriod,
        String goalsSummary,
        String strengths,
        String improvementAreas,
        String managerComments,
        Integer goalAchievementRating,
        Integer teamworkRating,
        Integer punctualityRating,
        BigDecimal overallRating,
        PerformanceReviewStatus status,
        Instant reviewedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
