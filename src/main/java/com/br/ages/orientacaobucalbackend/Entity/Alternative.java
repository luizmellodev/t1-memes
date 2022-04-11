package com.br.ages.orientacaobucalbackend.Entity;

import javax.persistence.*;

@Entity
@Table(name = "alternative")
public class Alternative {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name="id")
    private Question question;

    private String alternative_text;
    private String critical_level;

    public Alternative() {
    }

    public Alternative(Long id, String alternative_text, String critical_level) {
        this.id = id;
        this.alternative_text = alternative_text;
        this.critical_level = critical_level;
    }

    public Alternative(String alternative_text, String critical_level) {
        this.alternative_text = alternative_text;
        this.critical_level = critical_level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlternativeText() {
        return alternative_text;
    }

    public void setAlternativeText(String alternative_text) {
        this.alternative_text = alternative_text;
    }

    public String getCriticalLevel() {
        return critical_level;
    }

    public void setCriticalLevel(String critical_level) {
        this.critical_level = critical_level;
    }

    @Override
    public String toString() {
        return "Alternative{" +
                "id=" + id +
                ", alternative_text='" + alternative_text + '\'' +
                ", critical_level='" + critical_level + '\'' +
                '}';
    }
}
