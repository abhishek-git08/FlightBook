package com.flightbookingExceptionLayer;

public class NoSeatAvailableException extends RuntimeException{
	public NoSeatAvailableException(String msg){
		super(msg);
	}
}
