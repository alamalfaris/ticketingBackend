package com.backend.ticketing.constants;

import java.sql.Timestamp;

public interface Constants {
	
	final String FLAG_NEW = "N";
	final String FLAG_EDIT = "E";
	final String FLAG_DELETE = "D";
	
	final String SUCCESS_MESSAGE = "Processed Successfully";
	final String PAGE_NUMBER_MESSAGE = "Page Number must filled and more than 0";
	final String PAGE_SIZE_MESSAGE = "Page Size must filled and more than 0";
	final String MANDATORY_MESSAGE = "%s must be filled and less than %s characters";
	final String DELETED_MESSAGE = "Data with code: %s already exist with delete flag";
	
	final String TYPE_XLSX = "xlsx";
	final String TYPE_XLS = "xls";
	final String TYPE_CSV = "csv";
	
	final String TITLE_FILE_PARAMETER = "Parameter_";
	final String SHEET_NAME_PARAMETER = "Parameter";
	final String TEMPLATE_DATA_PARAMETER = "templates/Template Data Parameter.";
	
	Timestamp TIME_NOW = new Timestamp(System.currentTimeMillis());
}
