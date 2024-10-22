package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.MRTDTO;
import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.service.entity.MRTService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mrt")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MRTController {
    MRTService mrtService;

    public MRTController(MRTService mrtService) {
        this.mrtService = mrtService;
    }
    @PostMapping("/create/mrt")
    public MRTDTO add(@RequestBody @Valid MRTDTO request) {
        MRTDTO mrtdto =mrtService.create(request);
        return mrtdto;
    }
    @GetMapping("/{id}")
    public MRTDTO getBranchById(@PathVariable Long id) {
        MRTDTO mrtdto = mrtService.getById(id);
        return mrtdto;
    }
    @GetMapping("/get/all")
    public List<MRTDTO> getAllBranch(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "25") int size) {
        List<MRTDTO> mrtdto =mrtService.getAll();
        return mrtdto;
    }

    @PutMapping("/update/{id}")
    public MRTDTO updateBranch(@Valid @RequestBody MRTDTO request, @PathVariable Long id) {
        MRTDTO mrtdto = mrtService.update(id,request);
        return mrtdto;
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        mrtService.delete(id);
    }
}