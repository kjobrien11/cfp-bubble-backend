package com.cfpbubble.cfpbubble.service;

import com.cfpbubble.cfpbubble.dto.BubbleCreationReponse;
import com.cfpbubble.cfpbubble.dto.BubbleRequest;
import com.cfpbubble.cfpbubble.dto.BubbleResponse;
import com.cfpbubble.cfpbubble.dto.TeamResponse;
import com.cfpbubble.cfpbubble.entity.BubbleEntity;
import com.cfpbubble.cfpbubble.entity.BubbleTeamEntity;
import com.cfpbubble.cfpbubble.entity.SeasonEntity;
import com.cfpbubble.cfpbubble.entity.TeamEntity;
import com.cfpbubble.cfpbubble.exception.BubbleNotFoundException;
import com.cfpbubble.cfpbubble.exception.MaxBubblesException;
import com.cfpbubble.cfpbubble.exception.SeasonStartedException;
import com.cfpbubble.cfpbubble.repository.BubbleRepository;
import com.cfpbubble.cfpbubble.repository.BubbleTeamRepository;
import com.cfpbubble.cfpbubble.repository.SeasonRepository;
import com.cfpbubble.cfpbubble.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BubbleService {

    @Autowired
    private BubbleRepository bubbleRepository;

    @Autowired
    private BubbleTeamRepository bubbleTeamRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private PublicIdService publicIdService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamCacheService teamCacheService;

    public BubbleCreationReponse createBubble(BubbleRequest bubbleRequest) {
        SeasonEntity season = seasonRepository.findByYear(2026)
                .orElseThrow(() -> new RuntimeException("Season not found"));

        if (LocalDateTime.now().isAfter(season.getSubmissionClose())) {
            throw new SeasonStartedException();
        }

        BubbleEntity bubble = new BubbleEntity();
        bubble.setName(bubbleRequest.name());
        bubble.setEmail(bubbleRequest.email());
        bubble.setSeason(season);
        bubble.setSubmissionTime(LocalDateTime.now());
        bubble.setPublicId(publicIdService.generateBubbleId(season.getYear()));

        int bubbleNumber = bubbleRepository.countByEmailAndSeason_SeasonId(bubbleRequest.email(), season.getSeasonId()) + 1;
        if(bubbleNumber > 3){
            throw new MaxBubblesException();
        }
        bubble.setBubbleNumber(bubbleNumber);

        BubbleEntity saved = bubbleRepository.save(bubble);

        Set<Integer> teamIds = new HashSet<>();
        for (Integer teamId : bubbleRequest.teams()){
            if(teamCacheService.exists(teamId) && teamIds.add(teamId)){
                TeamEntity team = teamRepository.findById(teamId)
                        .orElseThrow(() -> new RuntimeException("Team not found"));
                bubbleTeamRepository.save(new BubbleTeamEntity(saved, team));
            }

        }

        return new BubbleCreationReponse(bubble.getPublicId());
    }

    public BubbleResponse getBubbleByPublicId(String publicId) {

        BubbleEntity bubble =  bubbleRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BubbleNotFoundException("Bubble with ID: " + publicId + " not found"));

        return generateBubbleResponse(bubble);
    }

    public List<BubbleResponse> getAllBubbles() {
        List<BubbleEntity> bubbles = bubbleRepository.findAll();
        return getBubbleResponses(bubbles);
    }

    public List<BubbleResponse> getBubblesByEmail(String email) {
        List<BubbleEntity> bubbles =  bubbleRepository.findByEmail(email);
        return getBubbleResponses(bubbles);
    }

    public long getUniqueUsers() {
        return bubbleRepository.countUniqueUsers();
    }

    private List<BubbleResponse> getBubbleResponses(List<BubbleEntity> bubbles){
        List<BubbleResponse> responses = new ArrayList<>();
        for (BubbleEntity bubble : bubbles) {
            responses.add(generateBubbleResponse(bubble));
        }
        return responses;
    }

    private BubbleResponse generateBubbleResponse(BubbleEntity bubble){
        List<TeamResponse> teams = new ArrayList<>();

        for(BubbleTeamEntity team : bubble.getTeams()){
            TeamEntity t = team.getTeam();
            teams.add(new TeamResponse(t.getEspnId(),
                    t.getSchoolName(),
                    t.getAbbreviation(),
                    t.getConferenceName(),
                    "https://a.espncdn.com/i/teamlogos/ncaa/500/"
                            + t.getEspnId()
                            + ".png"
            ));
        }
        int wins = ThreadLocalRandom.current().nextInt(0, 13) * teams.size();
        return new BubbleResponse(
                bubble.getPublicId(),
                bubble.getName(),
                bubble.getSeason().getYear(),
                bubble.getSubmissionTime(),
                teams,
                wins, 12*teams.size()-wins);
    }


}
