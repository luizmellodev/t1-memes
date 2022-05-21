package com.br.ages.orientacaobucalbackend.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class AlternativeStep {

    @Autowired
    WebTestClient client;

    @Given("o servidor tem alternativas cadastradas")
    public void o_servidor_tem_alternativas_cadastradas() {
        byte[] responseBody = client
            .get()
            .uri("api/alternative")
            .exchange()
            .expectBody()
            .returnResult()
            .getResponseBody();
        
        assertNotEquals("[]", new String(responseBody));
    }

    @Then("o servidor deve retornar uma lista de questoes")
    public void o_servidor_deve_retornar_uma_lista_de_questoes() {
        byte[] responseBody = client
            .get()
            .uri("api/question")
            .exchange()
            .expectBody()
            .returnResult()
            .getResponseBody();

        assertNotEquals("[]", new String(responseBody));
    }
}
