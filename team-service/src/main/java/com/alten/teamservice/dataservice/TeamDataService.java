package com.alten.teamservice.dataservice;

import com.alten.teamservice.dao.TeamRepository;
import com.alten.teamservice.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TeamDataService implements ITeamDataService {

    @Autowired
    private TeamRepository teamDao;

    @Override
    @Transactional
    public List<Team> findAll() {
        List<Team> teams = teamDao.findAll();

        return teams;
    }

    @Override
    @Transactional
    public Team findById(int teamId) {
        Optional<Team> result = teamDao.findById(teamId);

        return result.get();
    }

    @Override
    @Transactional
    public Team save(Team team) {
        return teamDao.save(team);

    }

    @Override
    @Transactional
    public List<Team> saveAll(List<Team> teams) {
        return teamDao.saveAll(teams);
    }

    @Override
    @Transactional
    public Team update(Team newTeam) {

        Optional<Team> result = teamDao.findById(newTeam.getId());

        if (result.isPresent()) {

            Team oldTeam = result.get();

            // update only if you have the last version
            int oldVersion = oldTeam.getVersion();
            if (oldVersion == newTeam.getVersion()) {


                oldTeam.setVersion(oldVersion + 1);
                oldTeam.setName(newTeam.getName());

                oldTeam.setEmployees(newTeam.getEmployees());

                return teamDao.save(oldTeam);

            } else {

                throw new RuntimeException("You are trying to update an older version of this team, db:" + oldVersion
                        + ", your object: " + newTeam.getVersion());

            }
        } else {
            throw new NullPointerException("Error, team not found in the db");
        }
    }

    @Override
    @Transactional
    public void delete(int teamId) {
        Optional<Team> result = teamDao.findById(teamId);
        if (result.isPresent()) {
            teamDao.deleteById(teamId);
        } else {
            throw new NullPointerException("Error, team not found in the db");
        }

    }


    @Override
    @Transactional
    public void deleteAll() {
        teamDao.deleteAll();
    }

}
