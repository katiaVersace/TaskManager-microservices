package com.alten.employeeservice.businessservice;


import com.alten.employeeservice.dto.EmployeeDto;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IEmployeeBusinessService {

//    public EmployeeDto findByUserName(String userName);

    public List<EmployeeDto> findAll();

    public EmployeeDto findById(int employeeId);

    public EmployeeDto save(EmployeeDto employee);

    public boolean update(EmployeeDto employee);

    public void delete(int employeeId);

    public String getAvailabilityByEmployee(int employeeId, String start, String end);

    public List<EmployeeDto> saveAll(List<? extends EmployeeDto> employees);
}
