package com.be.ticketing.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "app_param_group")
public class ParameterGroup extends CommonEntity {
	
	@Id
	@Column(name = "param_group_code", nullable = false)
	@JsonProperty("paramGroupCd")
	private String parameterGroupCd;
	
	@Column(name = "param_group_name", nullable = false)
	@JsonProperty("paramGroupName")
	private String parameterGroupName;
	
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "parameterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	@JsonManagedReference
	@JsonProperty("param")
	private List<Parameter> parameters = new ArrayList<Parameter>();

	public ParameterGroup(String parameterGroupCd, String parameterGroupName, List<Parameter> parameters) {
		super();
		this.parameterGroupCd = parameterGroupCd;
		this.parameterGroupName = parameterGroupName;
		this.parameters = parameters;
	}

	public ParameterGroup() {
	}

	public String getParameterGroupCd() {
		return parameterGroupCd;
	}

	public void setParameterGroupCd(String parameterGroupCd) {
		this.parameterGroupCd = parameterGroupCd;
	}

	public String getParameterGroupName() {
		return parameterGroupName;
	}

	public void setParameterGroupName(String parameterGroupName) {
		this.parameterGroupName = parameterGroupName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	
}
