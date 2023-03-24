package com.v1.drones.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "MEDICATION")
@Getter
@Setter
@NoArgsConstructor
public class Medication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @ManyToMany(mappedBy = "medications")
    private List<Drone> drones;
}
