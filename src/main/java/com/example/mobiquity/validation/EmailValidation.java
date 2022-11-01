package com.example.mobiquity.validation;

import java.util.regex.Pattern;

public class EmailValidation {

    private EmailValidation() {
    }
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
