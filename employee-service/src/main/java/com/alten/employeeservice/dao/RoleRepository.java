package com.alten.employeeservice.dao;

import com.alten.employeeservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
