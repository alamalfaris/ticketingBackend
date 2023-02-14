package com.be.ticketing.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.CommonEntity;
import com.be.ticketing.request.BaseSearchRequest;
import com.be.ticketing.response.CommonCountData;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.SearchResponse2;
import com.be.ticketing.response.SearchResponse3;

public class CommonService {
	
	protected Pageable getPageable(BaseSearchRequest request) {
		return PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
	}

	protected String convertValueForLike(String value) {
		return value != null ? value : "";
	}
	
	protected Long convertId(String value) {
		return value == null ? 0L  : value == "" ? 0L : Long.parseLong(value);
	}
	
	protected void setCreate(CommonEntity entity, String username) {
		entity.setCreatedBy(username);
		entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		entity.setRecordFlag(Constants.FLAG_NEW);
	}

	protected void setUpdate(CommonEntity entity, String username) {
		entity.setUpdatedBy(username);
		entity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		entity.setRecordFlag(Constants.FLAG_EDIT);
	}

	protected void setDelete(CommonEntity entity, String username) {
		entity.setRecordFlag(Constants.FLAG_DELETE);
		entity.setUpdatedBy(username);
		entity.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
	}
	
	protected <T> SearchResponse<T> createSearchResponse(Page<T> page) {
		List<T> data = page.toList();

		return new SearchResponse<>(Constants.SUCCESS_STATUS, Constants.SUCCESS_MESSAGE,
				page.getTotalElements(), data);
	}
	
	protected <T> SearchResponse<T> createSearchResponse2(List<T> data, Long totalData) {

		return new SearchResponse<>(Constants.SUCCESS_STATUS, Constants.SUCCESS_MESSAGE,
				totalData, data);
	}
	
	protected <T> SearchResponse2<T> createSearchResponse3(Page<T> page, BaseSearchRequest request,
			int start, int end) {
		
		List<T> data = page.toList();
		
		CommonCountData countData = new CommonCountData();
		countData.setPageNumber(request.getPageNumber());
		countData.setPageSize(request.getPageSize());
		countData.setTotalDataPage(data.size());
		countData.setTotalData(page.getTotalElements());
		countData.setTotalPage(page.getTotalPages());
		countData.setStartData(start + 1);
		countData.setEndData(end);
		
		return new SearchResponse2<>(Constants.SUCCESS_STATUS, Constants.SUCCESS_MESSAGE,
				countData, data);
		
	}
	
	protected <T> SearchResponse3<T> createSearchResponse4(Page<T> page, BaseSearchRequest request) {
		
		List<T> data = page.toList();

		return new SearchResponse3<>(Constants.SUCCESS_STATUS, Constants.SUCCESS_MESSAGE, 
				request.getPageNumber(), request.getPageSize(), data.size(), 
				page.getTotalElements(), page.getTotalPages(), data);
	}
	
	protected DownloadResponse downloadTemplate(InputStream input, String templateName) {
		
		DownloadResponse result = new DownloadResponse();
		
		Workbook wb = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        
        try {
        	wb = new XSSFWorkbook(input);
        	
        	baos = new ByteArrayOutputStream();
            wb.write(baos);
            
            bais = new ByteArrayInputStream(baos.toByteArray());
            
            bais.close();
            baos.close();
            input.close();
        	wb.close();
        }
        catch (Exception e) {
        	
        }
        
        byte[] byteData = bais.readAllBytes();
        result.setBase64Data(byteData);
        result.setFileName(templateName);
        
        return result;
	}
}
