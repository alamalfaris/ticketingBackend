package com.be.ticketing.request.parameter;

import java.util.List;

public class ParameterGroupRequest {
	
	private String paramGroupCd;
	private String paramGroupName;
	private List<ParameterRequest> paramList;
	
	public ParameterGroupRequest(String paramGroupCd, String paramGroupName, List<ParameterRequest> paramList) {
		super();
		this.paramGroupCd = paramGroupCd;
		this.paramGroupName = paramGroupName;
		this.paramList = paramList;
	}
	
	public ParameterGroupRequest() {
	}

	public String getParamGroupCd() {
		return paramGroupCd;
	}

	public void setParamGroupCd(String paramGroupCd) {
		this.paramGroupCd = paramGroupCd;
	}

	public String getParamGroupName() {
		return paramGroupName;
	}

	public void setParamGroupName(String paramGroupName) {
		this.paramGroupName = paramGroupName;
	}

	public List<ParameterRequest> getParamList() {
		return paramList;
	}

	public void setParamList(List<ParameterRequest> paramList) {
		this.paramList = paramList;
	}
	
	
}
