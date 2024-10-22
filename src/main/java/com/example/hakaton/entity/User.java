package com.example.hakaton.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "_user")
public class User extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String pin;
    @Column(nullable = false)
    String surname;
    @Column(nullable = false)
    String name;
    String patronymic;
    @ManyToMany
    @JoinTable(name = "_user_organization",
            joinColumns = @JoinColumn(name = "_user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    List<Organization> organizations;
    String phone;
    @Column(unique = true)
    String email;
    @Column(nullable = false)
    String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "_user_role",
            joinColumns = @JoinColumn(name = "_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;

    public String getFIO() {
        if (this.patronymic == null)
            return this.surname + " " + this.name;
        else
            return this.surname + " " + this.name + " " + this.patronymic;
    }
}
