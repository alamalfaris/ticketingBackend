package com.backend.ticketing.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "app_param_group")
public class ParameterGroup extends CommonEntity {
	
	@Id
	@Column(name = "param_group_code", nullable = false)
	private String parameterGroupCode;
	
	@Column(name = "param_group_name", nullable = false)
	private String parameterGroupName;
	
	@OneToMany(mappedBy = "parameterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	@JsonManagedReference
	private List<Parameter> parameter = new ArrayList<>();

	public ParameterGroup(String parameterGroupCode, String parameterGroupName, List<Parameter> parameter) {
		super();
		this.parameterGroupCode = parameterGroupCode;
		this.parameterGroupName = parameterGroupName;
		this.parameter = parameter;
	}
	
	public ParameterGroup() {
	}

	public String getParameterGroupCode() {
		return parameterGroupCode;
	}

	public void setParameterGroupCode(String parameterGroupCode) {
		this.parameterGroupCode = parameterGroupCode;
	}

	public String getParameterGroupName() {
		return parameterGroupName;
	}

	public void setParameterGroupName(String parameterGroupName) {
		this.parameterGroupName = parameterGroupName;
	}

	public List<Parameter> getParameter() {
		return parameter;
	}

	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}
}
