package com.saxy.drones.controllers;

import com.saxy.drones.domain.Drone;
import com.saxy.drones.domain.Medicament;
import com.saxy.drones.dto.DroneDto;
import com.saxy.drones.dto.DrugDto;
import com.saxy.drones.exception.DroneConflictException;
import com.saxy.drones.service.DroneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/drone")
@Tag(name = "Books", description = "Endpoints for drone operations")
@Validated
public class DroneManager {

    private final DroneService droneService ;

    public DroneManager(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping(path = "/api/drones", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> addDrone(@RequestBody @Valid DroneDto droneDto) throws EntityNotFoundException, DroneConflictException {

        Drone drone = droneService.addDrone(
                droneDto.getSerialNumber(),
                droneDto.getModel(),
                droneDto.getState(),
                droneDto.getLimit(),
                droneDto.getCapacity()
        );

        return new ResponseEntity<>(drone, HttpStatus.OK);

    }
    @PostMapping(path = "/api/drones/{droneId}/load")
    public  ResponseEntity<Medicament> loadDrone (
            @PathVariable long droneId,
            @RequestBody @Valid DrugDto drugDto
    )throws EntityNotFoundException, DroneConflictException{
        Medicament drug = droneService.drugLoad(droneId,drugDto) ;
        return new ResponseEntity<>(drug, HttpStatus.OK);
    }

    @GetMapping(path = "/api/drones/available")
    public ResponseEntity<List<Drone>> getAvailable(){
        List<Drone> drones = droneService.getAvailableDrone() ;
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping(path = "/api/drones/{droneId}/battery/capacity")
    public ResponseEntity<String> getDroneBatteryCapacity(
            @PathVariable long droneId
    ){
        String capacity = droneService.getDroneBatteryCapacity(droneId) ;
        return new ResponseEntity<>(capacity, HttpStatus.OK);
    }

}
