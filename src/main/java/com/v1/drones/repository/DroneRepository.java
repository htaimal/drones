package com.v1.drones.repository;

import com.v1.drones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DroneRepository extends JpaRepository<Drone, Integer>{
    
}
