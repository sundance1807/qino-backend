package com.qino.util;

public enum MessageSource {
    //Users
    USER_NOT_FOUND("User with id: '%s' not found."),
    USERNAME_IS_ALREADY_TAKEN("Username '%s' is already taken."),
    INVALID_USERNAME_FORMAT("Invalid username format"),

    //Genres
    GENRE_IS_ALREADY_EXIST("Genre '%s' is already exist."),
    GENRE_NOT_FOUND("Genre not found."),

    //Films
    FILM_NOT_FOUND("Film with id: '%s' not found."),
    RELEASE_YEAR_OUT_OF_BOUND("Release date out of bound."),

    //Reviews
    REVIEW_NOT_FOUND("Review with id: '%s' not found."),
    UNABLE_TO_UPDATE_BY_ANOTHER_USER("Unable to edit review by another user."),

    //Persons
    PERSON_NOT_FOUND("Person with id: '%s' not found."),
    ;
    private final String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String ...params) {
        return String.format(this.text, params);
    }
}
