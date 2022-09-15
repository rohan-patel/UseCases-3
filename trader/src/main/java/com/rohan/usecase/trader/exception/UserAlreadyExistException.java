package com.rohan.usecase.trader.exception;

public class UserAlreadyExistException extends RuntimeException{

	public UserAlreadyExistException(String msg) {
		super(msg);
	}
}