package com.cfpbubble.cfpbubble.service;

import com.cfpbubble.cfpbubble.entity.TeamEntity;
import com.cfpbubble.cfpbubble.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsService {

    @Autowired
    TeamRepository teamRepository;

    public List<TeamEntity> getAllTeams(){
        return teamRepository.findAll();
    }
}
