package com.cfpbubble.cfpbubble.controller;

import com.cfpbubble.cfpbubble.entity.TeamEntity;
import com.cfpbubble.cfpbubble.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamsController {

    @Autowired
    TeamsService teamsService;

    @GetMapping
    public List<TeamEntity> getTeams() {
        return teamsService.getAllTeams();
    }
}
