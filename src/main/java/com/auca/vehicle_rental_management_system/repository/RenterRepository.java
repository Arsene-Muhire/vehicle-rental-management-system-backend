package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RenterRepository extends JpaRepository<Renter, UUID> {

    List<Renter> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
