package com.be.ticketing.exception;

import com.be.ticketing.constant.Constants;

public class TryCatchException extends RuntimeException {
	
	private static final long serialVersionUID = 23614394576744959L;
	
	private final String status;
	private final String message;
	
	public TryCatchException(String message) {
		super();
		this.status = Constants.ERROR_STATUS;
		this.message = message;
	}
	
	public TryCatchException(String message, Throwable exception) {
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
