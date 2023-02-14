package com.be.ticketing.mapper.parameter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.entity.ParameterGroup;
import com.be.ticketing.request.parameter.ParameterGroupRequest;
import com.be.ticketing.request.parameter.ParameterRequest;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;
import com.be.ticketing.response.parameter.ParameterSearchResponse;

public class ParameterMapper {
	
	public static ParameterSearchResponse mapToDetailResponse(Object[] obj) {
		
		ParameterSearchResponse parameter = new ParameterSearchResponse();
		parameter.setParamCode((String) obj[0]);
		parameter.setParamName((String) obj[1]);
		parameter.setParamDesc((String) obj[2]);
		
		return parameter;
	}
	
	public static ParameterGroupSearchResponse mapToHeaderResponse(Object[] obj, List<ParameterSearchResponse> listParameter,
			List<String> listParameterName) {
		
		ParameterGroupSearchResponse parameterGroup = new ParameterGroupSearchResponse();
		parameterGroup.setParamGroupCode((String) obj[0]);
		parameterGroup.setParamGroupName((String) obj[1]);
		parameterGroup.setParamName(String.join(", ", listParameterName));
		parameterGroup.setCreatedBy((String) obj[3]);
		parameterGroup.setCreatedTime((String)obj[5]);
		parameterGroup.setUpdatedBy((String) obj[6]);
		parameterGroup.setUpdatedTime((String)obj[7]);
		parameterGroup.setListParameter(listParameter);
		
		return parameterGroup;
	}
	
	public static ParameterGroup mapToEntityDelete(ParameterGroup parameterGroup) {
		
		parameterGroup.setRecordFlag(Constants.FLAG_DELETE);
		parameterGroup.setUpdatedBy("admin");
		parameterGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		
		List<Parameter> parameters = parameterGroup.getParameters();
		for (Parameter p : parameters) {
			p.setRecordFlag(Constants.FLAG_DELETE);
			p.setUpdatedBy("admin");
			p.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		}
		
		return parameterGroup;
	}
	
	public static ParameterGroup mapToEntitySubmit(ParameterGroupRequest request, ParameterGroup parameterGroup,
			Boolean isExist) {
		
		Integer lineNo = 1;
		
		if (!isExist) {
			parameterGroup.setParameterGroupCd(request.getParamGroupCd().trim());
			parameterGroup.setParameterGroupName(request.getParamGroupName().trim());
			parameterGroup.setRecordFlag(Constants.FLAG_NEW);
			
			List<Parameter> listParameter = new ArrayList<>();
			
			for (ParameterRequest pr : request.getParamList()) {
				Parameter parameter = new Parameter();
				parameter.setParameterCd(pr.getParamCode().trim());
				parameter.setParameterName(pr.getParamName().trim());
				parameter.setParameterDesc(pr.getParamDesc().trim());
				parameter.setParameterGroup(parameterGroup);
				parameter.setLineNo(lineNo);
				parameter.setRecordFlag(Constants.FLAG_NEW);
				parameter.setCreatedBy("admin");
				parameter.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				
				listParameter.add(parameter);
				lineNo++;
			}
			
			parameterGroup.setParameters(listParameter);
			parameterGroup.setCreatedBy("admin");
			parameterGroup.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		}
		else {
			parameterGroup.setParameterGroupName(request.getParamGroupName().trim());
			parameterGroup.setRecordFlag(Constants.FLAG_EDIT);
			parameterGroup.setUpdatedBy("admin");
			parameterGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			
			List<Parameter> listParameter = new ArrayList<>();
			
			for (ParameterRequest pr : request.getParamList()) {
				Parameter parameter = new Parameter();
				parameter.setParameterCd(pr.getParamCode().trim());
				parameter.setParameterName(pr.getParamName().trim());
				parameter.setParameterDesc(pr.getParamDesc() != null ? pr.getParamDesc().trim() : null);
				parameter.setParameterGroup(parameterGroup);
				parameter.setRecordFlag(Constants.FLAG_NEW);
				parameter.setLineNo(lineNo);
				parameter.setCreatedBy("admin");
				parameter.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				
				listParameter.add(parameter);
				lineNo++;
			}
			
			parameterGroup.getParameters().clear();
			parameterGroup.getParameters().addAll(listParameter);
		}
		return parameterGroup;
	}
}
