package com.be.ticketing.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.response.BaseResponse;

public class ResponseUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> ResponseEntity<T> generateResponseSuccess(Object responseObj) {

		BaseResponse<Object> response = new BaseResponse<Object>();
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setStatus(Constants.SUCCESS_STATUS);
		response.setData(responseObj);

		return (ResponseEntity<T>) new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
