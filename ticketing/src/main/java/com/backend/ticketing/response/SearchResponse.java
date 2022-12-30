package com.backend.ticketing.response;

import java.util.List;

public class SearchResponse<T> {
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Integer totalDataInPage;
	
	private Long totalData;
	
	private Integer totalPages;
	
	private List<T> listData;

	public SearchResponse(Integer pageNo, Integer pageSize, Integer totalDataInPage, Long totalData, Integer totalPages,
			List<T> listData) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalDataInPage = totalDataInPage;
		this.totalData = totalData;
		this.totalPages = totalPages;
		this.listData = listData;
	}
	
	public SearchResponse() {
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

	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}
}
