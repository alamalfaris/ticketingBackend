package com.be.ticketing.response.city;

import com.be.ticketing.response.CommonResponse;

public class CityResponse extends CommonResponse {
	
	private Long cityId;
	private String cityCd;
	private String cityName;
	private String provinceId;
	private String recordFlag;
	
	public CityResponse(String createdBy, String createdTime, String updatedBy, String updatedTime, Long cityId,
			String cityCd, String cityName, String provinceId, String recordFlag) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.cityId = cityId;
		this.cityCd = cityCd;
		this.cityName = cityName;
		this.provinceId = provinceId;
		this.recordFlag = recordFlag;
	}
	
	public CityResponse() {
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
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

	public String getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}
	
	
}
