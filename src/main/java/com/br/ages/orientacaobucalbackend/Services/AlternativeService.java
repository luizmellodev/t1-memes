package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.enums.AlternativeCriticalLevel;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlternativeService {

    @Autowired
    AlternativeRepository alternativeRepository;
    @Autowired
    QuestionRepository questionRepository;

    public List<Alternative> getAllAlternatives() {
        return alternativeRepository.findAll();
    }

    public Optional<Alternative> getAlternativesByQuestionId(Long alternativeId) {
        return alternativeRepository.findById(alternativeId);
    }

    public Optional<Alternative> addNewAlternative(Long questionId, Alternative alternative) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()) {
            if ((alternative.getCriticalLevel() != null) && (alternative.getAlternativeText() != null)) {
                if(EnumUtils.isValidEnum(AlternativeCriticalLevel.class, alternative.getCriticalLevel().toString())) {
                    alternative.setQuestion(question.get());
                    return Optional.of(alternativeRepository.save(alternative));
                }
            }
            throw new IllegalArgumentException();
        } else {
            return Optional.empty();
        }
    }

    public Optional<Alternative> updateAlternative(Long alternativeId, Alternative newAlternative) {
        Optional<Alternative> oldAlternative = alternativeRepository.findById(alternativeId);
        if (oldAlternative.isPresent()) {
            Alternative alternative = oldAlternative.get();
            if(newAlternative.getCriticalLevel() != null) {
                if(EnumUtils.isValidEnum(AlternativeCriticalLevel.class, newAlternative.getCriticalLevel().toString())) {
                    alternative.setCriticalLevel(newAlternative.getCriticalLevel());
                } else {
                    throw new IllegalArgumentException();
                }
            }
            if (newAlternative.getAlternativeText() != null) {alternative.setAlternativeText(newAlternative.getAlternativeText());}
            return Optional.of(alternativeRepository.save(alternative));  
        } else {
            return Optional.empty();
        }
    }

    public Optional<Alternative> deleteAlternativeById(Long alternativeId) {
        Optional<Alternative> alternative = alternativeRepository.findById(alternativeId);
        if (alternative.isPresent()) {
            alternativeRepository.deleteById(alternativeId);
        }
        return alternative;
    }

    public List<Long> deleteAllAlternatives() {
        List<Long> alternativeIds = alternativeRepository.getAllIds();
        alternativeRepository.deleteAll();
        return alternativeIds;
    }
}
