package com.br.ages.orientacaobucalbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "alternative")
public class Alternative {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private Question question;
    @Getter
    @Setter
    private String alternativeText;
    @Getter
    @Setter
    private String criticalLevel;
}
