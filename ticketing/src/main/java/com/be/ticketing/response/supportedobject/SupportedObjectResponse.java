package com.be.ticketing.response.supportedobject;

import com.be.ticketing.response.CommonResponse;

public class SupportedObjectResponse extends CommonResponse {
	
	private String objectCode;
	private String objectName;
	private String objectTypeCode;
	private String objectTypeName;
	private String description;
	private String companyName;
	private String companyId;
	
	public SupportedObjectResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String objectCode, String objectName, String objectTypeCode, String objectTypeName, String description,
			String companyName, String companyId) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.objectCode = objectCode;
		this.objectName = objectName;
		this.objectTypeCode = objectTypeCode;
		this.objectTypeName = objectTypeName;
		this.description = description;
		this.companyName = companyName;
		this.companyId = companyId;
	}
	
	public SupportedObjectResponse() {
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

	public String getObjectTypeCode() {
		return objectTypeCode;
	}

	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
