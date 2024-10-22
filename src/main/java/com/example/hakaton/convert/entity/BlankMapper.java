package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.entity.Blank;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BlankMapper implements BaseMapper<Blank> {
    private final ModelMapper modelMapper;
    private final OrganizationsRepository organizationsRepository;
    private final UserRepository userRepository;

    public BlankMapper(ModelMapper modelMapper, OrganizationsRepository organizationsRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.organizationsRepository = organizationsRepository;
        this.userRepository = userRepository;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(Blank entity, Query query) {
        if (query instanceof BlankDTO) {
            return (Query) this.modelMapper.map(entity, BlankDTO.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> Blank commandToEntity(Command command) {
        if (command instanceof BlankDTO) {
            Long organizationId = ((BlankDTO) command).getOrganizationId();
            Long doctorId = ((BlankDTO) command).getDoctorId();
            Organization organization = null;
            User user = null;
            if (organizationId != null)
                organization = organizationsRepository.findById(organizationId)
                        .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));
            if (doctorId != null)
                user = userRepository.findById(doctorId)
                        .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));
            Blank entity = this.modelMapper.map(command, Blank.class);
            entity.setOrganization(organization);
            entity.setDoctor(user);
            entity.setNameAnaliza("Blank_" + entity.getName());
            return entity;
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(Blank full, Blank withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
