package com.flightbookingExceptionLayer;

public class NoPassengersException extends RuntimeException{
	public NoPassengersException(String msg) {
		super(msg);
	}

}
