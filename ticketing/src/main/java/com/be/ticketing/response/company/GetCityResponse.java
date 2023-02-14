package com.be.ticketing.response.company;

public class GetCityResponse {
	
	private String cityId;
	private String cityName;
	private String provinceName;
	
	public GetCityResponse(String cityId, String cityName, String provinceName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.provinceName = provinceName;
	}
	
	public GetCityResponse() {
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
