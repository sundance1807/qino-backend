package com.qino.util;

public enum MessageSource {
    //System

    //Users
    USER_NOT_FOUND("User with id: '%s' not found."),
    USERNAME_IS_ALREADY_TAKEN("Username '%s' is already taken."),
    INVALID_USERNAME_FORMAT("Invalid username format"),

    //Genres
    GENRE_IS_ALREADY_EXIST("Genre '%s' is already exist."),
    GENRE_NOT_FOUND("Genre with id: '%s' is not found."),

    //Films
    FILM_NOT_FOUND("Film with id: '%s' not found."),

    //Reviews
    REVIEW_NOT_FOUND("Review with id: '%s' not found."),

    //Careers
    CAREER_NOT_FOUND("Career '%s' is not found."),
    CAREER_ALREADY_EXIST("Career '%s' is already exists."),

    //Persons
    PERSON_NOT_FOUND("Person with id: '%s' not found."),

    ;
    private final String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String param) {
        return String.format(this.text, param);
    }
}
