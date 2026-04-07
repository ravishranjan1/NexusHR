package com.nexushr.employee.service;

import com.nexushr.employee.dto.CreateLeaveRequest;
import com.nexushr.employee.dto.LeaveDecisionRequest;
import com.nexushr.employee.dto.LeaveRequestResponse;
import com.nexushr.employee.model.Employee;
import com.nexushr.employee.model.LeaveRequest;
import com.nexushr.employee.model.LeaveStatus;
import com.nexushr.employee.repository.EmployeeRepository;
import com.nexushr.employee.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class LeaveManagementService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveManagementService(LeaveRequestRepository leaveRequestRepository,
                                  EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public LeaveRequestResponse submitLeaveRequest(CreateLeaveRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        Employee employee = findEmployee(request.employeeId());

        boolean overlappingApprovedLeave = leaveRequestRepository
                .existsByEmployeeIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        employee.getId(),
                        LeaveStatus.APPROVED,
                        request.endDate(),
                        request.startDate()
                );

        if (overlappingApprovedLeave) {
            throw new IllegalArgumentException("Employee already has approved leave in this date range.");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(request.leaveType());
        leaveRequest.setStartDate(request.startDate());
        leaveRequest.setEndDate(request.endDate());
        leaveRequest.setReason(request.reason());
        leaveRequest.setStatus(LeaveStatus.PENDING);

        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    public List<LeaveRequestResponse> getEmployeeLeaveRequests(Long employeeId) {
        findEmployee(employeeId);
        return leaveRequestRepository.findByEmployeeIdOrderByRequestedAtDesc(employeeId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<LeaveRequestResponse> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatusOrderByRequestedAtAsc(LeaveStatus.PENDING).stream()
                .map(this::toResponse)
                .toList();
    }

    public LeaveRequestResponse approveLeave(Long leaveRequestId, LeaveDecisionRequest request) {
        LeaveRequest leaveRequest = findLeaveRequest(leaveRequestId);
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalArgumentException("Only pending leave requests can be approved.");
        }

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setReviewedAt(Instant.now());
        leaveRequest.setReviewedBy(request.reviewerName());
        leaveRequest.setReviewComments(request.comments());

        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    public LeaveRequestResponse rejectLeave(Long leaveRequestId, LeaveDecisionRequest request) {
        LeaveRequest leaveRequest = findLeaveRequest(leaveRequestId);
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalArgumentException("Only pending leave requests can be rejected.");
        }

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setReviewedAt(Instant.now());
        leaveRequest.setReviewedBy(request.reviewerName());
        leaveRequest.setReviewComments(request.comments());

        return toResponse(leaveRequestRepository.save(leaveRequest));
    }

    private Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
    }

    private LeaveRequest findLeaveRequest(Long leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));
    }

    private LeaveRequestResponse toResponse(LeaveRequest leaveRequest) {
        Employee employee = leaveRequest.getEmployee();
        return new LeaveRequestResponse(
                leaveRequest.getId(),
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName() + " " + employee.getLastName(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus(),
                leaveRequest.getRequestedAt(),
                leaveRequest.getReviewedAt(),
                leaveRequest.getReviewedBy(),
                leaveRequest.getReviewComments()
        );
    }
}
