package com.qino.util;

public enum MessageSource {
    //System

    //Users
    USER_NOT_FOUND("User with id: '%s' not found."),
    USERNAME_IS_ALREADY_TAKEN("Username '%s' is already taken."),
    INVALID_USERNAME_FORMAT("Invalid username format"),
    USER_ID_CANNOT_BE_CHANGED("User id cannot be changed."),

    //Genres
    GENRE_IS_ALREADY_EXIST("Genre '%s' is already exist."),
    GENRE_NOT_FOUND("Genre with id: '%s' is not found."),

    //Directors
    DIRECTOR_NOT_FOUND("Director with id: '%s' not found."),

    //Writers
    WRITER_NOT_FOUND("Writer with id: '%s' not found."),


    //Composers
    COMPOSER_NOT_FOUND("Composer with id: '%s' not found."),

    //Films
    FILM_NOT_FOUND("Film with id: '%s' not found."),

    //Reviews
    REVIEW_NOT_FOUND("Review with id: '%s' not found."),

    //Careers
    CAREER_NOT_FOUND("Career '%s' is not found."),
    CAREER_ALREADY_EXIST("Career '%s' is already exists.")


    ;
    private final String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String param) {
        return String.format(this.text, param);
    }
}
