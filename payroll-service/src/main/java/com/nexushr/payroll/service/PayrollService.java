package com.nexushr.payroll.service;

import com.nexushr.payroll.dto.GeneratePayrollRequest;
import com.nexushr.payroll.dto.PayrollResponse;
import com.nexushr.payroll.exception.ResourceNotFoundException;
import com.nexushr.payroll.model.PayrollRecord;
import com.nexushr.payroll.model.PayrollStatus;
import com.nexushr.payroll.repository.PayrollRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PayrollService {

    private final PayrollRecordRepository payrollRecordRepository;

    public PayrollService(PayrollRecordRepository payrollRecordRepository) {
        this.payrollRecordRepository = payrollRecordRepository;
    }

    public PayrollResponse generatePayslip(GeneratePayrollRequest request) {
        if (request.payPeriodEnd().isBefore(request.payPeriodStart())) {
            throw new IllegalArgumentException("Pay period end date cannot be before the start date.");
        }

        payrollRecordRepository.findByEmployeeIdAndPayPeriodStartAndPayPeriodEnd(
                request.employeeId(),
                request.payPeriodStart(),
                request.payPeriodEnd()
        ).ifPresent(record -> {
            throw new IllegalArgumentException("Payroll already generated for this employee and pay period.");
        });

        BigDecimal grossSalary = amount(request.basicSalary())
                .add(amount(request.hra()))
                .add(amount(request.allowances()))
                .add(amount(request.bonus()));

        BigDecimal totalDeductions = amount(request.taxDeduction())
                .add(amount(request.providentFundDeduction()))
                .add(amount(request.otherDeductions()));

        BigDecimal netSalary = grossSalary.subtract(totalDeductions).setScale(2, RoundingMode.HALF_UP);

        PayrollRecord payrollRecord = new PayrollRecord();
        payrollRecord.setEmployeeId(request.employeeId());
        payrollRecord.setEmployeeCode(request.employeeCode().trim());
        payrollRecord.setEmployeeName(request.employeeName().trim());
        payrollRecord.setPayPeriodStart(request.payPeriodStart());
        payrollRecord.setPayPeriodEnd(request.payPeriodEnd());
        payrollRecord.setBasicSalary(amount(request.basicSalary()));
        payrollRecord.setHra(amount(request.hra()));
        payrollRecord.setAllowances(amount(request.allowances()));
        payrollRecord.setBonus(amount(request.bonus()));
        payrollRecord.setTaxDeduction(amount(request.taxDeduction()));
        payrollRecord.setProvidentFundDeduction(amount(request.providentFundDeduction()));
        payrollRecord.setOtherDeductions(amount(request.otherDeductions()));
        payrollRecord.setGrossSalary(grossSalary.setScale(2, RoundingMode.HALF_UP));
        payrollRecord.setTotalDeductions(totalDeductions.setScale(2, RoundingMode.HALF_UP));
        payrollRecord.setNetSalary(netSalary);
        payrollRecord.setStatus(PayrollStatus.GENERATED);

        return toResponse(payrollRecordRepository.save(payrollRecord));
    }

    public PayrollResponse getPayslip(Long id) {
        return toResponse(payrollRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payslip not found for id: " + id)));
    }

    public List<PayrollResponse> getPayslipsByEmployee(Long employeeId) {
        return payrollRecordRepository.findByEmployeeIdOrderByPayPeriodEndDesc(employeeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PayrollResponse toResponse(PayrollRecord payrollRecord) {
        return new PayrollResponse(
                payrollRecord.getId(),
                payrollRecord.getEmployeeId(),
                payrollRecord.getEmployeeCode(),
                payrollRecord.getEmployeeName(),
                payrollRecord.getPayPeriodStart(),
                payrollRecord.getPayPeriodEnd(),
                payrollRecord.getBasicSalary(),
                payrollRecord.getHra(),
                payrollRecord.getAllowances(),
                payrollRecord.getBonus(),
                payrollRecord.getGrossSalary(),
                payrollRecord.getTaxDeduction(),
                payrollRecord.getProvidentFundDeduction(),
                payrollRecord.getOtherDeductions(),
                payrollRecord.getTotalDeductions(),
                payrollRecord.getNetSalary(),
                payrollRecord.getStatus(),
                payrollRecord.getGeneratedAt(),
                payrollRecord.getUpdatedAt()
        );
    }

    private BigDecimal amount(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
