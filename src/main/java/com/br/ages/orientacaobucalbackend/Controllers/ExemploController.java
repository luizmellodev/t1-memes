package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Services.ExemploService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExemploController {

    private final ExemploService exemploService;

    ExemploController(ExemploService exemploService){
        this.exemploService = exemploService;
    }

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @PostMapping("/createExemplo")
    public void createExemplo(@RequestBody String name){
        exemploService.save(name);
    }

}
