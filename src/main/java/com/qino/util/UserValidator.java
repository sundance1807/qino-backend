package com.qino.util;

import com.qino.exception.CustomException;
import com.qino.repository.UserRepository;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String USERNAME_PATTERN =
        "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

    public boolean usernameIsValid(final String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
