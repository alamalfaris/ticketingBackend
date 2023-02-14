package com.be.ticketing.request.parameter;

import com.be.ticketing.request.BaseSearchRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterSearchRequest extends BaseSearchRequest {
	
	@JsonProperty("param")
	private String parameterName;
	
	@JsonProperty("paramGroupCode")
	private String parameterGroupCd;
	
	@JsonProperty("paramGroupName")
	private String parameterGroupName;

	public ParameterSearchRequest(Integer pageNumber, Integer pageSize, String parameterName, String parameterGroupCd,
			String parameterGroupName) {
		super(pageNumber, pageSize);
		this.parameterName = parameterName;
		this.parameterGroupCd = parameterGroupCd;
		this.parameterGroupName = parameterGroupName;
	}
	
	public ParameterSearchRequest() {
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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
	
	
}
