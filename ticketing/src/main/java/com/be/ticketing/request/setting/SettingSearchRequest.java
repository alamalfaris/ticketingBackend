package com.be.ticketing.request.setting;

import com.be.ticketing.request.BaseSearchRequest;

public class SettingSearchRequest extends BaseSearchRequest {
	private String settingCode;
	private String settingGroup;
	private String value;
	
	public SettingSearchRequest(String settingCode, String settingGroup, String value) {
		super();
		this.settingCode = settingCode;
		this.settingGroup = settingGroup;
		this.value = value;
	}
	
	public SettingSearchRequest() {
	}

	public String getSettingCode() {
		return settingCode;
	}

	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}

	public String getSettingGroup() {
		return settingGroup;
	}

	public void setSettingGroup(String settingGroup) {
		this.settingGroup = settingGroup;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
