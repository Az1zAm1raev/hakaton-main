package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.RoleDTO;
import com.example.hakaton.service.entity.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/role")
public class RoleController {
    RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<RoleDTO> getAll() {
        return roleService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO command) {
        RoleDTO saveEntity = roleService.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public RoleDTO getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> updateRole(@Valid @RequestBody RoleDTO command) {
        RoleDTO updatedEntity = roleService.update(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletedRole(@PathVariable Long id) {
        roleService.delete(id);
    }
}
