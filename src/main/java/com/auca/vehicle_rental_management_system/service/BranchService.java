package com.auca.vehicle_rental_management_system.service;

import com.auca.vehicle_rental_management_system.entity.Branch;
import com.auca.vehicle_rental_management_system.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public String save(Branch branch) {
        branchRepository.save(branch);
        return "Branch saved successfully";
    }

    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    public Optional<Branch> findById(UUID id) {
        return branchRepository.findById(id);
    }

    public List<Branch> findFree() {
        return branchRepository.findFreeBranches();
    }

    public String update(UUID id, Branch branch) {
        Optional<Branch> existing = branchRepository.findById(id);
        if (existing.isEmpty()) {
            return "The branch with that id does not exist";
        }
        Branch current = existing.get();
        current.setBranchNumber(branch.getBranchNumber());
        current.setName(branch.getName());
        branchRepository.save(current);
        return "Branch updated successfully";
    }

    public String delete(UUID id) {
        if (!branchRepository.existsById(id)) {
            return "The branch with that id does not exist";
        }
        branchRepository.deleteById(id);
        return "Branch deleted successfully";
    }
}
