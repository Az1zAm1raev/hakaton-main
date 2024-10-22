package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.OrganizationMapper;
import com.example.hakaton.dto.entity.OrganizationDTO;
import com.example.hakaton.dto.entity.RoleDTO;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.service.entity.OrganizationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrganizationsServiceImpl implements OrganizationService {
    UserRepository userRepository;
    OrganizationsRepository organizationsRepository;
    PasswordEncoder passwordEncoder;
    OrganizationMapper mapper;

    @Autowired
    public OrganizationsServiceImpl(UserRepository userRepository,
                                    OrganizationsRepository organizationsRepository,
                                    PasswordEncoder passwordEncoder,
                                    OrganizationMapper modelMapper) {
        this.userRepository = userRepository;
        this.organizationsRepository = organizationsRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = modelMapper;
    }

    @Override
    public List<OrganizationDTO> getByUser(String pin, String password) {
        User user = userRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CustomException(CustomError.USER_NOT_AUTHENTICATE);

        List<Organization> organizations = new ArrayList<>();
        if (user.getOrganizations().isEmpty()) {
            throw new CustomException(CustomError.USER_NOT_HAVE_ANY_ORGANISATION);
        } else {
            for (Organization organizationId : user.getOrganizations()) {
                Organization organization = organizationsRepository.findById(organizationId.getId())
                        .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));
                organizations.add(organization);
            }
        }

        return organizations.stream().map(i -> {
            return mapper.entityToQuery(i, new OrganizationDTO());
        }).collect(Collectors.toList());
    }

    @Override
    public Page<OrganizationDTO> getAll(int page,
                                        int size,
                                        Optional<Boolean> sortOrder,
                                        String sortBy) {
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

        return this.organizationsRepository.findAll(paging).map(p -> this.mapper.entityToQuery(p, new OrganizationDTO()));
    }

    @Override
    public List<OrganizationDTO> getList() {
        List<Organization> list = this.organizationsRepository.findAll();

        return list.stream().map(i -> {
            return mapper.entityToQuery(i, new OrganizationDTO());
        }).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getById(Long id) {
        Organization entity = organizationsRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));

        return mapper.entityToQuery(entity, new OrganizationDTO());
    }

    @Override
    public OrganizationDTO create(OrganizationDTO command) {
        Organization entityToSave = mapper.commandToEntity(command);

        Organization savedEntity;
        try {
            savedEntity = organizationsRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.ORGANISATION_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new OrganizationDTO());
    }

    @Override
    public OrganizationDTO update(OrganizationDTO command) {
        Long id = command.getId();
        Organization found = organizationsRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));

        Organization entityToSave = mapper.commandToEntity(command);
        mapper.replaceNullFields(entityToSave, found);

        Organization savedEntity;
        try {
            savedEntity = organizationsRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.ORGANISATION_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new OrganizationDTO());
    }

    @Override
    public void delete(Long id) {
        Organization entity = this.organizationsRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));

        try {
            organizationsRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.ORGANISATION_NOT_DELETED, e);
        }
    }
}
