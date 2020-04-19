package com.task.group.ignitis;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static final String INVALID_USERNAME = "user";
    public static final String USERNAME = "user@user.com";
    public static final String PASSWORD = "password";
    public static final String INVALID_PASSWORD = "invalidPassword";

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
