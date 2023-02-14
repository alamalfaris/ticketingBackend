package com.be.ticketing.service.supportedpic;

import java.util.List;

import com.be.ticketing.request.supportedpic.SupportedPicSearchRequest;
import com.be.ticketing.request.supportedpic.SupportedPicSubmitRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse2;
import com.be.ticketing.response.supportedpic.PicResponse;
import com.be.ticketing.response.supportedpic.SupportedPicAssignResponse;
import com.be.ticketing.response.supportedpic.SupportedPicSearchResponse;

public interface ISupportedPicService {
	
	SearchResponse2<SupportedPicSearchResponse> search(SupportedPicSearchRequest request);
	
	SupportedPicSubmitRequest submit(SupportedPicSubmitRequest request);
	
	SupportedPicAssignResponse assign(Long companyId);
	
	List<PicResponse> getAllPic();
	
	DownloadResponse download(SupportedPicSearchRequest request);
}
