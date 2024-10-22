package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.dto.entity.UziDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UziService {

    List<UziDTO> getAll();

    UziDTO getById(Long id);

    UziDTO create(UziDTO blankDTO);

    UziDTO update(Long id,UziDTO blankDTO);

    void delete(Long id);

}
