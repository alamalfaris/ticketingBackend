package com.be.ticketing.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.request.BaseSearchRequest;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.request.company.CompanyRequest;
import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.request.parameter.ParameterGroupRequest;
import com.be.ticketing.request.parameter.ParameterRequest;
import com.be.ticketing.request.province.ProvinceRequest;
import com.be.ticketing.request.role.RoleRequest;
import com.be.ticketing.request.setting.SettingRequest;
import com.be.ticketing.request.settinggroup.SettingGroupRequest;
import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.request.supportedpic.PicRequest;
import com.be.ticketing.request.supportedpic.SupportedPicSubmitRequest;

public class ValidationUtil {
	
	public static String ValidationSubmitParameter(ParameterGroupRequest request) {
		
		String message = null;
		
		if (request.getParamGroupCd().trim() == null || request.getParamGroupCd().trim() == ""
				|| request.getParamGroupCd().length() > 64) {
			message = "Parameter Code must filled and less than 65 characters";
			return message;
		}
		
		if (request.getParamGroupName().trim() == null || request.getParamGroupName().trim() == ""
				|| request.getParamGroupName().length() > 128) {
			message = "Parameter Name must filled and less than 129 characters";
			return message;
		}
		
		if (request.getParamList() != null) {
			for (ParameterRequest pr : request.getParamList()) {
				if (pr.getParamCode().trim() == null || pr.getParamCode().trim() == ""
						|| pr.getParamCode().length() > 64) {
					message = "Parameter Detail Code must filled and less than 65 characters";
					return message;
				}
				
				if (pr.getParamName().trim() == null || pr.getParamName().trim() == ""
						|| pr.getParamName().length() > 64) {
					message = "Parameter Detail Name must filled and less than 65 characters";
					return message;
				}
			}
		}
		return message;
	}
	
	public static String ValidationPageParameter(BaseSearchRequest request) {
		
		String message = null;
		
		if (request.getPageNumber() == null || request.getPageNumber() == 0) {
			message = "Page Number must filled and more than 0";
			return message;
		}
		
		if (request.getPageSize() == null || request.getPageSize() == 0) {
			message = "Page Size must filled and more than 0";
			return message;
		}
		return message;
	}
	
	public static String ValidationAddEditSettingGroup(SettingGroupRequest request) {
		
		String message = null;
		
		if (request.getGroupCd().trim() == null || request.getGroupCd().trim() == ""
				|| request.getGroupCd().length() > 64) {
			message = "Setting Group Code must filled and less than 65 characters";
			return message;
		}
		
		if (request.getGroupName().trim() == null || request.getGroupName().trim() == ""
				|| request.getGroupName().length() > 128) {
			message = "Setting Group Name must filled and less than 129 characters";
			return message;
		}
		return message;
	}
	
	public static void validationAddEditSetting(SettingRequest request) {
		
		String message = null;
		
		if (request.getSettingGroupCode().trim() == null || request.getSettingGroupCode().trim() == ""
				|| request.getSettingGroupCode().length() > 64) {
			message = "Setting Group must filled and less than 65 characters";
			throw new BadRequestException(message);
		}
		
		if (request.getSettingCode().trim() == null || request.getSettingCode().trim() == ""
				|| request.getSettingCode().length() > 64) {
			message = "Setting Code must filled and less than 65 characters";
			throw new BadRequestException(message);
		}
		
		if (request.getSettingDesc().trim() == null || request.getSettingDesc().trim() == ""
				|| request.getSettingDesc().length() > 64) {
			message = "Description must filled and less than 65 characters";
			throw new BadRequestException(message);
		}
		
		if (request.getSettingValueType().trim() == null || request.getSettingValueType().trim() == ""
				|| request.getSettingValueType().length() > 64) {
			message = "Setting Value Type must filled and less than 65 characters";
			throw new BadRequestException(message);
		}
		
		if (request.getSettingValue().trim() == null || request.getSettingValue().trim() == ""
				|| request.getSettingValue().length() > 128) {
			message = "Setting Value must filled and less than 129 characters";
			throw new BadRequestException(message);
		}
	}
	
	public static String ValidationAddEditProvince(ProvinceRequest request) {
		
		String message = null;
		
		if (request.getProvinceCd().trim() == null || request.getProvinceCd().trim() == ""
				|| request.getProvinceCd().length() > 8) {
			message = "Province Code must filled and less than 9 characters";
			return message;
		}
		
		if (request.getProvinceName().trim() == null || request.getProvinceName().trim() == ""
				|| request.getProvinceName().length() > 64) {
			message = "Province Name must filled and less than 65 characters";
			return message;
		}
		
		return message;
	} 
	
	public static void checkMandatory(CityRequest request) {
		
		if (request.getCityCd().trim().isEmpty() ||
				request.getCityCd().trim() == null || request.getCityCd().isBlank() 
				|| request.getCityCd().length() > 8) {
			throw new BadRequestException("City Code must filled and less than 9 character");
		}
		if (request.getCityName().trim().isEmpty() ||
				request.getCityName().trim() == null || request.getCityName().length() > 64) {
			throw new BadRequestException("City Name must filled and less than 65 character");
		}
		if (request.getProvinceId().trim().isEmpty() ||
				request.getProvinceId().trim() == null) {
			throw new BadRequestException("Province id must filled");
		}
	}
	
	public static void checkMandatory(CompanyRequest request) {
		
		if (request.getCompanyCd() == null || request.getCompanyCd() == "" || request.getCompanyCd().isBlank()
				|| request.getCompanyCd().length() > 16) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Company code", "17"));
		}
		if (request.getCompanyName() == null || request.getCompanyName() == ""
				|| request.getCompanyName().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Company name", "65"));
		}
		if (request.getCompanyType() == null || request.getCompanyType() == ""
				|| request.getCompanyType().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Company type", "65"));
		}
		if (request.getCompanyBusiness() == null || request.getCompanyBusiness() == ""
				|| request.getCompanyBusiness().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Company business", "65"));
		}
		if (request.getAddress() == null || request.getAddress() == ""
				|| request.getAddress().length() > 256) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Address", "257"));
		}
		if (request.getCityCd() == null || request.getCityCd() == "") {
			throw new BadRequestException(String.format(Constants.MUST_FILLED_MESSAGE, "City code"));
		}
	}
	
	public static void checkMandatory(SupportedObjectRequest request) {
		
		if (request.getCompanyId() == null || request.getCompanyId() == "") {
			throw new BadRequestException(String.format(Constants.MUST_FILLED_MESSAGE, "Company id"));
		}
		if (request.getDescription() == null || request.getDescription() == ""
				|| request.getDescription().length() > 256) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Description", "257"));
		}
		if (request.getObjectCode() == null || request.getObjectCode() == "" || request.getObjectCode().isBlank() 
				|| request.getObjectCode().length() > 16) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Object code", "17"));
		}
		if (request.getObjectName() == null || request.getObjectName() == "" 
				|| request.getObjectName().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Object name", "65"));
		}
		if (request.getObjectType() == null || request.getObjectType() == ""
				|| request.getObjectType().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, "Object type", "65"));
		}
	}
	
	public static void checkMandatory(SupportedPicSubmitRequest request) {
		
		if (request.getCompanyId() == null) {
			throw new BadRequestException(String.format(Constants.MUST_FILLED_MESSAGE, "Company id"));
		}
		if (request.getPic().size() > 0) {
			for (PicRequest p : request.getPic()) {
				if (p.getEmail() == null || p.getEmail() == "" || p.getEmail().isBlank() || p.getEmail().length() > 64) {
					throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
							"Email address", "65"));
				}
				if (p.getMobileNo() == null || p.getMobileNo() == "" || p.getMobileNo().isBlank() || p.getMobileNo().length() > 16) {
					throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
							"Mobile No", "17"));
				}
				if (p.getName() == null || p.getName() == "" || p.getName().isBlank() || p.getName().length() > 64) {
					throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
							"Full name", "65"));
				}
			}
		}
	}
	
	public static void checkMandatory(Long companyId) {
		
		if (companyId == null) {
			throw new BadRequestException(String.format(Constants.MUST_FILLED_MESSAGE, "Company id"));
		}
	}
	
	public static void checkMandatory(MenuRequest request) {
		
		if (request.getMenuCd() == null || request.getMenuCd()== "" || request.getMenuCd().isBlank()
				|| request.getMenuCd().length() > 32) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Menu code", "33"));
		}
		if (request.getMenuName() == null || request.getMenuName() == ""
				|| request.getMenuName().length() > 32) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Menu name", "33"));
		}
		if (request.getType() == null || request.getType() == ""
				|| request.getType().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Type", "65"));
		}
	}
	
	public static void checkMandatory(RoleRequest request) {
		
		if (request.getRoleCode() == null || request.getRoleCode() == "" || request.getRoleCode().isBlank()
				|| request.getRoleCode().length() > 32) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Role Code", "33"));
		}
		if (request.getRoleName() == null || request.getRoleName() == ""
				|| request.getRoleName().length() > 32) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Role Name", "33"));
		}
		if (request.getRoleType() == null || request.getRoleType() == ""
				|| request.getRoleType().length() > 64) {
			throw new BadRequestException(String.format(Constants.MANDATORY_MESSAGE, 
					"Role Type", "65"));
		}
	}
	
	public static void checkPage(BaseSearchRequest request) {
		
		String message = null;
		
		// check page number
		if (request.getPageNumber() == null || request.getPageNumber() == 0) {
			message = "Page Number must filled and more than 0";
			throw new BadRequestException(message);
		}
		// check page size 
		if (request.getPageSize() == null || request.getPageSize() == 0) {
			message = "Page Size must filled and more than 0";
			throw new BadRequestException(message);
		}
	}
	
	public static void checkExtentionFile(MultipartFile fileUpload) {
		
		String fileName = fileUpload.getOriginalFilename();
		boolean isExcelFile = fileName.endsWith(".xlsx");
		if (!isExcelFile) {
			throw new BadRequestException("file must have .xlsx extention");
		}
	}
	
	public static void checkExtentionFile2(String extention) {
		
		// null/empty check
		if (extention == null || extention == "") {
			throw new BadRequestException("file extention must sent");
		}
	}
	
	public static void checkListCode(ListCodeRequest request) {
		
		if (request.getListCode().size() > 0) {
			for (Code c : request.getListCode()) {
				if (c.getCode() == null || c.getCode().trim() == "" ) {
					throw new BadRequestException("code can't null or empty");
				}
			}
		}
		else {
			throw new BadRequestException("list code can't empty");
		}
	}
	
	public static String checkExcelValue(Cell cell){
        
		String val = null;

        if(cell == null || cell.getCellType() == CellType.BLANK){
            return "";
        }

        switch (cell.getCellType().toString()) {
            case "STRING":
                val = cell.getStringCellValue();
                break;
            case "NUMERIC":
                val = String.valueOf((long) cell.getNumericCellValue());
                break;
            default:
                val = "";
                break;
        }

        return val;
    }
}
