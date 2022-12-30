package com.backend.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ticketing.entity.ParameterGroup;
import com.backend.ticketing.request.parameter.ParameterRequest;
import com.backend.ticketing.response.SearchResponse;
import com.backend.ticketing.response.parameter.ParameterGroupResponse;
import com.backend.ticketing.service.parameter.IParameterService;
import com.backend.ticketing.utils.ResponseUtil;

@RestController
public class ParameterController {
	
	@Autowired
	private IParameterService parameterService;
	
	@PostMapping("/searchParameter")
	public SearchResponse<ParameterGroupResponse> search(@RequestBody ParameterRequest request) {
		
		var response = parameterService.search(request);
		
		return response;
	}
	
	@PostMapping("/submitParameter")
	public ResponseEntity<ParameterGroup> submit(@RequestBody ParameterRequest request) {
		
		var response = parameterService.submit(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
