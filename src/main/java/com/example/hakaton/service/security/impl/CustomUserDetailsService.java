package com.example.hakaton.service.security.impl;

import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.util.other.CustomUserDetails;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    OrganizationsRepository organizationsRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, OrganizationsRepository organizationsRepository) {
        this.userRepository = userRepository;
        this.organizationsRepository = organizationsRepository;
    }

    public CustomUserDetails loadUser(String pin, Long ozId) throws UsernameNotFoundException {
        User user = userRepository.findByPin(pin)
                .orElseThrow(() -> new UsernameNotFoundException(pin));

        List<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());

        Organization organization = organizationsRepository.findById(ozId)
                .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));

        return new CustomUserDetails(user, organization, grantedAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {

        User user = userRepository.findByPin(pin)
                .orElseThrow(() -> new UsernameNotFoundException(pin));

        List<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());

        return new CustomUserDetails(user, new Organization(), grantedAuthorities);
    }
}
