package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.Common.QuestionBadRequest;
import com.br.ages.orientacaobucalbackend.Controllers.AlternativeController;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

    public List<Question> getQuestionsWithAlternatives() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public Optional<Question> addNewQuestion(Question newQuestion) throws IllegalArgumentException {
        List<Alternative> alternatives = newQuestion.getAlternatives();
        if ((alternatives != null) && (alternatives.size() >= 2) && (alternatives.size() <= 3)) {
            for(Alternative alternative : newQuestion.getAlternatives()){
                alternative.setQuestion(newQuestion);
            }
            return Optional.of(questionRepository.save(newQuestion));
        } else {
            return Optional.empty();
        }   
    }

    public Optional<Question> updateQuestion(Long questionId, Question newQuestion) throws IllegalArgumentException {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            if (newQuestion.getQuestionText() != null) {
                question.get().setQuestionText(newQuestion.getQuestionText());
            }
            List<Alternative> alternatives = newQuestion.getAlternatives();
            if (alternatives != null) {
                if ((alternatives.size() >= 2) && (alternatives.size() <= 3)) {
                    questionRepository.deleteQuestionAlternatives(questionId);
                    for(Alternative newAlternative : alternatives) {
                        alternativeController.registerNewAlternative(question.get().getId(), newAlternative);
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return Optional.of(questionRepository.save(question.get()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Question> deleteQuestionById(Long questionId) {
        Optional<Question> question = getQuestionById(questionId);
        if (question.isPresent()) {
            questionRepository.deleteQuestionAlternatives(questionId);
            questionRepository.deleteQuestion(questionId);
        }
        return question;
    }

    public List<Long> deleteAllQuestions() {
        List<Long> ids = questionRepository.getAllIds();
        questionRepository.deleteAll();
        return ids;
    }
}
