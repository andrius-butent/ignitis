package com.task.group.ignitis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class SecurityValidator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String validatePayload(BindingResult bindingResult) {

        String errorMessage = "";

        if (bindingResult.hasErrors()) {

            // collecting all errors
            for(ObjectError error : bindingResult.getAllErrors()) {
                errorMessage += error.getDefaultMessage() + "\n";
            }
        }

        return errorMessage;
    }

    public boolean checkPasswords(String password, String encodedPassword) {

        // check if password matches with encoded password from DB
        return passwordEncoder.matches(password, encodedPassword);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
