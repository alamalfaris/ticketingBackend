package com.be.ticketing.response.supportedpic;

public class SupportedPicDownload {

	private String companyCode;
	private String companyName;
	private String fullName;
	private String mobileNo;
	private String emailAddress;
	
	public SupportedPicDownload(String companyCode, String companyName, String fullName, String mobileNo,
			String emailAddress) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.fullName = fullName;
		this.mobileNo = mobileNo;
		this.emailAddress = emailAddress;
	}
	
	public SupportedPicDownload() {
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
