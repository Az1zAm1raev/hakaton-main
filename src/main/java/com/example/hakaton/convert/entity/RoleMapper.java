package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.RoleDTO;
import com.example.hakaton.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements BaseMapper<Role> {
    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(Role entity, Query query) {
        if (query instanceof RoleDTO) {
            return (Query) this.modelMapper.map(entity, RoleDTO.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> Role commandToEntity(Command command) {
        if (command instanceof RoleDTO) {
            Role entity = this.modelMapper.map(command, Role.class);
            entity.setName("ROLE_" + entity.getName());
            return entity;
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(Role full, Role withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
