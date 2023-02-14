package com.be.ticketing.request.supportedobject;

import com.be.ticketing.request.BaseSearchRequest;

public class SupportedObjectRequest extends BaseSearchRequest {
	
	private String companyId;
	private String description;
	private String objectCode;
	private String objectName;
	private String objectType;
	private String companyName;
	private String companyCode;
	
	public SupportedObjectRequest(Integer pageNumber, Integer pageSize, String companyId, String description,
			String objectCode, String objectName, String objectType, String companyName, String companyCode) {
		super(pageNumber, pageSize);
		this.companyId = companyId;
		this.description = description;
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectType = objectType;
		this.companyName = companyName;
		this.companyCode = companyCode;
	}

	public SupportedObjectRequest() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
