package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.RentgenDTO;
import com.example.hakaton.dto.entity.UziDTO;

import java.util.List;

public interface RentgenService {
    List<RentgenDTO> getAll();

    RentgenDTO getById(Long id);

    RentgenDTO create(RentgenDTO rentgenDTO);

    RentgenDTO update(Long id,RentgenDTO rentgenDTO);

    void delete(Long id);

}
