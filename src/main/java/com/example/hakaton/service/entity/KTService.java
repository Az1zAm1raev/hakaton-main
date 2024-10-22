package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.KTDTO;
import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.dto.entity.OrganizationDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface KTService {
    KTDTO createObsledovanie(KTDTO command);
    KTDTO getObsledovanieById(Long id);
    Page<KTDTO> getAllObsledovaniya(int page,
                                 int size,
                                 Optional<Boolean> sortOrder,
                                 String sortBy);
    KTDTO updateObsledovanie(KTDTO command);
    void deleteObsledovanie(Long id);
}
