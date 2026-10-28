package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.entity.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    List<Rental> findByStatusOrderByPickupDateAsc(RentalStatus status);

    List<Rental> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start, LocalDate end);
}
