package com.backend.ticketing.response;

import org.springframework.http.HttpStatus;

public class BaseResponse<T> {
	
	private HttpStatus status;
	private String message;
	private Long countData;
	private T data;
	
	public BaseResponse(HttpStatus status, String message, Long countData, T data) {
		super();
		this.status = status;
		this.message = message;
		this.countData = countData;
		this.data = data;
	}
	
	public BaseResponse() {
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCountData() {
		return countData;
	}

	public void setCountData(Long countData) {
		this.countData = countData;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
