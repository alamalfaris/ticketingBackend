package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_province")
public class Province extends CommonEntity {
	
	@Id
    @Column(name = "province_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long provinceId;
	
	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "province_name")
	private String provinceName;

	public Province(Long provinceId, String provinceCode, String provinceName) {
		super();
		this.provinceId = provinceId;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
	}
	
	public Province() {
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
}
