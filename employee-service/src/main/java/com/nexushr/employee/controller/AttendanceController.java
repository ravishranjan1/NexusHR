package com.nexushr.employee.controller;

import com.nexushr.employee.dto.AttendanceCheckInRequest;
import com.nexushr.employee.dto.AttendanceCheckOutRequest;
import com.nexushr.employee.dto.AttendanceResponse;
import com.nexushr.employee.service.AttendanceService;
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
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public AttendanceResponse checkIn(@Valid @RequestBody AttendanceCheckInRequest request) {
        return attendanceService.checkIn(request);
    }

    @PostMapping("/{attendanceId}/check-out")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public AttendanceResponse checkOut(@PathVariable Long attendanceId,
                                       @Valid @RequestBody AttendanceCheckOutRequest request) {
        return attendanceService.checkOut(attendanceId, request);
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public List<AttendanceResponse> getAttendanceHistory(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceByEmployee(employeeId);
    }
}
