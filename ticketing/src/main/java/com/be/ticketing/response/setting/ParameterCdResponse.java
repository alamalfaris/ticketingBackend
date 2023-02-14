package com.be.ticketing.response.setting;

public class ParameterCdResponse {
	private String paramCode;
	private String settingValueType;
	
	public ParameterCdResponse(String paramCode, String settingValueType) {
		super();
		this.paramCode = paramCode;
		this.settingValueType = settingValueType;
	}
	
	public ParameterCdResponse() {
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getSettingValueType() {
		return settingValueType;
	}

	public void setSettingValueType(String settingValueType) {
		this.settingValueType = settingValueType;
	}
	
	
}
