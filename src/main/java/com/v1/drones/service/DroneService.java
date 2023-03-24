package com.v1.drones.service;

import com.v1.drones.dto.DroneDTO;
import com.v1.drones.dto.MedicationDTO;

import java.util.List;
import java.util.Optional;


public interface DroneService {
    
    DroneDTO create(DroneDTO droneDTO);

    Optional<DroneDTO>  findById(int id);

    DroneDTO update(DroneDTO droneDTO);

    void delete(Integer id);

    List<DroneDTO> getAll();


    List<DroneDTO> getAvailabeDrones();

    Integer getDroneBatteryLevel(Integer droneId);

    DroneDTO loadMedications(int droneId, List<MedicationDTO> medications);
}
