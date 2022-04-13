package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Get a list of all of the questions
     * @return
     */
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsWithAlternatives() {
        return questionRepository.findAllQuestionsWithAlternatives();
    }

    /**
     * Add a new question
     * @param question The question to be added
     */
    public void addNewQuestion(Question question) {
        // TODO adicionar possíveis validações

        questionRepository.save(question);
    }

    /**
     * Delete a question by id
     * @param questionId The id of the question to be deleted
     */
    public void deleteStudent(Long questionId) {
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
