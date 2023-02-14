package com.be.ticketing.response;

import java.util.List;

public class SearchResponse<T> {
	private String status;
	private String message;
	private Long countData;
	private List<T> data;
	
	public SearchResponse(String status, String message, Long countData, List<T> data) {
		super();
		this.status = status;
		this.message = message;
		this.countData = countData;
		this.data = data;
	}
	
	public SearchResponse() {
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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
