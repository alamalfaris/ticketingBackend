package com.be.ticketing.response.supportedobject;

public class GetObjectTypeResponse {
	
	private String objectTypeCode;
	private String objectTypeName;
	
	public GetObjectTypeResponse(String objectTypeCode, String objectTypeName) {
		super();
		this.objectTypeCode = objectTypeCode;
		this.objectTypeName = objectTypeName;
	}
	
	public GetObjectTypeResponse() {
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
}
