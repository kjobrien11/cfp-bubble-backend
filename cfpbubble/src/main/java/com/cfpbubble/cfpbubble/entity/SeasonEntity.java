package com.cfpbubble.cfpbubble.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SEASON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEASON_ID")
    private Long seasonId;

    @Column(name = "YEAR", nullable = false, unique = true)
    private Integer year;

    @Column(name = "SUBMISSION_OPEN", nullable = false)
    private LocalDateTime submissionOpen;

    @Column(name = "SUBMISSION_CLOSE", nullable = false)
    private LocalDateTime submissionClose;

    @ManyToOne
    @JoinColumn(name = "CHAMPION_TEAM_ID")
    private TeamEntity championTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private SeasonStatus status = SeasonStatus.OPEN;


    public enum SeasonStatus {
        OPEN,
        LOCKED,
        COMPLETE
    }
}