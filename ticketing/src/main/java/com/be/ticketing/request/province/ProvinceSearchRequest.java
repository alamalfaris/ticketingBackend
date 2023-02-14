package com.be.ticketing.request.province;

import com.be.ticketing.request.BaseSearchRequest;

public class ProvinceSearchRequest extends BaseSearchRequest {
	private String provinceCd;
	private String provinceName;
	
	public ProvinceSearchRequest(Integer pageNumber, Integer pageSize, String provinceCd, String provinceName) {
		super(pageNumber, pageSize);
		this.provinceCd = provinceCd;
		this.provinceName = provinceName;
	}
	
	public ProvinceSearchRequest() {
	}

	public String getProvinceCd() {
		return provinceCd;
	}

	public void setProvinceCd(String provinceCd) {
		this.provinceCd = provinceCd;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
}
