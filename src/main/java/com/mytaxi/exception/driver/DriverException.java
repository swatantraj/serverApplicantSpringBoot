package com.mytaxi.exception.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "MyTaxi Driver Exception")
public class DriverException extends Exception {

	private static final long serialVersionUID = 288950069576132984L;

	public DriverException(String message) {
		super(message);
	}

}
