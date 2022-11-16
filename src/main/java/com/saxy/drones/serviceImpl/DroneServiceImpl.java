package com.saxy.drones.serviceImpl;

import com.saxy.drones.domain.Drone;
import com.saxy.drones.domain.DroneModel;
import com.saxy.drones.domain.DroneState;
import com.saxy.drones.domain.Medicament;
import com.saxy.drones.dto.DrugDto;
import com.saxy.drones.exception.DroneConflictException;
import com.saxy.drones.repository.DroneRepository;
import com.saxy.drones.repository.MedicamentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.saxy.drones.service.DroneService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DroneServiceImpl implements DroneService {

    @Value("${limitation.max_weight}")
    private String maxWeight;

    private final DroneRepository droneRepository ;
    private final MedicamentRepository drugRepository ;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository,MedicamentRepository drugRepository ) {
        this.droneRepository = droneRepository;
        this.drugRepository = drugRepository;
    }

    @Override
    public Drone addDrone(String number, DroneModel model, DroneState state, long limit, String capacity) throws EntityNotFoundException, DroneConflictException{
        // check if the serial number it's unique
        if (droneRepository.findBySerialNumber(number).isPresent()) {
            throw new DroneConflictException("Serial Number mist be unique") ;
        }
        Drone drone = new Drone();
        drone.setSerial_number(number);
        drone.setModel(model);
        drone.setState(state);
        drone.setWeight_limit(limit);
        drone.setBattery_capacity(capacity);
        drone = droneRepository.save(drone) ;
        return drone ;
    }

    @Override
    public Medicament drugLoad(long droneId, DrugDto drugDto) throws EntityNotFoundException, DroneConflictException {
        // check if the serial number it's unique

        if (droneRepository.findById(droneId).isEmpty()) {
            throw new EntityNotFoundException("Drone not fund") ;
        }

        Drone drone = droneRepository.findById(droneId).get() ;

        if(Integer.parseInt(drone.getBattery_capacity()) < 25 ){
            throw new DroneConflictException("battery level is below 25%") ;
        }

        List<Medicament> list_drones = drone.getDrugs() ;
        int sum = 0;
        for (Medicament value : list_drones) {
            sum += value.getWeight();
        }

        if(sum >= drugDto.getWeight() ){
            throw new DroneConflictException("weight capacity limit excepted") ;
        }else{
            drone.setState(DroneState.DELIVRED);
        }

        Medicament drug =  new Medicament();
        drug.setName(drugDto.getName());
        drug.setWeight(drugDto.getWeight());
        drug.setCode(drug.getCode());
        drug.setImage(drug.getImage());

        drug = drugRepository.save(drug) ;
        List<Medicament> list = new ArrayList<Medicament>();
        list.add(drug);
        drone.setDrugs(list);
        droneRepository.save(drone) ;
        return drug ;
    }

    @Override
    public List<Drone> getAvailableDrone() throws EntityNotFoundException {
        List<Drone> drones = droneRepository.findAllAvailableDrone(DroneState.LOADED) ;
        return drones ;
    }

    @Override
    public String getDroneBatteryCapacity(long droneId) throws EntityNotFoundException {
        if (droneRepository.findByStatus(droneId,DroneState.INACTIVE).isEmpty()) {
            throw new EntityNotFoundException("Drone not fund") ;
        }
        Drone drone = droneRepository.findById(droneId).get() ;
        return drone.getBattery_capacity() ;
    }


}
