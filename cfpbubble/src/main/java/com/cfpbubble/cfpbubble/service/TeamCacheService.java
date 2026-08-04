package com.cfpbubble.cfpbubble.service;

import com.cfpbubble.cfpbubble.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TeamCacheService {

    private final Set<Integer> teamIds = new HashSet<>();

    public TeamCacheService(TeamRepository teamRepository) {
        teamRepository.findAll()
                .forEach(team -> teamIds.add(team.getEspnId()));
    }

    public boolean exists(Integer espnId) {
        return teamIds.contains(espnId);
    }
}
