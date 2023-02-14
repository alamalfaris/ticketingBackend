package com.be.ticketing.request.city;

import com.be.ticketing.request.BaseSearchRequest;

public class CityRequest extends BaseSearchRequest {
	
	private String cityCd;
	private String cityName;
	private String provinceId;
	private String provinceCd;
	
	public CityRequest(String cityCd, String cityName, String provinceId,
			String provinceCd) {
		super();
		this.cityCd = cityCd;
		this.cityName = cityName;
		this.provinceId = provinceId;
		this.provinceCd = provinceCd;
	}
	
	public CityRequest() {
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

	public String getProvinceCd() {
		return provinceCd;
	}

	public void setProvinceCd(String provinceCd) {
		this.provinceCd = provinceCd;
	}
	
	
}
