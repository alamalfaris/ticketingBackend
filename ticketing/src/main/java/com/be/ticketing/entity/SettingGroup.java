package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "app_setting_group")
public class SettingGroup extends CommonEntity {
	@Id
	@Column(name = "setting_group_code")
	@JsonProperty("settingGroupCode")
	private String settingGroupCd;

	@Column(name = "setting_group_name")
	private String settingGroupName;
	
	@Column(name = "setting_group_desc")
	private String settingGroupDesc;

	public SettingGroup() {
		
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

	public String getSettingGroupDesc() {
		return settingGroupDesc;
	}

	public void setSettingGroupDesc(String settingGroupDesc) {
		this.settingGroupDesc = settingGroupDesc;
	}
	
	

		
	
	
}
