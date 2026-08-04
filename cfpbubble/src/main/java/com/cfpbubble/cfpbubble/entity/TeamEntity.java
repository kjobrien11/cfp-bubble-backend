package com.cfpbubble.cfpbubble.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TEAM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    @Id
    @Column(name = "ESPN_ID")
    private Integer espnId;

    @Column(name = "SCHOOL_NAME", nullable = false)
    private String schoolName;

    @Column(name = "ABBREVIATION", nullable = false)
    private String abbreviation;

    @Column(name = "CONFERENCE_NAME", nullable = false)
    private String conferenceName;
}