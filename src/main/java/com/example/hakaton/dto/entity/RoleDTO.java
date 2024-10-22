package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDTO implements CommandDTO, QueryDTO {
    Long id;
    @ApiModelProperty(notes = "Название роли для системы", required = true)
    String name;
    @ApiModelProperty(notes = "Название роли на русском")
    @NotEmpty
    @NotBlank
    String nameRu;
}
