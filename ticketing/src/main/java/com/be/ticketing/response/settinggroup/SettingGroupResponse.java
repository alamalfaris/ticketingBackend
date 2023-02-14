package com.be.ticketing.response.settinggroup;

import com.be.ticketing.response.CommonResponse;

public class SettingGroupResponse extends CommonResponse {
	private String settingGroupCode;
	private String settingGroupName;
	private String settingGroupDesc;
	
	public SettingGroupResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String settingGroupCode, String settingGroupName, String settingGroupDesc) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.settingGroupCode = settingGroupCode;
		this.settingGroupName = settingGroupName;
		this.settingGroupDesc = settingGroupDesc;
	}

	public SettingGroupResponse() {
	}

	public String getSettingGroupCode() {
		return settingGroupCode;
	}

	public void setSettingGroupCode(String settingGroupCd) {
		this.settingGroupCode = settingGroupCd;
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
