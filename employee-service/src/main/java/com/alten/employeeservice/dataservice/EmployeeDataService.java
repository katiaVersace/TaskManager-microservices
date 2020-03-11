package com.alten.employeeservice.dataservice;

import com.alten.employeeservice.dao.EmployeeRepository;
import com.alten.employeeservice.dao.RoleRepository;
import com.alten.employeeservice.model.Employee;
import com.alten.employeeservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeDataService implements IEmployeeDataService {

    @Autowired
    private EmployeeRepository employeeDao;

    @Autowired
    private RoleRepository roleDao;

//    @Override
//    @Transactional
//    public Employee findByUserName(String userName) {
//
//        Employee employee = employeeDao.findByUserName(userName);
//        return employee;
//
//    }

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        Employee employee = employeeDao.findByUserName(userName);
//        if (employee == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new PrincipalUser(employee.getUserName(), employee.getPassword(), true, true, true, true,
//                mapRolesToAuthorities(employee.getRoles()), employee.getId());
//
//    }
//
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        List<Employee> employees = employeeDao.findAll();

        return employees;
    }

    @Override
    @Transactional
    public Employee findById(int employeeId) {
        Optional<Employee> result = employeeDao.findById(employeeId);

        return result.get();

    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        List<Role> roles = employee.getRoles().stream().map(r -> roleDao.findById(r.getId()).get()).collect(Collectors.toList());
        employee.setRoles(roles);

        return employeeDao.save(employee);

    }

    @Override
    @Transactional
    public List<Employee> saveAll(List<Employee> employees) {
        return employeeDao.saveAll(employees);
    }

    @Override
    @Transactional
    public Employee update(Employee newEmployee) {
        Optional<Employee> result = employeeDao.findById(newEmployee.getId());

        if (result.isPresent()) {
            Employee oldEmployee = result.get();

            // update only if you have the last version
            int oldVersion = oldEmployee.getVersion();
            if (oldVersion == newEmployee.getVersion()) {
                oldEmployee.setVersion(oldVersion + 1);
                oldEmployee.setUserName(newEmployee.getUserName());
                oldEmployee.setPassword(newEmployee.getPassword());
                oldEmployee.setFirstName(newEmployee.getFirstName());
                oldEmployee.setLastName(newEmployee.getLastName());
                oldEmployee.setEmail(newEmployee.getEmail());
                oldEmployee.setTopEmployee(newEmployee.isTopEmployee());

                oldEmployee.setRoles(newEmployee.getRoles());
                oldEmployee.setTasks(newEmployee.getTasks());


                return employeeDao.save(oldEmployee);

            } else {

                throw new RuntimeException("You are trying to update an older version of this employee (" + newEmployee.getUserName() + "), db:"
                        + oldVersion + ", your object: " + newEmployee.getVersion());

            }
        } else {
            throw new NullPointerException("Error, employee not found in the db");
        }
    }

    @Override
    @Transactional
    public void delete(int employeeId) {

        employeeDao.deleteById(employeeId);

    }

}
