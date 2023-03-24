package com.v1.drones.repository;

import com.v1.drones.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MedicationRepository extends JpaRepository<Medication, Integer> {

}
