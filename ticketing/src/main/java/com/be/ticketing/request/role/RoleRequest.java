package com.be.ticketing.request.role;

import com.be.ticketing.request.BaseSearchRequest;

public class RoleRequest extends BaseSearchRequest {
	
	private String roleCode;
	private String roleName;
	private String roleType;
	private String roleDesc;
	private String extention;
	
	public RoleRequest(Integer pageNumber, Integer pageSize, String roleCode, String roleName, String roleType,
			String roleDesc, String extention) {
		super(pageNumber, pageSize);
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.roleType = roleType;
		this.roleDesc = roleDesc;
		this.extention = extention;
	}
	
	public RoleRequest() {
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
}
