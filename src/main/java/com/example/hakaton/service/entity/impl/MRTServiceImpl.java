package com.example.hakaton.service.entity.impl;

import com.example.hakaton.dto.entity.MRTDTO;
import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.entity.MRT;
import com.example.hakaton.entity.Uzi;
import com.example.hakaton.exception.DataNotFoundException;
import com.example.hakaton.repository.entity.MRTRep;
import com.example.hakaton.service.entity.MRTService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MRTServiceImpl implements MRTService {
    ModelMapper modelMapper;

    MRTRep mrtRep;
    @Autowired
    public MRTServiceImpl(ModelMapper modelMapper, MRTRep mrtRep) {
        this.modelMapper = modelMapper;
        this.mrtRep = mrtRep;
    }


    public MRTDTO mapToResponse(MRT mrt) {
        MRTDTO response = modelMapper.map(mrt, MRTDTO.class);
        return response;
    }

    public MRT mapToEntity(MRTDTO request) {
        MRT pharmacyBranch = modelMapper.map(request, MRT.class);
        return pharmacyBranch;
    }


    @Override
    public List<MRTDTO> getAll() {
        List<MRT> mrt = mrtRep.findAll();
        return mrt.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MRTDTO getById(Long id) {
        Optional<MRT> mrt = mrtRep.findById(id);
        return mrt.map(this::mapToResponse).orElseThrow(() -> new DataNotFoundException("Uzi not found with id" + id));
    }


    @Override
    public MRTDTO create(MRTDTO mrtdto) {
        MRT pharmacyBranch = mapToEntity(mrtdto);
        MRT saved = mrtRep.save(pharmacyBranch);
        return mapToResponse(saved);
    }

    @Override
    public MRTDTO update(Long id, MRTDTO mrtdto) {
        MRT mrt = mrtRep.findById(id).orElseThrow(()->new DataNotFoundException("Uzi not found with id: " + id));

        mrt.setName(mrtdto.getName());
        mrt.setCode(mrtdto.getCode());

        MRT updateMRT = mrtRep.save(mrt);

        MRTDTO mrtDTO1 = new MRTDTO();
        mrtDTO1.setName(mrt.getName());
        mrtDTO1.setCode(mrt.getCode());

        return mrtDTO1;
    }

    @Override
    public void delete(Long id) {
        MRT pharmacyBranch = mrtRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Uzi not found with id: " + id));

        mrtRep.delete(pharmacyBranch);
    }
}
