package com.alten.taskservice.businessservice;

import com.alten.taskservice.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "employee-service", url="employee-service:8100")
public interface EmployeeServiceProxy {

    @GetMapping("/employees/{employeeId}")
    public EmployeeDto findById(@PathVariable("employeeId") int employeeId);

    @PutMapping("/employees")
    public EmployeeDto update(@RequestBody EmployeeDto employee);
}
