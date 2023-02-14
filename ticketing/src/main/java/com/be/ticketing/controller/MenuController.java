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

import com.be.ticketing.entity.Menu;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse3;
import com.be.ticketing.response.menu.MenuParent;
import com.be.ticketing.response.menu.MenuSearchResponse;
import com.be.ticketing.service.menu.IMenuService;
import com.be.ticketing.utils.ResponseUtil;

@CrossOrigin
@RestController
public class MenuController {
	
	@Autowired
	private IMenuService menuService;
	
	@PostMapping("/insertMenu")
	public ResponseEntity<Menu> insert(@RequestBody MenuRequest request) {
		
		var response = menuService.insert(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/getMenuParent")
	public ResponseEntity<List<MenuParent>> getMenuParent() {
		
		var response = menuService.getMenuParent();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/updateMenu")
	public ResponseEntity<Menu> update(@RequestBody MenuRequest request) {
		
		var response = menuService.update(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/searchMenu")
	public SearchResponse3<MenuSearchResponse> search(@RequestBody MenuRequest request) {
		
		var response = menuService.search(request);
		
		return response;
	}
	
	@PostMapping("/downloadTemplateMenu")
	public ResponseEntity<DownloadResponse> downloadTemplate() {
		
		var response = menuService.downloadTemplate();
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/deleteMenu")
	public ResponseEntity<List<String>> delete(@RequestBody ListCodeRequest request) {
		
		var response = menuService.delete(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/downloadMenu")
	public ResponseEntity<DownloadResponse> download(@RequestBody MenuRequest request) {
		
		var response = menuService.download(request);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
	
	@PostMapping("/uploadMenu")
	public ResponseEntity<List<String>> upload(@RequestParam(value = "file") MultipartFile fileUpload) {
		
		var response = menuService.upload(fileUpload);
		
		return ResponseUtil.generateResponseSuccess(response);
	}
}
