package com.be.ticketing.dao.city;

import java.util.List;

import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.response.city.CitySearchResponse;
import com.be.ticketing.response.province.ProvinceResponse;

public interface ICityDao {
	
	List<ProvinceResponse> getListProvince();
	
	List<CitySearchResponse> searchCities(CityRequest request);
	
	Integer countSearchCities(CityRequest request);
	
	List<CitySearchResponse> searchCity(CityRequest request);
	
	List<CitySearchResponse> downloadCity(CityRequest request);
}
