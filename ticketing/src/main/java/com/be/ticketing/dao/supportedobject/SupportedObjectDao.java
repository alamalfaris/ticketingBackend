package com.be.ticketing.dao.supportedobject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.supportedobject.SupportedObjectRepository;
import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.response.supportedobject.GetCompanyResponse;
import com.be.ticketing.response.supportedobject.GetObjectTypeResponse;
import com.be.ticketing.response.supportedobject.SupportedObjectResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class SupportedObjectDao extends CommonService implements ISupportedObjectDao {
	
	@Autowired
	private SupportedObjectRepository supportedObjectRepo;

	@Override
	public List<GetObjectTypeResponse> getObjectTypeSupportedObject() {

		List<GetObjectTypeResponse> results = new ArrayList<>();
		List<Object[]> rows = supportedObjectRepo.getObjectTypeSupportedObject();
		
		if (rows.size() > 0) {
			for (Object[] obj: rows) {
				
				GetObjectTypeResponse dto = new GetObjectTypeResponse();
				dto.setObjectTypeCode((String)obj[0]);
				dto.setObjectTypeName((String)obj[1]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<GetCompanyResponse> getCompanySupportedObject() {

		List<GetCompanyResponse> results = new ArrayList<>();
		List<Object[]> rows = supportedObjectRepo.getCompanySupportedObject();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				GetCompanyResponse dto = new GetCompanyResponse();
				dto.setCompanyId((String)obj[0]);
				dto.setCompanyName((String)obj[1]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<SupportedObjectResponse> search(SupportedObjectRequest request) {

		List<SupportedObjectResponse> results = new ArrayList<>();
		
		List<Object[]> rows = supportedObjectRepo.search(this.convertValueForLike(request.getObjectType()), 
				this.convertValueForLike(request.getObjectName()), 
				this.convertValueForLike(request.getCompanyName()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				SupportedObjectResponse dto = new SupportedObjectResponse();
				dto.setObjectCode((String)obj[0]);
				dto.setObjectName((String)obj[1]);
				dto.setObjectTypeCode((String)obj[2]);
				dto.setObjectTypeName((String)obj[3]);
				dto.setDescription((String)obj[4]);
				BigInteger bi = (BigInteger)obj[5];
				dto.setCompanyId(bi.toString());
				dto.setCompanyName((String)obj[6]);
				dto.setCreatedBy(obj[7] != null ? (String)obj[7] : "");
				dto.setCreatedTime(obj[11] != null ? (String)obj[11] : "");
				dto.setUpdatedBy(obj[9] != null ? (String)obj[9] : "");
				dto.setUpdatedTime(obj[12] != null ? (String)obj[12] : "");
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<SupportedObjectResponse> download(String objectType, String objectName, String companyName) {

		List<SupportedObjectResponse> results = new ArrayList<>();
		
		List<Object[]> rows = supportedObjectRepo.search(this.convertValueForLike(objectType), 
				this.convertValueForLike(objectName), 
				this.convertValueForLike(companyName));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				SupportedObjectResponse dto = new SupportedObjectResponse();
				dto.setObjectCode((String)obj[0]);
				dto.setObjectName((String)obj[1]);
				dto.setObjectTypeName((String)obj[3]);
				dto.setDescription((String)obj[4]);
				dto.setCompanyName((String)obj[6]);
				
				results.add(dto);
			}
		}
		
		return results;
	}
	
	
}
