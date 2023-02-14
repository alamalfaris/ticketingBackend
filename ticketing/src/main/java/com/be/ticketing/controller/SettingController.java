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
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.setting.SettingRequest;
import com.be.ticketing.request.setting.SettingSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.setting.ParameterCdResponse;
import com.be.ticketing.response.setting.SettingGroupCdResponse;
import com.be.ticketing.response.setting.SettingResponse;
import com.be.ticketing.response.setting.SettingSearchResponse;
import com.be.ticketing.service.setting.ISettingService;
import com.be.ticketing.utils.ResponseUtil;
import com.be.ticketing.utils.ValidationUtil;

@CrossOrigin
@RestController
public class SettingController {
	
	@Autowired
	private ISettingService settingService;
	
	@PostMapping("/getSettingGroupCode")
	public ResponseEntity<List<SettingGroupCdResponse>> getSettingGroupCode() {
		
		var response = settingService.getSettingGroupCode();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getSystemTypeValue")
	public ResponseEntity<List<ParameterCdResponse>> getSystemTypeValue() {
		
		var response = settingService.getSettingValueType();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/insertSetting")
	public ResponseEntity<SettingResponse> insertSetting(@RequestBody SettingRequest request) {
		
		var response = settingService.insertSetting(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/updateSetting")
	public ResponseEntity<SettingResponse> updateSetting(@RequestBody SettingRequest request) {
		
		var response = settingService.updateSetting(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/searchDataSetting")
	public ResponseEntity<BaseResponse<List<SettingSearchResponse>>> searchSetting(@RequestBody SettingSearchRequest request) {
		
		var response = new BaseResponse<List<SettingSearchResponse>>();
		
		String message = ValidationUtil.ValidationPageParameter(request);
		if (message != null) {
			response.setMessage(message);
			response.setStatus(Constants.ERROR_STATUS);
			return new ResponseEntity<BaseResponse<List<SettingSearchResponse>>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response = settingService.searchSetting(request);
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<List<SettingSearchResponse>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<List<SettingSearchResponse>>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/deleteSetting")
	public ResponseEntity<BaseResponse<List<String>>> deleteSetting(@RequestBody ListCodeRequest request) {
		
		var response = settingService.deleteSetting(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadTemplateSetting")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadTemplateSetting() {
		
		var response = settingService.downloadTemplateSetting();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadSetting")
	public ResponseEntity<BaseResponse<List<String>>> uploadSetting(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		var response = new BaseResponse<List<String>>();
		
		String fileName = fileUpload.getOriginalFilename();
		boolean isExcelFile = fileName.endsWith(".xlsx");
		
		if (isExcelFile) {
			response = settingService.uploadSetting(fileUpload);
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
	
	@PostMapping("/downloadSetting")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadSetting(@RequestHeader(required = false) String settingGroup,
			@RequestHeader(required = false) String settingName, 
			@RequestHeader(required = false) String value,
			@RequestHeader String extention) {
		
		var response = new BaseResponse<DownloadResponse>();
		
		if (extention == null || extention == "") {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage("file extention must sent");
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_DATA_SETTING + extention);
		
		response = settingService.downloadSetting(is, settingGroup, 
				settingName, value, extention);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.OK);
	}
}
