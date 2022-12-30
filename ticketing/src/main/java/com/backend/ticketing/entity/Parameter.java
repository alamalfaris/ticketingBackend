package com.backend.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "app_param")
public class Parameter extends CommonEntity {
	
	@Id
	@Column(name = "param_code", nullable = false)
	private String parameterCode;
	
	@Column(name = "param_name", nullable = false)
	private String parameterName;
	
	@Column(name = "param_desc")
	private String parameterDesc;
	
	@Column(name = "line_no", nullable = false)
	private Integer lineNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "param_group_code", referencedColumnName = "param_group_code")
	private ParameterGroup parameterGroup;

	public Parameter(String parameterCode, String parameterName, String parameterDesc, Integer lineNo,
			ParameterGroup parameterGroup) {
		super();
		this.parameterCode = parameterCode;
		this.parameterName = parameterName;
		this.parameterDesc = parameterDesc;
		this.lineNo = lineNo;
		this.parameterGroup = parameterGroup;
	}
	
	public Parameter() {
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
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

	public ParameterGroup getParameterGroup() {
		return parameterGroup;
	}

	public void setParameterGroup(ParameterGroup parameterGroup) {
		this.parameterGroup = parameterGroup;
	}
}
