package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "content")
public class Content {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Lob
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String textUrl;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String videoUrl;

    @Getter
    @Setter
    private String panfletoUrl;

    @Getter
    @Setter
    @Transient
    private String panfleto;

    @Getter
    @Setter
    @Column(unique = true)
    @ManyToMany(mappedBy = "contents")
    private List<Category> categories;

    @Getter
    @Setter
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendedSource> recommendedSource;
}
