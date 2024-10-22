package com.example.hakaton.dto.security;

import com.example.hakaton.dto.CommandDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationCommand implements CommandDTO {
    String pin;
    String password;
    Long ozId;
}
