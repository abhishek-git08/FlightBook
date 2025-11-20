package com.flightbooking.DAOLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.RepositoryLayer.PassengerRepository;

@Repository
public class PassengerDAO {

	@Autowired
	private PassengerRepository passengerRepository;
	
	public Passenger addPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}
	
	public List<String> getBookedSeats(Integer id) {
		return passengerRepository.findSeatNumbersByFlightId(id);
	}
	
	public List<Passenger> getAllPassenger(){
		return passengerRepository.findAll();
	}
	
	public Optional<Passenger> getPassengerById(Integer id){
		return passengerRepository.findById(id);
	}
	
	public Passenger updatePassenger(Passenger passenger){
		return passengerRepository.save(passenger);
	}
	
	public Optional<Passenger> getPassengerByContactNumber(Long contactNumber){
		return passengerRepository.findByContactNumber(contactNumber);
	}
	
	public Page<Passenger> getPassengersByPaginationAndSorting(Pageable pageable){
		return passengerRepository.findAll(pageable);
	}
	


	
	
	
	
	
	
	
}
