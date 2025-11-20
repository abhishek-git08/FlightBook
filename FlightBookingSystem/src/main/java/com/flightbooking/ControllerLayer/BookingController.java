package com.flightbooking.ControllerLayer;

import java.time.LocalDate;
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
import com.flightbooking.EntityLayer.Booking;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.EntityLayer.Payment;
import com.flightbooking.ServiceLayer.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> createBooking(@RequestBody Booking booking){
		return bookingService.createBooking(booking);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings(){
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(@PathVariable Integer id){
		return bookingService.getBookingById(id);
	}
	
	@GetMapping("/flightId/{id}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByFlightId(@PathVariable Integer id){
		return bookingService.getBookingByFlightId(id);
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByBookingDate(@PathVariable LocalDate date){
		return bookingService.getBookingByBookingDate(date);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByBookingStatus(@PathVariable String status){
		return bookingService.getBookingByBookingStatus(status);
	}
	
	@GetMapping("/BookingIdForPassengerDetails/{id}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByBookingId(@PathVariable Integer id){
		return bookingService.getPassengersByBookingId(id);
	}
	
	@GetMapping("/BookingIdForPaymentDetails/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsByBookingId(@PathVariable Integer id){
		return bookingService.getPaymentDetailsByBookingId(id);
	}
	
	@PutMapping("/{id}/{status}")
	public ResponseEntity<ResponseStructure<Booking>> updateBookingByBookingStatus(@PathVariable Integer id, @PathVariable String status){
		return bookingService.updateBookingByBookingStatus(id, status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> deleteBookingByBookingId(@PathVariable Integer id){
		return bookingService.deleteBookingByBookingId(id);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<ResponseStructure<Page<Booking>>> getFlightsByPaginationAndSorting(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "4") int size,
			@RequestParam(defaultValue = "id") String sortBy
			){
		return bookingService.getBookingsByPagination(page, size, sortBy);
	}
	
	
	

}
