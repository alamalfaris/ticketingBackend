package com.backend.ticketing.request.parameter;

import java.util.List;

import com.backend.ticketing.request.BaseSearchRequest;

public class ParameterRequest extends BaseSearchRequest {
	
	private String parameterGroupCode;
	private String parameterGroupName;
	private String parameterDetail;
	private List<DetailRequest> parameterList;
	
	public ParameterRequest(Integer pageNumber, Integer pageSize, String parameterGroupCode, String parameterGroupName,
			String parameterDetail, List<DetailRequest> parameterList) {
		super(pageNumber, pageSize);
		this.parameterGroupCode = parameterGroupCode;
		this.parameterGroupName = parameterGroupName;
		this.parameterDetail = parameterDetail;
		this.parameterList = parameterList;
	}
	
	public ParameterRequest() {
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

	public void setParameterDetail(String parameterName) {
		this.parameterDetail = parameterName;
	}

	public List<DetailRequest> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<DetailRequest> parameterList) {
		this.parameterList = parameterList;
	}
}
