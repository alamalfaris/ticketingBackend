package com.be.ticketing.constant;

public interface Constants {
	
	final String SUCCESS_MESSAGE = "Processed Successfully";
	final String BADREQUEST_MESSAGE = "Bad Request!";
	final String MANDATORY_MESSAGE = "%s must filled and less than %s character";
	final String MUST_FILLED_MESSAGE = "%s must filled";
	final String EXIST_MESSAGE = "%s with code or id: %s already exist";
	final String NOTFOUND_MESSAGE = "%s with code or id: %s not found";
	final String DELETE_SUCCESS_MESSAGE = "%s with code or id: %s deleted successfully";
	final String EMPTY_EXCEL_MESSAGE = "No data in excel file";
	final String UPLOAD_NEWDATA_MESSAGE = "New data %s uploaded";
	final String UPLOAD_EXISTDATA_MESSAGE = "Data %s updated";
	final String WASDELETE_MESSAGE = "%s with code or id: %s already exist with delete flag";
	
	final String SUCCESS_STATUS = "Success";
	final String ERROR_STATUS = "error";
	
	final String FLAG_NEW = "N";
	final String FLAG_EDIT = "E";
	final String FLAG_DELETE = "D";
	
	final Integer ROW_INDEX_START = 3;
	
	final String TEMPLATE_NAME_SETTING_GROUP = "Template Setting Group.xlsx";
	final String TEMPLATE_SETTING_GROUP = "templates/Template Setting Group.xlsx";
	final String TEMPLATE_DATA_SETTING_GROUP = "templates/Template Data Setting Group.";
	final String TITLE_FILE_SETTING_GROUP = "SettingGroup_";
	final String SHEET_NAME_SETTING_GROUP = "Setting Group";
	
	final String TYPE_XLSX = "xlsx";
	final String TYPE_XLS = "xls";
	final String TYPE_CSV = "csv";
	
	final String TITLE_FILE_PARAMETER = "Parameter_";
	final String SHEET_NAME_PARAMETER = "Parameter";
	final String TEMPLATE_DATA_PARAMETER = "templates/Template Data Parameter.";
	
	final String MESSAGE_SETTINGGROUPCODE_PARAMETERCODE = "Setting Group Code or System Type Value not found";
	final String MESSAGE_DATANOTFOUND = "Data not found";
	final String DATA_NOT_FOUND = "Data not found";
	
	final String TEMPLATE_NAME_SETTING = "Template Setting.xlsx";
	final String TEMPLATE_SETTING = "templates/Template Setting.xlsx";
	final String TEMPLATE_DATA_SETTING = "templates/Template Data Setting.";
	final String SHEET_NAME_SETTING = "Setting";
	final String TITLE_FILE_SETTING = "Setting_";
	
	final String TEMPLATE_NAME_PROVINCE = "Template Province.xlsx";
	final String TEMPLATE_PROVINCE = "templates/Template Province.xlsx";
	final String TITLE_FILE_PROVINCE = "Province_";
	final String TEMPLATE_DATA_PROVINCE = "templates/Template Data Province.";
	final String SHEET_NAME_PROVINCE = "Province";
	
	final String TEMPLATE_CITY = "templates/Template City.xlsx";
	final String TEMPLATE_NAME_CITY = "Template City.xlsx";
	final String TEMPLATE_DATA_CITY = "templates/Template Data City.";
	final String TITLE_FILE_CITY = "City_";
	final String SHEET_NAME_CITY = "City";
	
	final String TEMPLATE_COMPANY = "templates/Template Company.xlsx";
	final String TEMPLATE_NAME_COMPANY = "Template Company.xlsx";
	final String TEMPLATE_DATA_COMPANY = "templates/Template Data Company.";
	final String TITLE_FILE_COMPANY = "Company_";
	final String SHEET_NAME_COMPANY = "Company";
	
	final String TEMPLATE_SUPPORTED_OBJECT = "templates/Template Supported Object.xlsx";
	final String TEMPLATE_NAME_SUPPORTED_OBJECT = "Template Supported Object.xlsx";
	final String TEMPLATE_DATA_SUPPORTED_OBJECT = "templates/Template Data Supported Object.";
	final String TITLE_FILE_SUPPORTED_OBJECT = "Supported Object_";
	final String SHEET_NAME_SUPPORTED_OBJECT = "Supported Object";
	
	final String TEMPLATE_SUPPORTED_PIC = "templates/Template Supported Pic.xlsx";
	final String TEMPLATE_NAME_SUPPORTED_PIC = "Template Supported Pic.xlsx";
	final String TEMPLATE_DATA_SUPPORTED_PIC = "templates/Template Data Supported Pic.";
	final String TITLE_FILE_SUPPORTED_PIC = "Supported Pic_";
	final String SHEET_NAME_SUPPORTED_PIC = "Supported Pic";
	
	final String TEMPLATE_MENU = "templates/Template Menu.xlsx";
	final String TEMPLATE_NAME_MENU = "Template Menu.xlsx";
	final String TEMPLATE_DATA_MENU = "templates/Template Data Menu.";
	final String TITLE_FILE_MENU = "Menu_";
	final String SHEET_NAME_MENU = "Menu";
	
	final String TEMPLATE_ROLE = "templates/Template Role.xlsx";
	final String TEMPLATE_NAME_ROLE = "Template Role.xlsx";
	final String TEMPLATE_DATA_ROLE = "templates/Template Data Role.";
	final String TITLE_FILE_ROLE = "Role_";
	final String SHEET_NAME_ROLE = "Role";
}
