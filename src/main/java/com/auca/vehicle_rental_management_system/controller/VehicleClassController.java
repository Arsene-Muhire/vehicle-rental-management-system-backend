package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.VehicleClass;
import com.auca.vehicle_rental_management_system.service.VehicleClassService;
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
@RequestMapping("/api/vehicle-classes")
public class VehicleClassController {

    @Autowired
    private VehicleClassService vehicleClassService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody VehicleClass vehicleClass) {
        String message = vehicleClassService.save(vehicleClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(vehicleClassService.findAll());
    }

    @GetMapping("/unused")
    public ResponseEntity<Object> findUnused() {
        return ResponseEntity.ok(vehicleClassService.findUnused());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<VehicleClass> vehicleClass = vehicleClassService.findById(id);
        if (vehicleClass.isPresent()) {
            return ResponseEntity.ok(vehicleClass.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The vehicle class with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody VehicleClass vehicleClass) {
        String message = vehicleClassService.update(id, vehicleClass);
        if (message.equals("The vehicle class with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = vehicleClassService.delete(id);
        if (message.equals("The vehicle class with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
