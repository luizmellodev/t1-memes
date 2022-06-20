package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Common.EmptyJson;
import com.br.ages.orientacaobucalbackend.Common.QuestionBadRequest;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.Services.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getQuestionsWithAlternatives() {
        List<Question> questions = questionService.getQuestionsWithAlternatives();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", String.valueOf(questions.size()));
        headers.add("Access-Control-Expose-Headers", "Content-Range");
        return new ResponseEntity<>(questions, headers, HttpStatus.OK);
    }

    @GetMapping(value="/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        if (question.isPresent()) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewQuestion(@RequestBody Question question) {
        try {
            Optional<Question> newQuestion = questionService.addNewQuestion(question);
            if (newQuestion.isPresent()) {
                return new ResponseEntity<>(question, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new QuestionBadRequest(), HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody Question newQuestion) {
        try {
            Optional<?> updatedQuestion = questionService.updateQuestion(questionId, newQuestion);
            if (updatedQuestion.isPresent()) {
                return new ResponseEntity<>(updatedQuestion.get(), HttpStatus.OK);                
            } else {
                return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException error) {
            Object msg = error.getMessage();
            if (msg == null) {
                msg = new QuestionBadRequest();
            }
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{questionId}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable("questionId") Long questionId) {
        Optional<Question> question = questionService.deleteQuestionById(questionId);
        if  (question.isPresent()) {
            return new ResponseEntity<>(question.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmptyJson(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteAllQuestions() {
        List<Long> questionIds = questionService.deleteAllQuestions();
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }
}
