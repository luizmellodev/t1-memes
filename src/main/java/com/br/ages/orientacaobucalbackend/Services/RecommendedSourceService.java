package com.br.ages.orientacaobucalbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.RecommendedSourceRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecommendedSourceService {

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    RecommendedSourceRepository recommendedSourceRepository;
    
    private final String urlIsValid = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";


    public List<RecommendedSource> getRecommendedSourcesByContentId(Long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent()) {
            return recommendedSourceRepository.findAllRecommendedSourcesByContentId(contentId);
        } else {
            return Collections.emptyList();
        }
    }

    public List<RecommendedSource> getAllRecommendedSources() {
        return recommendedSourceRepository.findAll();
    }

    public Optional<RecommendedSource> getRecommendedSource(Long sourceId) {
        return recommendedSourceRepository.findById(sourceId);
    }

    public Optional<RecommendedSource> addNewRecommendedSource(RecommendedSource recommendedSource, Long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent()) {
            if ((recommendedSource.getDescription() != null) && 
             (recommendedSource.getLink() != null) && 
             (recommendedSource.getTitle() != null)) {
                Pattern pattern = Pattern.compile(this.urlIsValid);
                Matcher match = pattern.matcher(recommendedSource.getLink());
                if (match.matches()) {
                    recommendedSource.setContent(content.get());
                    return Optional.of(recommendedSourceRepository.save(recommendedSource));
                }
            }
            throw new IllegalArgumentException();
        }
        return Optional.empty();
    }

    public Optional<RecommendedSource> updateRecommendedsource(Long sourceId, RecommendedSource newRecommendedSource) {
        Optional<RecommendedSource> oldResponse = recommendedSourceRepository.findById(sourceId);
        if (oldResponse.isPresent()) {
            RecommendedSource recommendedSource = oldResponse.get();
            if (newRecommendedSource.getContent() != null) {recommendedSource.setContent(newRecommendedSource.getContent());}
            if (newRecommendedSource.getDescription() != null) {recommendedSource.setDescription(newRecommendedSource.getDescription());}
            if (newRecommendedSource.getTitle() != null) {recommendedSource.setTitle(newRecommendedSource.getTitle());}
            if (newRecommendedSource.getLink() != null) {
                Pattern pattern = Pattern.compile(this.urlIsValid);
                Matcher match = pattern.matcher(newRecommendedSource.getLink());
                if (match.matches()) {
                    recommendedSource.setLink(newRecommendedSource.getLink());
                } else {
                    throw new IllegalArgumentException();
                }               
            }
            return Optional.of(recommendedSourceRepository.save(recommendedSource));
        } else {
            return Optional.empty();
        }
    }

    public Optional<RecommendedSource> deleteRecommendedSourceById(Long sourceId) {
        Optional<RecommendedSource> recommendedSource = recommendedSourceRepository.findById(sourceId);
        if (recommendedSource.isPresent()) {
            recommendedSourceRepository.deleteById(sourceId);
        }
        return recommendedSource;
    }

    public List<Long> deleteAllRecommendedSources() {
        List<Long> recommendedSourceIds = recommendedSourceRepository.getAllIds();
        recommendedSourceRepository.deleteAll();
        return recommendedSourceIds;
    }
}