package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.HospitalDTO;
import com.example.hakaton.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalService {
    List<HospitalDTO> getAll();
    HospitalDTO getById(Long id);
    HospitalDTO create(HospitalDTO command);
    HospitalDTO update(HospitalDTO command);
    void delete(Long id);
//    ?
    List<Patient> getList();
}
