package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Branch;
import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import com.auca.vehicle_rental_management_system.entity.VehicleClass;
import com.auca.vehicle_rental_management_system.repository.BranchRepository;
import com.auca.vehicle_rental_management_system.repository.RentalAgentRepository;
import com.auca.vehicle_rental_management_system.repository.VehicleClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalAgentService {

    @Autowired
    private RentalAgentRepository rentalAgentRepository;

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    private BranchRepository branchRepository;

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

    public List<RentalAgent> findByVehicleClassName(String name) {
        return rentalAgentRepository.searchByVehicleClassName(name);
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

    public String addVehicleClass(UUID rentalAgentId, UUID vehicleClassId) {
        Optional<RentalAgent> agent = rentalAgentRepository.findById(rentalAgentId);
        if (agent.isEmpty()) {
            return "The rental agent with that id does not exist";
        }
        Optional<VehicleClass> vehicleClass = vehicleClassRepository.findById(vehicleClassId);
        if (vehicleClass.isEmpty()) {
            return "That vehicle class does not exist";
        }
        RentalAgent current = agent.get();
        if (current.getVehicleClasses().contains(vehicleClass.get())) {
            return "The rental agent already has that vehicle class";
        }
        current.getVehicleClasses().add(vehicleClass.get());
        rentalAgentRepository.save(current);
        return "Vehicle class added to the rental agent";
    }

    public String changeBranch(UUID rentalAgentId, int branchNumber) {
        Optional<RentalAgent> agent = rentalAgentRepository.findById(rentalAgentId);
        if (agent.isEmpty()) {
            return "The rental agent with that id does not exist";
        }
        Optional<Branch> branch = branchRepository.findByBranchNumber(branchNumber);
        if (branch.isEmpty()) {
            return "The branch with that branchNumber does not exist";
        }
        RentalAgent current = agent.get();
        boolean alreadyHere = current.getBranch() != null && current.getBranch().getId().equals(branch.get().getId());
        if (!alreadyHere && rentalAgentRepository.existsByBranch(branch.get())) {
            return "That branch is taken by another rental agent";
        }
        current.setBranch(branch.get());
        rentalAgentRepository.save(current);
        return "Branch changed successfully";
    }

    public String delete(UUID id) {
        if (!rentalAgentRepository.existsById(id)) {
            return "The rental agent with that id does not exist";
        }
        rentalAgentRepository.deleteById(id);
        return "Rental agent deleted successfully";
    }
}
