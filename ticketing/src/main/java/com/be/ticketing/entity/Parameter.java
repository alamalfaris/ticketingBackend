package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "app_param")
public class Parameter extends CommonEntity {
	
	@Id
	@Column(name = "param_code")
	@JsonProperty("paramCode")
	private String parameterCd;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonBackReference
	@JoinColumn(name = "param_group_code", referencedColumnName = "param_group_code")
	private ParameterGroup parameterGroup;
	
	@Column(name = "param_name")
	@JsonProperty("paramName")
	private String parameterName;
	
	@Column(name = "param_desc")
	@JsonProperty("paramDesc")
	private String parameterDesc;
	
	@Column(name = "line_no")
	private Integer lineNo;

	public Parameter(String parameterCd, ParameterGroup parameterGroup, String parameterName, String parameterDesc,
			Integer lineNo) {
		super();
		this.parameterCd = parameterCd;
		this.parameterGroup = parameterGroup;
		this.parameterName = parameterName;
		this.parameterDesc = parameterDesc;
		this.lineNo = lineNo;
	}
	
	public Parameter() {
	}

	public String getParameterCd() {
		return parameterCd;
	}

	public void setParameterCd(String parameterCd) {
		this.parameterCd = parameterCd;
	}

	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	
}
