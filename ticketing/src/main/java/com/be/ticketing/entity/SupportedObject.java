package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_supported_object")
public class SupportedObject extends CommonEntity {
	
	@Id
	@Column(name = "object_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long objectId;
	
	@Column(name = "object_code")
	private String objectCode;
	
	@Column(name = "object_name")
	private String objectName;
	
	@Column(name = "object_type")
	private String objectType;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "company_id")
	private Long companyId;

	public SupportedObject(Long objectId, String objectCode, String objectName, String objectType, String description,
			Long companyId) {
		super();
		this.objectId = objectId;
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectType = objectType;
		this.description = description;
		this.companyId = companyId;
	}
	
	public SupportedObject() {
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
}
