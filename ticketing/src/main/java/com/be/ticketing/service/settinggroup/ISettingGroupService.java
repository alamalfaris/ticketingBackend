package com.be.ticketing.service.settinggroup;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.settinggroup.SettingGroupRequest;
import com.be.ticketing.request.settinggroup.SettingGroupSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.settinggroup.SettingGroupResponse;

public interface ISettingGroupService {
	
	BaseResponse<List<SettingGroupResponse>> searchSettingGroup(SettingGroupSearchRequest request);
	
	BaseResponse<SettingGroup> addSettingGroup(SettingGroupRequest request);
	
	BaseResponse<SettingGroup> updateSettingGroup(SettingGroupRequest request);
	
	BaseResponse<List<String>> deleteSettingGroup(ListCodeRequest request);
	
	DownloadResponse downloadTemplateSettingGroup();
	
	BaseResponse<DownloadResponse> downloadSettingGroup(InputStream input, String settingGroupCode, 
			String settingGroupName, String extension);
	
	BaseResponse<List<String>> uploadSettingGroup(MultipartFile fileUpload);
}
