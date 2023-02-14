package com.be.ticketing.response.parameter;

import com.be.ticketing.response.CommonResponse;

public class ParameterResponse extends CommonResponse {
	private String recordFlag;
	private String paramCode;
	private String paramName;
	private String paramDesc;
	private Integer lineNo;
	
	public ParameterResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String recordFlag, String paramCode, String paramName, String paramDesc, Integer lineNo) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.recordFlag = recordFlag;
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
		this.lineNo = lineNo;
	}
	
	public ParameterResponse() {
	}

	public String getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	
}
