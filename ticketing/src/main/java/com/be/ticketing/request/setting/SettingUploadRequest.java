package com.be.ticketing.request.setting;

public class SettingUploadRequest {
	private String settingCode;
	private String settingGroupCode;
	private String description;
	private String dataType;
	private String value;
	
	public SettingUploadRequest(String settingCode, String settingGroupCode, String description, String dataType,
			String value) {
		super();
		this.settingCode = settingCode;
		this.settingGroupCode = settingGroupCode;
		this.description = description;
		this.dataType = dataType;
		this.value = value;
	}
	
	public SettingUploadRequest() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
