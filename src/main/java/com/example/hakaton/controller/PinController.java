package com.example.hakaton.controller;

import com.example.hakaton.entity.Patient;
import com.example.hakaton.service.entity.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pin")
public class PinController {
    private final PinService service;

    @Autowired
    public PinController(PinService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public Patient get(@RequestParam String pin) {
        return service.get(pin);
    }
}
