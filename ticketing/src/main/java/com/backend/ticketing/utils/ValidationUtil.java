package com.backend.ticketing.utils;

import com.backend.ticketing.constants.Constants;
import com.backend.ticketing.exception.BadRequestException;
import com.backend.ticketing.request.BaseSearchRequest;
import com.backend.ticketing.request.parameter.DetailRequest;
import com.backend.ticketing.request.parameter.ParameterRequest;

public class ValidationUtil {
	
	// validation for page
	public static void checkPage(BaseSearchRequest request) {
		
		// check page number
		if (request.getPageNumber() == null || request.getPageNumber() == 0) {
			throw new BadRequestException(Constants.PAGE_NUMBER_MESSAGE);
		}
		// check page size 
		if (request.getPageSize() == null || request.getPageSize() == 0) {
			throw new BadRequestException(Constants.PAGE_SIZE_MESSAGE);
		}
	}
	
	// validation for list code (when delete)
	
	// validation for list id (when delete)
	
	// validation for extension file (when download)
	
	// validation for extension file (when upload)
	
	// validation for mandatory
	public static void checkMandatory(ParameterRequest request) {
		
		if (request.getParameterGroupCode() == null
				|| request.getParameterGroupCode() == ""
				|| request.getParameterGroupCode().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Parameter Group Code", "65"));
		}
		
		if (request.getParameterGroupName() == null
				|| request.getParameterGroupName() == ""
				|| request.getParameterGroupName().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Parameter Group Name", "65")); 
		}
		
		if (request.getParameterList().size() > 0) {
			
			for (DetailRequest d : request.getParameterList()) {
				
				if (d.getParameterCode() == null
						|| d.getParameterCode() == ""
						|| d.getParameterCode().length() > 64) {
					throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
							"Parameter Code", "65")); 
				}
				
				if (d.getParameterName() == null
						|| d.getParameterName() == ""
						|| d.getParameterName().length() > 64) {
					throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
							"Parameter Name", "65")); 
				}
			}
		}
		else {
			throw new BadRequestException("Parameter list must filled");
		}
	}
}
