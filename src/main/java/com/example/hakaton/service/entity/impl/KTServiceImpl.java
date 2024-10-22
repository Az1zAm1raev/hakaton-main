package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.KTMapper;
import com.example.hakaton.dto.entity.KTDTO;
import com.example.hakaton.dto.entity.RoleDTO;
import com.example.hakaton.dto.entity.UserQuery;
import com.example.hakaton.entity.KT;
import com.example.hakaton.entity.Role;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.KTRep;
import com.example.hakaton.service.entity.KTService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@Slf4j
public class KTServiceImpl implements KTService {
    KTRep ktRep;

    KTMapper mapper;
    @Autowired
    public KTServiceImpl(KTRep ktRep,KTMapper mapper) {
        this.ktRep = ktRep;
        this.mapper= mapper;
    }

    @Override
    public KTDTO createObsledovanie(KTDTO command) {
        log.info("Trying to create Obsledovanie: {}", command);

        KT entityToSave = mapper.commandToEntity(command);

        KT savedEntity;
        try {
            savedEntity = ktRep.save(entityToSave);
            log.info("Obsledovanie created successfully: {}", savedEntity);
        } catch (Exception e) {
            log.error("Error creating Obsledovanie", e);
            throw new CustomException(CustomError.ROLE_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new KTDTO());
    }

    @Override
    public KTDTO getObsledovanieById(Long id) {
        log.info("Trying to retrieve Obsledovanie by ID: {}", id);

        KT entity = ktRep.findById(id)
                .orElseThrow(() -> {
                    log.warn("Obsledovanie not found for ID: {}", id);
                    return new CustomException(CustomError.ROLE_NOT_FOUND);
                });

        log.info("Obsledovanie found: {}", entity);

        return mapper.entityToQuery(entity, new KTDTO());
    }

    @Override
    public Page<KTDTO> getAllObsledovaniya(int page,
                                           int size,
                                           Optional<Boolean> sortOrder,
                                           String sortBy) {
        log.info("Trying to retrieve all Obsledovaniya with pagination - page: {}, size: {}, sortOrder: {}, sortBy: {}",
                page, size, sortOrder, sortBy);

        Pageable paging = null;
        if (sortOrder.isPresent()) {
            Sort.Direction direction = sortOrder.get() ? Sort.Direction.ASC : Sort.Direction.DESC;
            paging = PageRequest.of(page, size, direction, sortBy);
        } else {
            paging = PageRequest.of(page, size);
        }

        Page<KTDTO> result = this.ktRep.findAll(paging).map(p -> this.mapper.entityToQuery(p, new KTDTO()));

        log.info("Obsledovaniya retrieved successfully: {}", result);

        return result;
    }
    @Override
    public KTDTO updateObsledovanie(KTDTO command) {
        log.info("Trying to update Obsledovanie: {}", command);

        Long id = command.getId();
        KT found = ktRep.findById(id)
                .orElseThrow(() -> {
                    log.warn("Obsledovanie not found for ID: {}", id);
                    return new CustomException(CustomError.ROLE_NOT_FOUND);
                });

        KT entityToSave = mapper.commandToEntity(command);
        mapper.replaceNullFields(entityToSave, found);

        KT savedEntity;
        try {
            savedEntity = ktRep.save(entityToSave);
            log.info("Obsledovanie updated successfully: {}", savedEntity);
        } catch (Exception e) {
            log.error("Error updating Obsledovanie", e);
            throw new CustomException(CustomError.ROLE_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new KTDTO());
    }

    @Override
    public void deleteObsledovanie(Long id) {
        log.info("Trying to delete Obsledovanie with ID: {}", id);

        KT entity = this.ktRep.findById(id)
                .orElseThrow(() -> {
                    log.warn("Obsledovanie not found for ID: {}", id);
                    return new CustomException(CustomError.ROLE_NOT_FOUND);
                });

        try {
            ktRep.deleteById(entity.getId());
            log.info("Obsledovanie deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting Obsledovanie", e);
            throw new CustomException(CustomError.ROLE_NOT_DELETED, e);
        }
    }
}
