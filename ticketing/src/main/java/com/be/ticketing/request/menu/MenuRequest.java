package com.be.ticketing.request.menu;

import com.be.ticketing.request.BaseSearchRequest;

public class MenuRequest extends BaseSearchRequest {
	
	private String menuCd;
	private String menuName;
	private String parent;
	private String type;
	private String url;
	private String extention;
	
	public MenuRequest(Integer pageNumber, Integer pageSize, String menuCd, String menuName, String parent, String type,
			String url, String extention) {
		super(pageNumber, pageSize);
		this.menuCd = menuCd;
		this.menuName = menuName;
		this.parent = parent;
		this.type = type;
		this.url = url;
		this.extention = extention;
	}

	public MenuRequest() {
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

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	
	
	
}
