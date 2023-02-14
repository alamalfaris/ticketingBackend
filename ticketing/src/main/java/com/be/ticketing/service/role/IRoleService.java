package com.be.ticketing.service.role;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.Role;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.role.RoleRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse3;
import com.be.ticketing.response.role.RoleResponse;
import com.be.ticketing.response.role.RoleSearchResponse;

public interface IRoleService {
	
	Role insert(RoleRequest request);
	
	Role update(RoleRequest request);
	
	RoleResponse getRole(RoleRequest request);
	
	SearchResponse3<RoleSearchResponse> search(RoleRequest request);
	
	List<String> delete(ListCodeRequest request);
	
	DownloadResponse downloadTemplate();
	
	DownloadResponse downloadRole(RoleRequest request);
	
	List<String> upload(MultipartFile fileUpload);
}
