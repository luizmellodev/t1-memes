package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/question")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getQuestionsWithAlternatives() {
        List<Question> response = questionService.getQuestionsWithAlternatives();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", String.valueOf(response.size()));
        headers.add("Access-Control-Expose-Headers", "Content-Range");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(questionService.getQuestionById(id));
    }

    @PostMapping
    public ResponseEntity<?> registerNewQuestion(@RequestBody Question question) {
        try {
            questionService.addNewQuestion(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Long questionId) {
        try {
            Question response = questionService.deleteQuestionById(questionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question newQuestion) {
        try {
            Question response = questionService.updateQuestion(id, newQuestion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
