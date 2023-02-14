package com.be.ticketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.be.ticketing.service.company.ICompanyService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class CompanyController {
	
	@Autowired
	private ICompanyService companyService;
	
	@PostMapping("/insertCompany")
	public ResponseEntity<CompanyResponse> insert(@RequestBody CompanyRequest request) {
		
		var response = companyService.insert(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/updateCompany")
	public ResponseEntity<CompanyResponse> update(@RequestBody CompanyRequest request) {
		
		var response = companyService.update(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/searchCompany")
	public SearchResponse<CompanySearchResponse> search(@RequestBody CompanyRequest request) {
		
		var response = companyService.search(request);
		
		return response;
	}
	
	@PostMapping("/deleteCompany")
	public ResponseEntity<List<String>> delete(@RequestBody ListCodeRequest request) {
		
		var response = companyService.delete(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getCityCompany")
	public ResponseEntity<List<GetCityResponse>> getCityCompany() {
		
		var response = companyService.getCityCompany();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getBusinessCompany")
	public ResponseEntity<List<GetBusinessResponse>> getBusinessCompany() {
		
		var response = companyService.getBusinessCompany();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getTypeCompany")
	public ResponseEntity<List<GetTypeResponse>> getTypeCompany() {
		
		var response = companyService.getTypeCompany();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadTemplateCompany")
	public ResponseEntity<DownloadResponse> downloadTemplateCompany() {
		
		var response = companyService.downloadTemplateCompany();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadCompany")
	public ResponseEntity<List<String>> uploadCompany(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		var response = companyService.upload(fileUpload);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadCompany")
	public ResponseEntity<DownloadResponse> downloadCompany(
			@RequestHeader String business,
			@RequestHeader String companyCd,
			@RequestHeader String companyName,
			@RequestHeader String extention) {
		
		var response = companyService.downloadCompany(business, companyCd, companyName, extention);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	
	
}
