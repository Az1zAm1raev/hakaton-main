package com.example.hakaton.service.security;

import com.example.hakaton.dto.security.AuthenticationCommand;
import com.example.hakaton.dto.security.AuthenticationQuery;

import javax.servlet.http.HttpServletResponse;


public interface AuthenticationService {
    AuthenticationQuery generateToken(AuthenticationCommand authRequest, HttpServletResponse response);
}
