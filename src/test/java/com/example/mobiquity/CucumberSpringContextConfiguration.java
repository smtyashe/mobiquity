package com.example.mobiquity;


import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = MobiquityApplication.class)
public class CucumberSpringContextConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);

    @Value("${env}")
    private String environment;

    @Before
    public void setUp() {
        LOG.info("------ Spring Context Initialized For Executing Cucumber Tests In {} -------", environment);
    }
}
