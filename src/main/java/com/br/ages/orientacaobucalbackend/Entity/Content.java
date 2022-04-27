package com.br.ages.orientacaobucalbackend.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Content {
    @Id
    @Getter
    @GeneratedValue
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

    public Content(@NonNull String textUrl, @NonNull String title, String videoUrl, String panfletoUrl) {
        this.textUrl = textUrl;
        this.title = title;
        this.videoUrl = videoUrl;
        this.panfletoUrl = panfletoUrl;
    }
}