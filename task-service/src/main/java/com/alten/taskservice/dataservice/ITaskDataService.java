package com.alten.taskservice.dataservice;



import com.alten.taskservice.model.Task;

import java.util.List;

public interface ITaskDataService {

    public List<Task> findAll();

    public Task findById(int taskId);

    public Task save(Task task);

    public List<Task> saveAll(List<Task> tasks);

    public Task update(Task task);

    public void delete(int taskId);

    public List<Task> findByEmployeeId(int employeeId);

    public void deleteAll();


}
