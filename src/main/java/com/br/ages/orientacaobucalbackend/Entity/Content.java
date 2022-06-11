package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
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
    @NonNull
    private String textUrl;

    @Getter
    @Setter
    @NonNull
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

    @Getter
    @Setter
    @Transient
    private List<Long> categories_ids;
}
