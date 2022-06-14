package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedSourceRepository extends JpaRepository<RecommendedSource, Long> {
    @Query(value = "SELECT * FROM recommended_source JOIN content ON content.id = recommendedSource.content_id", nativeQuery = true)
    List<RecommendedSource> findAllRecommendedSources(@Param("content_id") Long content_id);
}
