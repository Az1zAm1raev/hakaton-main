package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.Uzi;

public class UziMapper implements BaseMapper<Uzi> {
    @Override
    public <Query extends QueryDTO> Query entityToQuery(Uzi uzi, Query query) {
        return null;
    }

    @Override
    public <Command extends CommandDTO> Uzi commandToEntity(Command command) {
        return null;
    }

    @Override
    public void replaceNullFields(Uzi full, Uzi withNullFields) {

    }
}
