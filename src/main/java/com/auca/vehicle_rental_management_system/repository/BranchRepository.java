package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {

    @Query("SELECT b FROM Branch b WHERE NOT EXISTS (SELECT a FROM RentalAgent a WHERE a.branch = b) ORDER BY b.branchNumber")
    List<Branch> findFreeBranches();
}
