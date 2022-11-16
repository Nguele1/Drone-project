package com.saxy.drones.dto;

import java.io.Serializable;

import com.saxy.drones.domain.DroneModel;
import com.saxy.drones.domain.DroneState;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DroneDto implements Serializable {

    private String serialNumber;
    private DroneModel model ;
    private DroneState state  ;
    private long limit;
    private String capacity;
}