package com.alten.teamservice.controller

import com.alten.springboot.taskmanager.controller.ITeamController
import com.alten.teamservice.businessservice.ITeamBusinessService
import com.alten.teamservice.dto.EmployeeDto
import com.alten.teamservice.dto.RandomPopulationInputDto
import com.alten.teamservice.dto.TaskDto
import com.alten.teamservice.dto.TeamDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
open class TeamController(@Autowired private val teamService: ITeamBusinessService) : ITeamController {

    override fun getTeams(): List<TeamDto?>? {
        return teamService!!.findAll()
    }

    override fun getTeam(teamId: Int): TeamDto? {
        return teamService!!.findById(teamId)
    }

    override fun addTeam(theTeam: TeamDto?): TeamDto? {
        theTeam!!.id = 0
        teamService!!.save(theTeam)
        return theTeam
    }

    override fun updateTeam(theTeam: TeamDto?): TeamDto? {
        teamService!!.update(theTeam)
        return theTeam
    }

    override fun deleteTeam(teamId: Int): String? {
        teamService!!.delete(teamId)
        return "Deleted team with id: $teamId"
    }

    override fun randomPopulation(input: RandomPopulationInputDto?): String? {
        return teamService!!.randomPopulation(input!!.start, input.end, input.teams_size,
                input.employees_size, input.tasks_size, input.task_max_duration)
    }

    override fun assignTaskToTeam(teamId: Int, task: TaskDto?): TaskDto? {
        return teamService!!.tryAssignTaskToTeam(task!!.expectedStartTime, task.expectedEndTime, teamId, task)
    }

    override fun getAvailableEmployeesByTeamAndTask(teamId: Int, theTask: TaskDto?): List<EmployeeDto?>? {
        return teamService!!.getAvailableEmployeesByTeamAndTask(teamId, theTask)
    }
}