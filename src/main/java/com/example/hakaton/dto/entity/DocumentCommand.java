package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentCommand implements CommandDTO {
    String id;
    String name;
    String surname;
    String patronymic;
    Long sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date dateAnaliza;
    String description;
    String zaklyuchenie;
    String pin;
    Long obsledovanieId;
}
