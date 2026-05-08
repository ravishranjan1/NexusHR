package com.nexushr.payroll.controller;

import com.nexushr.payroll.dto.GeneratePayrollRequest;
import com.nexushr.payroll.dto.PayrollResponse;
import com.nexushr.payroll.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payroll/payslips")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public PayrollResponse generatePayslip(@Valid @RequestBody GeneratePayrollRequest request) {
        return payrollService.generatePayslip(request);
    }

    @GetMapping("/{id}")
    public PayrollResponse getPayslip(@PathVariable Long id) {
        return payrollService.getPayslip(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PayrollResponse> getEmployeePayslips(@PathVariable Long employeeId) {
        return payrollService.getPayslipsByEmployee(employeeId);
    }
}
