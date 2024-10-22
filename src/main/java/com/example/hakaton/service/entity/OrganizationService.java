package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.OrganizationDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface OrganizationService {
    List<OrganizationDTO> getByUser(String pin, String password);

    Page<OrganizationDTO> getAll(int page,
                                 int size,
                                 Optional<Boolean> sortOrder,
                                 String sortBy);

    List<OrganizationDTO> getList();

    OrganizationDTO getById(Long id);

    OrganizationDTO create(OrganizationDTO command);

    OrganizationDTO update(OrganizationDTO command);

    void delete(Long id);
}
