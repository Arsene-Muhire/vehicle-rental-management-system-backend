package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.entity.RentalStatus;
import com.auca.vehicle_rental_management_system.repository.RentalRepository;
import com.auca.vehicle_rental_management_system.repository.ReturnChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ReturnChargeRepository returnChargeRepository;

    public String save(Rental rental) {
        if (rental.getPickupDate().isBefore(LocalDate.now())) {
            return "Rental date cannot be in the past";
        }
        if (rentalRepository.existsByRentalAgentIdAndPickupDateAndStatusNot(rental.getRentalAgent().getId(), rental.getPickupDate(), RentalStatus.CANCELLED)) {
            return "Rental agent already has a pickup on that date";
        }
        rental.setStatus(RentalStatus.RESERVED);
        rentalRepository.save(rental);
        return "Rental saved successfully";
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(UUID id) {
        return rentalRepository.findById(id);
    }

    public List<Rental> findByStatus(RentalStatus status) {
        return rentalRepository.findByStatusOrderByPickupDateAsc(status);
    }

    public List<Rental> findBetween(LocalDate start, LocalDate end) {
        return rentalRepository.findByPickupDateBetweenOrderByPickupDateAsc(start, end);
    }

    public List<Object[]> countByStatus() {
        return rentalRepository.countByStatus();
    }

    @Transactional
    public String cancelDay(UUID rentalAgentId, LocalDate date) {
        int count = rentalRepository.cancelAgentDay(rentalAgentId, date, RentalStatus.CANCELLED, RentalStatus.RETURNED);
        return count + " rentals cancelled";
    }

    public String update(UUID id, Rental rental) {
        Optional<Rental> existing = rentalRepository.findById(id);
        if (existing.isEmpty()) {
            return "The rental with that id does not exist";
        }
        Rental current = existing.get();
        current.setPickupDate(rental.getPickupDate());
        current.setPlateNumber(rental.getPlateNumber());
        current.setDays(rental.getDays());
        current.setRenter(rental.getRenter());
        current.setRentalAgent(rental.getRentalAgent());
        rentalRepository.save(current);
        return "Rental updated successfully";
    }

    public String updateStatus(UUID id, RentalStatus newStatus) {
        Optional<Rental> existing = rentalRepository.findById(id);
        if (existing.isEmpty()) {
            return "The rental with that id does not exist";
        }
        Rental current = existing.get();
        RentalStatus oldStatus = current.getStatus();
        boolean allowed = false;
        if (oldStatus == RentalStatus.RESERVED) {
            allowed = newStatus == RentalStatus.PICKED_UP || newStatus == RentalStatus.CANCELLED;
        } else if (oldStatus == RentalStatus.PICKED_UP) {
            allowed = newStatus == RentalStatus.RETURNED || newStatus == RentalStatus.CANCELLED;
        }
        if (!allowed) {
            return "Cannot change status from " + oldStatus + " to " + newStatus;
        }
        current.setStatus(newStatus);
        rentalRepository.save(current);
        return "Rental status updated successfully";
    }

    public String delete(UUID id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isEmpty()) {
            return "The rental with that id does not exist";
        }
        if (returnChargeRepository.existsByRental(rental.get())) {
            return "That rental has return charges, delete them first";
        }
        rentalRepository.deleteById(id);
        return "Rental deleted successfully";
    }
}
