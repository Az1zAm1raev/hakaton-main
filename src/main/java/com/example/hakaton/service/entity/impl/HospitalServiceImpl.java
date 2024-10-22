package com.example.hakaton.service.entity.impl;

import com.example.hakaton.dto.entity.HospitalDTO;
import com.example.hakaton.entity.Hospital;
import com.example.hakaton.entity.Patient;
import com.example.hakaton.entity.User;
import com.example.hakaton.repository.entity.HospitalRepository;
import com.example.hakaton.repository.entity.PatientRepository;
import com.example.hakaton.repository.security.UserRepository;
import com.example.hakaton.service.entity.HospitalService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class  HospitalServiceImpl implements HospitalService {

     ModelMapper modelMapper;

     HospitalRepository hospitalRepository;
     PatientRepository patientRepository;
     UserRepository userRepository;

    @Autowired
    public HospitalServiceImpl(ModelMapper modelMapper,HospitalRepository hospitalRepository, PatientRepository patientRepository,UserRepository userRepository) {
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HospitalDTO> getAll() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<HospitalDTO> hospitalDTOS = new ArrayList<>();

        for (Hospital hospital : hospitals){
            HospitalDTO hospitalDTO = new HospitalDTO();
            hospitalDTO.setName(hospital.getName());
            hospitalDTO.setAddress(hospital.getAddress());


            User user = userRepository.findById(hospitalDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Oblast not found with id: " + hospitalDTO.getId()));
        hospital.setUser(user);
        }
        return hospitalDTOS;
    }

    @Override
    public HospitalDTO getById(Long id) {
       Hospital hospital = hospitalRepository.findById(id).orElse(null);
       HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setName(hospital.getName());
        hospitalDTO.setAddress(hospital.getAddress());
//        hospitalDTO.se(hospital.getUser());
        return null;
    }

    @Override
    public HospitalDTO create(HospitalDTO command) {
        Hospital pharmacyBranch = mapToEntity(command);
        Hospital saved = hospitalRepository.save(pharmacyBranch);
        return mapToResponse(saved);
    }




    @Override
    public HospitalDTO update(HospitalDTO command) {
        return null;
    }

    @Override
    public void delete(Long id) {
        return;
//        hospitalRepository.deleteById(hospitalRepository.findById(id));
    }

    @Override
    public List<Patient> getList() {
        return null;
    }

    public HospitalRepository getHospitalRepository() {
        return hospitalRepository;
    }

    public HospitalDTO mapToResponse(Hospital hospital) {
        HospitalDTO response = modelMapper.map(hospital, HospitalDTO.class);
        if (hospital.getUser() != null) {
            response.setId(hospital.getUser().getId());
        }
        return response;
    }

    public Hospital mapToEntity(HospitalDTO request) {
        Hospital pharmacyBranch = modelMapper.map(request, Hospital.class);

        pharmacyBranch.setUser(userRepository.findById(request.getUser()).orElse(null));
        return pharmacyBranch;
    }

}
