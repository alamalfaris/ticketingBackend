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
import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.city.CityResponse;
import com.be.ticketing.response.city.CitySearchResponse;
import com.be.ticketing.response.province.ProvinceResponse;
import com.be.ticketing.service.city.ICityService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class CityController {
	
	@Autowired
	ICityService cityService;
	
	@PostMapping("/getProvinceList")
	public ResponseEntity<List<ProvinceResponse>> getListProvince() {
		
		List<ProvinceResponse> listProvince = cityService.getListProvince();
		
		return ResponseUtil.generateResponseSuccess(listProvince);
	}
	
	@PostMapping("/insertCity")
	public ResponseEntity<CityResponse> insertCity(@RequestBody CityRequest request) {
		
		CityResponse cityResponse = cityService.insertCity(request);
		
		return ResponseUtil.generateResponseSuccess(cityResponse);
	}
	
	@PostMapping("/updateCity")
	public ResponseEntity<CityResponse> updateCity(@RequestBody CityRequest request) {
		
		CityResponse cityResponse = cityService.updateCity(request);
		
		return ResponseUtil.generateResponseSuccess(cityResponse);
	}
	
	@PostMapping("/searchCity")
	public SearchResponse<CitySearchResponse> searchCity(@RequestBody CityRequest request) {
		
		SearchResponse<CitySearchResponse> response = cityService.searchCity(request);
		
		return response;
	}
	
	@PostMapping("/deleteCity")
	public ResponseEntity<List<String>> deleteCity(@RequestBody ListCodeRequest request) {
		
		List<String> response = cityService.deleteCity(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadTemplateCity")
	public ResponseEntity<DownloadResponse> downloadTemplateCity() {
		
		DownloadResponse response = cityService.downloadTemplateCity();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadCity")
	public ResponseEntity<List<String>> uploadCity(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		List<String> response = cityService.uploadCity(fileUpload);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadCity")
	public ResponseEntity<DownloadResponse> downloadCity(@RequestHeader String provinceCd,
			@RequestHeader String cityCd,
			@RequestHeader String cityName,
			@RequestHeader String extention) {
		
		DownloadResponse response = cityService.downloadCity(provinceCd, cityCd, cityName, extention);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
