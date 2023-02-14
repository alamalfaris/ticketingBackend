package com.be.ticketing.response.supportedpic;

import java.math.BigInteger;

public class PicResponse {
	
	private String email;
	private String mobileNo;
	private String name;
	private BigInteger picId;
	
	public PicResponse(String email, String mobileNo, String name, BigInteger picId) {
		super();
		this.email = email;
		this.mobileNo = mobileNo;
		this.name = name;
		this.picId = picId;
	}
	
	public PicResponse() {
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

	public BigInteger getPicId() {
		return picId;
	}

	public void setPicId(BigInteger picId) {
		this.picId = picId;
	}
}
