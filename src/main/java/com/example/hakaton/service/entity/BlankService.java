package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.dto.entity.DocumentQuery;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BlankService {
    Page<BlankDTO> getAll(int page,
                               int size,
                               Optional<Boolean> sortOrder,
                               String sortBy);

    BlankDTO getByPin(String pin);

    BlankDTO getById(String id);

    BlankDTO create(BlankDTO blankDTO);

    BlankDTO update(BlankDTO blankDTO);

    void delete(String id);
}
