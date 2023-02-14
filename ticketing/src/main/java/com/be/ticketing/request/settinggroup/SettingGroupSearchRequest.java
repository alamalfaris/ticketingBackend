package com.be.ticketing.request.settinggroup;

import com.be.ticketing.request.BaseSearchRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingGroupSearchRequest extends BaseSearchRequest {
	@JsonProperty("groupCd")
	private String groupCd;
	
	@JsonProperty("groupName")
	private String groupName;

	public SettingGroupSearchRequest(Integer pageNumber, Integer pageSize, String groupCd, String groupName) {
		super(pageNumber, pageSize);
		this.groupCd = groupCd;
		this.groupName = groupName;
	}
	
	public SettingGroupSearchRequest() {
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
	
	
}
