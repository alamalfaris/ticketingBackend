package com.be.ticketing.mapper.province;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Province;
import com.be.ticketing.request.province.ProvinceRequest;
import com.be.ticketing.response.province.ProvinceSearchResponse;

public class ProvinceMapper {
	
	public static Province mapToEntityAdd(ProvinceRequest request) {
		
		Province province = new Province();
		province.setProvinceCode(request.getProvinceCd().trim());
		province.setProvinceName(request.getProvinceName().trim());
		province.setRecordFlag(Constants.FLAG_NEW);
		province.setCreatedBy("admin");
		province.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		
		return province;
	}
	
	public static Province mapToEntityUpdate(Province province, ProvinceRequest request, Boolean isDelete) {
		
		if (!isDelete) {
			province.setProvinceName(request.getProvinceName());
			province.setRecordFlag(Constants.FLAG_EDIT);
			province.setUpdatedBy("admin");
			province.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		else {
			province.setRecordFlag(Constants.FLAG_DELETE);
			province.setUpdatedBy("admin");
			province.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return province;
	}
	
	public static ProvinceSearchResponse mapToResponse(Province p) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		ProvinceSearchResponse province = new ProvinceSearchResponse();
		province.setProvinceCd(p.getProvinceCode());
		province.setProvinceName(p.getProvinceName());
		province.setCreatedBy(p.getCreatedBy());
		province.setCreatedTime(p.getCreatedTime() != null ? dateFormatter.format(p.getCreatedTime()) : null);
		province.setUpdatedBy(p.getUpdatedBy());
		province.setUpdatedTime(p.getUpdatedTime() != null ? dateFormatter.format(p.getUpdatedTime()) : null);
		
		return province;
	}
}
