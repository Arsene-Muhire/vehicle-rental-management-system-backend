package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalAgentRepository extends JpaRepository<RentalAgent, UUID> {
}
