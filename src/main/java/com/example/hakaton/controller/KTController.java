package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.KTDTO;
import com.example.hakaton.dto.entity.OrganizationDTO;
import com.example.hakaton.service.entity.KTService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/kt")
public class KTController {
    KTService ktService;
    @Autowired
    public KTController(KTService ktService) {
        this.ktService = ktService;
    }
    @GetMapping("/")
    public Page<KTDTO> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                              @RequestParam(required = false, defaultValue = "25") int size,
                              @RequestParam(required = false) Optional<Boolean> sortOrder,
                              @RequestParam(required = false) String sortBy) {
        return ktService.getAllObsledovaniya(page, size, sortOrder, sortBy);
    }

    @PostMapping("/create")
    public ResponseEntity<KTDTO> create(@Valid @RequestBody KTDTO command) {
        KTDTO saveEntity = ktService.createObsledovanie(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public KTDTO getById(@PathVariable Long id) {
        return ktService.getObsledovanieById(id);
    }


    @PutMapping("/update")
    public ResponseEntity<KTDTO> updateOrganization(@Valid @RequestBody KTDTO command) {
        KTDTO updatedEntity = ktService.updateObsledovanie(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deletedOrganization(@PathVariable Long id) {
        ktService.deleteObsledovanie(id);
    }
}