package com.example.hakaton.dto.entity;

import com.example.hakaton.entity.Patient;
import com.example.hakaton.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HospitalDTO {
    Long id;
    String name;
    String address;
    Long user;
//    List<Patient> patients;

}
