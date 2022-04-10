package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Question;
import com.br.ages.orientacaobucalbackend.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @PostMapping
    public void registerNewQuestion(@RequestBody Question question) {
        questionService.addNewQuestion(question);
    }

    @DeleteMapping(path = "{questionId}")
    public void deleteQuestion(@PathVariable("questionId") Long questionId) {
        questionService.deleteStudent(questionId);
    }

    @PutMapping(path = "{questionId}")
    public void updateQuestion(
            @PathVariable("questionId") Long questionId,
            @RequestParam(required = true) String questionText) {
        questionService.updateQuestion(questionId, questionText);
    }
}
