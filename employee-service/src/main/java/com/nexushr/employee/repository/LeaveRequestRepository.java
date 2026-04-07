package com.nexushr.employee.repository;

import com.nexushr.employee.model.LeaveRequest;
import com.nexushr.employee.model.LeaveStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @EntityGraph(attributePaths = "employee")
    List<LeaveRequest> findByEmployeeIdOrderByRequestedAtDesc(Long employeeId);

    @EntityGraph(attributePaths = "employee")
    List<LeaveRequest> findByStatusOrderByRequestedAtAsc(LeaveStatus status);

    boolean existsByEmployeeIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long employeeId,
            LeaveStatus status,
            LocalDate endDate,
            LocalDate startDate
    );
}
