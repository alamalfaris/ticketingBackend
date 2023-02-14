package com.be.ticketing.mapper.settingroup;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.request.settinggroup.SettingGroupRequest;
import com.be.ticketing.request.settinggroup.SettingGroupUploadRequest;
import com.be.ticketing.response.settinggroup.SettingGroupResponse;

public class SettingGroupMapper {

	
	public static SettingGroupResponse mapToResponse(SettingGroup sg) {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		SettingGroupResponse settingGroupResponse = new SettingGroupResponse();
		settingGroupResponse.setSettingGroupCode(sg.getSettingGroupCd());
		settingGroupResponse.setSettingGroupName(sg.getSettingGroupName());
		settingGroupResponse.setSettingGroupDesc(sg.getSettingGroupDesc());
		settingGroupResponse.setCreatedBy(sg.getCreatedBy());
		settingGroupResponse.setCreatedTime(sg.getCreatedTime() != null ? dateFormatter.format(sg.getCreatedTime()) : "");
		settingGroupResponse.setUpdatedBy(sg.getUpdatedBy() != null ? sg.getUpdatedBy() : "");
		settingGroupResponse.setUpdatedTime(sg.getUpdatedTime() != null ? dateFormatter.format(sg.getUpdatedTime()) : "");
		
		return settingGroupResponse;
	}
	
	public static SettingGroup mapToEntityAdd(SettingGroupRequest request) {
		
		
		SettingGroup settingGroup = new SettingGroup();
		settingGroup.setSettingGroupCd(request.getGroupCd().trim());
		settingGroup.setSettingGroupName(request.getGroupName().trim());
		settingGroup.setSettingGroupDesc(request.getGroupDesc().trim());
		settingGroup.setRecordFlag(Constants.FLAG_NEW);
		settingGroup.setCreatedBy("admin");
		settingGroup.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		
		return settingGroup;
	}
	
	public static SettingGroup mapToEntityUpdate(SettingGroup settingGroup, SettingGroupRequest request, 
			Boolean isDelete) {
		
		if (!isDelete) {
			settingGroup.setSettingGroupName(request.getGroupName().trim());
			settingGroup.setSettingGroupDesc(request.getGroupDesc().trim());
			settingGroup.setRecordFlag(Constants.FLAG_EDIT);
			settingGroup.setUpdatedBy("admin");
			settingGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		else {
			settingGroup.setRecordFlag(Constants.FLAG_DELETE);
			settingGroup.setUpdatedBy("admin");
			settingGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return settingGroup;
	}
	
	public static SettingGroup mapToEntityUpload(SettingGroup settingGroup, SettingGroupUploadRequest sg,
			Boolean isExist) {
		
		if (!isExist) {
			settingGroup.setSettingGroupCd(sg.getSettingGroupCode().trim());
			settingGroup.setSettingGroupName(sg.getSettingGroupName().trim());
			settingGroup.setSettingGroupDesc(sg.getSettingGroupDesc().trim());
			settingGroup.setRecordFlag(Constants.FLAG_NEW);
			settingGroup.setCreatedBy("admin");
			settingGroup.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		}
		else {
			settingGroup.setSettingGroupName(sg.getSettingGroupName().trim());
			settingGroup.setSettingGroupDesc(sg.getSettingGroupDesc().trim());
			settingGroup.setRecordFlag(Constants.FLAG_EDIT);
			settingGroup.setUpdatedBy("admin");
			settingGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return settingGroup;
	}
	
	
}
