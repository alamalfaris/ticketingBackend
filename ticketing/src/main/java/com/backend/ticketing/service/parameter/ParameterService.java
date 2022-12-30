package com.backend.ticketing.service.parameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.backend.ticketing.constants.Constants;
import com.backend.ticketing.dao.parameter.IParameterDao;
import com.backend.ticketing.entity.Parameter;
import com.backend.ticketing.entity.ParameterGroup;
import com.backend.ticketing.exception.BadRequestException;
import com.backend.ticketing.repository.IParameterGroupRepository;
import com.backend.ticketing.repository.IParameterRepository;
import com.backend.ticketing.request.parameter.DetailRequest;
import com.backend.ticketing.request.parameter.ParameterRequest;
import com.backend.ticketing.response.SearchResponse;
import com.backend.ticketing.response.parameter.ParameterGroupResponse;
import com.backend.ticketing.response.parameter.ParameterResponse;
import com.backend.ticketing.service.CommonService;
import com.backend.ticketing.utils.ValidationUtil;

@Service
@Repository
public class ParameterService extends CommonService implements IParameterService {
	
	@Autowired
	private IParameterDao parameterDao;
	
	@Autowired
	private IParameterGroupRepository parameterGroupRepo;
	
	@Autowired
	private IParameterRepository parameterRepo;

	@Override
	public SearchResponse<ParameterGroupResponse> search(ParameterRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search parameter group
		List<ParameterGroupResponse> listParamGroup = parameterDao.search(request);
		
		for (ParameterGroupResponse p : listParamGroup) {

			// search parameter
			List<ParameterResponse> listParam = parameterDao.searchDetail(p.getParameterGroupCode());
			
			p.setListParameter(listParam);
		}
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listParamGroup.size());
		Page<ParameterGroupResponse> page = new PageImpl<>(listParamGroup.subList(start, end), pageable, 
				listParamGroup.size());
		
		return this.createSearchResponse(page, request);
	}

	@Override
	public ParameterGroup submit(ParameterRequest request) {

		// check mandatory
		ValidationUtil.checkMandatory(request);
		
		// find by id
		ParameterGroup paramGroupDb = parameterGroupRepo.findById(request.getParameterGroupCode())
				.orElse(null);
		
		var result = new ParameterGroup();
		
		if (paramGroupDb == null) {
			result = addParameter(request);
		}
		else if (paramGroupDb.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			throw new BadRequestException(String.format(Constants.DELETED_MESSAGE, 
					paramGroupDb.getParameterGroupCode()));
		}
		else {
			result = updateParameter(request, paramGroupDb);
		}
		
		return result;
	}
	
	private ParameterGroup addParameter(ParameterRequest request) {
		
		ParameterGroup parameterGroup = new ParameterGroup();
		Integer lineNo = 1;
		List<Parameter> listParameter = new ArrayList<>();
		
		for (DetailRequest d : request.getParameterList()) {
			
			Parameter param = new Parameter();
			
			param.setParameterCode(d.getParameterCode().trim());
			param.setParameterName(d.getParameterName().trim());
			param.setParameterDesc(d.getParameterDesc().trim());
			param.setParameterGroup(parameterGroup);
			param.setLineNo(lineNo);
			
			this.setCreate(param, "admin");
			
			listParameter.add(param);
			
			lineNo++;
		}
		
		parameterGroup.setParameterGroupCode(request.getParameterGroupCode().trim());
		parameterGroup.setParameterGroupName(request.getParameterGroupName().trim());
		parameterGroup.setParameter(listParameter);
		
		this.setCreate(parameterGroup, "admin");
		
		parameterGroupRepo.save(parameterGroup);
		
		return parameterGroup;
	}

	private ParameterGroup updateParameter(ParameterRequest request, ParameterGroup paramGroupDb) {
		
		// collect parameter code
		List<String> listParamCode = new ArrayList<>();
		for (DetailRequest d : request.getParameterList()) {
			listParamCode.add(d.getParameterCode());
		}
		
		// update record flag data not in request
		parameterRepo.updateRecordFlag("admin", request.getParameterGroupCode(), listParamCode);
		
		// get parameter code that deleted
		List<String> listParamDeleted = parameterRepo.getParamCodeDeleted(request.getParameterGroupCode(), listParamCode);
		
		// update record flag setting
		parameterRepo.updateRecordFlag("admin", listParamDeleted);
		
		// get max line no parameter, then +1
		Integer lineNo = parameterRepo.getMaxLineNo(request.getParameterGroupCode());
		lineNo += 1;
		
		List<Parameter> listParameter = new ArrayList<>();
		
		for (DetailRequest d : request.getParameterList()) {
			
			Parameter parameter = new Parameter();
			
			// check every parameter request in DB
			 Parameter param = parameterRepo.findById(d.getParameterCode()).orElse(null);
			 
			// add new parameter
			 if (param == null) {
				 parameter.setParameterCode(d.getParameterCode());
				 parameter.setLineNo(lineNo);
				 
				 this.setCreate(parameter, "admin");
				 
				 lineNo++;
			 }
			// parameter was deleted
			 else if (param.getRecordFlag().equals(Constants.FLAG_DELETE)) { 
				 throw new BadRequestException(String.format(Constants.DELETED_MESSAGE, d.getParameterCode()));
			 }
			// update parameter
			 else {
				 parameter.setParameterCode(param.getParameterCode());
				 parameter.setLineNo(param.getLineNo());
				 parameter.setCreatedBy(param.getCreatedBy());
				 parameter.setCreatedTime(param.getCreatedTime());
				 
				 this.setUpdate(parameter, "admin");
			 }
			 
			parameter.setParameterGroup(paramGroupDb);
			parameter.setParameterName(d.getParameterName());
			parameter.setParameterDesc(d.getParameterDesc());
			
			listParameter.add(parameter);
		}
		
		// set to parameter group
		ParameterGroup paramGroup = new ParameterGroup();
		
		paramGroup.setParameterGroupCode(request.getParameterGroupCode());
		paramGroup.setParameterGroupName(request.getParameterGroupName());
		paramGroup.setParameter(listParameter);
		paramGroup.setCreatedBy(paramGroupDb.getCreatedBy());
		paramGroup.setCreatedTime(paramGroupDb.getCreatedTime());
		
		this.setUpdate(paramGroup, "admin");
		
		parameterGroupRepo.save(paramGroup);
		
		return paramGroup;
	}
	
	
	
	
	
	
	
	
	
	
}
