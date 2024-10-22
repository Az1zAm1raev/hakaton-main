package com.example.hakaton.repository.entity;

import com.example.hakaton.entity.KT;
import com.example.hakaton.entity.Uzi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KTRep extends JpaRepository<KT,Long> {
}
