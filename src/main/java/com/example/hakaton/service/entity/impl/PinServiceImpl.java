package com.example.hakaton.service.entity.impl;

import com.example.hakaton.entity.Patient;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.PatientRepository;
import com.example.hakaton.service.entity.PinService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class PinServiceImpl implements PinService {
    PatientRepository patientRepository;

    @Autowired
    public PinServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient get(String pin) {
        return patientRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(CustomError.PATIENT_NOT_FOUND));
    }
}
