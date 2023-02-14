package com.be.ticketing.request.setting;

import java.util.List;

public class SettingDeleteRequest {
	private List<SettingCode> listCode;

	public SettingDeleteRequest(List<SettingCode> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public SettingDeleteRequest() {
	}

	public List<SettingCode> getListCode() {
		return listCode;
	}

	public void setListCode(List<SettingCode> listCode) {
		this.listCode = listCode;
	}
	
	
}
