package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.dto.entity.DocumentQuery;
import com.example.hakaton.entity.Document;
import com.example.hakaton.entity.Obsledovanie;
import com.example.hakaton.entity.Organization;
import com.example.hakaton.entity.User;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.ObsledovanieRepository;
import com.example.hakaton.repository.security.OrganizationsRepository;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.util.other.CustomUserDetails;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper implements BaseMapper<Document> {
    private final ModelMapper modelMapper;
    private final OrganizationsRepository organizationsRepository;
    private final UserRepository userRepository;
    private final ObsledovanieRepository obsledovanieRepository;

    public DocumentMapper(ModelMapper modelMapper,
                          ObsledovanieRepository obsledovanieRepository,
                          OrganizationsRepository organizationsRepository,
                          UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.obsledovanieRepository = obsledovanieRepository;
        this.organizationsRepository = organizationsRepository;
        this.userRepository = userRepository;

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        Converter<Document, Document> myConverter = new Converter<Document, Document>() {
            public Document convert(MappingContext<Document, Document> context) {
                Document source = context.getSource();
                Document destination = context.getDestination();

                destination.setId(destination.getId() != null ? destination.getId() : source.getId());
                destination.setPin(destination.getPin() != null ? destination.getPin() : source.getPin());
                destination.setName(destination.getName() != null ? destination.getName() : source.getName());
                destination.setSurname(destination.getSurname() != null ? destination.getSurname() : source.getSurname());
                destination.setPatronymic(destination.getPatronymic() != null ? destination.getPatronymic() : source.getPatronymic());
                destination.setSex(destination.getSex() != null ? destination.getSex() : source.getSex());
                destination.setBirthDate(destination.getBirthDate() != null ? destination.getBirthDate() : source.getBirthDate());
                destination.setDateAnaliza(destination.getDateAnaliza() != null ? destination.getDateAnaliza() : source.getDateAnaliza());
                destination.setDescription(destination.getDescription() != null ? destination.getDescription() : source.getDescription());
                destination.setZaklyuchenie(destination.getZaklyuchenie() != null ? destination.getZaklyuchenie() : source.getZaklyuchenie());
                destination.setObsledovanie(destination.getObsledovanie() != null ? destination.getObsledovanie() : source.getObsledovanie());
                destination.setOrganization(destination.getOrganization() != null ? destination.getOrganization() : source.getOrganization());
                destination.setDoctor(destination.getDoctor() != null ? destination.getDoctor() : source.getDoctor());
                destination.setFiles(destination.getFiles() != null ? destination.getFiles() : source.getFiles());

                return destination;
            }
        };

        modelMapper.addConverter(myConverter);
    }

    @Override
    public <Query extends QueryDTO> Query entityToQuery(Document entity, Query query) {
        if (query instanceof DocumentQuery) {
            DocumentQuery result = this.modelMapper.map(entity, DocumentQuery.class);
            String sex = (entity.getSex() == null) ? "" : (entity.getSex() == 1) ? "Мужской" : "Женский";
            result.setSex(sex);
            return (Query) result;
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> Document commandToEntity(Command command) {
        if (command instanceof DocumentCommand) {
            Long obsledovanieId = ((DocumentCommand) command).getObsledovanieId();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            Long organizationId = customUserDetails.getOrganization().getId();
            Long doctorId = customUserDetails.getUser().getId();

            Organization organization = null;
            User user = null;
            Obsledovanie obsledovanie = null;

            Document entity = this.modelMapper.map(command, Document.class);

            if (organizationId != null)
                organization = organizationsRepository.findById(organizationId)
                        .orElseThrow(() -> new CustomException(CustomError.ORGANISATION_NOT_FOUND));
            if (doctorId != null)
                user = userRepository.findById(doctorId)
                        .orElseThrow(() -> new CustomException(CustomError.USER_NOT_FOUND));
            if (obsledovanieId != null)
                obsledovanie = obsledovanieRepository.findById(obsledovanieId)
                        .orElseThrow(() -> new CustomException(CustomError.OBSLEDOVANIE_NOT_FOUND));

            entity.setOrganization(organization);
            entity.setDoctor(user);
            entity.setObsledovanie(obsledovanie);

            return entity;
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(Document source, Document destination) {
        this.modelMapper.map(source, destination);
    }
}
