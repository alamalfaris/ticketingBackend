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
@Table(name = "app_city")
public class City extends CommonEntity {
	
	@Id
    @Column(name = "city_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cityId;
	
	@Column(name = "city_code")
	private String cityCode;
	
	@Column(name = "city_name")
	private String cityName;
	
	@ManyToOne
	@JoinColumn(name = "province_id", referencedColumnName = "province_id")
	private Province province;

	public City(Long cityId, String cityCode, String cityName, Province province) {
		super();
		this.cityId = cityId;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.province = province;
	}
	
	public City() {
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
	
	
}
