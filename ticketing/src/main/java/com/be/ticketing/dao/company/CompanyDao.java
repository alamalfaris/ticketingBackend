package com.be.ticketing.dao.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.company.CompanyRepository;
import com.be.ticketing.request.company.CompanyRequest;
import com.be.ticketing.response.company.CompanySearchResponse;
import com.be.ticketing.response.company.GetBusinessResponse;
import com.be.ticketing.response.company.GetCityResponse;
import com.be.ticketing.response.company.GetTypeResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class CompanyDao extends CommonService implements ICompanyDao{
	
	@Autowired
	private CompanyRepository companyRepo;

	@Override
	public List<CompanySearchResponse> search(CompanyRequest request) {

		List<CompanySearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = companyRepo.search(this.convertValueForLike(request.getCompanyCd()), 
				this.convertValueForLike(request.getCompanyName()), 
				this.convertValueForLike(request.getBusiness()));
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				CompanySearchResponse dto = new CompanySearchResponse();
				
				dto.setCompanyCd((String)obj[0]);
				dto.setCompanyName((String)obj[1]);
				dto.setType((String)obj[6]);
				dto.setBusiness((String)obj[7]);
				dto.setAddress((String)obj[5]);
				
				BigInteger bi = (BigInteger)obj[4];
				dto.setCityId(bi.toString());
				
				dto.setCompanyType((String)obj[2]);
				dto.setCompanyBusiness((String)obj[3]);
				dto.setProvinceName((String)obj[8]);
				dto.setCreatedBy(obj[9] != null ? (String)obj[9] : "");
				dto.setCreatedTime(obj[13] != null ? (String)obj[13] : "");
				dto.setUpdatedBy(obj[11] != null ? (String)obj[11] : "");
				dto.setUpdatedTime(obj[14] != null ? (String)obj[14] : "");
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<GetCityResponse> getCityCompany() {

		List<GetCityResponse> listCity = new ArrayList<>();
		
		List<Object[]> rows = companyRepo.getCityCompany();
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				GetCityResponse dto = new GetCityResponse();
				
				BigInteger bi = (BigInteger)obj[0];
				dto.setCityId(bi.toString());
				dto.setCityName((String)obj[1]);
				dto.setProvinceName((String)obj[2]);
				
				listCity.add(dto);
			}
		}
		
		return listCity;
	}

	@Override
	public List<GetBusinessResponse> getBusinessCompany() {

		List<GetBusinessResponse> results = new ArrayList<>();
		
		List<Object[]> rows = companyRepo.getBusinessCompany();
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				GetBusinessResponse dto = new GetBusinessResponse();
				dto.setParamCode((String)obj[0]);
				dto.setCompanyBusiness((String)obj[1]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<GetTypeResponse> getTypeCompany() {

		List<GetTypeResponse> results = new ArrayList<>();
		
		List<Object[]> rows = companyRepo.getTypeCompany();
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				GetTypeResponse dto = new GetTypeResponse();
				dto.setParamCode((String)obj[0]);
				dto.setCompanyType((String)obj[1]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<CompanySearchResponse> download(String business, String companyCd, String companyName) {

		List<CompanySearchResponse> listCompany = new ArrayList<>();
		
		List<Object[]> rows = companyRepo.search(this.convertValueForLike(companyCd), 
				this.convertValueForLike(companyName), 
				this.convertValueForLike(business));
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				CompanySearchResponse dto = new CompanySearchResponse();
				dto.setCompanyCd((String)obj[0]);
				dto.setCompanyName((String)obj[1]);
				dto.setType((String)obj[6]);
				dto.setBusiness((String)obj[7]);
				dto.setAddress((String)obj[5]);
				
				listCompany.add(dto);
			}
		}
		
		return listCompany;
	}
	
	
}
