package com.be.ticketing.request.settinggroup;

public class SettingGroupDownloadRequest {
	private String groupCd;
	private String groupName;
	private String extention;
	
	public SettingGroupDownloadRequest(String groupCd, String groupName, String extention) {
		super();
		this.groupCd = groupCd;
		this.groupName = groupName;
		this.extention = extention;
	}
	
	public SettingGroupDownloadRequest() {
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

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	
}
