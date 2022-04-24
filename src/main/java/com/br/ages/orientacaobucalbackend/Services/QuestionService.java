package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getQuestion(Long id){
        return questionRepository.getById(id);
    }

    public List<Question> getQuestionsWithAlternatives() {
        List<Question> q = questionRepository.findAll();
        return q;
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        } else {
            return null;
        }
    }


    /**
     * Add a new question
     * @param question The question to be added
     */
    public Long addNewQuestion(Question question) {
        // TODO adicionar possíveis validações
        try{
            questionRepository.save(question);
            return question.getId();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Delete a question by id
     * @param questionId The id of the question to be deleted
     */
    public void deleteQuestion(Long questionId) {
        boolean exists = questionRepository.existsById(questionId);

        if(!exists) {
            throw new IllegalStateException("question with id " + questionId + " does not exist");
        }
        questionRepository.deleteById(questionId);
    }

    /**
     * Update a question
     * @param questionId The id of the question to be updated
     * @param questionText The updated text of the question
     */
    @Transactional
    public void updateQuestion(Long questionId, String questionText) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalStateException(
                "question with id " + questionId + " does not exist"));

        // TODO adicionar possíveis validações

        if (questionText != null && questionText.length() > 0) {
            question.setQuestionText(questionText);
        }
    }

}
