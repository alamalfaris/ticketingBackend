package com.be.ticketing.response.parameter;

import java.util.List;

import com.be.ticketing.response.CommonResponse;

public class ParameterGroupResponse extends CommonResponse {
	private String recordFlag;
	private String paramGroupCd;
	private String paramGroupName;
	private List<ParameterResponse> param;
	
	public ParameterGroupResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String recordFlag, String paramGroupCd, String paramGroupName, List<ParameterResponse> param) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.recordFlag = recordFlag;
		this.paramGroupCd = paramGroupCd;
		this.paramGroupName = paramGroupName;
		this.param = param;
	}
	
	public ParameterGroupResponse() {
	}

	public String getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
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

	public List<ParameterResponse> getParam() {
		return param;
	}

	public void setParam(List<ParameterResponse> param) {
		this.param = param;
	}
	
	
}
