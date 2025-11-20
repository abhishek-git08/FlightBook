package com.flightbooking.ControllerLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.ServiceLayer.PassengerService;


@RestController
@RequestMapping("/passenger")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;
	
	//add Passenger
	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(@RequestBody Passenger passenger){
		return passengerService.addPassenger(passenger);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers(){
		return passengerService.getAllPassengers();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(@PathVariable Integer id){
		return passengerService.getPassengerById(id);
	}
	
//-----------------------------------------------------------------------------------------------------------------------------//	
	@GetMapping
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(@PathVariable Passenger passenger){
		return passengerService.updatePassenger(passenger);
	}
	
//-----------------------------------------------------------------------------------------------------------------------------//	
	
	@GetMapping("/ContactNumber/{number}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNumber(@PathVariable Long number){
		return passengerService.getPassengerByContactNumber(number);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengersByPaginationAndSorting(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "4") int size,
			@RequestParam(defaultValue = "id") String sortby){
		return passengerService.getPassengersByPaginationAndSorting(page,size,sortby);
	}
	
	
	
	
	
}
