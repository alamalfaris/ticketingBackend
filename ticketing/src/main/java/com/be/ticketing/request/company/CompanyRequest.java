package com.be.ticketing.request.company;

import com.be.ticketing.request.BaseSearchRequest;

public class CompanyRequest extends BaseSearchRequest {
	
	private String address;
	private String cityCd;
	private String companyBusiness;
	private String companyCd;
	private String companyName;
	private String companyType;
	private String business;
	
	public CompanyRequest(Integer pageNumber, Integer pageSize, String address, String cityCd, String companyBusiness,
			String companyCd, String companyName, String companyType, String business) {
		super(pageNumber, pageSize);
		this.address = address;
		this.cityCd = cityCd;
		this.companyBusiness = companyBusiness;
		this.companyCd = companyCd;
		this.companyName = companyName;
		this.companyType = companyType;
		this.business = business;
	}

	public CompanyRequest() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
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

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}
}
