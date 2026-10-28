package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RenterRepository extends JpaRepository<Renter, UUID> {

    @Query("SELECT DISTINCT e.renter FROM Rental e WHERE e.rentalAgent.id = :rentalAgentId")
    List<Renter> findRentersOfRentalAgent(@Param("rentalAgentId") UUID rentalAgentId);

    List<Renter> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
