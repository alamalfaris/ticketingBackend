package com.be.ticketing.response.parameter;

import java.util.List;

import com.be.ticketing.response.CommonResponse;

public class ParameterGroupSearchResponse extends CommonResponse {
	private String paramGroupCode;
	private String paramGroupName;
	private String paramName;
	private List<ParameterSearchResponse> listParameter;
	
	public ParameterGroupSearchResponse(String paramGroupCode, String paramGroupName, String paramName,
			List<ParameterSearchResponse> listParameter) {
		super();
		this.paramGroupCode = paramGroupCode;
		this.paramGroupName = paramGroupName;
		this.paramName = paramName;
		this.listParameter = listParameter;
	}
	
	public ParameterGroupSearchResponse() {
	}

	public String getParamGroupCode() {
		return paramGroupCode;
	}

	public void setParamGroupCode(String paramGroupCode) {
		this.paramGroupCode = paramGroupCode;
	}

	public String getParamGroupName() {
		return paramGroupName;
	}

	public void setParamGroupName(String paramGroupName) {
		this.paramGroupName = paramGroupName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public List<ParameterSearchResponse> getListParameter() {
		return listParameter;
	}

	public void setListParameter(List<ParameterSearchResponse> listParameter) {
		this.listParameter = listParameter;
	}
	
	
}
