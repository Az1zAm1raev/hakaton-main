package com.example.hakaton.repository.entity;

import com.example.hakaton.entity.Obsledovanie;
import com.example.hakaton.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByPin(String pin);

//    List<Patient> getList();
}
