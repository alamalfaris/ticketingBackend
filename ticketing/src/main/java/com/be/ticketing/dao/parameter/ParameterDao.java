package com.be.ticketing.dao.parameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.parameter.ParameterGroupRepository;
import com.be.ticketing.repository.parameter.ParameterRepository;
import com.be.ticketing.request.parameter.ParameterSearchRequest;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;
import com.be.ticketing.response.parameter.ParameterSearchResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class ParameterDao extends CommonService implements IParameterDao {
	
	@Autowired
	private ParameterGroupRepository parameterGroupRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;

	@Override
	public List<ParameterGroupSearchResponse> searchParameterGroup(ParameterSearchRequest request) {

		List<ParameterGroupSearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = parameterGroupRepo.searchParameterGroup(this.convertValueForLike(request.getParameterGroupCd()), 
				this.convertValueForLike(request.getParameterGroupName()), 
				this.convertValueForLike(request.getParameterName()));
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				ParameterGroupSearchResponse vo = new ParameterGroupSearchResponse();
				
				vo.setParamGroupCode(obj[0] != null ? (String)obj[0] : "");
				vo.setParamGroupName(obj[1] != null ? (String)obj[1] : "");
				vo.setCreatedBy(obj[2] != null ? (String)obj[2] : "");
				vo.setCreatedTime(obj[7] != null ? (String)obj[7] : "");
				vo.setUpdatedBy(obj[4] != null ? (String)obj[4] : "");
				vo.setUpdatedTime(obj[8] != null ? (String)obj[8] : "");
				vo.setParamName(obj[9] != null ? (String)obj[9] : "");
				
				results.add(vo);
			}
		}
		
		return results;
	}

	@Override
	public List<ParameterSearchResponse> searchParameter(String paramGroupCode) {

		List<ParameterSearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = parameterRepo.searchParameter(this.convertValueForLike(paramGroupCode));
		
		if (rows.size() > 0) {
			
			for (Object[] obj: rows) {
				
				ParameterSearchResponse vo = new ParameterSearchResponse();
				
				vo.setParamCode(obj[0] != null ? (String)obj[0] : null);
				vo.setParamName(obj[1] != null ? (String)obj[1] : null);
				vo.setParamDesc(obj[2] != null ? (String)obj[2] : null);
				
				results.add(vo);
			}
		}
		
		return results;
	}

}
