package com.backend.ticketing.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.backend.ticketing.constants.Constants;
import com.backend.ticketing.response.BaseResponse;

public class ResponseUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> ResponseEntity<T> generateResponseSuccess(Object responseObj) {

		BaseResponse<Object> response = new BaseResponse<Object>();
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setStatus(HttpStatus.OK);
		response.setData(responseObj);

		return (ResponseEntity<T>) new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
