package com.br.ages.orientacaobucalbackend.Entity;

import javax.persistence.*;

@Entity
@Table
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

    private Long id;
    private String text;

    public Question() {
    }

    public Question(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Question(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", text=" + text + '\'' + '}';
    }
}
