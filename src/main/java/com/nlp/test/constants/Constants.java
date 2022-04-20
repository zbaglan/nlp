package com.nlp.test.constants;

public class Constants {

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("this is util class");
    }

    public static final String BASE_API = "/api";
    public static final String FILE_NAME = "src/main/resources/result.txt";
    public static final String EMPTY = " ";
}
