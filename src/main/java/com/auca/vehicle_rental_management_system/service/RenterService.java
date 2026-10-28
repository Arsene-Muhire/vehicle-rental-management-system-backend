package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Renter;
import com.auca.vehicle_rental_management_system.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RenterService {

    @Autowired
    private RenterRepository renterRepository;

    public String save(Renter renter) {
        renterRepository.save(renter);
        return "Renter saved successfully";
    }

    public List<Renter> findAll() {
        return renterRepository.findAll();
    }

    public Optional<Renter> findById(UUID id) {
        return renterRepository.findById(id);
    }

    public List<Renter> findByLastName(String lastName) {
        return renterRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public String update(UUID id, Renter renter) {
        Optional<Renter> existing = renterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The renter with that id does not exist";
        }
        Renter current = existing.get();
        current.setFirstName(renter.getFirstName());
        current.setLastName(renter.getLastName());
        current.setLicenseNumber(renter.getLicenseNumber());
        current.setPhone(renter.getPhone());
        renterRepository.save(current);
        return "Renter updated successfully";
    }

    public String delete(UUID id) {
        if (!renterRepository.existsById(id)) {
            return "The renter with that id does not exist";
        }
        renterRepository.deleteById(id);
        return "Renter deleted successfully";
    }
}
