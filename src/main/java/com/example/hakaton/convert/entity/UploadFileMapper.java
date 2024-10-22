package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.UploadFileDTO;
import com.example.hakaton.entity.UploadFile;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.RoleRepository;
import lombok.experimental.NonFinal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class UploadFileMapper implements BaseMapper<UploadFile> {
    private final ModelMapper modelMapper;
    private final OrganizationsRepository organizationsRepository;
    private final RoleRepository roleRepository;
    @Value("${hakaton.export.path}")
    @NonFinal
    String uploadPath;

    public UploadFileMapper(ModelMapper modelMapper, OrganizationsRepository organizationsRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.organizationsRepository = organizationsRepository;
        this.roleRepository = roleRepository;

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(UploadFile entity, Query query) {
        if (query instanceof UploadFileDTO) {
            UploadFileDTO result = this.modelMapper.map(entity, UploadFileDTO.class);
            result.setFullPath(Paths.get(entity.getPath()));
            return (Query) result;
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> UploadFile commandToEntity(Command command) {
        if (command instanceof UploadFileDTO) {
            UploadFile entity = this.modelMapper.map(command, UploadFile.class);
            entity.setPath(uploadPath + entity.getId() + entity.getName());
            return entity;
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(UploadFile full, UploadFile withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
