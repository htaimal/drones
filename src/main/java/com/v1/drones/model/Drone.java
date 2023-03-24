package com.v1.drones.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "DRONE")
@Getter
@Setter
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SERIAL", nullable = false)
    private String serial;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "MAX_LOAD_CAPACITY", nullable = false)
    private Integer maxLoadCapacity;

    @Column(name = "BATTERY", nullable = false)
    private Integer battery;

    @Column(name = "STATE", nullable = false)
    private String state;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "drone_medication",
            joinColumns = @JoinColumn(name = "drone_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id",referencedColumnName = "id"))
    private List<Medication> medications;


}
