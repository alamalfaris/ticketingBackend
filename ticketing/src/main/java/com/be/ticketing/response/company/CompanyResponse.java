package com.be.ticketing.response.company;

import com.be.ticketing.response.CommonResponse;

public class CompanyResponse extends CommonResponse {
	
	private String recordFlag;
	private Long companyId;
	private String companyCd;
	private String companyName;
	private String companyType;
	private String companyBusiness;
	private String companyAddress;
	private String cityId;
	
	public CompanyResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String recordFlag, Long companyId, String companyCd, String companyName, String companyType,
			String companyBusiness, String companyAddress, String cityId) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.recordFlag = recordFlag;
		this.companyId = companyId;
		this.companyCd = companyCd;
		this.companyName = companyName;
		this.companyType = companyType;
		this.companyBusiness = companyBusiness;
		this.companyAddress = companyAddress;
		this.cityId = cityId;
	}

	public CompanyResponse() {
	}

	public String getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}
