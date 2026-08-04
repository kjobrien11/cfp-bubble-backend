package com.cfpbubble.cfpbubble.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUBBLE_TEAM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BubbleTeamEntity {

    @EmbeddedId
    private BubbleTeamId id;


    @ManyToOne
    @MapsId("bubbleId")
    @JoinColumn(name = "BUBBLE_ID")
    private BubbleEntity bubble;


    @ManyToOne
    @MapsId("espnId")
    @JoinColumn(name = "ESPN_ID")
    private TeamEntity team;

    public BubbleTeamEntity(BubbleEntity bubble, TeamEntity team) {
        this.bubble = bubble;
        this.team = team;

        this.id = new BubbleTeamId(
                bubble.getBubbleId(),
                team.getEspnId()
        );
    }
}