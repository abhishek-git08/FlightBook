package com.flightbooking.DAOLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flightbooking.EntityLayer.Payment;
import com.flightbooking.RepositoryLayer.PaymentRepository;

@Repository
public class PaymentDAO {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment recordPayment(Payment payment) {
		return paymentRepository.save(payment);
	}
	
	public List<Payment> getAllPayment(){
		return paymentRepository.findAll();
	}
	
	public Optional<Payment> getPaymentById(Integer id){
		return paymentRepository.findById(id);
	}
	
	public Optional<List<Payment>> getPaymentByStatus(String status){
		return paymentRepository.findAllByStatus(status);
	}

	public Optional<List<Payment>> getAllPaymentGreaterThan(Double amount){
		return paymentRepository.findAllByAmountGreaterThan(amount);
	}
	
	public Optional<List<Payment>> getAllTransactionsBy(String mode){
		return paymentRepository.findAllByMode(mode);
	}
	
	public Payment updatePaymentStatus(Payment payment){
		return paymentRepository.save(payment);
	}
	
	public Page<Payment> getPaymentsByPaginationAndSorting(Pageable pageable ){
		return paymentRepository.findAllByPaginationAndSorting(pageable);
	}
}
