package com.v1.drones.controller;

import java.util.List;

import com.v1.drones.dto.MedicationDTO;
import com.v1.drones.exceptions.LoadExceededException;
import com.v1.drones.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.v1.drones.dto.DroneDTO;
import com.v1.drones.exceptions.LowBatteryException;
import com.v1.drones.service.DroneService;
import java.util.Optional;


@RestController
@RequestMapping("/drone")
public class DroneController {
    
    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;
    @GetMapping("/{id}")
    public  Optional<DroneDTO> getDroneById(@PathVariable("id") int droneId) {
        return droneService.findById(droneId);
    }

    @PostMapping
    public DroneDTO create(@RequestBody DroneDTO droneDTO) {
        return droneService.create(droneDTO);
    }

    @PutMapping
    public DroneDTO update(@RequestBody DroneDTO droneDTO) {
        return droneService.update(droneDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int droneId) {
        droneService.delete(droneId);
    }

    @GetMapping
    public List<DroneDTO> getAll() {
        return droneService.getAll();
    }

    @GetMapping("/available")
    public List<DroneDTO> getAvailabeDrones() {
        return droneService.getAvailabeDrones();
    }

    @PostMapping("/{droneId}/medication")
    public ResponseEntity<DroneDTO> loadMedications(@PathVariable int droneId, @RequestBody List<Integer> medicationIds) {
            List<MedicationDTO> medications = medicationService.getMedicationsByIds(medicationIds);
            return ResponseEntity.ok(droneService.loadMedications(droneId,medications));

    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable Integer droneId) {
        Integer batteryLevel = droneService.getDroneBatteryLevel(droneId);
        if (batteryLevel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(batteryLevel);
    }

    // Exception handler for LowBatteryException
    @ExceptionHandler(LowBatteryException.class)
    public ResponseEntity<?> handleLowBatteryException(LowBatteryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Exception handler for LoadExceededException
    @ExceptionHandler(LoadExceededException.class)
    public ResponseEntity<?> handleLowBatteryException(LoadExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
