package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlternativeRepository extends JpaRepository<Alternative, Long> {
    @Query(value = "SELECT * FROM Alternative JOIN Question ON Question.id = Alternative.question_id", nativeQuery = true)
    List<Alternative> findAllAlternativesWithQuestion();
}
