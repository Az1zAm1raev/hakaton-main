package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.dto.entity.BlankDTO;
import com.example.hakaton.service.entity.BlankService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/blank")
public class BlankController {
    private final BlankService service;

    public BlankController(BlankService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Page<BlankDTO> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "25") int size,
                                      @RequestParam(required = false) Optional<Boolean> sortOrder,
                                      @RequestParam(required = false) String sortBy) {
        return service.getAll(page, size, sortOrder, sortBy);
    }

    @PostMapping("/create")
    public ResponseEntity<BlankDTO> create(@Valid @RequestBody BlankDTO command) {
        BlankDTO saveEntity = service.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public BlankDTO getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/getByPin")
    public BlankDTO getByPin(@RequestParam String pin) {
        return service.getByPin(pin);
    }
    @PutMapping("/update")
    public ResponseEntity<BlankDTO> updateDocument(@Valid @RequestBody BlankDTO command) {
        BlankDTO updatedEntity = service.update(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
