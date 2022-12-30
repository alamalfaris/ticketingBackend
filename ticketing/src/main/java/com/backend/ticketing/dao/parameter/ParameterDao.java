package com.backend.ticketing.dao.parameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.ticketing.repository.IParameterGroupRepository;
import com.backend.ticketing.repository.IParameterRepository;
import com.backend.ticketing.request.parameter.ParameterRequest;
import com.backend.ticketing.response.parameter.ParameterGroupResponse;
import com.backend.ticketing.response.parameter.ParameterResponse;
import com.backend.ticketing.service.CommonService;

@Repository
public class ParameterDao extends CommonService implements IParameterDao {
	
	@Autowired
	private IParameterGroupRepository parameterGroupRepo;
	
	@Autowired
	private IParameterRepository parameterRepo;

	@Override
	public List<ParameterGroupResponse> search(ParameterRequest request) {

		List<ParameterGroupResponse> results = new ArrayList<>();
		
		// search parameter group
		List<Object[]> rows = parameterGroupRepo.search(this.convertValueForLike(request.getParameterGroupCode()),
				this.convertValueForLike(request.getParameterGroupName()),
				this.convertValueForLike(request.getParameterDetail()));
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				ParameterGroupResponse dto = new ParameterGroupResponse();
				
				dto.setParameterGroupCode(obj[0] != null ? (String)obj[0] : null);
				dto.setParameterGroupName(obj[1] != null ? (String)obj[1] : null);
				dto.setCreatedBy(obj[2] != null ? (String)obj[2] : null);
				dto.setCreatedTime(obj[7] != null ? (String)obj[7] : null);
				dto.setUpdatedBy(obj[4] != null ? (String)obj[4] : null);
				dto.setUpdatedTime(obj[8] != null ? (String)obj[8] : null);
				dto.setParameterDetail(obj[9] != null ? (String)obj[9] : null);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<ParameterResponse> searchDetail(String paramGroupCd) {

		List<ParameterResponse> results = new ArrayList<>();
		
		// search parameter
		List<Object[]> rows = parameterRepo.search(paramGroupCd);
		
		if (rows.size() > 0) {
			
			for (Object[] obj : rows) {
				
				ParameterResponse dto = new ParameterResponse();
				
				dto.setParameterCode(obj[0] != null ? (String)obj[0] : null);
				dto.setParameterName(obj[1] != null ? (String)obj[1] : null);
				dto.setParameterDesc(obj[2] != null ? (String)obj[2] : null);
				
				results.add(dto);
			}
		}
		
		return results;
	}
}
