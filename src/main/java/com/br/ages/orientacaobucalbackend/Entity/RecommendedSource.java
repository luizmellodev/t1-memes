package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recommendedSource")
public class RecommendedSource {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @JoinColumn(name = "content_id")
    @JsonIgnore
    @Setter
    private Content content;
}
