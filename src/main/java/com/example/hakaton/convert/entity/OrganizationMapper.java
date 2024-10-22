package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.OrganizationDTO;
import com.example.hakaton.dto.security.AuthenticationCommand;
import com.example.hakaton.dto.security.AuthenticationQuery;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper implements BaseMapper<Organization> {
    private final ModelMapper modelMapper;

    public OrganizationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(Organization entity, Query query) {
        if (query instanceof OrganizationDTO) {
            return (Query) this.modelMapper.map(entity, OrganizationDTO.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> Organization commandToEntity(Command command) {
        if (command instanceof OrganizationDTO) {
            return this.modelMapper.map(command, Organization.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(Organization full, Organization withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
