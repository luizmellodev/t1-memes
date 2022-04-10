package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlternativeService {

    private final AlternativeRepository alternativeRepository;

    @Autowired
    public AlternativeService(AlternativeRepository alternativeRepository) {
        this.alternativeRepository = alternativeRepository;
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
    public void addNewAlternative(Alternative alternative) {
        // TODO adicionar possíveis validações

        alternativeRepository.save(alternative);
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

    /**
     * Update an alternative
     * @param alternativeId The id of the alternative
     * @param alternativeText The text of the alternative
     * @param criticalLevel The critical level of the alternative
     */
    public void updateAlternative(Long alternativeId, String alternativeText, String criticalLevel) {
        Alternative alternative = alternativeRepository.findById(alternativeId).orElseThrow(() -> new IllegalStateException(
                "question with id " + alternativeId + " does not exist"));

        // TODO adicionar possíveis validações

        if (alternativeText != null && alternativeText.length() > 0) {
            alternative.setAlternativeText(alternativeText);
        }

        if (criticalLevel != null && criticalLevel.length() > 0) {
            alternative.setCriticalLevel(criticalLevel);
        }
    }




}
