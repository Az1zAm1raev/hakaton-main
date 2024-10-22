package com.example.hakaton.convert.entity;

import com.example.hakaton.convert.BaseMapper;
import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import com.example.hakaton.entity.MRT;

public class MRTMapper implements BaseMapper<MRT> {
    @Override
    public <Query extends QueryDTO> Query entityToQuery(MRT mrt, Query query) {
        return null;
    }

    @Override
    public <Command extends CommandDTO> MRT commandToEntity(Command command) {
        return null;
    }

    @Override
    public void replaceNullFields(MRT full, MRT withNullFields) {

    }
}
