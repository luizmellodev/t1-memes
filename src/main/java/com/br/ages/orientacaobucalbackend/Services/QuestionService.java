package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.Controllers.AlternativeController;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    AlternativeController alternativeController;

    public Question getQuestion(Long id) {
        return questionRepository.getById(id);
    }

    public List<Question> getQuestionsWithAlternatives() {
        List<Question> q = questionRepository.findAll();
        return q;
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            return null;
        }
    }
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
     *
     * @param questionId The id of the question to be deleted
     */
    public void deleteQuestion(Long questionId) {
        boolean exists = questionRepository.existsById(questionId);

        if (!exists) {
            throw new IllegalStateException("question with id " + questionId + " does not exist");
        }
        questionRepository.deleteById(questionId);
    }

    public boolean updateQuestion(Long id, Question newQuestion) {
        Optional<Question> oldQuestion = questionRepository.findById(id);
        if (oldQuestion.isPresent()) {
            Question question = oldQuestion.get();
            question.setQuestionText(newQuestion.getQuestionText());
            for(Alternative alternative : newQuestion.getAlternatives()) {
                alternativeController.updateAlternative(alternative.getId(), alternative); // gambiarra, refatorar
            }
            return true;
        } else {
            throw new NullPointerException("this question doesn't exist.");
        }
    }

}
