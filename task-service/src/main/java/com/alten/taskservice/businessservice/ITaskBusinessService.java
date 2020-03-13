package com.alten.taskservice.businessservice;



import com.alten.taskservice.dto.TaskDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITaskBusinessService {

    public List<TaskDto> findAll();

    public TaskDto findById(int taskId);

    public TaskDto save(TaskDto task);

    public boolean update(TaskDto task);

    public void delete(int taskId);

    public List<TaskDto> findByEmployeeId(int employeeId);

    public List<TaskDto> saveAll(List<? extends TaskDto> tasks);
}
