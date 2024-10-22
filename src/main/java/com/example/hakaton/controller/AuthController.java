package com.example.hakaton.controller;

import com.example.hakaton.dto.security.AuthenticationCommand;
import com.example.hakaton.dto.security.AuthenticationQuery;
import com.example.hakaton.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AuthenticationQuery generateToken(@Valid @RequestBody AuthenticationCommand authRequest, HttpServletResponse response) {
        return authenticationService.generateToken(authRequest, response);

    }
}

