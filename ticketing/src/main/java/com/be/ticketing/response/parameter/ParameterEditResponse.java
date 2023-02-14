package com.be.ticketing.response.parameter;

import com.be.ticketing.response.CommonResponse;

public class ParameterEditResponse extends CommonResponse {
	
	private String paramCode;
	private String paramName;
	private String paramDesc;
	private String recordFlag;
	private String lineNo;
	
	public ParameterEditResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			String paramCode, String paramName, String paramDesc, String recordFlag, String lineNo) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramDesc = paramDesc;
		this.recordFlag = recordFlag;
		this.lineNo = lineNo;
	}
	
	public ParameterEditResponse() {
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

	public String getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	
}
