package com.br.ages.orientacaobucalbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "alternative")
public class Alternative {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    @Getter
    @Setter
    private String alternativeText;
    
    @Getter
    @Setter
    private String criticalLevel;
}
