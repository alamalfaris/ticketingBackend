package com.be.ticketing.request.setting;

public class SettingRequest {
	private String settingCode;
	private String settingDesc;
	private String settingGroupCode;
	private String settingValue;
	private String settingValueType;
	
	public SettingRequest(String settingCode, String settingDesc, String settingGroupCode, String settingValue,
			String settingValueType) {
		super();
		this.settingCode = settingCode;
		this.settingDesc = settingDesc;
		this.settingGroupCode = settingGroupCode;
		this.settingValue = settingValue;
		this.settingValueType = settingValueType;
	}
	
	public SettingRequest() {
	}

	public String getSettingCode() {
		return settingCode;
	}

	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}

	public String getSettingDesc() {
		return settingDesc;
	}

	public void setSettingDesc(String settingDesc) {
		this.settingDesc = settingDesc;
	}

	public String getSettingGroupCode() {
		return settingGroupCode;
	}

	public void setSettingGroupCode(String settingGroupCode) {
		this.settingGroupCode = settingGroupCode;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

	public String getSettingValueType() {
		return settingValueType;
	}

	public void setSettingValueType(String settingValueType) {
		this.settingValueType = settingValueType;
	}
	
	
}
