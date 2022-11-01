package com.example.mobiquity.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationTest {
    @Test
    void testUsingSimpleRegex() {
        final String emailAddress = "username@domain.com";
        final String regexPattern = "^(.+)@(\\S+)$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }
}