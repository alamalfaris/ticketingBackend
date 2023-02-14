package com.be.ticketing.request.settinggroup;

public class SettingGroupUploadRequest {
	private String settingGroupCode;
	private String settingGroupName;
	private String settingGroupDesc;
	
	public SettingGroupUploadRequest(String settingGroupCode, String settingGroupName, String settingGroupDesc) {
		super();
		this.settingGroupCode = settingGroupCode;
		this.settingGroupName = settingGroupName;
		this.settingGroupDesc = settingGroupDesc;
	}
	
	public SettingGroupUploadRequest() {
	}

	public String getSettingGroupCode() {
		return settingGroupCode;
	}

	public void setSettingGroupCode(String settingGroupCode) {
		this.settingGroupCode = settingGroupCode;
	}

	public String getSettingGroupName() {
		return settingGroupName;
	}

	public void setSettingGroupName(String settingGroupName) {
		this.settingGroupName = settingGroupName;
	}

	public String getSettingGroupDesc() {
		return settingGroupDesc;
	}

	public void setSettingGroupDesc(String settingGroupDesc) {
		this.settingGroupDesc = settingGroupDesc;
	}
	
	
}
