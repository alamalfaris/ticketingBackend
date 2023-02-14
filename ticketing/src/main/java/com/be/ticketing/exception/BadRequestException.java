package com.be.ticketing.exception;

import com.be.ticketing.constant.Constants;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 2361439457674495952L;

	private final String status;
	private final String message;

	public BadRequestException(String message) {
		super();
		this.status = Constants.ERROR_STATUS;
		this.message = message;
	}

	public BadRequestException(String message, Throwable exception) {
		super(exception);
		this.status = Constants.ERROR_STATUS;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
