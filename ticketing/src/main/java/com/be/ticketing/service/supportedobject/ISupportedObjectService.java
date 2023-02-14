package com.be.ticketing.service.supportedobject;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.entity.SupportedObject;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.supportedobject.GetCompanyResponse;
import com.be.ticketing.response.supportedobject.GetObjectTypeResponse;
import com.be.ticketing.response.supportedobject.SupportedObjectResponse;

public interface ISupportedObjectService {
	
	List<GetObjectTypeResponse> getObjectTypeSupportedObject();
	
	List<GetCompanyResponse> getCompanySupportedObject();
	
	SupportedObject insert(SupportedObjectRequest request);
	
	SupportedObject update(SupportedObjectRequest request);
	
	List<String> delete(ListCodeRequest request);
	
	SearchResponse<SupportedObjectResponse> search(SupportedObjectRequest request);
	
	DownloadResponse downloadTemplate();
	
	List<String> upload(MultipartFile fileUpload);
	
	DownloadResponse download(String objectType, String objectName, String companyName, String extention);
}
