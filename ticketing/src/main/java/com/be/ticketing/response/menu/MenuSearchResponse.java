package com.be.ticketing.response.menu;

import com.be.ticketing.response.CommonResponse;

public class MenuSearchResponse extends CommonResponse {
	
	private String menuCd;
	private String menuName;
	private String parent;
	private String type;
	private String url;
	
	public MenuSearchResponse(String createdBy, String createdTime, String updatedBy, String updatedTime, String menuCd,
			String menuName, String parent, String type, String url) {
		super(createdBy, createdTime, updatedBy, updatedTime);
		this.menuCd = menuCd;
		this.menuName = menuName;
		this.parent = parent;
		this.type = type;
		this.url = url;
	}

	public MenuSearchResponse() {
	}

	public String getMenuCd() {
		return menuCd;
	}

	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
