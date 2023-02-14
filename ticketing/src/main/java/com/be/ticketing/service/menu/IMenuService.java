package com.be.ticketing.service.menu;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.Menu;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse3;
import com.be.ticketing.response.menu.MenuParent;
import com.be.ticketing.response.menu.MenuSearchResponse;

public interface IMenuService {
	
	Menu insert(MenuRequest request);
	
	List<MenuParent> getMenuParent();
	
	Menu update(MenuRequest request);
	
	SearchResponse3<MenuSearchResponse> search(MenuRequest request);
	
	DownloadResponse downloadTemplate();
	
	List<String> delete(ListCodeRequest request);
	
	DownloadResponse download(MenuRequest request);
	
	List<String> upload(MultipartFile fileUpload);
}
