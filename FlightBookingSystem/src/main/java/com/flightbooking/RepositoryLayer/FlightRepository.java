package com.flightbooking.RepositoryLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.EntityLayer.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer>{
	
	Optional<List<Flight>> getFlightsBySourceAndDestination(String source, String destination);
	
	Optional<List<Flight>> getFlightsByAirline(String airline);

	List<String> findAllSeatNumberById(Integer id);
	
}
