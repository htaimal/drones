package com.v1.drones.service.impl;

import com.v1.drones.dto.DroneDTO;
import com.v1.drones.dto.MedicationDTO;
import com.v1.drones.exceptions.LoadExceededException;
import com.v1.drones.exceptions.LowBatteryException;
import com.v1.drones.exceptions.ResourceNotFoundException;
import com.v1.drones.model.Drone;
import com.v1.drones.repository.DroneRepository;
import com.v1.drones.service.DroneService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@EnableScheduling
public class DroneServiceImpl implements DroneService {

    private DroneRepository droneRepository;
    private ModelMapper modelMapper;

    private static final Logger auditLogger = LoggerFactory.getLogger("auditLogger");


    public DroneServiceImpl(DroneRepository droneRepository, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DroneDTO create(DroneDTO droneDTO) {
        Drone drone = modelMapper.map(droneDTO, Drone.class);
        drone = droneRepository.save(drone);
        return modelMapper.map(drone, DroneDTO.class);
    }

    private Drone findDroneById(int id) {
         return droneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found"));
    }

    @Override
    public Optional<DroneDTO> findById(int id) {
        Drone drone = findDroneById(id);
        return Optional.ofNullable(modelMapper.map(drone, DroneDTO.class));
    }

    @Override
    public DroneDTO update(DroneDTO droneDTO) {
        Drone drone = findDroneById(droneDTO.getId());
        Drone drone2 = modelMapper.map(droneDTO, Drone.class);
        drone.setMedications(drone2.getMedications());
        droneRepository.save(drone);
        return modelMapper.map(drone, DroneDTO.class);

    }


    @Override
    public void delete(Integer id) {
        Drone drone = findDroneById(id);
        droneRepository.delete(drone);
    }

    @Override
    public List<DroneDTO> getAll() {
        List<Drone> drones = droneRepository.findAll();
        return drones.stream().map(drone -> modelMapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());    }

    @Override
    public List<DroneDTO> getAvailabeDrones() {
        List<Drone> drones = droneRepository.findAll();
        return drones.stream()
                .filter(d -> d.getMedications().isEmpty())
                .map(drone -> modelMapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());      }

    @Override
    public Integer getDroneBatteryLevel(Integer droneId) {
        Drone drone = droneRepository.findById(droneId).orElse(null);
        if (drone == null) {
            return null;
        }
        return drone.getBattery();
    }

    @Override
    public DroneDTO loadMedications(int droneId, List<MedicationDTO> medications) throws LowBatteryException,LoadExceededException{
        Optional<DroneDTO> droneOptional = findById(droneId);
        if (droneOptional.isPresent()) {
            DroneDTO droneDTO = droneOptional.get();
            if(droneDTO.isLowBattery()){
                throw new LowBatteryException("Cannot load drone due to low battery");
            }
            int totalWeight = medications.stream()
                    .mapToInt(medication -> medication.getWeight())
                    .sum();
            if (droneDTO.exceedCapacity(totalWeight)) {
                throw new LoadExceededException("Item with weight " + totalWeight + " exceeds available capacity " + droneDTO.getMaxLoadCapacity());
            }
            droneDTO.setMedications(medications);
            return update(droneDTO);
        } else {
            throw new ResourceNotFoundException("Cannot find the drone specified");
        }
    }

    @Service
    public class DroneBatteryCheckService {
        @Scheduled(fixedRate = 50000) // run every 50 seconds to log
        public void checkBatteryLevels() {
            List<DroneDTO> drones = getAll();
            for (DroneDTO drone : drones) {
                if (drone.isLowBattery()) {
                    auditLogger.info("Drone Serial {} battery level is below threshold", drone.getSerial());
                }
            }
        }
    }

}
