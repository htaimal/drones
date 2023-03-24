package com.v1.drones.dto;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicationDTO {
    
    private Integer id;

    private String name;

    private Integer weight;

    private String code;

    private String imageUrl;
}
