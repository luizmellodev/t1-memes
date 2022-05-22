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
    //DELETE FROM public.category_content
    //	WHERE category_id= and content_id = ;
    @Transactional
    @Modifying
    @Query(value = "DELETE from category_content cc where cc.content_id =:content_id",  nativeQuery = true)
    void deleteCategoryContent(@Param("content_id") Long content_id);
}