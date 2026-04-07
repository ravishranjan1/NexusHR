package com.nexushr.employee.service;

import com.nexushr.employee.dto.AttendanceCheckInRequest;
import com.nexushr.employee.dto.AttendanceCheckOutRequest;
import com.nexushr.employee.dto.AttendanceResponse;
import com.nexushr.employee.model.AttendanceRecord;
import com.nexushr.employee.model.Employee;
import com.nexushr.employee.repository.AttendanceRecordRepository;
import com.nexushr.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository,
                             EmployeeRepository employeeRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    public AttendanceResponse checkIn(AttendanceCheckInRequest request) {
        LocalDate attendanceDate = request.attendanceDate() != null ? request.attendanceDate() : LocalDate.now();

        attendanceRecordRepository.findByEmployeeIdAndAttendanceDate(request.employeeId(), attendanceDate)
                .ifPresent(record -> {
                    throw new IllegalArgumentException("Attendance already marked for this employee and date.");
                });

        Employee employee = findEmployee(request.employeeId());

        AttendanceRecord record = new AttendanceRecord();
        record.setEmployee(employee);
        record.setAttendanceDate(attendanceDate);
        record.setCheckInTime(request.checkInTime() != null ? request.checkInTime() : Instant.now());
        record.setStatus(request.status());
        record.setNotes(request.notes());

        return toResponse(attendanceRecordRepository.save(record));
    }

    public AttendanceResponse checkOut(Long attendanceId, AttendanceCheckOutRequest request) {
        AttendanceRecord record = attendanceRecordRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found."));

        record.setCheckOutTime(request.checkOutTime() != null ? request.checkOutTime() : Instant.now());
        if (request.notes() != null && !request.notes().isBlank()) {
            record.setNotes(request.notes());
        }

        return toResponse(attendanceRecordRepository.save(record));
    }

    public List<AttendanceResponse> getAttendanceByEmployee(Long employeeId) {
        findEmployee(employeeId);
        return attendanceRecordRepository.findByEmployeeIdOrderByAttendanceDateDesc(employeeId).stream()
                .map(this::toResponse)
                .toList();
    }

    private Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
    }

    private AttendanceResponse toResponse(AttendanceRecord record) {
        Employee employee = record.getEmployee();
        return new AttendanceResponse(
                record.getId(),
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName() + " " + employee.getLastName(),
                record.getAttendanceDate(),
                record.getCheckInTime(),
                record.getCheckOutTime(),
                record.getStatus(),
                record.getNotes(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );
    }
}
