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

    @Autowired
    ContentRepository contentRepository;
    @Autowired
    RecommendedSourceRepository recommendedSourceRepository;

    public List<RecommendedSource> getRecommendedSourcesByContentId(Long contentId) {
        return recommendedSourceRepository.findAllRecommendedSources(contentId);
    }

    public Optional<RecommendedSource> getRecommendedSource(Long id) {
        return recommendedSourceRepository.findById(id);
    }

    public Optional<RecommendedSource> addNewRecommendedSource(RecommendedSource recommendedSource, Long contentId) {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent()) {
            recommendedSource.setContent(content.get());
            recommendedSourceRepository.save(recommendedSource);
            return Optional.of(recommendedSource);
        }

        
        return Optional.empty();
    }

    public Optional<RecommendedSource> updateRecommendedsource(Long id, RecommendedSource recommendedSource) {
        Optional<RecommendedSource> oldResponse = recommendedSourceRepository.findById(id);
        if (oldResponse.isPresent()) {
            RecommendedSource rs = oldResponse.get();
            rs.setContent(recommendedSource.getContent());
            rs.setDescription(recommendedSource.getDescription());
            rs.setLink(recommendedSource.getLink());
            rs.setTitle(recommendedSource.getTitle());
            recommendedSourceRepository.save(rs);
            return Optional.of(rs);
        }else {
            return Optional.empty();
        }
    }

    public Optional<Long> deleteRecommendedSource(Long id) {
        boolean exists = recommendedSourceRepository.existsById(id);

        if (!exists) {
            return Optional.empty();
        }

        recommendedSourceRepository.deleteById(id);
        return Optional.of(id);
    }
}