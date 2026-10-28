package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, UUID> {

    @Query("SELECT v FROM VehicleClass v WHERE v.rentalAgents IS EMPTY")
    List<VehicleClass> findUnusedVehicleClasses();
}
