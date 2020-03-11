package com.alten.taskservice.dao;

import com.alten.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findByEmployeeId(int employeeId);

}
