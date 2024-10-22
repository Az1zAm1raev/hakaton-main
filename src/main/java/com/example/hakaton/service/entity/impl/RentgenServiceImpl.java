package com.example.hakaton.service.entity.impl;

import com.example.hakaton.dto.entity.RentgenDTO;
import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.entity.Rentgen;
import com.example.hakaton.entity.Uzi;
import com.example.hakaton.exception.DataNotFoundException;
import com.example.hakaton.repository.entity.RentgenRep;
import com.example.hakaton.service.entity.RentgenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RentgenServiceImpl implements RentgenService {
    ModelMapper modelMapper;

    RentgenRep rentgenRep;
    @Autowired
    public RentgenServiceImpl(ModelMapper modelMapper, RentgenRep rentgenRep) {
        this.modelMapper = modelMapper;
        this.rentgenRep = rentgenRep;
    }

    public RentgenDTO mapToResponse(Rentgen rentgen) {
        RentgenDTO response = modelMapper.map(rentgen, RentgenDTO.class);
        return response;
    }

    public Rentgen mapToEntity(RentgenDTO request) {
        Rentgen pharmacyBranch = modelMapper.map(request, Rentgen.class);
        return pharmacyBranch;
    }
    @Override
    public List<RentgenDTO> getAll() {
        List<Rentgen> rentgens = rentgenRep.findAll();
        return rentgens.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RentgenDTO getById(Long id) {
        Optional<Rentgen> rentgen = rentgenRep.findById(id);
        return rentgen.map(this::mapToResponse).orElseThrow(() -> new DataNotFoundException("Uzi not found with id" + id));
    }

    @Override
    public RentgenDTO create(RentgenDTO rentgenDTO) {
        Rentgen rentgen = mapToEntity(rentgenDTO);
        Rentgen saved = rentgenRep.save(rentgen);
        return mapToResponse(saved);
    }

    @Override
    public RentgenDTO update(Long id, RentgenDTO rentgenDTO) {
        Rentgen rentgen = rentgenRep.findById(id).orElseThrow(()->new DataNotFoundException("Uzi not found with id: " + id));
        rentgen.setName(rentgenDTO.getName());
        rentgen.setCode(rentgenDTO.getCode());

        Rentgen updateRentgen = rentgenRep.save(rentgen);

        RentgenDTO rentgenDTO1 = new RentgenDTO();
        rentgenDTO1.setName(rentgen.getName());
        rentgenDTO1.setCode(rentgen.getCode());

        return rentgenDTO1;
    }

    @Override
    public void delete(Long id) {
        Rentgen pharmacyBranch = rentgenRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Uzi not found with id: " + id));

        rentgenRep.delete(pharmacyBranch);
    }

}
