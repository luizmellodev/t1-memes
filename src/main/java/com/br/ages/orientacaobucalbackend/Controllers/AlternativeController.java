package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Common.AlternativeBadRequest;
import com.br.ages.orientacaobucalbackend.Common.EmptyJson;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Services.AlternativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/alternative")
@CrossOrigin
public class AlternativeController {

    @Autowired
    AlternativeService alternativeService;

    @GetMapping
    public ResponseEntity<List<Alternative>> getAllAlternatives() {
        List<Alternative> alternatives = alternativeService.getAllAlternatives();
        return new ResponseEntity<>(alternatives, HttpStatus.OK);
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<?> registerNewAlternative(@PathVariable Long questionId, @RequestBody Alternative newAlternative) {
        try {
            Optional<Alternative> alternative = alternativeService.addNewAlternative(questionId, newAlternative);
            if (alternative.isPresent()) {
                return new ResponseEntity<>(alternative, HttpStatus.OK);
            } else {
               return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException error) {
            return new ResponseEntity<>(new AlternativeBadRequest(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{alternativeId}")
    public ResponseEntity<?> updateAlternative(@PathVariable Long alternativeId, @RequestBody Alternative alternative) {
        try {
            Optional<Alternative> updatedAlternative = alternativeService.updateAlternative(alternativeId, alternative);
            if (updatedAlternative.isPresent()) {
                return new ResponseEntity<>(updatedAlternative, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException error) {
            return new ResponseEntity<>(new AlternativeBadRequest(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{alternativeId}")
    public ResponseEntity<?> deleteAlterativeById(@PathVariable Long alternativeId) {
        Optional<Alternative> deletedAlternative = alternativeService.deleteAlternativeById(alternativeId);
        if (deletedAlternative.isPresent()) {
            return new ResponseEntity<>(deletedAlternative.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllAlternatives() {
        List<Long> alternativeIds = alternativeService.deleteAllAlternatives();
        return new ResponseEntity<>(alternativeIds, HttpStatus.OK);
    }

}
