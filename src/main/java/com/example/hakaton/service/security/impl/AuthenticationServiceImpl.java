package com.example.hakaton.service.security.impl;

import com.example.hakaton.convert.security.AuthenticationMapper;
import com.example.hakaton.dto.security.AuthenticationCommand;
import com.example.hakaton.dto.security.AuthenticationQuery;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.service.security.AuthenticationService;
import com.example.hakaton.util.JwtUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    OrganizationsRepository organizationsRepository;
    AuthenticationMapper mapper;

    @Autowired
    public AuthenticationServiceImpl(JwtUtil jwtUtil,
                                     AuthenticationManager authenticationManager,
                                     UserRepository userRepository,
                                     OrganizationsRepository organizationsRepository,
                                     AuthenticationMapper modelMapper) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.organizationsRepository = organizationsRepository;
        this.mapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationQuery generateToken(AuthenticationCommand command, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getPin(), command.getPassword())
        );

        String jwt = jwtUtil.generateToken(command.getPin(), command.getOzId());


        User user = userRepository.findByPin(command.getPin())
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        Organization organization = null;
        if (user.getOrganizations() != null) {
            for (Organization organizations : user.getOrganizations()) {
                if (organizations.getId() == command.getOzId()) {
                    organization = organizationsRepository.getById(command.getOzId());
                    break;
                }
            }
        }

        if (organization == null)
            throw new CustomException(CustomError.USER_NOT_AUTHENTICATE);

        AuthenticationQuery result = mapper.entityToQuery(user, new AuthenticationQuery());
        result.setOrganization(organization);
        result.setJwtToken(jwt);

        return result;
    }
}
