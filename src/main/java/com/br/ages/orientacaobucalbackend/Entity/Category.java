package com.br.ages.orientacaobucalbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String color;

    @Getter
    @Setter
    private String imageUrl;

    @Getter
    @Setter
    @ManyToMany
    @Column(unique=true)
    @JoinTable(
        name = "category_content",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    @JsonIgnore
    private List<Content> contents;

}