package com.flightbookingExceptionLayer;

public class DateNotFoundException extends RuntimeException{
	public DateNotFoundException(String msg) {
			super(msg);
	}
}