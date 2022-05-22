package com.br.ages.orientacaobucalbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.java.Log;
import org.hibernate.mapping.Array;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.Set;

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
    private File panfleto;

    @Getter
    @Setter
    @Column(unique=true)
    @ManyToMany(mappedBy = "contents")
    private List<Category> categories;

    @Getter
    @Setter
    @Transient
    private List<Long> categories_ids;
}