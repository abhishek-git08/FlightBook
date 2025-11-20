package com.flightbooking.ControllerLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Flight;
import com.flightbooking.ServiceLayer.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	
	//Add a Flight 
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> addFlight(@RequestBody Flight flight){
		return flightService.addFlight(flight);
	}
	
	//Get All Flights list
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights(){
		return flightService.getAllFlights(); 
		
	}
	
	
	//Get the flight by id 
	@GetMapping("id/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id){
		return flightService.getFlightById(id);
	}
	
	//Get Flight by Source and Destination
	@GetMapping("/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(@PathVariable String source, @PathVariable String destination){
		return flightService.getFlightBySourceAndDestination(source, destination);
	}
	
	//Get Flight by Airline
	@GetMapping("airline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(@PathVariable String airline){
		return flightService.getFlightsByAirline(airline);
	}
	
	//Update Flight by id
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight){
		return flightService.updateFlight(flight);
	}
	
	//Delete Flight by id
	@DeleteMapping
	public ResponseEntity<ResponseStructure<String>> deleteFlight(@PathVariable Integer id){
		return flightService.deleteFlight(id);
	}
	
	@GetMapping("/page")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightsByPage(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size,
	        @RequestParam(defaultValue = "id") String sortBy) {
	    return flightService.getFlightsByPage(page, size, sortBy);
	}

	
	

	

}
