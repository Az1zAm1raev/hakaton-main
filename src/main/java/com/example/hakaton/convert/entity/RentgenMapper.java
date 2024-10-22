package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.Rentgen;

public class RentgenMapper implements BaseMapper<Rentgen> {
    @Override
    public <Query extends QueryDTO> Query entityToQuery(Rentgen rentgen, Query query) {
        return null;
    }

    @Override
    public <Command extends CommandDTO> Rentgen commandToEntity(Command command) {
        return null;
    }

    @Override
    public void replaceNullFields(Rentgen full, Rentgen withNullFields) {

    }
}
