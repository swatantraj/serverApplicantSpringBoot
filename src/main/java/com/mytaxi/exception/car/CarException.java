package com.mytaxi.exception.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "MyTaxi Car Exception")
public class CarException extends Exception {

	private static final long serialVersionUID = 5504591282971907887L;

	public CarException(String message) {
		super(message);
	}

}
