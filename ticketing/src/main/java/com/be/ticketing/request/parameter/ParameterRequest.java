package com.be.ticketing.request.parameter;

public class ParameterRequest {
	
	private String paramCode;
	private String paramName;
	private String paramDesc;
	
	public ParameterRequest(String paramCode, String paramName, String paramDesc) {
		super();
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
	}
	
	public ParameterRequest() {
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
	
}
