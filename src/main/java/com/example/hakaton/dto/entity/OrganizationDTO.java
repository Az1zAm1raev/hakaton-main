package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationDTO implements CommandDTO, QueryDTO {
    Long id;
    String ozShortName;
    String ozFullName;
    String address;

}
