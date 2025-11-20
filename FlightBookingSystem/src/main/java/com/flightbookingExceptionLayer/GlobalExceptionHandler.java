package com.flightbookingExceptionLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Flight;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> INFHandler(IdNotFoundException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Invalid Id");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SourceDestinationException.class)
	public ResponseEntity<ResponseStructure<String>> SDEHandler(SourceDestinationException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Details not available for the selected locations");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NoAirlineException.class)
	public ResponseEntity<ResponseStructure<String>> NAEHandler(NoAirlineException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No details Available for the selected airline");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(DateNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> DNFHandler(DateNotFoundException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No Details available");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NoBookingsException.class)
	public ResponseEntity<ResponseStructure<String>> NBEHandler(NoBookingsException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No Details available");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NoPassengersException.class)
	public ResponseEntity<ResponseStructure<String>> NPEHandler(NoPassengersException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No Details available");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NoPaymentException.class)
	public ResponseEntity<ResponseStructure<String>> NPayEHandler(NoPaymentException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No Payment details available");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NoSeatAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> NPayEHandler(NoSeatAvailableException exception){
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Seat already Booked");
		response.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>> (response,HttpStatus.NOT_FOUND);
		
	}

}