package com.cfpbubble.cfpbubble.repository;

import com.cfpbubble.cfpbubble.entity.BubbleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {

    Optional<BubbleEntity> findByPublicId(String publicId);

    Integer countByEmailAndSeason_SeasonId(
            String email,
            Long seasonId
    );

}
