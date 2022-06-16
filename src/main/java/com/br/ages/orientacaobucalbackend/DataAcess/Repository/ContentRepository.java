package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM category_content cc WHERE cc.content_id =:contentId", nativeQuery = true)
    void deleteCategoryContentByContentId(@Param("contentId") Long contentId);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE category_content", nativeQuery = true)
    void deleteAllCategoryContent();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM content c WHERE c.id =:contentId", nativeQuery = true)
    void deleteContent(@Param("contentId") Long contentId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM recommended_source r WHERE r.content_id =:contentId", nativeQuery = true)
    void deleteAllContentRecommendedSource(@Param("contentId") Long contentId);
}
