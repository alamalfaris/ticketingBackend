package com.be.ticketing.dao.menu;

import java.util.List;

import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.response.menu.MenuParent;
import com.be.ticketing.response.menu.MenuSearchResponse;

public interface IMenuDao {
	
	List<MenuParent> getMenuParent();
	
	List<MenuSearchResponse> search(MenuRequest request);
}
