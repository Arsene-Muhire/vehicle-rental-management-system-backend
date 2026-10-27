package com.auca.vehicle_rental_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicle_class")
public class VehicleClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "vehicleClasses")
    @JsonIgnore
    private List<RentalAgent> rentalAgents;

    public VehicleClass() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RentalAgent> getRentalAgents() {
        return rentalAgents;
    }

    public void setRentalAgents(List<RentalAgent> rentalAgents) {
        this.rentalAgents = rentalAgents;
    }
}
