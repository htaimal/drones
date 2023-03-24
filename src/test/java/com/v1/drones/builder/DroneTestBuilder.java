package com.v1.drones.builder;

import com.v1.drones.model.Drone;
import com.v1.drones.model.Medication;
import lombok.Builder;

import java.util.List;

public class DroneTestBuilder {

    @Builder
    public static Drone drone(Integer id, String serial, String model, Integer maxLoadCapacity, Integer battery, String state,List<Medication> medications) {

        Drone drone = new Drone();
        drone.setId(id);
        drone.setSerial(serial);
        drone.setModel(model);
        drone.setMaxLoadCapacity(maxLoadCapacity);
        drone.setBattery(battery);
        drone.setState(state);
        drone.setMedications(medications);

        return drone;
    }

}
