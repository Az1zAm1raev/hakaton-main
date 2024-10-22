package com.example.hakaton.dto.security;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationCommand implements CommandDTO {
    String pin;
    String password;
    Role role;
}
