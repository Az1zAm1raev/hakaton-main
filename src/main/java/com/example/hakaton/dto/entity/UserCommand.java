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
public class UserCommand implements CommandDTO {
    Long id;
    @ApiModelProperty(notes = "Пин")
    String pin;
    @ApiModelProperty(notes = "Фамилия")
    String surname;
    @ApiModelProperty(notes = "Имя")
    String name;
    @ApiModelProperty(notes = "Отчество")
    String patronymic;
    @ApiModelProperty(notes = "Организации")
    List<Long> organizations;
    @ApiModelProperty(notes = "Роли")
    List<Long> roles;
    @ApiModelProperty(notes = "Телефон")
    String phone;
    @ApiModelProperty(notes = "E-Mail")
    @Email(message = "Провертье правильность Email")
    String email;
    @ApiModelProperty(notes = "Пароль")
    String password;
}
