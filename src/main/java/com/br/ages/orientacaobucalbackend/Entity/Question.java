package com.br.ages.orientacaobucalbackend.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternative> alternatives;

    private String question_text;

    public Question() {
    }

    public void addAlternative(Alternative alternative) {
        alternatives.add(alternative);
    }

    public void removeAlternative(Alternative alternative) {
        alternatives.remove(alternative);
    }

    public Question(Long id, String question_text) {
        this.id = id;
        this.question_text = question_text;
    }

    public Question(String question_text) {
        this.question_text = question_text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", question_text=" + question_text + '\'' + '}';
    }
}
