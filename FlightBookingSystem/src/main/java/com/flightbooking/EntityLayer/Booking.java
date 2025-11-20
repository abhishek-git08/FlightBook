package com.flightbooking.EntityLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@CreationTimestamp
	private LocalDate bookingDate;
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private Flight flight;
	
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<Passenger> passenger;
	
	@OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
	private Payment payment;
}
