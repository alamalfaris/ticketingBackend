package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "app_setting")
public class Setting extends CommonEntity {
	
	@Id
	@Column(name = "setting_code")
	private String settingCd;
	
	@ManyToOne
	@JoinColumn(name = "setting_group_code", referencedColumnName = "setting_group_code")
	private SettingGroup settingGroup;
	
	@Column(name = "setting_desc")
	private String settingDescription;
	
	@Column(name = "setting_value")
	private String settingValue;
	
	@ManyToOne
	@JoinColumn(name = "setting_value_type", referencedColumnName = "param_code")
	private Parameter parameter;

	public Setting(String settingCd, SettingGroup settingGroup, String settingDescription, String settingValue,
			Parameter parameter) {
		super();
		this.settingCd = settingCd;
		this.settingGroup = settingGroup;
		this.settingDescription = settingDescription;
		this.settingValue = settingValue;
		this.parameter = parameter;
	}
	
	public Setting() {
	}

	public String getSettingCd() {
		return settingCd;
	}

	public void setSettingCd(String settingCd) {
		this.settingCd = settingCd;
	}

	public SettingGroup getSettingGroup() {
		return settingGroup;
	}

	public void setSettingGroup(SettingGroup settingGroup) {
		this.settingGroup = settingGroup;
	}

	public String getSettingDescription() {
		return settingDescription;
	}

	public void setSettingDescription(String settingDescription) {
		this.settingDescription = settingDescription;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	
	
}
