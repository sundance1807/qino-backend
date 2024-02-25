package com.qino.util;

public enum MessageSource {

    //Users
    USER_NOT_FOUND("User '%s' not found."),
    USERNAME_IS_ALREADY_TAKEN("Username '%s' is already taken."),

    //Genres
    GENRE_IS_ALREADY_EXIST("A '%s' genre is already exist."),
    GENRE_NOT_FOUND("Genre with id: '%s' is not found."),

    //Directors
    DIRECTOR_NOT_FOUND("Director with id: '%s' not found."),

    //Writers
    WRITER_NOT_FOUND("Writer with id: '%s' not found'.")



    ;

    private final String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
