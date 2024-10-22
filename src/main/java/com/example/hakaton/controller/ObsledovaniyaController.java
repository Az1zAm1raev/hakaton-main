package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.ObsledovaniyaDTO;
import com.example.hakaton.service.entity.ObsledovaniyaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obsledovanie")
public class ObsledovaniyaController{
    private final ObsledovaniyaService obsledovanieService;

    public ObsledovaniyaController(ObsledovaniyaService obsledovanieService) {
        this.obsledovanieService = obsledovanieService;
    }

    @PostMapping
    public ObsledovaniyaDTO createObsledovanie(@RequestBody ObsledovaniyaDTO obsledovanieDTO) {
        return obsledovanieService.createObsledovanie(obsledovanieDTO);
    }

    @GetMapping("/{id}")
    public ObsledovaniyaDTO getObsledovanieById(@PathVariable("id") Long id) {
        return obsledovanieService.getObsledovanieById(id);
    }

    @GetMapping
    public List<ObsledovaniyaDTO> getAllObsledovaniya() {
        return obsledovanieService.getAllObsledovaniya();
    }

    @PutMapping("/{id}")
    public ObsledovaniyaDTO updateObsledovanie(@RequestBody ObsledovaniyaDTO obsledovanieDTO) {
        return obsledovanieService.updateObsledovanie(obsledovanieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteObsledovanie(@PathVariable("id") Long id) {
        obsledovanieService.deleteObsledovanie(id);
    }

}
