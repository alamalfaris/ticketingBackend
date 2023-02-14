package com.be.ticketing.response;

public class BaseResponse<T> {
	private String status;
	private String message;
	private Long countData;
	private T data;
	
	public BaseResponse(String status, String message, Long countData, T data) {
		super();
		this.status = status;
		this.message = message;
		this.countData = countData;
		this.data = data;
	}
	
	public BaseResponse() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
