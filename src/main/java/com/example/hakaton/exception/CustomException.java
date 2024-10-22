package com.example.hakaton.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomError error;
    private final Exception exception;

    public CustomException(CustomError error) {
        this.error = error;
        this.exception = null;
    }

    public CustomException(CustomError error, Exception exception) {
        this.error = error;
        this.exception = exception;
    }
}
