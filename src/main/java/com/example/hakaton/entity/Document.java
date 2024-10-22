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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Document extends Audit<String> implements Serializable {
    @Id
    @GenericGenerator(name = "id", strategy = "com.example.hakaton.util.other.CustomIdGenerator")
    @GeneratedValue(generator = "id")
    String id;

    String pin;

    String name;
    String surname;
    String patronymic;
    Long sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bishkek")
    Date dateAnaliza;
    @Column(columnDefinition = "TEXT")
    String description;
    @Column(columnDefinition = "TEXT")
    String zaklyuchenie;

    @ManyToOne
    Obsledovanie obsledovanie;
    @ManyToOne
    Organization organization;
    @ManyToOne
    User doctor;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<UploadFile> files = new ArrayList<>();
}
