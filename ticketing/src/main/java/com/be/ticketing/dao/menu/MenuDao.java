package com.be.ticketing.dao.menu;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.menu.MenuRepository;
import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.response.menu.MenuParent;
import com.be.ticketing.response.menu.MenuSearchResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class MenuDao extends CommonService implements IMenuDao {
	
	@Autowired
	private MenuRepository menuRepo;

	@Override
	public List<MenuParent> getMenuParent() {

		List<MenuParent> results = new ArrayList<>();
		
		List<Object[]> rows = menuRepo.getMenuParent();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				MenuParent dto = new MenuParent();
				
				BigInteger bi = (BigInteger)obj[0];
				dto.setMenuId(bi.toString());
				dto.setMenuName((String)obj[1]);
				
				results.add(dto);
			} 
		}
		
		return results;
	}

	@Override
	public List<MenuSearchResponse> search(MenuRequest request) {

		List<MenuSearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = menuRepo.search(this.convertValueForLike(request.getMenuCd()), 
				this.convertValueForLike(request.getMenuName()), this.convertId(request.getParent()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				MenuSearchResponse dto = new MenuSearchResponse();
				dto.setMenuCd((String)obj[0]);
				dto.setMenuName((String)obj[1]);
				dto.setUrl((String)obj[2]);
				dto.setParent((String)obj[3]);
				dto.setType((String)obj[4]);
				dto.setCreatedBy((String)obj[5]);
				dto.setCreatedTime((String)obj[9]);
				dto.setUpdatedBy(obj[7] != null ? (String)obj[7] : "");
				dto.setUpdatedTime(obj[10] != null ? (String)obj[10] : "");
				
				results.add(dto);
			}
		}
		
		return results;
	}

}
