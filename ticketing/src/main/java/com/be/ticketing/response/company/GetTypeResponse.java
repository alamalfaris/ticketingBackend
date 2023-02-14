package com.be.ticketing.response.company;

public class GetTypeResponse {
	
	private String paramCode;
	private String companyType;
	
	public GetTypeResponse(String paramCode, String companyType) {
		super();
		this.paramCode = paramCode;
		this.companyType = companyType;
	}
	
	public GetTypeResponse() {
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
}
