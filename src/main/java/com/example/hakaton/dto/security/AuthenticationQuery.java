package com.example.hakaton.dto.security;

import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationQuery implements QueryDTO {
    String pin;
    String surname;
    String name;
    String patronymic;
    String phone;
    String email;
    List<Role> roles;
    Organization organization;
    String jwtToken;
}
