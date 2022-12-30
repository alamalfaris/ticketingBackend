package com.backend.ticketing.response.parameter;

import java.util.List;

import com.backend.ticketing.response.CommonResponse;

public class ParameterGroupResponse extends CommonResponse {
	
	private String parameterGroupCode;
	private String parameterGroupName;
	private String parameterDetail;
	private List<ParameterResponse> listParameter;
	
	public ParameterGroupResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
		String parameterGroupCode, String parameterGroupName, String parameterDetail,
			List<ParameterResponse> listParameter) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.parameterGroupCode = parameterGroupCode;
		this.parameterGroupName = parameterGroupName;
		this.parameterDetail = parameterDetail;
		this.listParameter = listParameter;
	}
	
	public ParameterGroupResponse() {
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

	public String getParameterDetail() {
		return parameterDetail;
	}

	public void setParameterDetail(String parameterDetail) {
		this.parameterDetail = parameterDetail;
	}

	public List<ParameterResponse> getListParameter() {
		return listParameter;
	}

	public void setListParameter(List<ParameterResponse> listParameter) {
		this.listParameter = listParameter;
	}
}
