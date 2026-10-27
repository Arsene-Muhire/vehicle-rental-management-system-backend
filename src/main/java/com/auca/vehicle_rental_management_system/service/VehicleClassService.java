package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.VehicleClass;
import com.auca.vehicle_rental_management_system.repository.VehicleClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleClassService {

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    public String save(VehicleClass vehicleClass) {
        vehicleClassRepository.save(vehicleClass);
        return "Vehicle class saved successfully";
    }

    public List<VehicleClass> findAll() {
        return vehicleClassRepository.findAll();
    }

    public Optional<VehicleClass> findById(UUID id) {
        return vehicleClassRepository.findById(id);
    }

    public String update(UUID id, VehicleClass vehicleClass) {
        Optional<VehicleClass> existing = vehicleClassRepository.findById(id);
        if (existing.isEmpty()) {
            return "The vehicle class with that id does not exist";
        }
        VehicleClass current = existing.get();
        current.setName(vehicleClass.getName());
        vehicleClassRepository.save(current);
        return "Vehicle class updated successfully";
    }

    public String delete(UUID id) {
        if (!vehicleClassRepository.existsById(id)) {
            return "The vehicle class with that id does not exist";
        }
        vehicleClassRepository.deleteById(id);
        return "Vehicle class deleted successfully";
    }
}
