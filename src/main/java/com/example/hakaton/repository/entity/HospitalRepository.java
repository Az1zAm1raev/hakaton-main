package com.example.hakaton.repository.entity;

import com.example.hakaton.dto.entity.HospitalDTO;
import com.example.hakaton.entity.Hospital;
import com.example.hakaton.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

//    List<HospitalDTO> getAll();
//
//    Hospital getById(Long id);

}
