package com.alten.teamservice.dataservice;


import com.alten.teamservice.model.Team;

import java.util.List;

public interface ITeamDataService {

    public List<Team> findAll();

    public Team findById(int teamId);

    public Team save(Team team);

    public List<Team> saveAll(List<Team> teams);

    public Team update(Team team);

    public void delete(int teamId);

    public void deleteAll();
}
