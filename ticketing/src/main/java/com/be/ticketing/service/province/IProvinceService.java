package com.be.ticketing.service.province;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.Province;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.province.ProvinceRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.province.ProvinceSearchResponse;

public interface IProvinceService {
	
	Province insertProvince(ProvinceRequest request);
	
	Province updateProvince(ProvinceRequest request);
	
	SearchResponse<ProvinceSearchResponse> searchProvince(ProvinceRequest request);
	
	List<String> deleteProvince(ListCodeRequest request);
	
	DownloadResponse downloadTemplateProvince();
	
	BaseResponse<List<String>> uploadProvince(MultipartFile fileUpload);
	
	BaseResponse<DownloadResponse> downloadProvince(InputStream input, String provinceCode,
			String provinceName, String extension);
}
