package com.be.ticketing.request.province;

import com.be.ticketing.request.BaseSearchRequest;

public class ProvinceRequest extends BaseSearchRequest {
	private String provinceCd;
	private String provinceName;
	
	public ProvinceRequest(String provinceCd, String provinceName) {
		super();
		this.provinceCd = provinceCd;
		this.provinceName = provinceName;
	}
	
	public ProvinceRequest() {
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
