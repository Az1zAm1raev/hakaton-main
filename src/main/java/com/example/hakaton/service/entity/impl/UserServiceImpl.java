package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.UserMapper;
import com.example.hakaton.dto.entity.UserCommand;
import com.example.hakaton.dto.entity.UserQuery;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.service.entity.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserServiceImpl implements UserService{
    UserMapper mapper;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserQuery> getAll(int page, int size, Optional<Boolean> sortOrder, String sortBy) {
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

        return this.userRepository.findAll(paging).map(p -> this.mapper.entityToQuery(p, new UserQuery()));
    }

    @Override
    public UserQuery getByUserPin(String pin) {
        User entity = userRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        return mapper.entityToQuery(entity, new UserQuery());
    }

    @Override
    public UserQuery getById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        return mapper.entityToQuery(entity, new UserQuery());
    }

    @Override
    public UserQuery create(UserCommand command) {
        if (userRepository.findByPin(command.getPin()).isPresent())
            throw new CustomException(CustomError.USER_DUPLICATE_PIN);

        User entityToSave = this.mapper.commandToEntity(command);
        entityToSave.setPassword(passwordEncoder.encode(command.getPassword()));

        User savedEntity;
        try {
            savedEntity = userRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.USER_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new UserQuery());
    }

    @Override
    public UserQuery update(UserCommand command) {
        if (userRepository.findByPin(command.getPin()).isPresent())
            throw new CustomException(CustomError.USER_DUPLICATE_PIN);

        Long id = command.getId();
        User found = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        User entityToSave = mapper.commandToEntity(command);

        mapper.replaceNullFields(found, entityToSave);

        User savedEntity;
        try {
            savedEntity = userRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.USER_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new UserQuery());
    }

    @Override
    public void delete(Long id) {
        User entity = this.userRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));

        try {
            userRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.USER_NOT_DELETED, e);
        }
    }
}
