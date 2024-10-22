package com.example.hakaton.exception;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject responseBody = new JSONObject();
        if (ex.getError() != null) {
            responseBody.put("error", ex.getError().getStatus().toString());
            responseBody.put("series", ex.getError().getStatus().series());
            responseBody.put("message", ex.getError().getMessage());
        }

        if (ex.getException() != null) {
            JSONObject exception = new JSONObject();
            exception.put("class", ex.getException().getClass());
            exception.put("cause", ex.getException().getCause());
            exception.put("message", ex.getException().getMessage());
            responseBody.put("exception", exception);
        }

        return new ResponseEntity<>(responseBody.toString(), headers, ex.getError().getStatus());
    }
}