package com.qino.model;

import lombok.Getter;

@Getter
public enum Currency {

    USD("$");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }
}
