package com.example.hakaton.service.entity.impl;

import com.example.hakaton.convert.entity.ObsledovaniyaMapper;
import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.entity.Obsledovanie;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.ObsledovanieRepository;
import com.example.hakaton.service.entity.ObsledovaniyaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObsledovaniyaServiceImpl implements ObsledovaniyaService {
    private final ObsledovanieRepository obsledovanieRepository;
    private final ObsledovaniyaMapper obsledovaniyaMapper;

    public ObsledovaniyaServiceImpl(ObsledovanieRepository obsledovanieRepository, ObsledovaniyaMapper obsledovaniyaMapper) {
        this.obsledovanieRepository = obsledovanieRepository;
        this.obsledovaniyaMapper = obsledovaniyaMapper;
    }

    @Override
    public ObsledovaniyaDTO createObsledovanie(ObsledovaniyaDTO obsledovanieDTO) {
        Obsledovanie obsledovanie = this.obsledovaniyaMapper.commandToEntity(obsledovanieDTO);
        Obsledovanie saved = obsledovanieRepository.save(obsledovanie);
        return this.obsledovaniyaMapper.entityToQuery(saved, new ObsledovaniyaDTO());
    }

    @Override
    public ObsledovaniyaDTO getObsledovanieById(Long id) {
        Obsledovanie found = obsledovanieRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        return obsledovaniyaMapper.entityToQuery(found, new ObsledovaniyaDTO());
    }

    @Override
    public List<ObsledovaniyaDTO> getAllObsledovaniya() {
        List<Obsledovanie> list = this.obsledovanieRepository.findAll();

        return list.stream().map(i -> {
            return obsledovaniyaMapper.entityToQuery(i, new ObsledovaniyaDTO());
        }).collect(Collectors.toList());

    }

    @Override
    public ObsledovaniyaDTO updateObsledovanie( ObsledovaniyaDTO obsledovanieDTO) {
            Long id = obsledovanieDTO.getId();
            Obsledovanie found = obsledovanieRepository.findById(id)
                    .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));

            Obsledovanie entityToSave = obsledovaniyaMapper.commandToEntity(obsledovanieDTO);
        obsledovaniyaMapper.replaceNullFields(entityToSave, found);

            Obsledovanie savedEntity;
            try {
                savedEntity = obsledovanieRepository.save(entityToSave);
            } catch (Exception e) {
                throw new CustomException(CustomError.ROLE_NOT_UPDATED, e);
            }

            return obsledovaniyaMapper.entityToQuery(savedEntity, new ObsledovaniyaDTO());
        }


    @Override
    public void deleteObsledovanie(Long id) {
        Obsledovanie entity = this.obsledovanieRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.ROLE_NOT_FOUND));

        try {
            obsledovanieRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.ROLE_NOT_DELETED, e);
        }
    }


}
