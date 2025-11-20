package com.flightbookingExceptionLayer;

public class NoPaymentException extends RuntimeException{
	public NoPaymentException(String msg) {
			super(msg);
	}
	

}
