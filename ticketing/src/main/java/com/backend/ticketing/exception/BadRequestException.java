package com.backend.ticketing.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 2361439457674495952L;

	private final HttpStatus status;
	private final String message;

	public BadRequestException(String message) {
		super();
		this.status = HttpStatus.BAD_REQUEST;
		this.message = message;
	}

	public BadRequestException(String message, Throwable exception) {
		super(exception);
		this.status = HttpStatus.BAD_REQUEST;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
