package com.cfpbubble.cfpbubble.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "BUBBLE",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_BUBBLE_NUMBER",
                        columnNames = {
                                "SEASON_ID",
                                "EMAIL",
                                "BUBBLE_NUMBER"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BubbleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUBBLE_ID")
    private Long bubbleId;


    @Column(name = "PUBLIC_ID", nullable = false, unique = true)
    private String publicId;


    @ManyToOne
    @JoinColumn(name = "SEASON_ID", nullable = false)
    private SeasonEntity season;


    @Column(name = "NAME", nullable = false)
    private String name;


    @Column(name = "EMAIL", nullable = false)
    private String email;


    @Column(name = "BUBBLE_NUMBER", nullable = false)
    private Integer bubbleNumber;


    @Column(
            name = "SUBMISSION_TIME",
            nullable = false
    )
    private LocalDateTime submissionTime;


    @OneToMany(
            mappedBy = "bubble",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BubbleTeamEntity> teams;
}