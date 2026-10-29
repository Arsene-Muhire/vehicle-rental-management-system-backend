package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.entity.RentalStatus;
import com.auca.vehicle_rental_management_system.entity.ReturnCharge;
import com.auca.vehicle_rental_management_system.repository.RentalRepository;
import com.auca.vehicle_rental_management_system.repository.ReturnChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReturnChargeService {

    @Autowired
    private ReturnChargeRepository returnChargeRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public String save(UUID rentalId, ReturnCharge returnCharge) {
        Optional<Rental> rental = rentalRepository.findById(rentalId);
        if (rental.isEmpty()) {
            return "The rental with that id does not exist";
        }
        if (rental.get().getStatus() != RentalStatus.RETURNED) {
            return "Return charges are only for RETURNED rentals";
        }
        returnCharge.setRental(rental.get());
        returnCharge.setChargedDate(LocalDate.now());
        returnChargeRepository.save(returnCharge);
        return "Return charge saved successfully";
    }

    public List<ReturnCharge> findByRenter(String licenseNumber) {
        return returnChargeRepository.findByRenterLicenseNumber(licenseNumber);
    }

    public String delete(UUID id) {
        if (!returnChargeRepository.existsById(id)) {
            return "The return charge with that id does not exist";
        }
        returnChargeRepository.deleteById(id);
        return "Return charge deleted successfully";
    }
}
