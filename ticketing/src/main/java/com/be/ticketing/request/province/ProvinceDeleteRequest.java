package com.be.ticketing.request.province;

import java.util.List;

public class ProvinceDeleteRequest {
	private List<ProvinceCode> listCode;

	public ProvinceDeleteRequest(List<ProvinceCode> listCode) {
		super();
		this.listCode = listCode;
	}
	
	public ProvinceDeleteRequest() {
	}

	public List<ProvinceCode> getListCode() {
		return listCode;
	}

	public void setListCode(List<ProvinceCode> listCode) {
		this.listCode = listCode;
	}
	
	
}
