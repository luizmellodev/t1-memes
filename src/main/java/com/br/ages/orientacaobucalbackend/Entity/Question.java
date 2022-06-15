package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@Entity
@Table(name = "question")
// @JsonIdentityInfo(
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id")
public class Question {
    @Getter
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference
    private List<Alternative> alternatives;

    @Getter
    @Setter
    @NonNull
    private String questionText;
}
