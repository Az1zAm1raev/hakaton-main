package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.Obsledovanie;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentQuery implements QueryDTO {
    String id;
    String pin;
    String name;
    String surname;
    String patronymic;
    String sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date dateAnaliza;
    String description;
    String zaklyuchenie;
    Obsledovanie obsledovanie;
    Organization organization;
    User doctor;
}
