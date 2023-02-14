package com.be.ticketing.response.parameter;

import java.util.List;

import com.be.ticketing.response.CommonResponse;

public class ParameterGroupSubmitResponse extends CommonResponse {
	
	private String paramGroupCd;
	private String paramGroupName;
	private Character recordFlag;
	private List<ParameterEditResponse> param;
	
	public ParameterGroupSubmitResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String paramGroupCd, String paramGroupName, Character recordFlag, List<ParameterEditResponse> param) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.paramGroupCd = paramGroupCd;
		this.paramGroupName = paramGroupName;
		this.recordFlag = recordFlag;
		this.param = param;
	}
	
	public ParameterGroupSubmitResponse() {
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

	public Character getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(Character recordFlag) {
		this.recordFlag = recordFlag;
	}

	public List<ParameterEditResponse> getParam() {
		return param;
	}

	public void setParam(List<ParameterEditResponse> param) {
		this.param = param;
	}
	
	
}
