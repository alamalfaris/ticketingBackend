package com.be.ticketing.response.role;

public class RoleResponse {
	
	private String roleCode;
	private String roleName;
	private String roleType;
	private String roleDesc;
	
	public RoleResponse(String roleCode, String roleName, String roleType, String roleDesc) {
		super();
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.roleType = roleType;
		this.roleDesc = roleDesc;
	}
	
	public RoleResponse() {
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
	
	
}
