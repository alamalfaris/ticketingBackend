package com.be.ticketing.response.supportedpic;

import java.math.BigInteger;
import java.util.List;

import com.be.ticketing.response.CommonResponse;

public class SupportedPicSearchResponse extends CommonResponse {
	
	private BigInteger companyId;
	private String companyCode;
	private String companyName;
	private List<String> pic;
	
	public SupportedPicSearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime,
			BigInteger companyId, String companyCode, String companyName, List<String> pic) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.pic = pic;
	}
	
	public SupportedPicSearchResponse() {
	}

	public BigInteger getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigInteger companyId) {
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

	public List<String> getPic() {
		return pic;
	}

	public void setPic(List<String> pic) {
		this.pic = pic;
	}
}
