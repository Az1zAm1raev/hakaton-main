package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.UserCommand;
import com.example.hakaton.dto.entity.UserQuery;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface UserService {
    Page<UserQuery> getAll(int page,
                           int size,
                           Optional<Boolean> sortOrder,
                           String sortBy);

    UserQuery getByUserPin(String pin);

    UserQuery getById(Long id);

    UserQuery create(UserCommand command);

    UserQuery update(UserCommand command);

    void delete(Long id);
}
