package com.be.ticketing.dao.company;

import java.util.List;

import com.be.ticketing.request.company.CompanyRequest;
import com.be.ticketing.response.company.CompanySearchResponse;
import com.be.ticketing.response.company.GetBusinessResponse;
import com.be.ticketing.response.company.GetCityResponse;
import com.be.ticketing.response.company.GetTypeResponse;

public interface ICompanyDao {
	
	List<CompanySearchResponse> search(CompanyRequest request);
	
	List<GetCityResponse> getCityCompany();
	
	List<GetBusinessResponse> getBusinessCompany();
	
	List<GetTypeResponse> getTypeCompany();
	
	List<CompanySearchResponse> download(String business, String companyCd, String companyName);
}
