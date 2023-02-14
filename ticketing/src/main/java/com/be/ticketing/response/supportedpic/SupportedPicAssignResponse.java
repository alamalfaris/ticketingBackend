package com.be.ticketing.response.supportedpic;

import java.util.List;

import com.be.ticketing.response.CommonResponse;

public class SupportedPicAssignResponse extends CommonResponse {
	private Long companyId;
	private String companyCode;
	private String companyName;
	private List<PicResponse> pic;
	
	public SupportedPicAssignResponse(Long companyId, String companyCode, String companyName,
			List<PicResponse> pic) {
		super();
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.pic = pic;
	}
	
	public SupportedPicAssignResponse() {
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<PicResponse> getPic() {
		return pic;
	}

	public void setPic(List<PicResponse> pic) {
		this.pic = pic;
	}
}
