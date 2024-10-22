package com.example.hakaton.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandException extends RuntimeException {

    HttpStatus status;
    String code;
    String service = "kg.cez.e-recipe";
    String message;

    public CommandException(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
