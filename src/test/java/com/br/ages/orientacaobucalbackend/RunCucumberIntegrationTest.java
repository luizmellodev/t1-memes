package com.br.ages.orientacaobucalbackend;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	plugin = {
		"pretty",
		"html:target/cucumber/report.html"
	},
	tags = "",
	features = "src/test/resources/features",
	glue = "com.br.ages.orientacaobucalbackend.steps"
)
public class RunCucumberIntegrationTest {}
