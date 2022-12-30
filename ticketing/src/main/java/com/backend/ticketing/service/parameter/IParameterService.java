package com.backend.ticketing.service.parameter;

import com.backend.ticketing.entity.ParameterGroup;
import com.backend.ticketing.request.parameter.ParameterRequest;
import com.backend.ticketing.response.SearchResponse;
import com.backend.ticketing.response.parameter.ParameterGroupResponse;

public interface IParameterService {
	
	SearchResponse<ParameterGroupResponse> search(ParameterRequest request);
	
	ParameterGroup submit(ParameterRequest request);
}
