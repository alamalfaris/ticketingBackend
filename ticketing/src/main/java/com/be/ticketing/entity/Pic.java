package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_pic")
public class Pic extends CommonEntity {
	
	@Id
	@Column(name = "pic_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long picId;
	
	@Column(name = "email_address")
	private String email;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "full_name")
	private String fullName;

	public Pic(Long picId, String email, String mobileNo, String fullName) {
		super();
		this.picId = picId;
		this.email = email;
		this.mobileNo = mobileNo;
		this.fullName = fullName;
	}
	
	public Pic() {
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
