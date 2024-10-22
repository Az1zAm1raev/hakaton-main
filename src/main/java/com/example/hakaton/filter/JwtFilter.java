package com.example.hakaton.filter;

import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.service.security.impl.CustomUserDetailsService;
import com.example.hakaton.util.JwtUtil;
import com.example.hakaton.util.other.CustomUserDetails;
import org.apache.http.HttpHeaders;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService service;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService service) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        String token = null;
        String pin = null;
        Long ozId = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer_")) {
            token = authorizationHeader.substring(7);
            pin = jwtUtil.extractUsername(token);
            ozId = jwtUtil.extractOzId(token);
        }

        if (pin != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            CustomUserDetails userDetails = service.loadUser(pin, Long.valueOf(ozId));

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

//        Long length = httpServletRequest.getContentLengthLong();
//        if (length != null)
//            if (length >= 1048576)
//                throw new CustomException(CustomError.FILE_TOO_LARGE);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getToken(HttpServletRequest request) {
        Cookie cookie = (Cookie) WebUtils.getCookie(request, cookieName);
        return cookie != null ? cookie.getValue() : null;
    }
}
