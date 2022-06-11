package com.br.ages.orientacaobucalbackend.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import com.br.ages.orientacaobucalbackend.Services.RecommendedSourceService;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/source")
@CrossOrigin
public class RecommendedSourceController {

      private final RecommendedSourceService recommendedSourceService;

      @Autowired
      public RecommendedSourceController(RecommendedSourceService recommendedSourceService) {
            this.recommendedSourceService = recommendedSourceService;
      }

      @GetMapping("/{id}")
      public Optional<RecommendedSource> getRecommendedSources(@PathVariable Long id) {
            return recommendedSourceService.getRecommendedSource(id);

      }

      @GetMapping("/content/{id}")
      public List<RecommendedSource> getRecommendedSourcesByContentId(Long contentId) {
            return recommendedSourceService.getRecommendedSourcesByContentId(contentId);
      }

      // POST = create link
      @PostMapping("/{contentId}")
      public Long addNewRecommendedSource(@RequestBody RecommendedSource recommendedSource,
                  @PathVariable Long contentId) {
            return recommendedSourceService.addNewRecommendedSource(recommendedSource, contentId);
      }

      // PUT = Alternate/Update Link
      @PutMapping("/{id}")
      public RecommendedSource updateRecommendedsource(@PathVariable Long id, @RequestBody String title,
                  @RequestBody String description, @RequestBody RecommendedSourceService recommendedSourceService) {
            return recommendedSourceService.updateRecommendedsource(id, title, description, recommendedSourceService);
      }

      @DeleteMapping("/{id}")
      public Long deleteRecommendedSource(@PathVariable Long id) {
            return recommendedSourceService.deleteRecommendedSource(id);
      }
}
