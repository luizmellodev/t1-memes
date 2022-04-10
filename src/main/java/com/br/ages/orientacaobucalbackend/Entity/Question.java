package com.br.ages.orientacaobucalbackend.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @SequenceGenerator(
            name="question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )

//    @OneToMany
//    private List<Alternative> alternatives;

    private Long id;
    private String question_text;

    public Question() {
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
