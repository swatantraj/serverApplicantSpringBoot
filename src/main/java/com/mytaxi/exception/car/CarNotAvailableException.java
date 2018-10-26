package com.mytaxi.exception.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car Not Available")
public class CarNotAvailableException extends CarException {

	private static final long serialVersionUID = -8286002109063841731L;
	
	public CarNotAvailableException(String message) {
		super(message);
	}

}
