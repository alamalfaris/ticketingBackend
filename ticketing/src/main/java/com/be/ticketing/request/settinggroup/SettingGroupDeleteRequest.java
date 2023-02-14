package com.be.ticketing.request.settinggroup;

import java.util.List;

public class SettingGroupDeleteRequest {
	private List<SettingGroupCode> listCode;

	public SettingGroupDeleteRequest(List<SettingGroupCode> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public SettingGroupDeleteRequest() {
	}

	public List<SettingGroupCode> getListCode() {
		return listCode;
	}

	public void setListCode(List<SettingGroupCode> listCode) {
		this.listCode = listCode;
	}
	
	
}
