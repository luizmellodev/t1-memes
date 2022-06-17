package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedSourceRepository extends JpaRepository<RecommendedSource, Long> {
    @Query(value = "SELECT * FROM recommended_source rs JOIN content c ON c.id = rs.content_id WHERE rs.content_id=:contentId", nativeQuery = true)
    List<RecommendedSource> findAllRecommendedSourcesByContentId(@Param("contentId") Long contentId);

    @Query(value = "SELECT id FROM recommended_source", nativeQuery = true)
    List<Long> getAllIds();
}
