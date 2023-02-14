package com.be.ticketing.request.supportedpic;

import java.util.List;

public class SupportedPicSubmitRequest {
	
	private Long companyId;
	private List<PicRequest> pic;
	
	public SupportedPicSubmitRequest(Long companyId, List<PicRequest> pic) {
		super();
		this.companyId = companyId;
		this.pic = pic;
	}
	
	public SupportedPicSubmitRequest() {
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<PicRequest> getPic() {
		return pic;
	}

	public void setPic(List<PicRequest> pic) {
		this.pic = pic;
	}
}
