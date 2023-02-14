package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_company")
public class Company extends CommonEntity {
	
	@Id
	@Column(name = "company_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyId;
	
	@Column(name = "company_code")
	private String companyCode;
	
	@Column(name = "company_name")
	private String companyName;
	
	@ManyToOne
	@JoinColumn(name = "company_type", referencedColumnName = "param_code")
	private Parameter companyType;
	
	@ManyToOne
	@JoinColumn(name = "company_business", referencedColumnName = "param_code")
	private Parameter companyBusiness;
	
	@Column(name = "address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "city_id", referencedColumnName = "city_id")
	private City cityId;

	public Company(Long companyId, String companyCode, String companyName, Parameter companyType,
			Parameter companyBusiness, String address, City cityId) {
		super();
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.companyType = companyType;
		this.companyBusiness = companyBusiness;
		this.address = address;
		this.cityId = cityId;
	}

	public Company() {
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Parameter getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Parameter companyType) {
		this.companyType = companyType;
	}

	public Parameter getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(Parameter companyBusiness) {
		this.companyBusiness = companyBusiness;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public City getCityId() {
		return cityId;
	}

	public void setCityId(City cityId) {
		this.cityId = cityId;
	}
}
