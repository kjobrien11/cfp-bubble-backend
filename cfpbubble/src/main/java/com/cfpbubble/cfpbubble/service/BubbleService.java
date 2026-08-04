package com.cfpbubble.cfpbubble.service;

import com.cfpbubble.cfpbubble.dto.BubbleRequest;
import com.cfpbubble.cfpbubble.dto.BubbleResponse;
import com.cfpbubble.cfpbubble.dto.TeamResponse;
import com.cfpbubble.cfpbubble.entity.BubbleEntity;
import com.cfpbubble.cfpbubble.entity.BubbleTeamEntity;
import com.cfpbubble.cfpbubble.entity.SeasonEntity;
import com.cfpbubble.cfpbubble.entity.TeamEntity;
import com.cfpbubble.cfpbubble.repository.BubbleRepository;
import com.cfpbubble.cfpbubble.repository.BubbleTeamRepository;
import com.cfpbubble.cfpbubble.repository.SeasonRepository;
import com.cfpbubble.cfpbubble.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public String createBubble(BubbleRequest bubbleRequest) {

        SeasonEntity season = seasonRepository.findByYear(2026)
                .orElseThrow(() -> new RuntimeException("Season not found"));

        BubbleEntity bubble = new BubbleEntity();
        bubble.setName(bubbleRequest.name());
        bubble.setEmail(bubbleRequest.email());
        bubble.setSeason(season);
        bubble.setSubmissionTime(LocalDateTime.now());
        bubble.setPublicId(publicIdService.generateBubbleId(season.getYear()));

        int bubbleNumber = bubbleRepository.countByEmailAndSeason_SeasonId(bubbleRequest.email(), season.getSeasonId()) + 1;
        if(bubbleNumber > 5){
            return null;
        }
        bubble.setBubbleNumber(bubbleNumber);

        BubbleEntity saved = bubbleRepository.save(bubble);

        //add teams to bubble

        for (Integer teamId : bubbleRequest.teams()){
            TeamEntity team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("Team not found"));

            bubbleTeamRepository.save(new BubbleTeamEntity(saved, team));
        }

        return bubble.getPublicId();
    }

    public BubbleResponse getBubbleByPublicId(String publicId) {

        BubbleEntity bubble =  bubbleRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Bubble not found"));

        return generateBubbleResponse(bubble);
    }

    public List<BubbleResponse> getBubblesByEmail(String email) {
        List<BubbleEntity> bubbles =  bubbleRepository.findByEmail(email);
        List<BubbleResponse> responses = new ArrayList<>();
        for (BubbleEntity bubble : bubbles) {
            responses.add(generateBubbleResponse(bubble));
        }
        return responses;
        //more code
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
        return new BubbleResponse(
                bubble.getPublicId(),
                bubble.getName(),
                bubble.getSeason().getYear(),
                bubble.getSubmissionTime(),
                teams);
    }
}
