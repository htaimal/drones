package com.v1.drones.service;

import com.v1.drones.builder.DroneTestBuilder;
import com.v1.drones.dto.DroneDTO;
import com.v1.drones.dto.MedicationDTO;
import com.v1.drones.exceptions.LoadExceededException;
import com.v1.drones.exceptions.LowBatteryException;
import com.v1.drones.exceptions.ResourceNotFoundException;
import com.v1.drones.model.Drone;
import com.v1.drones.repository.DroneRepository;
import com.v1.drones.service.impl.DroneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {
    @InjectMocks
    public DroneServiceImpl droneService;

    @Mock
    public DroneRepository droneRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testRegisterDrone() {
        String droneSerial1 = "s1";
        Drone drone1 = DroneTestBuilder.builder().id(2)
                .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
                .medications(new ArrayList<>())
                .build();
        DroneDTO expectedDroneDTO= new DroneDTO();
        expectedDroneDTO.setId(3);

        Mockito.when(droneRepository.save(Mockito.any())).thenReturn(drone1);
        Mockito.when(mapper.map(drone1,DroneDTO.class)).thenReturn(expectedDroneDTO);

        DroneDTO prevDroneDTO=mapper.map(drone1, DroneDTO.class);
        DroneDTO droneDTO=droneService.create(expectedDroneDTO);
        Assertions.assertNotNull(droneDTO.getId());
    }


    @Test
    public void testLoadDroneExceedCapacity() {
        String droneSerial1 = "s1";
        MedicationDTO medication1= new MedicationDTO();
        medication1.setWeight(200);
        MedicationDTO medication2= new MedicationDTO();
        medication2.setWeight(200);

        DroneDTO expectedDroneDTO= new DroneDTO();
        expectedDroneDTO.setId(3);
        expectedDroneDTO.setMaxLoadCapacity(300);
        expectedDroneDTO.setBattery(40);

        Drone drone1 = DroneTestBuilder.builder().id(2)
                .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
                .medications(new ArrayList<>())
                .build();

        Mockito.when(droneRepository.findById(4)).thenReturn(Optional.of(drone1));
        Mockito.when(mapper.map(drone1,DroneDTO.class)).thenReturn(expectedDroneDTO);


        List<MedicationDTO> medicationList = Arrays.asList(new MedicationDTO[]{medication1, medication2});


        Assertions.assertThrows(LoadExceededException.class,
                () -> droneService.loadMedications(4,medicationList));
    }

    @Test
    public void testLoadDroneLowBattery() {
        String droneSerial1 = "s1";
        MedicationDTO medication1= new MedicationDTO();
        medication1.setWeight(200);
        MedicationDTO medication2= new MedicationDTO();
        medication2.setWeight(200);

        DroneDTO expectedDroneDTO= new DroneDTO();
        expectedDroneDTO.setId(3);
        expectedDroneDTO.setMaxLoadCapacity(300);
        expectedDroneDTO.setBattery(20);

        Drone drone1 = DroneTestBuilder.builder().id(2)
                .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
                .medications(new ArrayList<>())
                .build();

        Mockito.when(droneRepository.findById(4)).thenReturn(Optional.of(drone1));
        Mockito.when(mapper.map(drone1,DroneDTO.class)).thenReturn(expectedDroneDTO);


        List<MedicationDTO> medicationList = Arrays.asList(new MedicationDTO[]{medication1, medication2});


        Assertions.assertThrows(LowBatteryException.class,
                () -> droneService.loadMedications(4,medicationList));
    }

    @Test
    public void testLoadDroneSuccessfully() {
        String droneSerial1 = "s1";
        int id=4;
        MedicationDTO medication1= new MedicationDTO();
        medication1.setWeight(200);
        MedicationDTO medication2= new MedicationDTO();
        medication2.setWeight(100);

        DroneDTO expectedDroneDTO= new DroneDTO();
        expectedDroneDTO.setId(id);
        expectedDroneDTO.setMaxLoadCapacity(300);
        expectedDroneDTO.setBattery(40);

        Drone drone1 = DroneTestBuilder.builder().id(id)
                .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
                .medications(new ArrayList<>())
                .build();

        Mockito.when(droneRepository.findById(4)).thenReturn(Optional.of(drone1));
        Mockito.when(mapper.map(drone1,DroneDTO.class)).thenReturn(expectedDroneDTO);
        Mockito.when(mapper.map(expectedDroneDTO,Drone.class)).thenReturn(drone1);


        List<MedicationDTO> medicationList = Arrays.asList(new MedicationDTO[]{medication1, medication2});

        DroneDTO resultDroneDTO=droneService.loadMedications(id,medicationList);
        Assertions.assertTrue(resultDroneDTO.getMedications().size()>0);

    }

    @Test
    public void testGetAvailabeDrones() {
        String droneSerial1 = "s1";
        String droneSerial2 = "s2";
        Drone drone1 = DroneTestBuilder.builder().id(1)
                .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
                .medications(new ArrayList<>())
                .build();

        Drone drone2 = DroneTestBuilder.builder().id(2)
                .serial(droneSerial2).model("M").maxLoadCapacity(250).battery(50).state("RETURNING")
                .medications(new ArrayList<>())
                .build();

        List<Drone> droneList = Arrays.asList(new Drone[]{drone1, drone2});
        Mockito.when(droneRepository.findAll()).thenReturn(droneList);

        droneService.getAvailabeDrones();

        Mockito.verify(droneRepository).findAll();
        Mockito.verify(mapper).map(droneList.get(0), DroneDTO.class);
        Mockito.verify(mapper).map(droneList.get(1), DroneDTO.class);
    }

    @Test
    public void testCheckDroneBattery() throws Exception {
        int id = 1;
        Drone drone1 = DroneTestBuilder.builder().battery(21).build();
        Mockito.when(droneRepository.findById(id)).thenReturn(Optional.of(drone1));
        Integer batttery=droneService.getDroneBatteryLevel(id);
        Mockito.verify(droneRepository).findById(id);
        Assertions.assertEquals(batttery,21);
    }

   @Test
   public void testGetAllDrones() {
       String droneSerial1 = "s1";
       String droneSerial2 = "s2";
       Drone drone1 = DroneTestBuilder.builder().id(1)
               .serial(droneSerial1).model("L").maxLoadCapacity(20).battery(21).state("DELIVERING")
               .build();
   
       Drone drone2 = DroneTestBuilder.builder().id(2)
               .serial(droneSerial2).model("M").maxLoadCapacity(250).battery(50).state("RETURNING")
               .build();
   
       List<Drone> droneList = Arrays.asList(new Drone[]{drone1, drone2});
       Mockito.when(droneRepository.findAll()).thenReturn(droneList);
       
       droneService.getAll();
   
       Mockito.verify(droneRepository).findAll();
       Mockito.verify(mapper).map(droneList.get(0), DroneDTO.class);
       Mockito.verify(mapper).map(droneList.get(1), DroneDTO.class);
   }

    @Test
    public void testGetDroneByIdSuccessfully() throws Exception {
        int id = 1;
        Drone drone1 = DroneTestBuilder.builder().build();
        Mockito.when(droneRepository.findById(id)).thenReturn(Optional.of(drone1));
        droneService.findById(id);
        Mockito.verify(droneRepository).findById(id);
        Mockito.verify(mapper).map(drone1, DroneDTO.class);
    }

    @Test
    public void testGetDroneByIdNotExists() throws Exception {
        int id = 1;        
        Mockito.when(droneRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                                    () -> droneService.findById(id));
    }

    @Test
    public void testDeleteDroneSuccessfully() throws Exception {
        int id = 1;
        Drone drone1 = DroneTestBuilder.builder().build();
        Mockito.when(droneRepository.findById(id)).thenReturn(Optional.of(drone1));
        Mockito.doNothing().when(droneRepository).delete(drone1);

        droneService.delete(id);

        Mockito.verify(droneRepository).findById(id);
        Mockito.verify(droneRepository).delete(drone1);
    }


}
