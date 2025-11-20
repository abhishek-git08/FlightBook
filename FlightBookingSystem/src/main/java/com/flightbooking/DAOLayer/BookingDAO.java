package com.flightbooking.DAOLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flightbooking.EntityLayer.Booking;
import com.flightbooking.EntityLayer.Flight;
import com.flightbooking.RepositoryLayer.BookingRepository;

@Repository
public class BookingDAO {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}
	
	public List<Booking> getAllBookings(){
		return bookingRepository.findAll();
	}
	
	
	public Optional<Booking> getBookingById(Integer integer) {
		return bookingRepository.findById(integer);
	}
	
	public Optional<List<Booking>> getBookingsByFlight(Flight flight) {
		return bookingRepository.findAllByFlight(flight);
	}
	
	public Optional<List<Booking>> getBookingByDate(LocalDate bookingDate) {
		return bookingRepository.findAllByBookingDate(bookingDate);
	}
	
	public Optional<List<Booking>> getBookingByStatus(String status) {
		return bookingRepository.findAllByStatus(status);
	}
	
	public Optional<Booking> getAllPassengersInABooking(Integer id) {
		return bookingRepository.findById(id);
	}
	
	public Optional<Booking> getPaymentDetailsOfABooking(Integer id) {
		return bookingRepository.findById(id);
	}
	
	public Booking updateBookingByStatus(Booking booking) {
		return bookingRepository.save(booking);
	}
	
	public void deleteBooking(Integer id) {
		bookingRepository.deleteById(id);
	}
	
	public Page<Booking> getAllBookings(Pageable pageable){
		return bookingRepository.findAll(pageable);
	}
	
	
	
	
	
	
	
	
}
