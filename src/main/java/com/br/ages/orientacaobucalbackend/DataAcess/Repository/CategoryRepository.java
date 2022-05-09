package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}