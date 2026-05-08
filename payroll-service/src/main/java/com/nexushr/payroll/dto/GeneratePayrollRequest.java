package com.nexushr.payroll.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GeneratePayrollRequest(
        @NotNull Long employeeId,
        @NotBlank String employeeCode,
        @NotBlank String employeeName,
        @NotNull LocalDate payPeriodStart,
        @NotNull LocalDate payPeriodEnd,
        @NotNull @DecimalMin("0.0") BigDecimal basicSalary,
        @NotNull @DecimalMin("0.0") BigDecimal hra,
        @NotNull @DecimalMin("0.0") BigDecimal allowances,
        @NotNull @DecimalMin("0.0") BigDecimal bonus,
        @NotNull @DecimalMin("0.0") BigDecimal taxDeduction,
        @NotNull @DecimalMin("0.0") BigDecimal providentFundDeduction,
        @NotNull @DecimalMin("0.0") BigDecimal otherDeductions
) {
}
