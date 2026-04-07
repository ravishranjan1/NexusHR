package com.nexushr.employee.repository;

import com.nexushr.employee.model.AttendanceRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    @EntityGraph(attributePaths = "employee")
    List<AttendanceRecord> findByEmployeeIdOrderByAttendanceDateDesc(Long employeeId);

    @EntityGraph(attributePaths = "employee")
    Optional<AttendanceRecord> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
}
