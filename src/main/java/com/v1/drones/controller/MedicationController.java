package com.v1.drones.controller;

import com.v1.drones.dto.MedicationDTO;
import com.v1.drones.service.MedicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/medication")
public class MedicationController {

    private MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public List<MedicationDTO> getAll() {
        return medicationService.getAll();
    }
    
}
