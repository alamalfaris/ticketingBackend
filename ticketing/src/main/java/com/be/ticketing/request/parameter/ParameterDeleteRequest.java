package com.be.ticketing.request.parameter;

import java.util.List;

public class ParameterDeleteRequest {
	
	private List<ParameterGroupCode> listCode;

	public ParameterDeleteRequest(List<ParameterGroupCode> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public ParameterDeleteRequest() {
	}

	public List<ParameterGroupCode> getListCode() {
		return listCode;
	}

	public void setListCode(List<ParameterGroupCode> listCode) {
		this.listCode = listCode;
	}
	
	
}
