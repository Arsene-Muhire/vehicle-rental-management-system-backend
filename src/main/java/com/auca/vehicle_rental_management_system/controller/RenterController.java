package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.Renter;
import com.auca.vehicle_rental_management_system.service.RenterService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/renters")
public class RenterController {

    @Autowired
    private RenterService renterService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Renter renter) {
        String message = renterService.save(renter);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(renterService.findAll());
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<Object> findByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(renterService.findByLastName(lastName));
    }

    @GetMapping("/of-rental-agent/{rentalAgentId}")
    public ResponseEntity<Object> findByRentalAgent(@PathVariable UUID rentalAgentId) {
        Optional<List<Renter>> renters = renterService.findByRentalAgent(rentalAgentId);
        if (renters.isPresent()) {
            return ResponseEntity.ok(renters.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The rental agent with that id does not exist");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Renter> renter = renterService.findById(id);
        if (renter.isPresent()) {
            return ResponseEntity.ok(renter.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The renter with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Renter renter) {
        String message = renterService.update(id, renter);
        if (message.equals("The renter with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = renterService.delete(id);
        if (message.equals("The renter with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
