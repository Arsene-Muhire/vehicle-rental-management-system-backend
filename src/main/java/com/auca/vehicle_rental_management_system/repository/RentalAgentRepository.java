package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RentalAgentRepository extends JpaRepository<RentalAgent, UUID> {

    @Query("SELECT DISTINCT a FROM RentalAgent a JOIN a.vehicleClasses c WHERE LOWER(c.name) = LOWER(:name)")
    List<RentalAgent> searchByVehicleClassName(@Param("name") String name);
}
