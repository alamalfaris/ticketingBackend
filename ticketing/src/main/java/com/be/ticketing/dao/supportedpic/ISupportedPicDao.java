package com.be.ticketing.dao.supportedpic;

import java.util.List;

import com.be.ticketing.request.supportedpic.SupportedPicSearchRequest;
import com.be.ticketing.response.supportedpic.PicResponse;
import com.be.ticketing.response.supportedpic.SupportedPicDownload;
import com.be.ticketing.response.supportedpic.SupportedPicSearchResponse;

public interface ISupportedPicDao {
	
	List<SupportedPicSearchResponse> search(SupportedPicSearchRequest request);
	
	List<PicResponse> assign(Long companyId);
	
	List<SupportedPicDownload> download(SupportedPicSearchRequest request);
	
	List<PicResponse> getAllPic();
}
