package com.be.ticketing.response.menu;

public class MenuParent {
	
	private String menuId;
	private String menuName;
	
	public MenuParent(String menuId, String menuName) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
	}
	
	public MenuParent() {
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	
}
