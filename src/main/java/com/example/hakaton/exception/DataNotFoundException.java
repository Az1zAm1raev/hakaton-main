package com.example.hakaton.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataNotFoundException extends CommandException {

    public DataNotFoundException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                "unified.core.basic.error.not-found",
                message
        );
    }
}
