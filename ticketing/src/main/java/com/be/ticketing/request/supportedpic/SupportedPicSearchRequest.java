package com.be.ticketing.request.supportedpic;

import com.be.ticketing.request.BaseSearchRequest;

public class SupportedPicSearchRequest extends BaseSearchRequest {
	
	private String company;
	private String picEmail;
	private String picName;
	private String extension;
	
	public SupportedPicSearchRequest(Integer pageNumber, Integer pageSize, String company, String picEmail,
			String picName, String extension) {
		super(pageNumber, pageSize);
		this.company = company;
		this.picEmail = picEmail;
		this.picName = picName;
		this.extension = extension;
	}

	public SupportedPicSearchRequest() {
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPicEmail() {
		return picEmail;
	}

	public void setPicEmail(String picEmail) {
		this.picEmail = picEmail;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
