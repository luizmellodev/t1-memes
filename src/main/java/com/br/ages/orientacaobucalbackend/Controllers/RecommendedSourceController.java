package com.br.ages.orientacaobucalbackend.Controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import com.br.ages.orientacaobucalbackend.Services.RecommendedSourceService;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;
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
      public ResponseEntity<RecommendedSource> getRecommendedSources(@PathVariable Long id) {
            Optional<RecommendedSource> response = recommendedSourceService.getRecommendedSource(id);
            if(response.isPresent()){
            return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response.get());
            }else{
                  return ResponseEntity.notFound().build();
            }
      }

      @GetMapping("/content/{id}")
      public ResponseEntity<List<RecommendedSource>> getRecommendedSourcesByContentId(Long contentId) {
            List<RecommendedSource> response = recommendedSourceService.getRecommendedSourcesByContentId(contentId);
            if (response.size() == 0) {
                  return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            } else {
                  return ResponseEntity
                        .notFound()
                        .build();
            }
      }

      // POST = create link
      @PostMapping("/{contentId}")
      public ResponseEntity<RecommendedSource> addNewRecommendedSource(@RequestBody RecommendedSource recommendedSource,
                  @PathVariable Long contentId) {
            Optional<RecommendedSource> response = recommendedSourceService.addNewRecommendedSource(recommendedSource, contentId);
            if (response.isPresent()) {
                  return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response.get());
            } else {
                  return ResponseEntity
                        .notFound()
                        .build();
            }
      }

      // PUT = Alternate/Update Link
      @PutMapping("/{id}")
      public ResponseEntity<RecommendedSource> updateRecommendedsource(@PathVariable Long id,
                  @RequestBody RecommendedSource recommendedSource) {
            Optional<RecommendedSource> response = recommendedSourceService.updateRecommendedsource(id, recommendedSource);
            if (response.isPresent()) {
                  return ResponseEntity
                        .ok()
                        .body(response.get());
            } else {
                  return ResponseEntity
                        .notFound()
                        .build();
            }
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Long> deleteRecommendedSource(@PathVariable Long id) {
            Optional<Long> response = recommendedSourceService.deleteRecommendedSource(id);
            if (response.isPresent()) {
                  return ResponseEntity
                        .ok()
                        .build();
            } else {
                  return ResponseEntity
                        .notFound()
                        .build();
            }
      }
}
