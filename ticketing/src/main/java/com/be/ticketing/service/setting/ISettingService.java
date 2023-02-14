package com.be.ticketing.service.setting;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.setting.SettingRequest;
import com.be.ticketing.request.setting.SettingSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.setting.ParameterCdResponse;
import com.be.ticketing.response.setting.SettingGroupCdResponse;
import com.be.ticketing.response.setting.SettingResponse;
import com.be.ticketing.response.setting.SettingSearchResponse;

public interface ISettingService {
	
	List<SettingGroupCdResponse> getSettingGroupCode();
	
	List<ParameterCdResponse> getSettingValueType();
	
	SettingResponse insertSetting(SettingRequest request);
	
	SettingResponse updateSetting(SettingRequest request);
	
	BaseResponse<List<SettingSearchResponse>> searchSetting(SettingSearchRequest request);
	
	List<String> deleteSetting(ListCodeRequest request);
	
	DownloadResponse downloadTemplateSetting();
	
	BaseResponse<List<String>> uploadSetting(MultipartFile fileUpload);
	
	BaseResponse<DownloadResponse> downloadSetting(InputStream input, String settingGroup,
			String settingName, String value, String extension);
}
