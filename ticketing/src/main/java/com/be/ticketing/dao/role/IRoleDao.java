package com.be.ticketing.dao.role;

import java.util.List;

import com.be.ticketing.request.role.RoleRequest;
import com.be.ticketing.response.role.RoleResponse;
import com.be.ticketing.response.role.RoleSearchResponse;

public interface IRoleDao {
	
	RoleResponse getRole(String roleCode);
	
	List<RoleSearchResponse> search(RoleRequest request);
	
	Integer searchCount(RoleRequest request);
	
	List<RoleSearchResponse> searchDownload(RoleRequest request);
}
