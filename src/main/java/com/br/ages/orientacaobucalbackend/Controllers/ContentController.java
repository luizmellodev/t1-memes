package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Common.ContentBadRequest;
import com.br.ages.orientacaobucalbackend.Common.EmptyJson;
import com.br.ages.orientacaobucalbackend.Entity.Content;
import com.br.ages.orientacaobucalbackend.Services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = contentService.getAllContents();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", String.valueOf(contents.size()));
        headers.add("Access-Control-Expose-Headers", "Content-Range");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> getContentById(@PathVariable Long contentId) {
        Optional<Content> content = contentService.getContentById(contentId);
        if (content.isPresent()) {
            return new ResponseEntity<>(content.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewContent(@RequestBody Map<String, Object> newContent) {
        Optional<Content> content = contentService.addNewContent(newContent);
        if (content.isPresent()) {
            return new ResponseEntity<>(content, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ContentBadRequest(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<?> updateContent(@PathVariable Long contentId, @RequestBody Content newContent) {
        Optional<Content> updatedContent = contentService.updateContent(contentId, newContent);
        if (updatedContent.isPresent()) {
            return new ResponseEntity<>(updatedContent.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<?> deleteContentById(@PathVariable Long contentId) {
        Optional<Content> deletedContent = contentService.deleteById(contentId);
        if (deletedContent.isPresent()) {
            return new ResponseEntity<>(deletedContent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllContents() {
        List<Long> contentIds = contentService.deleteAll();
        return new ResponseEntity<>(contentIds, HttpStatus.OK);
    }
}
