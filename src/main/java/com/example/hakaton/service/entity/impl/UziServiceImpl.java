package com.example.hakaton.service.entity.impl;

import com.example.hakaton.dto.entity.HospitalDTO;
import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.entity.Uzi;
import com.example.hakaton.exception.DataNotFoundException;
import com.example.hakaton.repository.entity.UziRep;
import com.example.hakaton.service.entity.UziService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UziServiceImpl implements UziService {
    UziRep uziRep;
    ModelMapper modelMapper;
    @Autowired
    public UziServiceImpl(UziRep uziRep, ModelMapper modelMapper) {
        this.uziRep = uziRep;
        this.modelMapper = modelMapper;
    }

    public UziDTO mapToResponse(Uzi uzi) {
        UziDTO response = modelMapper.map(uzi, UziDTO.class);
        return response;
    }

    public Uzi mapToEntity(UziDTO request) {
        Uzi pharmacyBranch = modelMapper.map(request, Uzi.class);
        return pharmacyBranch;
    }

    @Override
    public List<UziDTO> getAll() {
        List<Uzi> pharmacyBranches = uziRep.findAll();
        return pharmacyBranches.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UziDTO getById(Long id) {
        Optional<Uzi> uzi = uziRep.findById(id);
        return uzi.map(this::mapToResponse).orElseThrow(() -> new DataNotFoundException("Uzi not found with id" + id));
    }


    @Override
    public UziDTO create(UziDTO request) {
        Uzi pharmacyBranch = mapToEntity(request);
        Uzi saved = uziRep.save(pharmacyBranch);
        return mapToResponse(saved);
    }

    @Override
    public UziDTO update(Long id,UziDTO blankDTO) {
        Uzi uzi = uziRep.findById(id).orElseThrow(()->new DataNotFoundException("Uzi not found with id: " + id));
        uzi.setName(blankDTO.getName());
        uzi.setCode(blankDTO.getCode());

        Uzi updatedUzi = uziRep.save(uzi);

        UziDTO uziDTO = new UziDTO();
        uziDTO.setName(uzi.getName());
        uziDTO.setCode(uzi.getCode());

        return uziDTO;
    }

    @Override
    public void delete(Long id) {
        Uzi pharmacyBranch = uziRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Uzi not found with id: " + id));

        uziRep.delete(pharmacyBranch);
    }

}
