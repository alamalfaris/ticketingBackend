package com.be.ticketing.dao.parameter;

import java.util.List;

import com.be.ticketing.request.parameter.ParameterSearchRequest;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;
import com.be.ticketing.response.parameter.ParameterSearchResponse;

public interface IParameterDao {
	
	List<ParameterGroupSearchResponse> searchParameterGroup(ParameterSearchRequest request);
	
	List<ParameterSearchResponse> searchParameter(String paramGroupCode);
}
