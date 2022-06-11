package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlternativeRepository extends JpaRepository<Alternative, Long> {
    @Query(value = "SELECT * FROM Alternative JOIN Question ON Question.id = Alternative.question_id", nativeQuery = true)
    List<Alternative> findAllAlternativesWithQuestion();
}
