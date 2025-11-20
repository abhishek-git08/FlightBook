package com.flightbooking.ServiceLayer;

import com.flightbooking.EntityLayer.Passenger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightbooking.DAOLayer.BookingDAO;
import com.flightbooking.DAOLayer.FlightDAO;
import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Booking;
import com.flightbooking.EntityLayer.Flight;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.EntityLayer.Payment;
import com.flightbookingExceptionLayer.DateNotFoundException;
import com.flightbookingExceptionLayer.IdNotFoundException;
import com.flightbookingExceptionLayer.NoAirlineException;
import com.flightbookingExceptionLayer.NoBookingsException;
import com.flightbookingExceptionLayer.NoPaymentException;
import com.flightbookingExceptionLayer.NoSeatAvailableException;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

	@Autowired
	private BookingDAO bookingDAO;
	
	@Autowired
	private FlightDAO flightDAO;
	
	@Transactional
	public ResponseEntity<ResponseStructure<Booking>> createBooking(Booking booking) {

	    Optional<Flight> optionalFlight = flightDAO.getFlightById(booking.getFlight().getId());
	    if (optionalFlight.isEmpty()) {
	        throw new RuntimeException("Flight not found");
	    }

	    Flight flight = optionalFlight.get();
	    booking.setFlight(flight);

	    int totalSeats = flight.getTotalSeats();

	    List<String> bookedSeats = flightDAO.getAllBookedSeats(flight.getId());
	    int remainingSeats = totalSeats - bookedSeats.size();

	    for (Passenger p : booking.getPassenger()) {
	        if (bookedSeats.contains(p.getSeatNumber())) {
	            throw new NoSeatAvailableException(
	                "Seat " + p.getSeatNumber() + " for " + p.getName() +
	                " is already booked! Try another seat. " +
	                remainingSeats + " seats are still available."
	            );
	        }
	    }

	    double pricePerSeat = flight.getPrice();
	    int noOfPeople = booking.getPassenger().size();
	    double totalAmount = pricePerSeat * noOfPeople;

	    Payment payment = booking.getPayment();
	    if (payment == null) {
	        throw new NoPaymentException("No Payment details provided");
	    }

	    payment.setAmount(totalAmount);
	    payment.setBooking(booking);
	    booking.setPayment(payment);

	    for (Passenger p : booking.getPassenger()) {
	        p.setBooking(booking);
	    }

	    Booking booked = bookingDAO.createBooking(booking);

	    ResponseStructure<Booking> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.CREATED.value());
	    response.setMessage("Booking created successfully!");
	    response.setData(booked);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings(){
		List<Booking> bookings = bookingDAO.getAllBookings();
		
		ResponseStructure<List<Booking>> response = new ResponseStructure();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking details retrieved");
		response.setData(bookings);
		
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer id){
		Optional<Booking> opt = bookingDAO.getBookingById(id);
		if(opt.isPresent()) {
			ResponseStructure<Booking> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking details for id no: "+id);
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
		}else {
			throw new IdNotFoundException("Id not Available");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(Integer fid){
		Optional<Flight> flight = flightDAO.getFlightById(fid);
		if(flight.isPresent()) {
			Optional<List<Booking>> opt = bookingDAO.getBookingsByFlight(flight.get());
		
			if(opt.isPresent()&&opt.get().size()>0) {
				ResponseStructure<List<Booking>> response = new ResponseStructure();
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("Booking details on Flight with id "+fid+" retrieved");
				response.setData(opt.get());
				
				return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
			}else {
				throw new NoBookingsException("No Bookings Available for the given Flight id "+fid);
			}
		}
		else {
			throw new IdNotFoundException("No Flights found for the given id "+fid);
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByBookingDate(LocalDate date){
		Optional<List<Booking>> opt = bookingDAO.getBookingByDate(date);
		if(opt.isPresent()) {
			ResponseStructure<List<Booking>> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking details of Bookings done on "+date+" retrieved");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
		}else {
			throw new DateNotFoundException("No bookings on "+date+" found");
		}
	}
	
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByBookingStatus(String status){
		Optional<List<Booking>> opt = bookingDAO.getBookingByStatus(status);
		if(opt.isPresent()) {
			ResponseStructure<List<Booking>> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking details based on booking status :"+status);
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
		}else {
			throw new NoBookingsException("No "+status+" bookings found");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByBookingId(Integer id){
		Optional<Booking> opt = bookingDAO.getAllPassengersInABooking(id);
		
		if(opt.isPresent()) {
			List<Passenger> passengers = opt.get().getPassenger();
			ResponseStructure<List<Passenger>> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("List of passengers in the booking with id "+id);
			response.setData(passengers);
			
			return new ResponseEntity<ResponseStructure<List<Passenger>>>(response,HttpStatus.OK);
		}else {
			throw new NoBookingsException("No Bookings found with id "+id);
		}
	}
	
	
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsByBookingId(Integer id){
		Optional<Booking> opt = bookingDAO.getPaymentDetailsOfABooking(id);
		
		if(opt.isPresent()) {
			Payment payment = opt.get().getPayment();
			ResponseStructure<Payment> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Payment details for the booking with id "+id);
			response.setData(payment);
			
			return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
		}else {
			throw new NoBookingsException("No Bookings found with id "+id);
		}
	}
	
	

	
	public ResponseEntity<ResponseStructure<Booking>> updateBookingByBookingStatus(Integer id, String status){
		Optional<Booking> opt = bookingDAO.getBookingById(id);
		ResponseStructure<Booking> response = new ResponseStructure();
		if(opt.isPresent()) {
			if(opt.get().getStatus()==status) {
				response.setMessage("Booking status already updated to '"+status+"'");
			}else {
				opt.get().setStatus(status);
				Booking booked = bookingDAO.updateBookingByStatus(opt.get());
				response.setMessage("Booking Status updated to "+status);
			}
			response.setStatusCode(HttpStatus.OK.value());
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
		}
		else {
			throw new NoBookingsException("No Booking available to update");
		}
	}
	
	public ResponseEntity<ResponseStructure<Booking>> deleteBookingByBookingId(Integer id){
		Optional<Booking> opt = bookingDAO.getBookingById(id);
		if(opt.isPresent()) {
			bookingDAO.deleteBooking(id);
			ResponseStructure<Booking> response = new ResponseStructure();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Booking details of id : "+id+" deleted ");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
		}else {
			throw new NoBookingsException("No bookings found with id "+id);
		}
	}
	
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingsByPagination(int page, int size, String sortBy){
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<Booking> bookingPages= bookingDAO.getAllBookings(pageable);
		
		ResponseStructure<Page<Booking>> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Bookings retrieved with pagination and sorting");
	    response.setData(bookingPages);

	    return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	
}
