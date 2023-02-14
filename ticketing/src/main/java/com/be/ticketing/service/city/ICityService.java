package com.be.ticketing.service.city;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.city.CityResponse;
import com.be.ticketing.response.city.CitySearchResponse;
import com.be.ticketing.response.province.ProvinceResponse;

public interface ICityService {
	
	List<ProvinceResponse> getListProvince();
	
	CityResponse insertCity(CityRequest request);
	
	CityResponse updateCity(CityRequest request);
	
	SearchResponse<CitySearchResponse> searchCity(CityRequest request);
	
	List<String> deleteCity(ListCodeRequest request);
	
	DownloadResponse downloadTemplateCity();
	
	List<String> uploadCity(MultipartFile fileUpload);
	
	DownloadResponse downloadCity(String provinceCode,
			String cityCode, String cityName, String extension);
}
