package com.example.hakaton.repository.entity;

import com.example.hakaton.entity.Blank;
import com.example.hakaton.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlankRepository extends JpaRepository<Blank,String > {
    Optional<Blank> findByPin(String pin);
}
