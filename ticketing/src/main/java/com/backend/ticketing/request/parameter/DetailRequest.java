package com.backend.ticketing.request.parameter;

public class DetailRequest {
	
	private String parameterCode;
	private String parameterName;
	private String parameterDesc;
	
	public DetailRequest(String parameterCode, String parameterName, String parameterDesc) {
		super();
		this.parameterCode = parameterCode;
		this.parameterName = parameterName;
		this.parameterDesc = parameterDesc;
	}
	
	public DetailRequest() {
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}
}
