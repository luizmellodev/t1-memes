package com.br.ages.orientacaobucalbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    @NonNull
    private String color;

    @Getter
    @Setter
    @NonNull
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