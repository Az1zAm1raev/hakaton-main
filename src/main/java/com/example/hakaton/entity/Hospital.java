package com.example.hakaton.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hospital{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column
    String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    User user;

//    @ManyToMany
//    @JoinTable(
//            name = "hospital_patient",
//            joinColumns = @JoinColumn(name = "hospital_id"),
//            inverseJoinColumns = @JoinColumn(name = "patient_id")
//    )
//    private List<Patient> patients;

}
