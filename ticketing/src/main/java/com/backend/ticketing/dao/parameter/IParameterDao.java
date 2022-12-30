package com.backend.ticketing.dao.parameter;

import java.util.List;

import com.backend.ticketing.request.parameter.ParameterRequest;
import com.backend.ticketing.response.parameter.ParameterGroupResponse;
import com.backend.ticketing.response.parameter.ParameterResponse;

public interface IParameterDao {
	
	List<ParameterGroupResponse> search(ParameterRequest request);
	
	List<ParameterResponse> searchDetail(String paramGroupCd);
}
