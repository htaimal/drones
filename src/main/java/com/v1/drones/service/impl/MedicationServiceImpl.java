package com.v1.drones.service.impl;

import com.v1.drones.dto.MedicationDTO;
import com.v1.drones.model.Medication;
import com.v1.drones.repository.MedicationRepository;
import com.v1.drones.service.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {

    private MedicationRepository medicationRepository;
    private ModelMapper modelMapper;

   
    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;    
    }

    /**
     * List all existence medications
     */
    @Override
    public List<MedicationDTO> getAll() {
        List<Medication> medications = medicationRepository.findAll();
        return medications.stream().map(medication -> modelMapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }



}
