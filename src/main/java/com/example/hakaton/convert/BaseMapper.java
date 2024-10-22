package com.example.hakaton.convert;


import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;

public interface BaseMapper<Entity> {
    <Query extends QueryDTO> Query entityToQuery(Entity entity, Query query);

    <Command extends CommandDTO> Entity commandToEntity(Command command);

    void replaceNullFields(Entity full, Entity withNullFields);
}
