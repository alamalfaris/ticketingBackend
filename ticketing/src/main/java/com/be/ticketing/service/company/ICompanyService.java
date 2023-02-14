package com.be.ticketing.service.company;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.company.CompanyRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.company.CompanyResponse;
import com.be.ticketing.response.company.CompanySearchResponse;
import com.be.ticketing.response.company.GetBusinessResponse;
import com.be.ticketing.response.company.GetCityResponse;
import com.be.ticketing.response.company.GetTypeResponse;

public interface ICompanyService {
	
	CompanyResponse insert(CompanyRequest request);
	
	CompanyResponse update(CompanyRequest request);
	
	SearchResponse<CompanySearchResponse> search(CompanyRequest request);
	
	List<String> delete(ListCodeRequest request);
	
	List<GetCityResponse> getCityCompany();
	
	List<GetBusinessResponse> getBusinessCompany();
	
	List<GetTypeResponse> getTypeCompany();
	
	DownloadResponse downloadTemplateCompany();
	
	List<String> upload(MultipartFile fileUpload);
	
	DownloadResponse downloadCompany(String business, String companyCd, String companyName,
			String extention);
}
