package com.be.ticketing.mapper.setting;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.entity.Setting;
import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.request.setting.SettingRequest;
import com.be.ticketing.request.setting.SettingUploadRequest;
import com.be.ticketing.response.setting.SettingResponse;
import com.be.ticketing.response.setting.SettingSearchResponse;

public class SettingMapper {
	
	public static Setting mapToEntity(Setting setting, SettingRequest request, 
			SettingGroup settingGroup, Parameter parameter, Boolean isUpdate) {
		
		if (!isUpdate) {
			Setting s = new Setting();
			s.setSettingCd(request.getSettingCode().trim());
			s.setSettingDescription(request.getSettingDesc().trim());
			s.setSettingValue(request.getSettingValue().trim());
			s.setSettingGroup(settingGroup);
			s.setParameter(parameter);
			s.setRecordFlag(Constants.FLAG_NEW);
			s.setCreatedBy("admin");
			s.setCreatedTime(new Timestamp(System.currentTimeMillis()));
			
			return s;
		}
		else {
			setting.setSettingDescription(request.getSettingDesc().trim());
			setting.setSettingValue(request.getSettingValue().trim());
			setting.setSettingGroup(settingGroup);
			setting.setParameter(parameter);
			setting.setRecordFlag(Constants.FLAG_EDIT);
			setting.setUpdatedBy("admin");
			setting.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			
			return setting;
		}
	}
	
	public static SettingResponse mapToResponse(SettingRequest request, Setting setting,
			Boolean isUpdate) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		SettingResponse settingResponse = new SettingResponse();
		settingResponse.setSettingCode(setting.getSettingCd());
		settingResponse.setSettingGroupCode(request.getSettingGroupCode());
		settingResponse.setSettingDesc(setting.getSettingDescription());
		settingResponse.setSettingValue(setting.getSettingValue());
		settingResponse.setSettingValueType(request.getSettingValueType());
		settingResponse.setRecordFlag(setting.getRecordFlag());
		
		if (!isUpdate) {
			settingResponse.setCreatedBy(setting.getCreatedBy());
			settingResponse.setCreatedTime(dateFormatter.format(setting.getCreatedTime()));
		}
		else {
			settingResponse.setUpdatedBy(setting.getUpdatedBy());
			settingResponse.setUpdatedTime(dateFormatter.format(setting.getUpdatedTime()));
		}
		
		return settingResponse;
	}
	
	public static SettingSearchResponse mapToSearchResponse(Object[] obj) {
		
		SettingSearchResponse settingResponse = new SettingSearchResponse();
		settingResponse.setSettingCode((String)obj[0]);
		settingResponse.setSettingGroupCode((String)obj[1]);
		settingResponse.setSettingDesc((String)obj[2]);
		settingResponse.setSettingValue((String)obj[3]);
		settingResponse.setSettingValueType((String)obj[4]);
		settingResponse.setCreatedBy((String)obj[6]);
		settingResponse.setUpdatedBy(obj[8] != null ? (String)obj[8] : "");
		settingResponse.setCreatedTime((String)obj[10]);
		settingResponse.setUpdatedTime(obj[11] != null ? (String)obj[11] : "");
		
		return settingResponse;
	}
	
	public static Setting mapToEntityDelete(Setting setting) {
		
		setting.setRecordFlag(Constants.FLAG_DELETE);
		setting.setUpdatedBy("admin");
		setting.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		
		return setting;
	}
	
	public static Setting mapToEntityUpload(Setting setting, SettingUploadRequest s, 
			SettingGroup settingGroup, Parameter parameter, Boolean isUpdate) {
		
		if (!isUpdate) {
			setting.setSettingCd(s.getSettingCode());
			setting.setSettingGroup(settingGroup);
			setting.setSettingDescription(s.getDescription());
			setting.setParameter(parameter);
			setting.setSettingValue(s.getValue());
			setting.setRecordFlag(Constants.FLAG_NEW);
			setting.setCreatedBy("admin");
			setting.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		}
		else {
			setting.setSettingGroup(settingGroup);
			setting.setSettingDescription(s.getDescription());
			setting.setParameter(parameter);
			setting.setSettingValue(s.getValue());
			setting.setRecordFlag(Constants.FLAG_EDIT);
			setting.setUpdatedBy("admin");
			setting.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return setting;
	}
}
