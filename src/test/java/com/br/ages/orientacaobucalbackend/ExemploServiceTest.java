package com.br.ages.orientacaobucalbackend;

import com.br.ages.orientacaobucalbackend.Entity.Exemplo;
import com.br.ages.orientacaobucalbackend.Services.ExemploService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExemploServiceTest {
    @Autowired
    private ExemploService exemploService;

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        exemploService.deleteAll();
        exemploService.save("João Teste");
        List<Exemplo> books = exemploService.list();
        Assert.isTrue(books.size() == 1);
    }
}
