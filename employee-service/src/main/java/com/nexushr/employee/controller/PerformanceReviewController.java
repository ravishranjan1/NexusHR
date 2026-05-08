package com.nexushr.employee.controller;

import com.nexushr.employee.dto.CreatePerformanceReviewRequest;
import com.nexushr.employee.dto.PerformanceReviewResponse;
import com.nexushr.employee.service.PerformanceReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public PerformanceReviewResponse createReview(@Valid @RequestBody CreatePerformanceReviewRequest request) {
        return performanceReviewService.createReview(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public PerformanceReviewResponse getReviewById(@PathVariable Long id) {
        return performanceReviewService.getReviewById(id);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public List<PerformanceReviewResponse> getReviewsByEmployee(@PathVariable Long employeeId) {
        return performanceReviewService.getReviewsByEmployee(employeeId);
    }

    @GetMapping("/reviewer/{reviewerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public List<PerformanceReviewResponse> getReviewsByReviewer(@PathVariable Long reviewerId) {
        return performanceReviewService.getReviewsByReviewer(reviewerId);
    }
}
