package com.example.hakaton.convert.security;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.security.AuthenticationCommand;
import com.example.hakaton.dto.security.AuthenticationQuery;
import com.example.hakaton.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper implements BaseMapper<User> {
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(User entity, Query query) {
        if (query instanceof AuthenticationQuery) {
            return (Query) this.modelMapper.map(entity, AuthenticationQuery.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> User commandToEntity(Command command) {
        if (command instanceof AuthenticationCommand) {
            return this.modelMapper.map(command, User.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(User full, User withNullFields) {
        //not will use
    }
}
