package com.be.ticketing.dao.supportedobject;

import java.util.List;

import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.response.supportedobject.GetCompanyResponse;
import com.be.ticketing.response.supportedobject.GetObjectTypeResponse;
import com.be.ticketing.response.supportedobject.SupportedObjectResponse;

public interface ISupportedObjectDao {
	
	List<GetObjectTypeResponse> getObjectTypeSupportedObject();
	
	List<GetCompanyResponse> getCompanySupportedObject();
	
	List<SupportedObjectResponse> search(SupportedObjectRequest request);
	
	List<SupportedObjectResponse> download(String objectType, String objectName, String companyName);
}
