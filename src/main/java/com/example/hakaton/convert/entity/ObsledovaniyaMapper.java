package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import com.example.hakaton.entity.Obsledovanie;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObsledovaniyaMapper implements BaseMapper<Obsledovanie> {
    private final ModelMapper modelMapper;

    public ObsledovaniyaMapper(ModelMapper modelMapper, OrganizationsRepository organizationsRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
      

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(Obsledovanie entity, Query query) {
        if (query instanceof ObsledovaniyaDTO) {
            return (Query) this.modelMapper.map(entity, ObsledovaniyaDTO.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> Obsledovanie commandToEntity(Command command) {
        if (command instanceof ObsledovaniyaDTO) {
           return this.modelMapper.map(command, Obsledovanie.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(Obsledovanie full, Obsledovanie withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
