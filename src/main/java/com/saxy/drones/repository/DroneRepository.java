package com.saxy.drones.repository;

import com.saxy.drones.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("Select d From Drone d Where d.serial_number = :serialNumber")
    Optional<Drone> findBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query("Select d From Drone d Where d.id = :id and d.state <> :state")
    Optional<Drone> findByStatus(@Param("id") long id, @Param("state") DroneState state);

    @Query("Select d From Drone d Where d.state = :state")
    List<Drone> findAllAvailableDrone(@Param("state") DroneState state);
}

