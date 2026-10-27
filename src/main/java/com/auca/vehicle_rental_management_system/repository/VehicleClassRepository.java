package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, UUID> {
}
