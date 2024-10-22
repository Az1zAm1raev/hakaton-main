package com.example.hakaton.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table
public class UploadFile {
    @Id
    @GenericGenerator(name = "id", strategy = "com.example.hakaton.util.other.CustomIdGenerator")
    @GeneratedValue(generator = "id")
    String id;
    @Column(nullable = false)
    String name;
    String path;
    Long size;
}