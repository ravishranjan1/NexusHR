package com.nexushr.employee.service;

import com.nexushr.employee.dto.CreatePerformanceReviewRequest;
import com.nexushr.employee.dto.PerformanceReviewResponse;
import com.nexushr.employee.model.Employee;
import com.nexushr.employee.model.PerformanceReview;
import com.nexushr.employee.model.PerformanceReviewStatus;
import com.nexushr.employee.repository.EmployeeRepository;
import com.nexushr.employee.repository.PerformanceReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeRepository employeeRepository;

    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository,
                                    EmployeeRepository employeeRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.employeeRepository = employeeRepository;
    }

    public PerformanceReviewResponse createReview(CreatePerformanceReviewRequest request) {
        Employee employee = findEmployee(request.employeeId());
        Employee reviewer = findEmployee(request.reviewerId());

        if (employee.getId().equals(reviewer.getId())) {
            throw new IllegalArgumentException("Employee and reviewer cannot be the same.");
        }

        BigDecimal overallRating = calculateOverallRating(
                request.goalAchievementRating(),
                request.teamworkRating(),
                request.punctualityRating()
        );

        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setReviewer(reviewer);
        review.setReviewPeriod(request.reviewPeriod().trim());
        review.setGoalsSummary(request.goalsSummary());
        review.setStrengths(request.strengths());
        review.setImprovementAreas(request.improvementAreas());
        review.setManagerComments(request.managerComments());
        review.setGoalAchievementRating(request.goalAchievementRating());
        review.setTeamworkRating(request.teamworkRating());
        review.setPunctualityRating(request.punctualityRating());
        review.setOverallRating(overallRating);
        review.setStatus(PerformanceReviewStatus.COMPLETED);
        review.setReviewedAt(Instant.now());

        return toResponse(performanceReviewRepository.save(review));
    }

    public PerformanceReviewResponse getReviewById(Long id) {
        return toResponse(performanceReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance review not found.")));
    }

    public List<PerformanceReviewResponse> getReviewsByEmployee(Long employeeId) {
        findEmployee(employeeId);
        return performanceReviewRepository.findByEmployeeIdOrderByReviewedAtDesc(employeeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PerformanceReviewResponse> getReviewsByReviewer(Long reviewerId) {
        findEmployee(reviewerId);
        return performanceReviewRepository.findByReviewerIdOrderByReviewedAtDesc(reviewerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
    }

    private BigDecimal calculateOverallRating(int goalAchievementRating, int teamworkRating, int punctualityRating) {
        return BigDecimal.valueOf(goalAchievementRating + teamworkRating + punctualityRating)
                .divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
    }

    private PerformanceReviewResponse toResponse(PerformanceReview review) {
        Employee employee = review.getEmployee();
        Employee reviewer = review.getReviewer();
        return new PerformanceReviewResponse(
                review.getId(),
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName() + " " + employee.getLastName(),
                reviewer.getId(),
                reviewer.getEmployeeCode(),
                reviewer.getFirstName() + " " + reviewer.getLastName(),
                review.getReviewPeriod(),
                review.getGoalsSummary(),
                review.getStrengths(),
                review.getImprovementAreas(),
                review.getManagerComments(),
                review.getGoalAchievementRating(),
                review.getTeamworkRating(),
                review.getPunctualityRating(),
                review.getOverallRating(),
                review.getStatus(),
                review.getReviewedAt(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
