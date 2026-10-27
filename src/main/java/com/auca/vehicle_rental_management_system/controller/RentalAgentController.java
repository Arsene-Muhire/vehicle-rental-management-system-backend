package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.RentalAgent;
import com.auca.vehicle_rental_management_system.service.RentalAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
