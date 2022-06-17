package com.br.ages.orientacaobucalbackend.Controllers;

import java.util.List;
import java.util.Optional;

import com.br.ages.orientacaobucalbackend.Services.RecommendedSourceService;
import com.br.ages.orientacaobucalbackend.Common.EmptyJson;
import com.br.ages.orientacaobucalbackend.Common.RecommendedSourceBadRequest;
import com.br.ages.orientacaobucalbackend.Entity.RecommendedSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/source")
@CrossOrigin
public class RecommendedSourceController {

      @Autowired
      RecommendedSourceService recommendedSourceService;

      @GetMapping
      public ResponseEntity<List<RecommendedSource>> getAllRecommendedSources() {
            List<RecommendedSource> recommendedSources = recommendedSourceService.getAllRecommendedSources();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Range", String.valueOf(recommendedSources.size()));
            headers.add("Access-Control-Expose-Headers", "Content-Range");
            return new ResponseEntity<>(recommendedSources, headers, HttpStatus.OK);
      }

      @GetMapping("/content/{contentId}")
      public ResponseEntity<?> getRecommendedSourcesByContentId(@PathVariable Long contentId) {
            List<RecommendedSource> recommendedSources = recommendedSourceService.getRecommendedSourcesByContentId(contentId);
            return new ResponseEntity<>(recommendedSources, HttpStatus.OK);
      }

      @GetMapping("/{sourceId}")
      public ResponseEntity<?> getRecommendedSources(@PathVariable Long sourceId) {
            Optional<RecommendedSource> recommendedSource = recommendedSourceService.getRecommendedSource(sourceId);
            if(recommendedSource.isPresent()){
                  return new ResponseEntity<>(recommendedSource.get(), HttpStatus.OK);
            }else{
                  return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
            }
      }

      @PostMapping("/{contentId}")
      public ResponseEntity<?> addNewRecommendedSource(@RequestBody RecommendedSource newRecommendedSource, 
      @PathVariable Long contentId) {
            try {
                  Optional<RecommendedSource> recommendedSource = recommendedSourceService.addNewRecommendedSource(newRecommendedSource, contentId);
                  if (recommendedSource.isPresent()) {
                        return new ResponseEntity<>(recommendedSource, HttpStatus.OK);
                  } else {
                        return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
                  }
            } catch (IllegalArgumentException error) {
                  return new ResponseEntity<>(new RecommendedSourceBadRequest(), HttpStatus.BAD_REQUEST);
            }
      }

      @PutMapping("/{sourceId}")
      public ResponseEntity<?> updateRecommendedsource(@PathVariable Long sourceId,
                  @RequestBody RecommendedSource recommendedSource) {
            try {
                  Optional<RecommendedSource> updatedRecommendedSource = recommendedSourceService.updateRecommendedsource(sourceId, recommendedSource);
                  if (updatedRecommendedSource.isPresent()) {
                        return new ResponseEntity<>(updatedRecommendedSource, HttpStatus.OK);
                  } else {
                        return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
                  }
            } catch (IllegalArgumentException error) {
                  return new ResponseEntity<>(new RecommendedSourceBadRequest(), HttpStatus.BAD_REQUEST);
            }
      }

      @DeleteMapping("/{sourceId}")
      public ResponseEntity<?> deleteRecommendedSourceById(@PathVariable Long sourceId) {
            Optional<RecommendedSource> deletedRecommendedSourceService = recommendedSourceService.deleteRecommendedSourceById(sourceId);
            if (deletedRecommendedSourceService.isPresent()) {
                  return new ResponseEntity<>(deletedRecommendedSourceService, HttpStatus.OK);
            } else {
                  return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
            }
      }

      @DeleteMapping("/delete")
      public ResponseEntity<?> deleteAllRecommendedSources() {
            List<Long> recommendeSourceIds = recommendedSourceService.deleteAllRecommendedSources();
            return new ResponseEntity<>(recommendeSourceIds, HttpStatus.OK);
      }
}
