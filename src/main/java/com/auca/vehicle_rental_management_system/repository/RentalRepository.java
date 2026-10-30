package com.auca.vehicle_rental_management_system.repository;

import com.auca.vehicle_rental_management_system.entity.Rental;
import com.auca.vehicle_rental_management_system.entity.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    boolean existsByRentalAgentIdAndPickupDateAndStatusNot(UUID rentalAgentId, LocalDate pickupDate, RentalStatus status);

    List<Rental> findByStatusOrderByPickupDateAsc(RentalStatus status);

    List<Rental> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start, LocalDate end);

    @Query("SELECT r.status, COUNT(r) FROM Rental r GROUP BY r.status ORDER BY r.status")
    List<Object[]> countByStatus();

    @Modifying
    @Query("UPDATE Rental r SET r.status = :newStatus WHERE r.rentalAgent.id = :rentalAgentId AND r.pickupDate = :pickupDate AND r.status <> :keepStatus")
    int cancelAgentDay(@Param("rentalAgentId") UUID rentalAgentId, @Param("pickupDate") LocalDate pickupDate, @Param("newStatus") RentalStatus newStatus, @Param("keepStatus") RentalStatus keepStatus);
}
