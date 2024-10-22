package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlankDTO implements QueryDTO, CommandDTO {
    String id;
    String  pin;
    String name;


    String surname;
    String patronymic;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date dateAnaliza;
    Long organizationId;
    Long doctorId;
    String result;
    String nameAnaliza;
    String typeAnaliza;
}
