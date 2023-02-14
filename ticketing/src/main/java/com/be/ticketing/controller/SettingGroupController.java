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
import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.settinggroup.SettingGroupRequest;
import com.be.ticketing.request.settinggroup.SettingGroupSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.settinggroup.SettingGroupResponse;
import com.be.ticketing.service.settinggroup.ISettingGroupService;
import com.be.ticketing.utils.ResponseUtil;
import com.be.ticketing.utils.ValidationUtil;

@CrossOrigin
@RestController
public class SettingGroupController {
	
	@Autowired
	private ISettingGroupService settingGroupService;
	
	@PostMapping("/searchSettingGroup")
	public ResponseEntity<BaseResponse<List<SettingGroupResponse>>> searchSettingGroup(@RequestBody SettingGroupSearchRequest request) {
		
		var response = new BaseResponse<List<SettingGroupResponse>>();
		
		String message = ValidationUtil.ValidationPageParameter(request);
		if (message != null) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(message);
			return new ResponseEntity<BaseResponse<List<SettingGroupResponse>>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response = settingGroupService.searchSettingGroup(request);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<List<SettingGroupResponse>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<List<SettingGroupResponse>>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/insertSettingGroup")
	public ResponseEntity<BaseResponse<SettingGroup>> addSettingGroup(@RequestBody SettingGroupRequest request) {
		
		var response = new BaseResponse<SettingGroup>();
		
		String message = ValidationUtil.ValidationAddEditSettingGroup(request);
		if (message != null) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(message);
			return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response = settingGroupService.addSettingGroup(request);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/updateSettingGroup")
	public ResponseEntity<BaseResponse<SettingGroup>> updateSettingGroup(@RequestBody SettingGroupRequest request) {
		
		var response = new BaseResponse<SettingGroup>();
		
		String message = ValidationUtil.ValidationAddEditSettingGroup(request);
		if (message != null) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(message);
			return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response = settingGroupService.updateSettingGroup(request);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<SettingGroup>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/deleteSettingGroup")
	public ResponseEntity<BaseResponse<List<String>>> deleteSettingGroup(@RequestBody ListCodeRequest request) {
		
		var response = settingGroupService.deleteSettingGroup(request);
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		return new ResponseEntity<BaseResponse<List<String>>>(response, HttpStatus.OK);		
	}
	
	@PostMapping("/downloadTemplateSettingGroup")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadTemplateSettingGroup() {
		
		var response = settingGroupService.downloadTemplateSettingGroup();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadSettingGroup")
	public ResponseEntity<BaseResponse<DownloadResponse>> downloadSettingGroup(@RequestHeader(required = false) String groupCd,
			@RequestHeader(required = false) String groupName, @RequestHeader String extention) {
		
		var response = new BaseResponse<DownloadResponse>();
		
		if (extention == null || extention == "") {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage("file extention must sent");
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.BAD_REQUEST); 
		}
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_DATA_SETTING_GROUP + extention);
		
		response = settingGroupService.downloadSettingGroup(is, groupCd, 
				groupName, extention);
		
		if (!response.getStatus().equals(Constants.SUCCESS_STATUS)) {
			return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<BaseResponse<DownloadResponse>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/uploadSettingGroup")
	public ResponseEntity<BaseResponse<List<String>>> uploadSettingGroup(
			@RequestParam(value = "file", required = false) MultipartFile fileUpload) {
		
		var response = new BaseResponse<List<String>>();
		
		String fileName = fileUpload.getOriginalFilename();
		boolean isExcelFile = fileName.endsWith(".xlsx");
		
		if (isExcelFile) {
			response = settingGroupService.uploadSettingGroup(fileUpload);
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
}
