package com.auca.vehicle_rental_management_system.controller;

import com.auca.vehicle_rental_management_system.entity.Branch;
import com.auca.vehicle_rental_management_system.service.BranchService;
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
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Branch branch) {
        String message = branchService.save(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(branchService.findAll());
    }

    @GetMapping("/free")
    public ResponseEntity<Object> findFree() {
        return ResponseEntity.ok(branchService.findFree());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Branch> branch = branchService.findById(id);
        if (branch.isPresent()) {
            return ResponseEntity.ok(branch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The branch with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Branch branch) {
        String message = branchService.update(id, branch);
        if (message.equals("The branch with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = branchService.delete(id);
        if (message.equals("The branch with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
