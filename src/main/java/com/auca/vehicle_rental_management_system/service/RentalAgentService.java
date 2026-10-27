package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import com.auca.vehicle_rental_management_system.repository.RentalAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalAgentService {

    @Autowired
    private RentalAgentRepository rentalAgentRepository;

    public String save(RentalAgent rentalAgent) {
        rentalAgentRepository.save(rentalAgent);
        return "Rental agent saved successfully";
    }

    public List<RentalAgent> findAll() {
        return rentalAgentRepository.findAll();
    }

    public Optional<RentalAgent> findById(UUID id) {
        return rentalAgentRepository.findById(id);
    }

    public String update(UUID id, RentalAgent rentalAgent) {
        Optional<RentalAgent> existing = rentalAgentRepository.findById(id);
        if (existing.isEmpty()) {
            return "The rental agent with that id does not exist";
        }
        RentalAgent current = existing.get();
        current.setFirstName(rentalAgent.getFirstName());
        current.setLastName(rentalAgent.getLastName());
        current.setEmail(rentalAgent.getEmail());
        current.setBranch(rentalAgent.getBranch());
        current.setVehicleClasses(rentalAgent.getVehicleClasses());
        rentalAgentRepository.save(current);
        return "Rental agent updated successfully";
    }

    public String delete(UUID id) {
        if (!rentalAgentRepository.existsById(id)) {
            return "The rental agent with that id does not exist";
        }
        rentalAgentRepository.deleteById(id);
        return "Rental agent deleted successfully";
    }
}
