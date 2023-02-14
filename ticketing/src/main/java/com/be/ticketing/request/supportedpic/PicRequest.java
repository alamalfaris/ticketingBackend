package com.be.ticketing.request.supportedpic;

public class PicRequest {
	
	private String email;
	private String mobileNo;
	private String name;
	private Long picId;
	
	public PicRequest(String email, String mobileNo, String name, Long picId) {
		super();
		this.email = email;
		this.mobileNo = mobileNo;
		this.name = name;
		this.picId = picId;
	}
	
	public PicRequest() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}
}
