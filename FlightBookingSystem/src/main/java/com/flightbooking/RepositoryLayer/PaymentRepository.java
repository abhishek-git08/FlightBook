package com.flightbooking.RepositoryLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.EntityLayer.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Optional<List<Payment>> findAllByStatus(String status);

	Optional<List<Payment>> findAllByAmountGreaterThan(Double amount);

	Optional<List<Payment>> findAllByMode(String mode);

	Page<Payment> findAllByPaginationAndSorting(Pageable pageable);
	

}
