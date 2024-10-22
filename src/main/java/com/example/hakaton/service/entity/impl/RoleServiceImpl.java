package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.RoleMapper;
import com.example.hakaton.dto.entity.RoleDTO;
import com.example.hakaton.entity.Role;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.RoleRepository;
import com.example.hakaton.service.entity.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper mapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RoleDTO> getAll() {
        List<Role> list = this.roleRepository.findAll();

        return list.stream().map(i -> {
            return mapper.entityToQuery(i, new RoleDTO());
        }).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getById(Long id) {
        Role entity = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));

        return mapper.entityToQuery(entity, new RoleDTO());
    }

    @Override
    public RoleDTO create(RoleDTO command) {
        Role entityToSave = mapper.commandToEntity(command);

        Role savedEntity;
        try {
            savedEntity = roleRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.ROLE_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new RoleDTO());
    }

    @Override
    public RoleDTO update(RoleDTO command) {
        Long id = command.getId();
        Role found = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));

        Role entityToSave = mapper.commandToEntity(command);
        mapper.replaceNullFields(entityToSave, found);

        Role savedEntity;
        try {
            savedEntity = roleRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.ROLE_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new RoleDTO());
    }

    @Override
    public void delete(Long id) {
        Role entity = this.roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));

        try {
            roleRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.ROLE_NOT_DELETED, e);
        }
    }
}
