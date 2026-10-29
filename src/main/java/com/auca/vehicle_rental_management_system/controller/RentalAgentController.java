package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import com.auca.vehicle_rental_management_system.service.RentalAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rental-agents")
public class RentalAgentController {

    @Autowired
    private RentalAgentService rentalAgentService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody RentalAgent rentalAgent) {
        String message = rentalAgentService.save(rentalAgent);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(rentalAgentService.findAll());
    }

    @GetMapping("/by-vehicle-class")
    public ResponseEntity<Object> findByVehicleClassName(@RequestParam String name) {
        return ResponseEntity.ok(rentalAgentService.findByVehicleClassName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<RentalAgent> rentalAgent = rentalAgentService.findById(id);
        if (rentalAgent.isPresent()) {
            return ResponseEntity.ok(rentalAgent.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The rental agent with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody RentalAgent rentalAgent) {
        String message = rentalAgentService.update(id, rentalAgent);
        if (message.equals("The rental agent with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @PutMapping("/{rentalAgentId}/vehicle-classes/{vehicleClassId}")
    public ResponseEntity<String> addVehicleClass(@PathVariable UUID rentalAgentId, @PathVariable UUID vehicleClassId) {
        String message = rentalAgentService.addVehicleClass(rentalAgentId, vehicleClassId);
        if (message.equals("The rental agent with that id does not exist") || message.equals("That vehicle class does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("The rental agent already has that vehicle class")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @PatchMapping("/{rentalAgentId}/branch")
    public ResponseEntity<String> changeBranch(@PathVariable UUID rentalAgentId, @RequestParam int branchNumber) {
        String message = rentalAgentService.changeBranch(rentalAgentId, branchNumber);
        if (message.equals("The rental agent with that id does not exist") || message.equals("The branch with that branchNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("That branch is taken by another rental agent")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = rentalAgentService.delete(id);
        if (message.equals("The rental agent with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
