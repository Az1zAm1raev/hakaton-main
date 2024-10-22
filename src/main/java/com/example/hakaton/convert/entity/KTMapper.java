package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.dto.entity.KTDTO;
import com.example.hakaton.entity.KT;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KTMapper implements BaseMapper<KT> {
    private final ModelMapper modelMapper;

    public KTMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }


    @Override
    public <Query extends QueryDTO> Query entityToQuery(KT entity, Query query) {
        if (query instanceof KTDTO) {
            return (Query) this.modelMapper.map(entity, KTDTO.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + query.getClass().getName());
    }

    @Override
    public <Command extends CommandDTO> KT commandToEntity(Command command) {
        if (command instanceof KTDTO) {
            return this.modelMapper.map(command, KT.class);
        } else
            throw new IllegalArgumentException("Unknown query type: " + command.getClass().getName());
    }

    @Override
    public void replaceNullFields(KT full, KT withNullFields) {
        this.modelMapper.map(full, withNullFields);
    }
}
