package com.process.shop.model.enunm;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    USER_NOT_FOUND("User not found!"),
    USER_EMAIL_EXISTS("This email is already registered!");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}