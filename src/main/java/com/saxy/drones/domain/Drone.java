package com.saxy.drones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "drones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "serial_number",unique = true)
    private String serial_number;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private DroneModel model ;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DroneState state = DroneState.LOADED;

    @Column(name = "weight_limit")
    @Range(min=1, max=500)
    private long weight_limit = 1;

    @Column(name = "battery_capacity")
    private String battery_capacity;

    @ManyToMany
    private List<Medicament> drugs = new ArrayList<>();

}
