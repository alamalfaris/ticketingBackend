package com.be.ticketing.response.company;

public class GetBusinessResponse {
	
	private String paramCode;
	private String companyBusiness;
	
	public GetBusinessResponse(String paramCode, String companyBusiness) {
		super();
		this.paramCode = paramCode;
		this.companyBusiness = companyBusiness;
	}
	
	public GetBusinessResponse() {
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
	}
}
