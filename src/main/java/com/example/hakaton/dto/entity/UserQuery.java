package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserQuery implements QueryDTO {
    Long id;
    String pin;
    String surname;
    String name;
    String patronymic;
    List<Organization> organizations;
    List<Role> roles;
    String phone;
    String email;
    String password;
}
