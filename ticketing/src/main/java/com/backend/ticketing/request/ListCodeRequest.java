package com.backend.ticketing.request;

import java.util.List;

public class ListCodeRequest {
	
	private List<CodeRequest> listCode;

	public ListCodeRequest(List<CodeRequest> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public ListCodeRequest() {
	}

	public List<CodeRequest> getListCode() {
		return listCode;
	}

	public void setListCode(List<CodeRequest> listCode) {
		this.listCode = listCode;
	}
}
