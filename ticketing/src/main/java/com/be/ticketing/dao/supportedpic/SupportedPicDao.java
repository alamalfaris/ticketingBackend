package com.be.ticketing.dao.supportedpic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.supportedpic.PicRepository;
import com.be.ticketing.repository.supportedpic.SupportedPicRepository;
import com.be.ticketing.request.supportedpic.SupportedPicSearchRequest;
import com.be.ticketing.response.supportedpic.PicResponse;
import com.be.ticketing.response.supportedpic.SupportedPicDownload;
import com.be.ticketing.response.supportedpic.SupportedPicSearchResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class SupportedPicDao extends CommonService implements ISupportedPicDao {
	
	@Autowired
	private SupportedPicRepository supportedPicRepo;
	
	@Autowired
	private PicRepository picRepo;

	@Override
	public List<SupportedPicSearchResponse> search(SupportedPicSearchRequest request) {

		List<SupportedPicSearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = supportedPicRepo.search(this.convertValueForLike(request.getCompany()), 
				this.convertValueForLike(request.getPicEmail()), 
				this.convertValueForLike(request.getPicName()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				SupportedPicSearchResponse dto = new SupportedPicSearchResponse();
				dto.setCompanyId((BigInteger)obj[0]);
				dto.setCompanyCode((String)obj[1]);
				dto.setCompanyName((String)obj[2]);
				dto.setCreatedBy(obj[3] != null ? (String)obj[3] : "");
				dto.setCreatedTime(obj[7] != null ? (String)obj[7] : "");
				dto.setUpdatedBy(obj[5] != null ? (String)obj[5] : "");
				dto.setUpdatedTime(obj[8] != null ? (String)obj[8] : "");
				
				List<String> listPic = new ArrayList<>();
				if (obj[9] != null) {
					String pic = (String)obj[9];
					String[] arrPic = pic.split("-");
					
					for (int i = 0; i < arrPic.length; i++) {
						listPic.add(arrPic[i]);
					}
				}
				dto.setPic(listPic);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<PicResponse> assign(Long companyId) {

		//search detail (pic_company)
		List<PicResponse> listPic = new ArrayList<>();
		List<Object[]> rows = supportedPicRepo.searchCompanyPic(companyId);
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				PicResponse dto = new PicResponse();
				dto.setPicId((BigInteger)obj[0]);
				dto.setName((String)obj[1]);
				dto.setMobileNo((String)obj[2]);
				dto.setEmail((String)obj[3]);
				
				listPic.add(dto);
			}	
		}
		
		return listPic;
	}

	@Override
	public List<SupportedPicDownload> download(SupportedPicSearchRequest request) {

		List<SupportedPicDownload> results = new ArrayList<>();
		
		List<Object[]> rows = supportedPicRepo.searchDownload(this.convertValueForLike(request.getCompany()), 
				this.convertValueForLike(request.getPicEmail()), 
				this.convertValueForLike(request.getPicName()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				SupportedPicDownload dto = new SupportedPicDownload();
				dto.setCompanyCode((String)obj[0]);
				dto.setCompanyName((String)obj[1]);
				dto.setFullName((String)obj[2]);
				dto.setMobileNo((String)obj[3]);
				dto.setEmailAddress((String)obj[4]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<PicResponse> getAllPic() {

		List<PicResponse> results = new ArrayList<>();
		
		List<Object[]> rows = picRepo.getAllPic();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				PicResponse dto = new PicResponse();
				dto.setPicId((BigInteger)obj[0]);
				dto.setName((String)obj[1]);
				dto.setEmail((String)obj[2]);
				dto.setMobileNo((String)obj[3]);
				
				results.add(dto);
			}
		}
		
		return results;
	}
	
	
}
