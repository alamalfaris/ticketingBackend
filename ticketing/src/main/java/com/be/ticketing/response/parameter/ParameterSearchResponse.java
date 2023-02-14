package com.be.ticketing.response.parameter;

public class ParameterSearchResponse {
	private String paramCode;
	private String paramName;
	private String paramDesc;
	
	public ParameterSearchResponse(String paramCode, String paramName, String paramDesc) {
		super();
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
	}
	
	public ParameterSearchResponse() {
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
