package com.be.ticketing.service.parameter;

import java.io.InputStream;
import java.util.List;

import com.be.ticketing.entity.ParameterGroup;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.parameter.ParameterGroupRequest;
import com.be.ticketing.request.parameter.ParameterSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;


public interface IParameterService {
	
	BaseResponse<List<String>> getParameterGroupCode();
	
	SearchResponse<ParameterGroupSearchResponse> searchParameter(ParameterSearchRequest request);
	
	BaseResponse<List<String>> deleteParameter(ListCodeRequest request);
	
	BaseResponse<ParameterGroup> submitParameter(ParameterGroupRequest request);
	
	BaseResponse<DownloadResponse> downloadParameter(InputStream input, String parameterGroupCode,
			String parameterGroupName, String parameterName, String extension);
}
