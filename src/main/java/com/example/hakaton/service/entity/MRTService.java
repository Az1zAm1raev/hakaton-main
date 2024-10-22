package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.MRTDTO;


import java.util.List;

public interface MRTService {
    List<MRTDTO> getAll();

    MRTDTO getById(Long id);

    MRTDTO create(MRTDTO mrtdto);

    MRTDTO update(Long id,MRTDTO mrtdto);

    void delete(Long id);
}
