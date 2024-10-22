package com.example.hakaton.repository.entity;

import com.example.hakaton.entity.Obsledovanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObsledovanieRepository extends JpaRepository<Obsledovanie, Long> {
}
