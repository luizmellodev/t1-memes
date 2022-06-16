package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT id FROM category", nativeQuery = true)
    List<Long> getAllIds();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM category_content cc WHERE cc.category_id =:categoryId", nativeQuery = true)
    void deleteCategoryContentByCategoryId(@Param("categoryId") Long categoryId);
}
