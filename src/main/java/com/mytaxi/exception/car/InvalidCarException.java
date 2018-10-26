package com.mytaxi.exception.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car Deleted from system. Contact Admin")
public class InvalidCarException extends CarException {

	private static final long serialVersionUID = 1797171921822915933L;

	public InvalidCarException(String message) {
		super(message);
	}

}
