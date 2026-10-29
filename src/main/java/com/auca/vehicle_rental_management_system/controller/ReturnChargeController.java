package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.ReturnCharge;
import com.auca.vehicle_rental_management_system.service.ReturnChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/return-charges")
public class ReturnChargeController {

    @Autowired
    private ReturnChargeService returnChargeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID rentalId, @RequestBody ReturnCharge returnCharge) {
        String message = returnChargeService.save(rentalId, returnCharge);
        if (message.equals("The rental with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("Return charges are only for RETURNED rentals")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    @GetMapping("/renter/{licenseNumber}")
    public ResponseEntity<Object> findByRenter(@PathVariable String licenseNumber) {
        return ResponseEntity.ok(returnChargeService.findByRenter(licenseNumber));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = returnChargeService.delete(id);
        if (message.equals("The return charge with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
