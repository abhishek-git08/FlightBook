package com.flightbooking.RepositoryLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightbooking.EntityLayer.Booking;
import com.flightbooking.EntityLayer.Flight;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

	Optional<List<Booking>> findAllByBookingDate(LocalDate bookingDate);

	Optional<List<Booking>> findAllByStatus(String status);

	Optional<List<Booking>> findAllByFlight(Flight flight);
	
	

}
