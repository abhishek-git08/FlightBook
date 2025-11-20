package com.flightbooking.ServiceLayer;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.flightbooking.DAOLayer.FlightDAO;
import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Flight;
import com.flightbookingExceptionLayer.IdNotFoundException;
import com.flightbookingExceptionLayer.NoAirlineException;
import com.flightbookingExceptionLayer.SourceDestinationException;

@Service
public class FlightService {
	
	@Autowired
	private FlightDAO flightdao;
	
	//Add a Flight 
	public ResponseEntity<ResponseStructure<Flight>> addFlight(@RequestBody Flight flight){
		
		Flight addedFlight  = flightdao.addFlight(flight);
		ResponseStructure<Flight> response = new ResponseStructure<>();
		
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Details Added");
		response.setData(addedFlight);
		
		return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.CREATED);
	}
	
	//Get All Flights list
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights(){
		List<Flight> flights = flightdao.getAllFlight();
		
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Flight Details Retrieved");
		response.setData(flights);
		
		return new ResponseEntity<ResponseStructure<List<Flight>>>(response, HttpStatus.OK);

	}
	
	
	//Get the flight by id 
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id){
		Optional<Flight> opt = flightdao.getFlightById(id);
		
		ResponseStructure<Flight> response = new ResponseStructure<Flight>();
		
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight detail retreived based on id");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.OK);
		}
		else {
			throw new IdNotFoundException("The given Id is not Available");
		}
		
	}
	
	//Get Flight by Source and Destination
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(@PathVariable String source, String destination){
		Optional<List<Flight>> opt = flightdao.getFlightBySourceAndDestination(source, destination);
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();
		
		if(opt.isPresent()&& !opt.get().isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Flights from "+source+" to "+destination+" retrieved");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<List<Flight>>>(response,HttpStatus.OK);
			
		}
		else {
			throw new SourceDestinationException("No Flights Available");
		}
	}
	
	
	//Get Flight by Airline
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightsByAirline(@PathVariable String airline){
		Optional<List<Flight>> opt = flightdao.getFlightsByAirline(airline);
		ResponseStructure<List<Flight>> response = new ResponseStructure<>();
		
		if(opt.isPresent()&& !opt.get().isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Flights under "+airline+" retrieved");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<List<Flight>>>(response,HttpStatus.OK);
			
		}
		else {
			throw new NoAirlineException("No flights Available");
		}
	}
	
	//Update Flight
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight){
		Flight newFlight = flightdao.updateFlight(flight);
		ResponseStructure<Flight> response = new ResponseStructure<>();
		
		if(newFlight.getId()!=0){
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("New Flight details updated");
			response.setData(newFlight);
			return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.OK);
			
		}
		else {
			throw new NoAirlineException("No flights found in this ID");
		}
	}
	
	//Delete Flight by id
	public ResponseEntity<ResponseStructure<String>> deleteFlight(@PathVariable Integer id){
		Optional<Flight> opt = flightdao.getFlightById(id);
		ResponseStructure<String> response = new ResponseStructure<>();
		
		if(opt.isPresent()){
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight details deleted for id : "+id);
			response.setData(response.getMessage());
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
			
		}
		else {
			throw new NoAirlineException("No Airline found in this ID");
		}
	}
	
	
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightsByPage(int page, int size, String sortBy) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<Flight> flightPage = flightdao.getAllFlights(pageable);

	    ResponseStructure<Page<Flight>> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Flights retrieved with pagination and sorting");
	    response.setData(flightPage);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	
	
	
}
