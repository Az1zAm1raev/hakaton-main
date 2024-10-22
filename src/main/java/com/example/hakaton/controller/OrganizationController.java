package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.OrganizationDTO;
import com.example.hakaton.service.entity.OrganizationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrganizationDTO> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "25") int size,
                                        @RequestParam(required = false) Optional<Boolean> sortOrder,
                                        @RequestParam(required = false) String sortBy) {
        return organizationService.getAll(page, size, sortOrder, sortBy);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationDTO> create(@Valid @RequestBody OrganizationDTO command) {
        OrganizationDTO saveEntity = organizationService.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationDTO getById(@PathVariable Long id) {
        return organizationService.getById(id);
    }

    @GetMapping("/getByUser")
    public List<OrganizationDTO> getByUser(@RequestParam String pin, @RequestParam String password) {
        return organizationService.getByUser(pin, password);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrganizationDTO> updateOrganization(@Valid @RequestBody OrganizationDTO command) {
        OrganizationDTO updatedEntity = organizationService.update(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletedOrganization(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
