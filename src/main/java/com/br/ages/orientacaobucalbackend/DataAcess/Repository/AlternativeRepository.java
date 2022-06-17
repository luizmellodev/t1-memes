package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlternativeRepository extends JpaRepository<Alternative, Long> {
    @Query(value = "SELECT * FROM alternative a JOIN question q ON q.id = a.question_id", nativeQuery = true)
    List<Alternative> findAllAlternativesWithQuestion();

    @Query(value = "SELECT id FROM alternative", nativeQuery = true)
    List<Long> getAllIds();
}
