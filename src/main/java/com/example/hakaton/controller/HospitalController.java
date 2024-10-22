package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.HospitalDTO;
import com.example.hakaton.service.entity.HospitalService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/")
    public List<HospitalDTO> getAll() {
        return hospitalService.getAll();
    }

    @GetMapping("/{id}")
    public HospitalDTO getById(@PathVariable Long id) {
        return hospitalService.getById(id);
    }

    @PostMapping("/create")

    public ResponseEntity<HospitalDTO> create(@Valid @RequestBody HospitalDTO command){
        HospitalDTO saveEntity = hospitalService.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HospitalDTO> updateHospital(@Valid @RequestBody HospitalDTO command){
        HospitalDTO updateHospital = hospitalService.update(command);
        return new ResponseEntity<>(updateHospital, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHospital(@PathVariable Long id){ hospitalService.delete(id);}

//    @GetMapping("/patients")
//    public List<Patient> getAll(){ return ?}

}
