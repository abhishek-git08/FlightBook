package com.flightbooking.DAOLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.EntityLayer.Flight;
import com.flightbooking.RepositoryLayer.FlightRepository;
import com.flightbooking.RepositoryLayer.PassengerRepository;



@Repository
public class FlightDAO {
	
	
	@Autowired
	private FlightRepository flightRepository;
	
	 @Autowired
	 private PassengerRepository passengerRepository;
	
	public Flight addFlight(Flight flight) {
		return flightRepository.save(flight);
	}
	
	public List<Flight> getAllFlight() {
		return flightRepository.findAll();
	}
	
	public Optional<Flight> getFlightById(Integer id) {
		return flightRepository.findById(id);
	}
	
	public Optional<List<Flight>> getFlightBySourceAndDestination(String source, String destination){
		return flightRepository.getFlightsBySourceAndDestination(source,destination);
	}
	
	public Optional<List<Flight>> getFlightsByAirline(String airline){
		return flightRepository.getFlightsByAirline(airline);
	}
	
	public Flight updateFlight(Flight flight) {
		return flightRepository.save(flight);
	}
	
	public void deleteFlight(Integer id) {
		flightRepository.deleteById(id);
	}
	
	public Page<Flight> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

	public List<String> getAllBookedSeats(Integer flightId) {
		return passengerRepository.findSeatNumbersByFlightId(flightId);
	}
	
}
