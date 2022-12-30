package com.backend.ticketing.handler;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.backend.ticketing.exception.BadRequestException;

@ControllerAdvice
public class ResponseExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> resolveException(Exception ex) {
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> resolveException(NullPointerException ex) {
		String message = "NullPointerException";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> resolveException(BadRequestException exception) {
		String message = exception.getMessage();
		HttpStatus status = HttpStatus.BAD_REQUEST;

		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", status);
		response.put("message", message);
		response.put("countData", null);
		response.put("data", null);

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
}
