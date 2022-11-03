package com.example.mobiquity;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"classpath:com.example.mobiquity"},
        plugin = {"pretty",
                "json:target/cucumber-report.json",
                "junit:target/cucumber-report.xml",
                "html:target/cucumber-report.html"
        }
)
public class TestRunner {
}