package com.qino.util;

import lombok.Data;

@Data
public class Generator {
    public String generateFullName(String firstName, String secondName) {
        return firstName.trim() + " " + secondName.trim();
    }

}
