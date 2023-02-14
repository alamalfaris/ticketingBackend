package com.be.ticketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.ticketing.request.supportedpic.SupportedPicSearchRequest;
import com.be.ticketing.request.supportedpic.SupportedPicSubmitRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse2;
import com.be.ticketing.response.supportedpic.PicResponse;
import com.be.ticketing.response.supportedpic.SupportedPicAssignResponse;
import com.be.ticketing.response.supportedpic.SupportedPicSearchResponse;
import com.be.ticketing.service.supportedpic.ISupportedPicService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class SupportedPicController {
	
	@Autowired
	private ISupportedPicService supportedPicService;
	
	@PostMapping("/searchSupportPic")
	public SearchResponse2<SupportedPicSearchResponse> search(@RequestBody SupportedPicSearchRequest request) {
		
		var response = supportedPicService.search(request);
		
		return response;
	}
	
	@PostMapping("/submitSupportPic")
	public ResponseEntity<SupportedPicSubmitRequest> submit(@RequestBody SupportedPicSubmitRequest request) {
		
		var response = supportedPicService.submit(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@GetMapping("/assignSupportPic")
	public ResponseEntity<SupportedPicAssignResponse> assign(@RequestParam Long companyId) {
		
		var response = supportedPicService.assign(companyId);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@GetMapping("/picAutoSupportPic")
	public ResponseEntity<List<PicResponse>> getAllPic() {
		
		var response = supportedPicService.getAllPic();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadSupportPic")
	public ResponseEntity<DownloadResponse> download(@RequestBody SupportedPicSearchRequest request) {
		
		var response = supportedPicService.download(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
