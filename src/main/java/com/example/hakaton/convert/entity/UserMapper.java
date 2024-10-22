package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.UserCommand;
import com.example.hakaton.dto.entity.UserQuery;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.Role;
import com.example.hakaton.entity.User;
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
public class UserMapper implements BaseMapper<User> {
    private final ModelMapper modelMapper;
    private final OrganizationsRepository organizationsRepository;
    private final RoleRepository roleRepository;

    public UserMapper(ModelMapper modelMapper, OrganizationsRepository organizationsRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.organizationsRepository = organizationsRepository;
        this.roleRepository = roleRepository;

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(new PropertyMap<User, User>() {
            @Override
            protected void configure() {
                skip(destination.getOrganizations());
                skip(destination.getRoles());
            }
        });
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(User entity, Query query) {
        if (query instanceof UserQuery) {
            return (Query) this.modelMapper.map(entity, UserQuery.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> User commandToEntity(Command command) {
        if (command instanceof UserCommand) {
            List<Long> organizationIds = ((UserCommand) command).getOrganizations();
            List<Long> roleIds = ((UserCommand) command).getRoles();
            List<Organization> organizations = new ArrayList<Organization>();
            List<Role> roles = new ArrayList<Role>();

            User entity = this.modelMapper.map(command, User.class);

            for (Long id : organizationIds) {
                Organization organization = organizationsRepository.findById(id)
                        .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));
                organizations.add(organization);
            }
            for (Long id : roleIds) {
                Role role = roleRepository.findById(id)
                        .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));
                roles.add(role);
            }

            entity.setOrganizations(organizations);
            entity.setRoles(roles);

            return entity;
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(User full, User withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
