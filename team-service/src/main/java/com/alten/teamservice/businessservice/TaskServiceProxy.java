package com.alten.teamservice.businessservice;

import com.alten.teamservice.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "task-service", url = "task-service:8200")
public interface TaskServiceProxy {

    @PostMapping("/tasks/saveAll")
    public List<TaskDto> saveAll(@RequestBody List<TaskDto> tasks);

    @PostMapping("/tasks")
    public TaskDto save(@RequestBody TaskDto task);
}
