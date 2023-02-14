package com.be.ticketing.request;

import java.util.List;

public class ListCodeRequest {
	
	private List<Code> listCode;

	public ListCodeRequest(List<Code> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public ListCodeRequest() {
	}

	public List<Code> getListCode() {
		return listCode;
	}

	public void setListCode(List<Code> listCode) {
		this.listCode = listCode;
	}
	
	
}
