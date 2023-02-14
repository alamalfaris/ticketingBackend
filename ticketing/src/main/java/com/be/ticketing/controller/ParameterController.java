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
import org.springframework.web.bind.annotation.RestController;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.ParameterGroup;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.parameter.ParameterGroupRequest;
import com.be.ticketing.request.parameter.ParameterSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;
import com.be.ticketing.service.parameter.IParameterService;
import com.be.ticketing.utils.ValidationUtil;

@CrossOrigin
@RestController
public class ParameterController {
	
	@Autowired
	private IParameterService parameterService;
	
	@PostMapping("/getParamCode")
	public ResponseEntity<BaseResponse<List<String>>> getParameterGroupCode() {
		
		var response = parameterService.getParameterGroupCode();
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/searchParameter")
	public SearchResponse<ParameterGroupSearchResponse> searchParameter(@RequestBody ParameterSearchRequest request) {
		
		var response = parameterService.searchParameter(request);
		
		return response;
	}
	
	@PostMapping("/deleteParamGroup")
	public ResponseEntity<BaseResponse<List<String>>> deleteParameter(@RequestBody ListCodeRequest request) {
		
		var response = new BaseResponse<List<String>>();
		
		response = parameterService.deleteParameter(request);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/submitParameter")
	public ResponseEntity<BaseResponse<ParameterGroup>> submitParameter(@RequestBody ParameterGroupRequest request) {
		
		var response = new BaseResponse<ParameterGroup>();
		
		String message = ValidationUtil.ValidationSubmitParameter(request);
		if (message != null) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(message);
			return new ResponseEntity<BaseResponse<ParameterGroup>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response = parameterService.submitParameter(request);
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<ParameterGroup>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<ParameterGroup>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/downloadParameter")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadParameter(
			@RequestHeader(required = false) String paramGroupCd,
			@RequestHeader(required = false) String paramGroupName,
			@RequestHeader(required = false) String paramName,
			@RequestHeader String extention) {
		
		var response = new BaseResponse<DownloadResponse>();
		
		if (extention == null || extention == "") {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage("file extention must sent");
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_DATA_PARAMETER + extention);
		
		response = parameterService.downloadParameter(is, paramGroupCd, paramGroupName, paramName, extention);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.OK);
	}
}
