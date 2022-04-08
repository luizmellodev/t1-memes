package com.br.ages.orientacaobucalbackend.EntityConfigs;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.QuestionRepository;
import com.br.ages.orientacaobucalbackend.Entity.Question;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QuestionConfig {

    @Bean
    CommandLineRunner commandLineRunner(QuestionRepository repository) {
        return args -> {
            Question question1 = new Question(
                    "Você tem dor de garganta?"
            );

            Question question2 = new Question(
                    "Você sente sensibilidade nos dentes?"
            );

            repository.saveAll(List.of(question1, question2));
        };
    }
}
