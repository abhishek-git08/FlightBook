package com.flightbooking.ServiceLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient.ResponseSpec;

import com.flightbooking.DAOLayer.PassengerDAO;
import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Booking;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbookingExceptionLayer.NoPassengersException;

@Service
public class PassengerService {
	
	@Autowired
	private PassengerDAO passengerDAO;
	
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(Passenger passenger){
		Passenger addedPassenger = passengerDAO.addPassenger(passenger);
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
		
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Passenger details added succesfully");
		response.setData(addedPassenger);
		
		return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers(){
		List<Passenger> passengers = passengerDAO.getAllPassenger();
		if(passengers.size()>0) {
			ResponseStructure<List<Passenger>> response = new ResponseStructure<>();
			
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("All passenger details retrieved succesfully");
			response.setData(passengers);
			
			return new ResponseEntity<ResponseStructure<List<Passenger>>>(response, HttpStatus.CREATED);
		}
		else {
			throw new NoPassengersException("No Passengers Available");
		}
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer id){
		Optional<Passenger> opt = passengerDAO.getPassengerById(id);
		if(opt.isPresent()) {
			ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
			
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Passenger details retrieved for id "+id);
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.CREATED);
		}
		else {
			throw new NoPassengersException("No Passenger with the id "+id);
		}
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger){
		
		if (passenger.getId() == null) {
	        throw new NoPassengersException("Passenger ID must be provided for updating");
	    }
		
		Optional<Passenger> opt = passengerDAO.getPassengerById(passenger.getId());
		if(opt.isPresent()) {
			Passenger newPassenger = passengerDAO.updatePassenger(passenger);
			
			ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger details updated for id "+opt.get().getName());
			response.setData(newPassenger);
			
			return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.OK);
		}
		else {
			throw new NoPassengersException("No such passenger found to be updated");
		}
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNumber(Long number){
		Optional<Passenger> opt = passengerDAO.getPassengerByContactNumber(number);
		if(opt.isPresent()) {
			ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger details retrieved based on contact number "+number);
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.CREATED);
		}
		else {
			throw new NoPassengersException("No Passenger with contact number "+number);
		}
	}
	
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengersByPaginationAndSorting(int page, int size, String sortby){
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
		Page<Passenger> pages=  passengerDAO.getPassengersByPaginationAndSorting(pageable);
		
		ResponseStructure<Page<Passenger>> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Passengers retrieved with pagination and sorting");
	    response.setData(pages);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
}
