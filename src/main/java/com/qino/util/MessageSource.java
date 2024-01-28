package com.qino.util;

public enum MessageSource {
    USER_NOT_FOUND("User not found"),
    USERNAME_NOT_FOUND("Username not found"),
    USERNAME_IS_ALREADY_TAKEN("Username is already taken"),
    USER_REGISTERED_SUCCESS("User registered success"),
    USER_SIGNED_SUCCESS("User signed success");


    private final String text;

    MessageSource(String text) {
        this.text = text;
    }

    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
