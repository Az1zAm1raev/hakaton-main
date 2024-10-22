package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.ObsledovaniyaDTO;

import java.util.List;

public interface ObsledovaniyaService {
    ObsledovaniyaDTO createObsledovanie(ObsledovaniyaDTO obsledovanieDTO);
    ObsledovaniyaDTO getObsledovanieById(Long id);
    List<ObsledovaniyaDTO> getAllObsledovaniya();
    ObsledovaniyaDTO updateObsledovanie(ObsledovaniyaDTO obsledovanieDTO);
    void deleteObsledovanie(Long id);
}
