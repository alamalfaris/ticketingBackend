package com.be.ticketing.controller;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Province;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.province.ProvinceRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.province.ProvinceSearchResponse;
import com.be.ticketing.service.province.IProvinceService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class ProvinceController {
	
	@Autowired
	private IProvinceService provinceService;
	
	@PostMapping("/insertProvince")
	public ResponseEntity<Province> insertProvince(@RequestBody ProvinceRequest request) {
		
		var province = provinceService.insertProvince(request);
		
		return ResponseUtil.generateResponseSuccess(province);
	}
	
	@PostMapping("/updateProvince")
	public ResponseEntity<Province> updateProvince(@RequestBody ProvinceRequest request) {
		
		var province = provinceService.updateProvince(request);
		
		return ResponseUtil.generateResponseSuccess(province);
	}
	
	@PostMapping("/searchProvince")
	public SearchResponse<ProvinceSearchResponse> searchProvince(@RequestBody ProvinceRequest request) {
		
		var response = provinceService.searchProvince(request);
		
		return response;
	}
	
	@PostMapping("/deleteProvince")
	public ResponseEntity<List<String>> deleteProvince(@RequestBody ListCodeRequest request) {
		
		var response = provinceService.deleteProvince(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadTemplateProvince")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadTemplateProvince() {
		
		var response = provinceService.downloadTemplateProvince();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadProvince")
	public ResponseEntity<BaseResponse<List<String>>> uploadProvince(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		var response = new BaseResponse<List<String>>();
		
		String fileName = fileUpload.getOriginalFilename();
		boolean isExcelFile = fileName.endsWith(".xlsx");
		
		if (isExcelFile) {
			response = provinceService.uploadProvince(fileUpload);
		}
		else {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage("file must have .xlsx extension");
			return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (!response.getStatus().equals(Constants.ERROR_STATUS)) {
			return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/downloadProvince")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadProvince(@RequestHeader(required = false) String provinceCd,
			@RequestHeader(required = false) String provinceName, @RequestHeader String extention) {
		
		var response = new BaseResponse<DownloadResponse>();
		
		if (extention == null || extention == "") {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage("file extention must sent");
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_DATA_PROVINCE + extention);
		
		response = provinceService.downloadProvince(is, provinceCd, 
				provinceName, extention);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.OK);
	}
}
