package com.nexushr.employee.repository;

import com.nexushr.employee.model.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findByEmployeeIdOrderByReviewedAtDesc(Long employeeId);

    List<PerformanceReview> findByReviewerIdOrderByReviewedAtDesc(Long reviewerId);
}
