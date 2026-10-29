package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.entity.ReturnCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReturnChargeRepository extends JpaRepository<ReturnCharge, UUID> {

    boolean existsByRental(Rental rental);

    @Query("SELECT d FROM ReturnCharge d WHERE d.rental.renter.licenseNumber = :licenseNumber ORDER BY d.chargedDate DESC")
    List<ReturnCharge> findByRenterLicenseNumber(@Param("licenseNumber") String licenseNumber);
}
