package com.be.ticketing.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseSearchRequest {
	@JsonProperty("pageNumber")
	private Integer pageNumber;
	
	@JsonProperty("pageSize")
	private Integer pageSize;

	public BaseSearchRequest(Integer pageNumber, Integer pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public BaseSearchRequest() {
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
