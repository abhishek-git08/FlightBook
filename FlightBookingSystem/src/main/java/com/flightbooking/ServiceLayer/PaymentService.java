package com.flightbooking.ServiceLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightbooking.DAOLayer.PaymentDAO;
import com.flightbooking.DTOLayer.ResponseStructure;
import com.flightbooking.EntityLayer.Passenger;
import com.flightbooking.EntityLayer.Payment;
import com.flightbookingExceptionLayer.IdNotFoundException;
import com.flightbookingExceptionLayer.NoPaymentException;

public class PaymentService {
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	public ResponseEntity<ResponseStructure<Payment>> recordPayment(Payment payment) {
		Payment record=paymentDAO.recordPayment(payment);
		ResponseStructure<Payment> response=new ResponseStructure<Payment>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Payment details saved.");
		response.setData(record);
		
		return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {
		List<Payment> records=paymentDAO.getAllPayment();
		
		if(records.size()>0) {
			ResponseStructure<List<Payment>> response=new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All payment details fetched.");
			response.setData(records);
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		else {
			throw new NoPaymentException("No Payment records available");
		}
	}
	
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(Integer id) {
		Optional<Payment> opt = paymentDAO.getPaymentById(id);
		if(opt.isPresent()) {
			ResponseStructure<Payment> response=new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All payment details fetched.");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
		}
		else {
			throw new IdNotFoundException("No Payment found for the particular id");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(String status) {
		Optional<List<Payment>> opt = paymentDAO.getPaymentByStatus(status);
		if(opt.isPresent()) {
			ResponseStructure<List<Payment>> response=new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All "+status+" payment details fetched");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		else {
			throw new NoPaymentException("No "+status+" payment records available ");
		}
	}
	
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPaymentsGreaterThan(Double amount) {
		Optional<List<Payment>> opt = paymentDAO.getAllPaymentGreaterThan(amount);
		if(opt.isPresent()) {
			ResponseStructure<List<Payment>> response=new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All payment details greater than "+amount+" fetched");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		else {
			throw new NoPaymentException("No payment records greater than "+amount+"available ");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPaymentsWithModeOfTransaction(String mode) {
		Optional<List<Payment>> opt = paymentDAO.getAllTransactionsBy(mode);
		if(opt.isPresent()) {
			ResponseStructure<List<Payment>> response=new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All payment details with "+mode+ "  transaction fetched");
			response.setData(opt.get());
			
			return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
		}
		else {
			throw new NoPaymentException("No payment records with "+mode+" transaction");
		}
	}
	
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(Integer id, String status) {
		
		Optional<Payment> opt = paymentDAO.getPaymentById(id);
		if(opt.isPresent()) {
			Payment payment = opt.get();
			payment.setStatus(status);
			Payment record=paymentDAO.recordPayment(payment);
			
			ResponseStructure<Payment> response=new ResponseStructure<Payment>();
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Payment details saved.");
			response.setData(record);
			return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.CREATED);

		}else {
			throw new IdNotFoundException("No payment record available to update with id "+id);
		}
			
	}

	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentsByPagination(int page, int size, String sortby) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
		Page<Payment> pages=  paymentDAO.getPaymentsByPaginationAndSorting(pageable);
		
		ResponseStructure<Page<Payment>> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Passengers retrieved with pagination and sorting");
	    response.setData(pages);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
