package com.be.ticketing.response.province;

public class ProvinceResponse {
	private String provinceId;
	private String provinceName;
	
	public ProvinceResponse(String provinceId, String provinceName) {
		super();
		this.provinceId = provinceId;
		this.provinceName = provinceName;
	}
	
	public ProvinceResponse() {
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
}
