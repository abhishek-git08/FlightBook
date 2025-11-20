package com.flightbooking.RepositoryLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightbooking.EntityLayer.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {


	@Query("SELECT p.seatNumber FROM Passenger p WHERE p.booking.flight.id = :flightId")
	List<String> findSeatNumbersByFlightId(@Param("flightId") Integer flightId);

	Optional<Passenger> findByContactNumber(Long contactNumber);

	
}
