package com.alten.taskservice.businessservice;


import com.alten.taskservice.dataservice.ITaskDataService;
import com.alten.taskservice.dto.EmployeeDto;
import com.alten.taskservice.dto.TaskDto;
import com.alten.taskservice.model.Employee;
import com.alten.taskservice.model.Task;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskBusinessService implements ITaskBusinessService {

    @Autowired
    private ITaskDataService taskDataService;

    @Autowired
    private EmployeeServiceProxy employeeServiceProxy;

    @Autowired
    private ModelMapper modelMapper;

    public static boolean checkDate(Task theTask) {
        LocalDate today = LocalDate.now();
        //se exTaskStart è < oggi oppure exTaskStart > exTaskEnd oppure ((realTaskStart!= null o realTaskEnd !=null) && (realTaskStart<oggi oppure realTaskStart>realTaskEnd)) le date non sono valide
        return !(theTask.getExpectedStartTime().isBefore(today) || theTask.getExpectedStartTime().isAfter(theTask.getExpectedEndTime()) ||
                ((theTask.getRealStartTime() != null || theTask.getRealEndTime() != null) && (theTask.getRealStartTime().isBefore(today) || theTask.getRealStartTime().isAfter(theTask.getRealEndTime()))));
    }

    @Override
    public TaskDto findById(int taskId) {
        Task task = taskDataService.findById(taskId);
        return (task != null) ? modelMapper.map(task, TaskDto.class) : null;
    }

    @Override
    public TaskDto save(TaskDto taskDto) {

        Task task = modelMapper.map(taskDto, Task.class);
        if (!checkDate(task))
            return null;

        EmployeeDto employee = employeeServiceProxy.findById(taskDto.getEmployeeId());

        List<Task> tasksOfEmployee = taskDataService.findByEmployeeId(employee.getId());
        taskDto = null;
        if (employeeAvailable(tasksOfEmployee, task.getExpectedStartTime(), task.getExpectedEndTime())) {
            assignTaskToEmployee(task, employee, tasksOfEmployee);
            taskDto = modelMapper.map(taskDataService.save(task), TaskDto.class);

        }
System.out.println("Prova");
        return taskDto;
    }

    @Override
    public boolean update(TaskDto taskDto) {

        Task task = modelMapper.map(taskDto, Task.class);
        if (!checkDate(task)) {
            return false;
        }
        Task result = taskDataService.update(task);
        return result != null;
    }

    @Override
    public void delete(int taskId) {

        Task task = taskDataService.findById(taskId);
        List<Task> tasks = taskDataService.findByEmployeeId(task.getEmployee().getId());

        Task taskToDelete = tasks.stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
        if (taskToDelete != null) {

            assignTaskToEmployee(taskToDelete, null, tasks);

            taskDataService.delete(taskId);

        }
    }

    @Override
    public List<TaskDto> findAll() {
        return taskDataService.findAll().stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findByEmployeeId(int employeeId) {
        return taskDataService.findByEmployeeId(employeeId).stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> saveAll(List<? extends TaskDto> tasksDto) {
        List<TaskDto> saved = tasksDto.parallelStream().map(t->save(t)).collect(Collectors.toList());
//        List<Task> tasks = modelMapper.map(tasksDto, new TypeToken<List<Task>>() {
//        }.getType());
//        List<TaskDto> taskDtoList = modelMapper.map(taskDataService.saveAll(tasks), new TypeToken<List<TaskDto>>() {
//        }.getType());
//        return taskDtoList;
        return saved;
    }

    private void assignTaskToEmployee(Task task, EmployeeDto employee, List<Task> tasks) {
        if (task.getEmployee() != null) {
            task.getEmployee().getTasks().remove(task);
            if (task.getEmployee().getTasks().size() < 5 && task.getEmployee().isTopEmployee()) {
                task.getEmployee().setTopEmployee(false);
                employeeServiceProxy.update(modelMapper.map(task.getEmployee(), EmployeeDto.class));
            }
        }
        if (employee != null) {
            tasks.add(task);
            if (tasks.size() >= 5 && !employee.isTopEmployee()) {
                employee.setTopEmployee(true);
                employeeServiceProxy.update(employee);
            }
            task.setEmployee(modelMapper.map(employee, Employee.class));
        } else task.setEmployee(null);

    }

    private boolean employeeAvailable(List<Task> tasksOfEmployee, LocalDate startTask, LocalDate endTask) {

        Optional<Task> result = tasksOfEmployee.stream().filter(t -> betweenTwoDate(startTask, t.getExpectedStartTime(), t.getExpectedEndTime())
                || betweenTwoDate(endTask, t.getExpectedStartTime(), t.getExpectedEndTime())
                || betweenTwoDate(t.getExpectedStartTime(), startTask, endTask)
                || betweenTwoDate(t.getExpectedEndTime(), startTask, endTask))
                .findFirst();

        return !result.isPresent();

    }

    public boolean betweenTwoDate(LocalDate toCheck, LocalDate start, LocalDate end) {

        return (toCheck.isAfter(start) && toCheck.isBefore(end)) || toCheck.equals(start)
                || toCheck.equals(end);
    }

}
