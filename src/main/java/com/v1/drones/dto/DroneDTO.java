package com.v1.drones.dto;


import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DroneDTO {
    private Integer id;

    private String serial;
    private String model;

    private Integer maxLoadCapacity;

    private Integer battery;

    private String state;

    private List<MedicationDTO> medications;

    private static final int lowBatteryLevel=25;

    public boolean isLowBattery(){
        return battery<lowBatteryLevel;
    }

    public boolean exceedCapacity(int totalWeight){
        return totalWeight > this.maxLoadCapacity;
    }

}
