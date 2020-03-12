package com.alten.teamservice.businessservice;

import com.alten.teamservice.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "employee-service", url="localhost:8100")
public interface EmployeeServiceProxy {

    @PostMapping("/employees")
    public List<EmployeeDto> saveAll(@RequestBody List<EmployeeDto> employees);
}
