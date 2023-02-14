package com.be.ticketing.response.setting;

public class SettingGroupCdResponse {
	private String settingGroupCd;
	private String settingGroupName;
	
	public SettingGroupCdResponse(String settingGroupCd, String settingGroupName) {
		super();
		this.settingGroupCd = settingGroupCd;
		this.settingGroupName = settingGroupName;
	}
	
	public SettingGroupCdResponse() {
	}

	public String getSettingGroupCd() {
		return settingGroupCd;
	}

	public void setSettingGroupCd(String settingGroupCd) {
		this.settingGroupCd = settingGroupCd;
	}

	public String getSettingGroupName() {
		return settingGroupName;
	}

	public void setSettingGroupName(String settingGroupName) {
		this.settingGroupName = settingGroupName;
	}
	
	
}
