package com.cfpbubble.cfpbubble.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BubbleTeamId implements Serializable {

    private Long bubbleId;

    private Integer espnId;
}