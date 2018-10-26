package com.mytaxi.exception.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car Already in Use")
public class CarAlreadyInUseException extends CarException {

	private static final long serialVersionUID = 3859780152447089226L;

	public CarAlreadyInUseException(String message) {
		super(message);
	}

}
