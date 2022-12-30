package com.backend.ticketing.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.backend.ticketing.constants.Constants;
import com.backend.ticketing.entity.CommonEntity;
import com.backend.ticketing.request.BaseSearchRequest;
import com.backend.ticketing.response.DownloadResponse;
import com.backend.ticketing.response.SearchResponse;

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
		entity.setCreatedTime(Constants.TIME_NOW);
		entity.setRecordFlag(Constants.FLAG_NEW);
	}

	protected void setUpdate(CommonEntity entity, String username) {
		entity.setUpdatedBy(username);
		entity.setUpdatedTime(Constants.TIME_NOW);
		entity.setRecordFlag(Constants.FLAG_EDIT);
	}

	protected void setDelete(CommonEntity entity, String username) {
		entity.setRecordFlag(Constants.FLAG_DELETE);
		entity.setUpdatedBy(username);
		entity.setUpdatedTime(Constants.TIME_NOW);
	}
	
	protected <T> SearchResponse<T> createSearchResponse(Page<T> page, BaseSearchRequest request) {
		List<T> data = page.toList();

		return new SearchResponse<>(request.getPageNumber(), request.getPageSize(), 
				data.size(), page.getTotalElements(),
				page.getTotalPages(), data);
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
