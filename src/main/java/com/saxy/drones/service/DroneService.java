package com.saxy.drones.service;

import com.saxy.drones.domain.Drone;
import com.saxy.drones.domain.DroneModel;
import com.saxy.drones.domain.DroneState;
import com.saxy.drones.domain.Medicament;
import com.saxy.drones.dto.DrugDto;
import com.saxy.drones.exception.DroneConflictException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface DroneService {
    Drone addDrone(String number, DroneModel model, DroneState state, long limit, String capacity) throws EntityNotFoundException, DroneConflictException;
    Medicament drugLoad(long droneId, DrugDto drugDto) throws EntityNotFoundException, DroneConflictException ;
    List<Drone> getAvailableDrone() throws EntityNotFoundException ;
    String getDroneBatteryCapacity(long droneId) throws EntityNotFoundException ;
}