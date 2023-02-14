package com.be.ticketing.handler;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.exception.TryCatchException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> resolveException(Exception ex) {
		String message = ex.getMessage();
		String status = Constants.ERROR_STATUS;

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("countData", null);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> resolveException(NullPointerException ex) {
		String message = "NullPointerException";
		String status = Constants.ERROR_STATUS;

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("countData", null);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> resolveException(BadRequestException exception) {
		String message = exception.getMessage();
		String status = exception.getStatus();

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("countData", null);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TryCatchException.class)
	public ResponseEntity<Object> resolveException(TryCatchException exception) {
		String message = exception.getMessage();
		String status = exception.getStatus();

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("countData", null);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
