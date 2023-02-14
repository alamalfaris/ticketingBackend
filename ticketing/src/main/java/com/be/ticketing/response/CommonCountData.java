package com.be.ticketing.response;

public class CommonCountData {
	
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalDataPage;
	private Integer startData;
	private Integer endData;
	private Long totalData;
	private Integer totalPage;
	
	public CommonCountData(Integer pageNumber, Integer pageSize, Integer totalDataPage, Integer startData,
			Integer endData, Long totalData, Integer totalPage) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalDataPage = totalDataPage;
		this.startData = startData;
		this.endData = endData;
		this.totalData = totalData;
		this.totalPage = totalPage;
	}
	
	public CommonCountData() {
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

	public Integer getTotalDataPage() {
		return totalDataPage;
	}

	public void setTotalDataPage(Integer totalDataPage) {
		this.totalDataPage = totalDataPage;
	}

	public Integer getStartData() {
		return startData;
	}

	public void setStartData(Integer startData) {
		this.startData = startData;
	}

	public Integer getEndData() {
		return endData;
	}

	public void setEndData(Integer endData) {
		this.endData = endData;
	}

	public Long getTotalData() {
		return totalData;
	}

	public void setTotalData(Long totalData) {
		this.totalData = totalData;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}
