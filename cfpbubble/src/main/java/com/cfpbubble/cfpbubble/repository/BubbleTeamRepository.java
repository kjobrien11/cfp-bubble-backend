package com.cfpbubble.cfpbubble.repository;

import com.cfpbubble.cfpbubble.entity.BubbleTeamEntity;
import com.cfpbubble.cfpbubble.entity.BubbleTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BubbleTeamRepository extends JpaRepository<BubbleTeamEntity, BubbleTeamId> {

}
