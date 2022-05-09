package com.br.ages.orientacaobucalbackend.steps;

import com.br.ages.orientacaobucalbackend.OrientacaoBucalBackendApplication;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(
	classes = OrientacaoBucalBackendApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CucumberStep {}
