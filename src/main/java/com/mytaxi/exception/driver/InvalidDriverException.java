package com.mytaxi.exception.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Driver Deleted from system. Contact Admin")
public class InvalidDriverException extends DriverException {

	private static final long serialVersionUID = -1666366974084819025L;

	public InvalidDriverException(String message) {
		super(message);
	}

}
