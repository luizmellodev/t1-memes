package com.br.ages.orientacaobucalbackend.EntityConfigs;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.AlternativeRepository;
import com.br.ages.orientacaobucalbackend.Entity.Alternative;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class AlternativeConfig {

    @Bean
    CommandLineRunner commandLineRunner(AlternativeRepository repository) {
        return args -> {
            Alternative alternative1 = new Alternative("Sim", "Alto");

            Alternative alternative2 = new Alternative("Não", "Baixo");

            repository.saveAll(List.of(alternative1, alternative2));
        };
    }
}
