package com.br.ages.orientacaobucalbackend.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SimpleTestStep extends CucumberStep {

    @Autowired
    WebTestClient client;
    private String endpoint;

    @Given("the user triggers {string}")
    public void the_user_triggers(String string) {
        this.endpoint = string;
    }

    @Then("the server sould return status code {string}")
    public void the_server_sould_return_status_code(String string) {
        this.client
            .get()
            .uri(this.endpoint)
            .exchange()
            .expectStatus()
            .equals(string);
    }

    @Then("the server says {string}")
    public void the_server_says(String string) {
        this.client
            .get()
            .uri(this.endpoint)
            .exchange()
            .expectBody()
            .equals(string);
    }
}
