package com.example.hakaton.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient implements Serializable {
    @Id
    @GenericGenerator(name = "id", strategy = "com.example.hakaton.util.other.CustomIdGenerator")
    @GeneratedValue(generator = "id")
    @JsonIgnore
    String id;
    @Column(unique = true)
    String pin;
    String name;
    String surname;
    String patronymic;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    Integer sex;
//    @ManyToMany(mappedBy = "patients")
//    List<Hospital> hospitals;

}
