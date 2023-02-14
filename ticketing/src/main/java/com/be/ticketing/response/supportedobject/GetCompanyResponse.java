package com.be.ticketing.response.supportedobject;

public class GetCompanyResponse {
	
	private String companyId;
	private String companyName;
	
	public GetCompanyResponse(String companyId, String companyName) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public GetCompanyResponse() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
