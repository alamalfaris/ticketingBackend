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

import com.be.ticketing.entity.SupportedObject;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.supportedobject.GetCompanyResponse;
import com.be.ticketing.response.supportedobject.GetObjectTypeResponse;
import com.be.ticketing.response.supportedobject.SupportedObjectResponse;
import com.be.ticketing.service.supportedobject.ISupportedObjectService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class SupportedObjectController {
	
	@Autowired
	private ISupportedObjectService supportedObjectService;
	
	@PostMapping("/getObjectTypeSupportedObject")
	public ResponseEntity<GetObjectTypeResponse> getObjectTypeSupportedObject() {
		
		var response = supportedObjectService.getObjectTypeSupportedObject();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getCompanySupportedObject")
	public ResponseEntity<GetCompanyResponse> getCompanySupportedObject() {
		
		var response = supportedObjectService.getCompanySupportedObject();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/insertSupportedObject")
	public ResponseEntity<SupportedObject> insert(@RequestBody SupportedObjectRequest request) {
		
		var response = supportedObjectService.insert(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/updateSupportedObject")
	public ResponseEntity<SupportedObject> update(@RequestBody SupportedObjectRequest request) {
		
		var response = supportedObjectService.update(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/deleteSupportedObject")
	public ResponseEntity<List<String>> delete(@RequestBody ListCodeRequest request) {
		
		var response = supportedObjectService.delete(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/searchSupportedObject")
	public SearchResponse<SupportedObjectResponse> search(@RequestBody SupportedObjectRequest request) {
		
		var response = supportedObjectService.search(request);
		
		return response;
	}
	
	@PostMapping("/downloadTemplateSupportedObject")
	public ResponseEntity<DownloadResponse> downloadTemplate() {
		
		var response = supportedObjectService.downloadTemplate();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadSupportedObject")
	public ResponseEntity<List<String>> upload(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		var response = supportedObjectService.upload(fileUpload);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadSupportedObject")
	public ResponseEntity<DownloadResponse> download(@RequestHeader String objectType,
			@RequestHeader String objectName,
			@RequestHeader String companyName,
			@RequestHeader String extention) {
		
		var response = supportedObjectService.download(objectType, objectName, companyName, extention);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
