package com.nexushr.payroll.dto;

import com.nexushr.payroll.model.PayrollStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PayrollResponse(
        Long id,
        Long employeeId,
        String employeeCode,
        String employeeName,
        LocalDate payPeriodStart,
        LocalDate payPeriodEnd,
        BigDecimal basicSalary,
        BigDecimal hra,
        BigDecimal allowances,
        BigDecimal bonus,
        BigDecimal grossSalary,
        BigDecimal taxDeduction,
        BigDecimal providentFundDeduction,
        BigDecimal otherDeductions,
        BigDecimal totalDeductions,
        BigDecimal netSalary,
        PayrollStatus status,
        Instant generatedAt,
        Instant updatedAt
) {
}
