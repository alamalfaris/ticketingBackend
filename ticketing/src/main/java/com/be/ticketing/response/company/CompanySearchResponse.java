package com.be.ticketing.response.company;

import com.be.ticketing.response.CommonResponse;

public class CompanySearchResponse extends CommonResponse {
	
	private String companyCd;
	private String companyName;
	private String type;
	private String business;
	private String address;
	private String cityId;
	private String companyType;
	private String companyBusiness;
	private String provinceName;
	
	public CompanySearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime, String companyCd,
			String companyName, String type, String business, String address, String cityId, String companyType,
			String companyBusiness, String provinceName) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.companyCd = companyCd;
		this.companyName = companyName;
		this.type = type;
		this.business = business;
		this.address = address;
		this.cityId = cityId;
		this.companyType = companyType;
		this.companyBusiness = companyBusiness;
		this.provinceName = provinceName;
	}

	public CompanySearchResponse() {
	}

	public String getCompanyCd() {
		return companyCd;
	}

	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
