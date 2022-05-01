package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.enums.AlternativeCriticalLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AlternativeService(AlternativeRepository alternativeRepository,QuestionRepository questionRepository) {
        this.alternativeRepository = alternativeRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Get a list of all of the alternatives
     * @return
     */
    public List<Alternative> getAlternatives() {
        return alternativeRepository.findAll();
    }

    /**
     * Get a list of alternatives linked to a question
     * @param questionId The id of the question
     */
    public void getAlternativesByQuestionId(Long questionId) {
        // TODO implementar busca de alternativas por id da pergunta
    }

    /**
     * Add a new alternative
     * @param alternative The alternative to be added
     */
    public void addNewAlternative(Alternative alternative, long question_id) {
        // TODO adicionar possíveis validações
        Optional<Question> question = questionRepository.findById(question_id);
        if(question.isPresent()) {
            alternative.setQuestion(question.get());
            alternativeRepository.save(alternative);
        }
    }

    /**
     * Delete an alternative by id
     * @param alternativeId
     */
    public void deleteAlternative(Long alternativeId) {
        boolean exists = alternativeRepository.existsById(alternativeId);

        if(!exists) {
            throw new IllegalStateException("alternative with id " + alternativeId + " does not exist");
        }

        alternativeRepository.deleteById(alternativeId);
    }

    public boolean updateAlternative(Long id, Alternative newAlternative) {
        Optional<Alternative> oldAlternative = alternativeRepository.findById(id);
        if (oldAlternative.isPresent()) {
            Alternative alternative = oldAlternative.get();
            alternative.setAlternativeText(newAlternative.getAlternativeText());
            alternative.setCriticalLevel(newAlternative.getCriticalLevel());
            alternativeRepository.save(alternative);
            return true;
        } else {
            throw new NullPointerException("this alternative doesn't exist.");
        }
    }
}
