package com.nexushr.employee.controller;

import com.nexushr.employee.dto.CreateLeaveRequest;
import com.nexushr.employee.dto.LeaveDecisionRequest;
import com.nexushr.employee.dto.LeaveRequestResponse;
import com.nexushr.employee.service.LeaveManagementService;
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
@RequestMapping("/api/leaves")
public class LeaveManagementController {

    private final LeaveManagementService leaveManagementService;

    public LeaveManagementController(LeaveManagementService leaveManagementService) {
        this.leaveManagementService = leaveManagementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public LeaveRequestResponse submitLeaveRequest(@Valid @RequestBody CreateLeaveRequest request) {
        return leaveManagementService.submitLeaveRequest(request);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public List<LeaveRequestResponse> getEmployeeLeaveRequests(@PathVariable Long employeeId) {
        return leaveManagementService.getEmployeeLeaveRequests(employeeId);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public List<LeaveRequestResponse> getPendingLeaveRequests() {
        return leaveManagementService.getPendingLeaveRequests();
    }

    @PostMapping("/{leaveRequestId}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public LeaveRequestResponse approveLeave(@PathVariable Long leaveRequestId,
                                             @Valid @RequestBody LeaveDecisionRequest request) {
        return leaveManagementService.approveLeave(leaveRequestId, request);
    }

    @PostMapping("/{leaveRequestId}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER')")
    public LeaveRequestResponse rejectLeave(@PathVariable Long leaveRequestId,
                                            @Valid @RequestBody LeaveDecisionRequest request) {
        return leaveManagementService.rejectLeave(leaveRequestId, request);
    }
}
