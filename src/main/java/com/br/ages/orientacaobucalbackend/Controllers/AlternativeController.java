package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import com.br.ages.orientacaobucalbackend.Services.AlternativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alternative")
@CrossOrigin
public class AlternativeController {

    private final AlternativeService alternativeService;

    @Autowired
    public AlternativeController(AlternativeService alternativeService) {
        this.alternativeService = alternativeService;
    }

    @GetMapping
    public List<Alternative> getAlternatives() {
        return alternativeService.getAlternatives();
    }

    @PostMapping("/{questionId}")
    public void registerNewAlternative(
        @PathVariable Long questionId,
        @RequestBody Alternative alternative) {
        alternativeService.addNewAlternative(alternative, questionId);
    }

    @DeleteMapping(path = "{alternativeId}")
    public void deleteAlternative(
        @PathVariable("alternativeId") Long alternativeId) {
        alternativeService.deleteAlternative(alternativeId);
    }

    @PutMapping(path = "{alternativeId}")
    public void updateAlternative(
            @PathVariable("alternativeId") Long alternativeId,
            @RequestBody Alternative alternative) {
        alternativeService.updateAlternative(alternativeId, alternative.getAlternativeText(), alternative.getCriticalLevel());
    }
}
