package com.be.ticketing.response;

import java.util.List;

public class SearchResponse3<T> {
	
	private String status;
	
	private String message;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Integer totalDataInPage;
	
	private Long totalData;
	
	private Integer totalPages;
	
	private List<T> data;

	public SearchResponse3(String status, String message, Integer pageNo, Integer pageSize, Integer totalDataInPage,
			Long totalData, Integer totalPages, List<T> data) {
		super();
		this.status = status;
		this.message = message;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalDataInPage = totalDataInPage;
		this.totalData = totalData;
		this.totalPages = totalPages;
		this.data = data;
	}

	public SearchResponse3() {
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalDataInPage() {
		return totalDataInPage;
	}

	public void setTotalDataInPage(Integer totalDataInPage) {
		this.totalDataInPage = totalDataInPage;
	}

	public Long getTotalData() {
		return totalData;
	}

	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
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
	
	
	
	
}
