package com.example.hakaton.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class KT extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String code;
//    @ManyToMany
//    @JoinTable(
//            name = "hospital_patient",
//            joinColumns = @JoinColumn(name = "hospital_id"),
//            inverseJoinColumns = @JoinColumn(name = "patient_id")
//    )
//    private List<Patient> patients;
}
