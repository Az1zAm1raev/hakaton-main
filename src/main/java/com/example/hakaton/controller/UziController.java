package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.service.entity.UziService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/uzi")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UziController {
    UziService uziService;

    public UziController(UziService uziService) {
        this.uziService = uziService;
    }

    @PostMapping("/create/uzi")
    public UziDTO add(@RequestBody @Valid UziDTO request) {
        UziDTO pharmacyBranchCreateResponse =uziService.create(request);
        return pharmacyBranchCreateResponse;
    }
    @GetMapping("/{id}")
    public UziDTO getBranchById(@PathVariable Long id) {
        UziDTO pharmacyBranchCreateResponse = uziService.getById(id);
        return pharmacyBranchCreateResponse;
    }
    @GetMapping("/get/all")
    public List<UziDTO> getAllBranch(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "25") int size) {
        List<UziDTO> pharmacyBranchCreateResponse =uziService.getAll();
        return pharmacyBranchCreateResponse;
    }

    @PutMapping("/update/{id}")
    public UziDTO updateBranch(@Valid @RequestBody UziDTO request, @PathVariable Long id) {
        UziDTO pharmacyBranchCreateResponse = uziService.update(id,request);
        return pharmacyBranchCreateResponse;
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        uziService.delete(id);
    }
}

