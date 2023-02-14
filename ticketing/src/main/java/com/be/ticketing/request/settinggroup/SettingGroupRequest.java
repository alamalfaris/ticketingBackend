package com.be.ticketing.request.settinggroup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingGroupRequest {
	@JsonProperty("groupCd")
	private String groupCd;
	
	@JsonProperty("groupName")
	private String groupName;
	
	@JsonProperty("groupDesc")
	private String groupDesc;

	public SettingGroupRequest(String groupCd, String groupName, String groupDesc) {
		super();
		this.groupCd = groupCd;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
	}
	
	public SettingGroupRequest() {
	}

	public String getGroupCd() {
		return groupCd;
	}

	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	
	
}
