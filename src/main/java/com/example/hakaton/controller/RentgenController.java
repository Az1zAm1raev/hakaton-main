package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.RentgenDTO;
import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.service.entity.RentgenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/rentgen")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RentgenController {

    RentgenService rentgenService;

    public RentgenController(RentgenService rentgenService) {
        this.rentgenService = rentgenService;
    }

    @PostMapping("/create/rentgen")
    public RentgenDTO add(@RequestBody @Valid RentgenDTO request) {
        RentgenDTO rentgenDTO =rentgenService.create(request);
        return rentgenDTO;
    }
    @GetMapping("/{id}")
    public RentgenDTO getBranchById(@PathVariable Long id) {
        RentgenDTO rentgenDTO = rentgenService.getById(id);
        return rentgenDTO;
    }
    @GetMapping("/get/all")
    public List<RentgenDTO> getAllBranch(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "25") int size) {
        List<RentgenDTO> rentgenDTO =rentgenService.getAll();
        return rentgenDTO;
    }

    @PutMapping("/update/{id}")
    public RentgenDTO updateBranch(@Valid @RequestBody RentgenDTO request, @PathVariable Long id) {
        RentgenDTO rentgenDTO = rentgenService.update(id,request);
        return rentgenDTO;
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        rentgenService.delete(id);
    }
}

