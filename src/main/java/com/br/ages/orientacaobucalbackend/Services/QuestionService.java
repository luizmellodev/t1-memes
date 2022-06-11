package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.Controllers.AlternativeController;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    AlternativeRepository alternativeRepository;

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
    public Question addNewQuestion(Question question) {
        try{
            questionRepository.save(question);
            for(Alternative alternative : question.getAlternatives()){
                alternativeController.registerNewAlternative(question.getId(), alternative);
            }
            return question;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void deleteQuestion(Long questionId) {
        questionRepository.deleteQuestionAlternatives(questionId);
        questionRepository.deleteQuestion(questionId);
    }

    /**
     * Delete a question by id
     *
     * @param questionId The id of the question to be deleted
     */
    public Question deleteQuestionById(Long questionId) throws IOException {
        boolean exists = questionRepository.existsById(questionId);
        if (exists) {
            Question questionToBeDeleted = getQuestionById(questionId);
            deleteQuestion(questionId);
            return questionToBeDeleted;
        } else {
            throw new NullPointerException("this content doesn't exist.");
        }
    }

    public Question updateQuestion(Long id, Question newQuestion) {
        Optional<Question> oldQuestion = questionRepository.findById(id);
        if (oldQuestion.isPresent()) {
            Question question = oldQuestion.get();
            question.setQuestionText(newQuestion.getQuestionText());
            questionRepository.deleteQuestionAlternatives(id);

            if(!(newQuestion.getAlternatives().isEmpty())) {
                for(Alternative alternative : newQuestion.getAlternatives()) {
                    alternativeController.registerNewAlternative(question.getId(), alternative);
                }
            }

            return newQuestion;
        } else {
            throw new NullPointerException("this question doesn't exist.");
        }
    }

}
