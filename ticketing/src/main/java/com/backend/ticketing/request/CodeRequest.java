package com.backend.ticketing.request;

public class CodeRequest {
	
	private String code;

	public CodeRequest(String code) {
		super();
		this.code = code;
	}
	
	public CodeRequest() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
