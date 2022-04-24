package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Question> getQuestionsWithAlternatives() {
        return questionService.getQuestionsWithAlternatives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntity<Question>> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(questionService.getQuestionById(id));
    }

    @PostMapping
    public Long registerNewQuestion(@RequestBody Question question) {
        return questionService.addNewQuestion(question);
    }

    @DeleteMapping(path = "{questionId}")
    public void deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteQuestion(questionId);
    }

    @PutMapping(path = "{questionId}")
    public void updateQuestion(
            @PathVariable("questionId") Long questionId,
            @RequestBody Question question) {
        questionService.updateQuestion(questionId, question.getQuestionText());
    }
}
