package com.example.hakaton.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Blank extends Audit<String> implements Serializable {
    @Id
    @GenericGenerator(name = "id", strategy = "com.example.hakaton.util.other.CustomIdGenerator")
    @GeneratedValue(generator = "id")
    String  id;
    String  pin;
    String name;


    String surname;
    String patronymic;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date dateAnaliza;
    String typeAnaliza;
    String result;
    String nameAnaliza;
    @ManyToOne
    Organization organization;
    @ManyToOne
    User doctor;
}
