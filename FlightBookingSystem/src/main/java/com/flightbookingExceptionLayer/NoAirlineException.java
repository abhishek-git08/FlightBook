package com.flightbookingExceptionLayer;

public class NoAirlineException extends RuntimeException{
	public NoAirlineException(String msg) {
		super(msg);
	}

}
