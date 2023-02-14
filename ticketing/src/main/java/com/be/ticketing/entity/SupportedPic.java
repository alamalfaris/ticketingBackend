package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_pic_company")
public class SupportedPic extends CommonEntity {
	
	@Id
	@Column(name = "pic_company_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long picCompanyId;
	
	@Column(name = "pic_id")
	private Long picId;
	
	@Column(name = "company_id")
	private Long companyId;

	public SupportedPic(Long picCompanyId, Long picId, Long companyId) {
		super();
		this.picCompanyId = picCompanyId;
		this.picId = picId;
		this.companyId = companyId;
	}
	
	public SupportedPic() {
	}

	public Long getPicCompanyId() {
		return picCompanyId;
	}

	public void setPicCompanyId(Long picCompanyId) {
		this.picCompanyId = picCompanyId;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
