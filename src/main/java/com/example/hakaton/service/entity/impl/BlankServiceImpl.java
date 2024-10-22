package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.BlankMapper;
import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.entity.Blank;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.BlankRepository;
import com.example.hakaton.service.entity.BlankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlankServiceImpl implements BlankService {
    BlankMapper mapper;
    BlankRepository blankRepository;

    public BlankServiceImpl(BlankMapper mapper, BlankRepository blankRepository) {
        this.mapper = mapper;
        this.blankRepository = blankRepository;
    }

    @Override
    public Page<BlankDTO> getAll(int page, int size, Optional<Boolean> sortOrder, String sortBy) {
        Pageable paging = null;
        if (sortOrder.isPresent()) {
            Sort.Direction direction = null;

            if (sortOrder.get())
                direction = Sort.Direction.ASC;
            else
                direction = Sort.Direction.DESC;

            paging = PageRequest.of(page, size, direction, sortBy);
        } else {
            paging = PageRequest.of(page, size);
        }

        return this.blankRepository.findAll(paging)
                .map(p -> this.mapper.entityToQuery(p, new BlankDTO()));
    }

    @Override
    public BlankDTO getByPin(String pin) {
        Blank found = blankRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(CustomError.BLANK_NOT_FOUND));

        return mapper.entityToQuery(found, new BlankDTO());
    }

    @Override
    public BlankDTO getById(String id) {
        Blank found = blankRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLANK_NOT_FOUND));

        return mapper.entityToQuery(found, new BlankDTO());
    }

    @Override
    public BlankDTO create(BlankDTO blankDTO) {
        Blank entityToSave = this.mapper.commandToEntity(blankDTO);

        Blank savedEntity;
        try {
            savedEntity = blankRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.BLANK_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new BlankDTO());
    }

    @Override
    public BlankDTO update(BlankDTO blankDTO) {
        String id = blankDTO.getId();
        Blank found = blankRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLANK_NOT_FOUND));

        Blank entityToSave = mapper.commandToEntity(blankDTO);

        mapper.replaceNullFields(found, entityToSave);

        Blank savedEntity;
        try {
            savedEntity = blankRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.BLANK_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new BlankDTO());
    }

    @Override
    public void delete(String id) {
        Blank entity = this.blankRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLANK_NOT_FOUND));

        try {
            blankRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.BLANK_NOT_DELETED, e);
        }
    }
}
