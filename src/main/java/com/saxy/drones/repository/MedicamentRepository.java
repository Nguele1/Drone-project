package com.saxy.drones.repository;

import com.saxy.drones.domain.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}