package com.nexushr.payroll.controller;

import com.nexushr.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payroll")
public class PayrollHealthController {

    @GetMapping("/health")
    public ApiResponse health() {
        return new ApiResponse("payroll-service", "Payroll service base module is running.");
    }
}
