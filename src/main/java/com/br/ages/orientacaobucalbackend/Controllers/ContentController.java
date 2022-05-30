package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Content;
import com.br.ages.orientacaobucalbackend.Services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity<List<Content>> getAllContents(@RequestParam String search) {
        List<Content> response;
        if(search.isBlank()){
            response = contentService.list();
        }
        else{
            response = contentService.list(); //TODO filtrar os resultados pela busca
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", String.valueOf(response.size()));
        headers.add("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContentById(@PathVariable Long id) {
        Content response = contentService.getById(id);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createContent(@RequestBody Content content) {
        try {
            // content.setPanfletoUrl(this.uploadFileTos3bucket(content.getPanfleto()));
            contentService.save(content);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContent(@PathVariable Long id, @RequestBody Content newContent) throws IOException {
        if (contentService.update(id, newContent)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllContents() {
        contentService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAllContents(@PathVariable Long id) {
        if (contentService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
