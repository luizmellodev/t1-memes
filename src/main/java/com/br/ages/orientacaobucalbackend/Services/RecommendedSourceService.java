package com.br.ages.orientacaobucalbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.RecommendedSourceRepository;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ContentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendedSourceService {

    private final ContentRepository contentRepository;
    private final RecommendedSourceRepository recommendedSourceRepository;

    @Autowired
    public RecommendedSourceService(RecommendedSourceRepository recommendedSourceRepository,
            ContentRepository contentRepository) {
        this.recommendedSourceRepository = recommendedSourceRepository;
        this.contentRepository = contentRepository;
    }

    public List<RecommendedSource> getRecommendedSourcesByContentId(Long contentId) {
        return recommendedSourceRepository.findAllRecommendedSources(contentId);
    }

    public Optional<RecommendedSource> getRecommendedSource(Long id) {
        return recommendedSourceRepository.findById(id);
    }

    public Long addNewRecommendedSource(RecommendedSource recommendedSource, Long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent()) {
            recommendedSource.setContent(content.get());
            recommendedSourceRepository.save(recommendedSource);
        }
        return recommendedSource.getId();
    }

    public RecommendedSource updateRecommendedsource(Long id, String title, String description,
            RecommendedSourceService recommendedSourceService) {

        Optional<RecommendedSource> recommendedSource = recommendedSourceRepository.findById(id);
                //.orElseThrow(() -> new IllegalStateException("RecommendedService with id " + id + " does not exist"));

        if (recommendedSource.isPresent()) {
            recommendedSourceRepository.save(recommendedSource.get());
            return recommendedSource.get();
        }

        return null;
    }

    public Long deleteRecommendedSource(Long id) {
        return (long) 0;
    }

}