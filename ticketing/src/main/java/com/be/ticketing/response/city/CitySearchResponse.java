package com.be.ticketing.response.city;

import com.be.ticketing.response.CommonResponse;

public class CitySearchResponse extends CommonResponse {
	
	private String cityCd;
	private String cityName;
	private String provinceId;
	private String provinceCode;
	
	public CitySearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime, String cityCd,
			String cityName, String provinceId, String provinceCode) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.cityCd = cityCd;
		this.cityName = cityName;
		this.provinceId = provinceId;
		this.provinceCode = provinceCode;
	}
	
	public CitySearchResponse() {
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	
}
