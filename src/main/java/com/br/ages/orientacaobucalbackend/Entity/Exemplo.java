package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Exemplo {
    @Id
    @GeneratedValue
    @Getter private Long id;

    @Getter @Setter
    @NonNull
    private String name;

    public Exemplo(String name) {
        this.name = name;
    }

    public Exemplo() {

    }
}
