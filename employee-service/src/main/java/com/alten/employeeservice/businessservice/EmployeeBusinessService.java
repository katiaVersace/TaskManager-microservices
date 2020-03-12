package com.alten.employeeservice.businessservice;

import com.alten.employeeservice.dataservice.IEmployeeDataService;
import com.alten.employeeservice.dto.EmployeeDto;
import com.alten.employeeservice.model.Employee;
import com.alten.employeeservice.model.Task;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class EmployeeBusinessService implements IEmployeeBusinessService {

    @Autowired
    private IEmployeeDataService employeeDataService;

//    @Autowired
//    private ITeamDataService teamDataService;

    @Autowired
    private ModelMapper modelMapper;

    public static String printEmployeeScheduling(Employee employee, long schedule_size, LocalDate start, LocalDate end) {

        String[] schedule = new String[(int) schedule_size];
        Arrays.fill(schedule, "__");

        employee.getTasks().stream().forEach(task -> {
            LocalDate taskStartDate = task.getExpectedStartTime(), taskEndDate = task.getExpectedEndTime();
            // check on task start and end because a task can end over the end of the period specified or start before
            taskStartDate = (taskStartDate.isBefore(start)) ? start : taskStartDate;
            taskEndDate = (taskEndDate.isAfter(end)) ? end : taskEndDate;
            long taskStart = ChronoUnit.DAYS.between(start, taskStartDate), taskDuration = ChronoUnit.DAYS.between(taskStartDate, taskEndDate) + 1;
            String stringToPrint = task.getDescription();
            stringToPrint = (stringToPrint.length() < 2) ? "0" + stringToPrint : stringToPrint;
            String finalStringToPrint = stringToPrint;
            IntStream.range((int) taskStart, (int) (taskStart + taskDuration))
                    .forEach(i -> schedule[i] = finalStringToPrint);
        });

        StringBuilder sb = new StringBuilder();
        Stream.of(schedule).forEach(availability -> sb.append(availability + "  "));
        sb.append(employee.getUserName());
        return sb.toString();

    }

    public static boolean betweenTwoDate(LocalDate toCheck, LocalDate start, LocalDate end) {

        return (toCheck.isAfter(start) && toCheck.isBefore(end)) || toCheck.equals(start)
                || toCheck.equals(end);
    }

    static boolean employeeAvailable(Employee e, LocalDate startTask, LocalDate endTask) {

        Optional<Task> result = e.getTasks().stream().filter(t -> betweenTwoDate(startTask, t.getExpectedStartTime(), t.getExpectedEndTime())
                || betweenTwoDate(endTask, t.getExpectedStartTime(), t.getExpectedEndTime())
                || betweenTwoDate(t.getExpectedStartTime(), startTask, endTask)
                || betweenTwoDate(t.getExpectedEndTime(), startTask, endTask))
                .findFirst();

        return !result.isPresent();

    }

//    @Override
//    public EmployeeDto findByUserName(String userName) {
//
//        Employee employee = employeeDataService.findByUserName(userName);
//        return (employee != null) ? modelMapper.map(employee, EmployeeDto.class) : null;
//    }

    @Override
    public List<EmployeeDto> findAll() {

        return employeeDataService.findAll().stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(int employeeId) {

        Employee employee = employeeDataService.findById(employeeId);
        return (employee != null) ? modelMapper.map(employee, EmployeeDto.class) : null;

    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setPassword("{noop}" + employee.getPassword());
        return modelMapper.map(employeeDataService.save(employee), EmployeeDto.class);
    }

    @Override
    public boolean update(EmployeeDto employeeDto) {

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return (employeeDataService.update(employee) != null) ? true : false;
    }

    @Override
    public void delete(int employeeId) {

        Employee employee = employeeDataService.findById(employeeId);
        employee.getTasks().parallelStream().forEach(t -> t.setEmployee(null));
        employee.getTeams().parallelStream().forEach(t -> t.getEmployees().remove(employee));
        employeeDataService.delete(employeeId);
    }



    @Override
    public String getAvailabilityByEmployee(int employeeId, String start_date, String end_date) {

        Employee employee = employeeDataService.findById(employeeId);
        LocalDate start = LocalDate.parse(start_date), end = LocalDate.parse(end_date);
        return printEmployeeScheduling(employee, ChronoUnit.DAYS.between(start, end) + 1, start, end);

    }

    @Override
    public List<EmployeeDto> saveAll(List<? extends EmployeeDto> employeesDto) {
        List<Employee> employees = modelMapper.map(employeesDto,new TypeToken<List<Employee>>(){}.getType());
        employees.parallelStream().forEach(e->
        {
            if(!e.getPassword().startsWith("{noop}"))
            e.setPassword("{noop}" + e.getPassword());
        });
        List<EmployeeDto> empDtoList = modelMapper.map(employeeDataService.saveAll(employees),new TypeToken<List<EmployeeDto>>(){}.getType());
        return empDtoList;
    }

}
