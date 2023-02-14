package com.be.ticketing.response.setting;

import com.be.ticketing.response.CommonResponse;

public class SettingSearchResponse extends CommonResponse {
	private String settingCode;
	private String settingGroupCode;
	private String settingDesc;
	private String settingValue;
	private String settingValueType;
	
	public SettingSearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String settingCode, String settingGroupCode, String settingDesc, String settingValue,
			String settingValueType) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.settingCode = settingCode;
		this.settingGroupCode = settingGroupCode;
		this.settingDesc = settingDesc;
		this.settingValue = settingValue;
		this.settingValueType = settingValueType;
	}
	
	public SettingSearchResponse() {
	}

	public String getSettingCode() {
		return settingCode;
	}

	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}

	public String getSettingGroupCode() {
		return settingGroupCode;
	}

	public void setSettingGroupCode(String settingGroupCode) {
		this.settingGroupCode = settingGroupCode;
	}

	public String getSettingDesc() {
		return settingDesc;
	}

	public void setSettingDesc(String settingDesc) {
		this.settingDesc = settingDesc;
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
