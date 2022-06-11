package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM alternative a WHERE a.question_id =:questionId", nativeQuery = true)
    void deleteQuestionAlternatives(@Param("questionId") Long questionId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM question q WHERE q.id =:questionId", nativeQuery = true)
    void deleteQuestion(@Param("questionId") Long questionId);
}
