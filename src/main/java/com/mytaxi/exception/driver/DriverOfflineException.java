package com.mytaxi.exception.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Driver OFFLINE")
public class DriverOfflineException extends DriverException {

	private static final long serialVersionUID = 2209234602726648827L;

	public DriverOfflineException(String message) {
		super(message);
	}

}
