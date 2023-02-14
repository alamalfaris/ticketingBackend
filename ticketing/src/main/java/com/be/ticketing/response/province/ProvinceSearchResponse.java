package com.be.ticketing.response.province;

import com.be.ticketing.response.CommonResponse;

public class ProvinceSearchResponse extends CommonResponse {
	private String provinceCd;
	private String provinceName;
	
	public ProvinceSearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String provinceCd, String provinceName) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.provinceCd = provinceCd;
		this.provinceName = provinceName;
	}
	
	public ProvinceSearchResponse() {
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
