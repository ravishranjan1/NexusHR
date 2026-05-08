package com.nexushr.payroll.repository;

import com.nexushr.payroll.model.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Long> {

    List<PayrollRecord> findByEmployeeIdOrderByPayPeriodEndDesc(Long employeeId);

    Optional<PayrollRecord> findByEmployeeIdAndPayPeriodStartAndPayPeriodEnd(
            Long employeeId,
            LocalDate payPeriodStart,
            LocalDate payPeriodEnd
    );
}
