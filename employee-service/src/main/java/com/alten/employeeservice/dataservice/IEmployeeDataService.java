package com.alten.employeeservice.dataservice;

import com.alten.employeeservice.model.Employee;

import java.util.List;


public interface IEmployeeDataService //extends UserDetailsService
{

   // public Employee findByUserName(String userName);

    public List<Employee> findAll();

    public Employee findById(int employeeId);

    public Employee save(Employee employee);

    public List<Employee> saveAll(List<Employee> employees);

    public Employee update(Employee employee);

    public void delete(int employeeId);

}
