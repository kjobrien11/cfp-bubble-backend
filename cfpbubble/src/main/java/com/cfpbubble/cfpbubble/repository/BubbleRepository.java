package com.cfpbubble.cfpbubble.repository;

import com.cfpbubble.cfpbubble.entity.BubbleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {

    Optional<BubbleEntity> findByPublicId(String publicId);

    Integer countByEmailAndSeason_SeasonId(String email, Long seasonId);

    List<BubbleEntity> findByEmail(String email);

    @Query("SELECT COUNT(DISTINCT b.email) FROM BubbleEntity b")
    long countUniqueUsers();
}
