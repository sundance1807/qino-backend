package com.qino.util;

public enum ErrorMessageSource {
    USER_NOT_FOUND("Пользователь не найден");


    private String text;
    ErrorMessageSource(String text) {
        this.text = text;
    }
    public String getText(String... params) {
        return String.format(this.text, params);
    }
}
