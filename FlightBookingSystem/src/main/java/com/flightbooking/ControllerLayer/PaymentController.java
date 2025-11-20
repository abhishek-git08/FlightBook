package com.flightbooking.ControllerLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.EntityLayer.Payment;
import com.flightbooking.ServiceLayer.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Payment>> recordPayment(Payment payment) {
		return paymentService.recordPayment(payment);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {
		return paymentService.getAllPayments();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable Integer id) {
		return paymentService.getPaymentById(id);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable String status) {
		return paymentService.getPaymentByStatus(status);
	}
	
	@GetMapping("/amount/{amount}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPaymentsGreaterThan(@PathVariable Double amount) {
		return paymentService.getAllPaymentsGreaterThan(amount);
	}
	
	@GetMapping("/mode/{mode}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPaymentsWithModeOfTransaction(@PathVariable String mode) {
		return paymentService.getAllPaymentsWithModeOfTransaction(mode);
	}
	
	@PostMapping("/id/{id}/status/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer id, @PathVariable String status) {
		return paymentService.updatePaymentStatus(id, status);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentsByPaginationAndSorting(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "4") int size,
			@RequestParam(defaultValue = "id") String sortby){
		return paymentService.getPaymentsByPagination(page,size,sortby);
	}
	
	
}
