package com.alten.teamservice.businessservice;


import com.alten.teamservice.dto.EmployeeDto;
import com.alten.teamservice.dto.TaskDto;
import com.alten.teamservice.dto.TeamDto;

import java.util.List;

public interface ITeamBusinessService {

    public List<TeamDto> findAll();

    public TeamDto findById(int teamId);

    public TeamDto save(TeamDto team);

    public boolean update(TeamDto team);

    public void delete(int teamId);

    public String randomPopulation(String start, String end, int teams_size, int employees_size, int tasks_size,
                                   int task_max_duration);

    public TaskDto tryAssignTaskToTeam(String start, String end, int team_id, TaskDto theTask);

    public void deleteAll();

    public List<EmployeeDto> getAvailableEmployeesByTeamAndTask(int teamId, TaskDto theTask);
}
