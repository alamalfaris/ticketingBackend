package com.be.ticketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.Role;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.role.RoleRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse3;
import com.be.ticketing.response.role.RoleResponse;
import com.be.ticketing.response.role.RoleSearchResponse;
import com.be.ticketing.service.role.IRoleService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@PostMapping("/insertRole")
	public ResponseEntity<Role> insert(@RequestBody RoleRequest request) {
		
		var response = roleService.insert(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/updateRole")
	public ResponseEntity<Role> update(@RequestBody RoleRequest request) {
		
		var response = roleService.update(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getRole")
	public ResponseEntity<RoleResponse> getRole(@RequestBody RoleRequest request) {
		
		var response = roleService.getRole(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/searchRole")
	public SearchResponse3<RoleSearchResponse> search(@RequestBody RoleRequest request) {
		
		var response = roleService.search(request);
		
		return response;
	}
	
	@PostMapping("/deleteRole")
	public ResponseEntity<List<String>> delete(@RequestBody ListCodeRequest request) {
		
		var response = roleService.delete(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadTemplateRole")
	public ResponseEntity<DownloadResponse> downloadTemplate() {
		
		var response = roleService.downloadTemplate();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadRole")
	public ResponseEntity<DownloadResponse> downloadRole(@RequestBody RoleRequest request) {
		
		var response = roleService.downloadRole(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadRole")
	public ResponseEntity<List<String>> upload(@RequestParam(value = "file") MultipartFile fileUpload) {
		
		var response = roleService.upload(fileUpload);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
