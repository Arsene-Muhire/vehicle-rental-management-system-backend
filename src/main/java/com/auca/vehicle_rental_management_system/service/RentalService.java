package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public String save(Rental rental) {
        rentalRepository.save(rental);
        return "Rental saved successfully";
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(UUID id) {
        return rentalRepository.findById(id);
    }

    public String update(UUID id, Rental rental) {
        Optional<Rental> existing = rentalRepository.findById(id);
        if (existing.isEmpty()) {
            return "The rental with that id does not exist";
        }
        Rental current = existing.get();
        current.setPickupDate(rental.getPickupDate());
        current.setStatus(rental.getStatus());
        current.setPlateNumber(rental.getPlateNumber());
        current.setDays(rental.getDays());
        current.setRenter(rental.getRenter());
        current.setRentalAgent(rental.getRentalAgent());
        rentalRepository.save(current);
        return "Rental updated successfully";
    }

    public String delete(UUID id) {
        if (!rentalRepository.existsById(id)) {
            return "The rental with that id does not exist";
        }
        rentalRepository.deleteById(id);
        return "Rental deleted successfully";
    }
}
