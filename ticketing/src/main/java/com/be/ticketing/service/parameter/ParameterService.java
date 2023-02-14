package com.be.ticketing.service.parameter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.dao.parameter.IParameterDao;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.entity.ParameterGroup;
import com.be.ticketing.repository.parameter.ParameterGroupRepository;
import com.be.ticketing.repository.parameter.ParameterRepository;
import com.be.ticketing.repository.setting.SettingRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.parameter.ParameterGroupRequest;
import com.be.ticketing.request.parameter.ParameterRequest;
import com.be.ticketing.request.parameter.ParameterSearchRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.parameter.ParameterGroupSearchResponse;
import com.be.ticketing.response.parameter.ParameterSearchResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class ParameterService extends CommonService implements IParameterService {

	@Autowired
	private ParameterGroupRepository parameterGroupRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;
	
	@Autowired
	private SettingRepository settingRepo;
	
	@Autowired
	private IParameterDao parameterDao;
	
	@Override
	public BaseResponse<List<String>> getParameterGroupCode() {

		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> parameterGroupCodes = new ArrayList<>();
		
		try {
			parameterGroupCodes = parameterGroupRepo.searchParameterGroupCode();
			
			if (parameterGroupCodes == null || parameterGroupCodes.isEmpty()) {
				response.setMessage("No one parameter group code found");
				response.setStatus(Constants.ERROR_STATUS);
				return response;
			}
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
			return response;
		}
		
		response.setStatus(Constants.SUCCESS_STATUS);
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setData(parameterGroupCodes);
		response.setCountData((long)parameterGroupCodes.size());
		
		return response;
	}

	@Override
	public SearchResponse<ParameterGroupSearchResponse> searchParameter(ParameterSearchRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search parameter group
		List<ParameterGroupSearchResponse> listParamGroup = parameterDao.searchParameterGroup(request);
		
		for (ParameterGroupSearchResponse p : listParamGroup) {
			
			// search parameter
			List<ParameterSearchResponse> listParam = parameterDao.searchParameter(p.getParamGroupCode());
			
			p.setListParameter(listParam);
		}
		
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listParamGroup.size());
		Page<ParameterGroupSearchResponse> page = new PageImpl<>(listParamGroup.subList(start, end), pageable, 
				listParamGroup.size());
		
		return this.createSearchResponse(page);
	}

	@Override
	public BaseResponse<List<String>> deleteParameter(ListCodeRequest request) {
		
		// check list code
		ValidationUtil.checkListCode(request);

		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> results = new ArrayList<>();
		
		try {
			for (Code code : request.getListCode()) {
				ParameterGroup parameterGroup = parameterGroupRepo.findById(code.getCode()).orElse(null);
				if (parameterGroup == null) {
					results.add(String.format("Data %s not found", code.getCode()));
				}
				else {
					// update flag app_param_group by param_group_code
					parameterGroupRepo.updateRecordFlag("admin", code.getCode());
					
					// update flag app_param by param_group_code
					parameterRepo.updateRecordFlag("admin", code.getCode());
					
					// select distinct param_code by param_group_code
					List<String> listParamCodeDelete = parameterRepo.getParamCodeDeleted(code.getCode());
					
					// update flag app_setting by param_code
					settingRepo.updateRecordFlag("admin", listParamCodeDelete);
					
					results.add(String.format("Delete %s success", code.getCode()));
				}
			}
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
			return response;
		}
		
		response.setStatus(Constants.SUCCESS_STATUS);
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setData(results);
		
		return response;
	}
	
	@Override
	public BaseResponse<ParameterGroup> submitParameter(ParameterGroupRequest request) {

		BaseResponse<ParameterGroup> response = new BaseResponse<ParameterGroup>();
		
		try {
			ParameterGroup checkParamGroupDb = parameterGroupRepo.findById(request.getParamGroupCd()).orElse(null);
			if (checkParamGroupDb == null) {
				// add new
				response = addParameter(request, response);
			}
			else if (checkParamGroupDb.getRecordFlag().equals(Constants.FLAG_DELETE)) {
				// set message data was deleted
				response.setStatus(Constants.ERROR_STATUS);
				 response.setMessage(String.format("Data with code: %s already exist with delete flag", 
						 request.getParamGroupCd()));
			}
			else {
				// update data
				response = updateParameter(request, checkParamGroupDb, response);
			}
		}
		catch (Exception e) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	private BaseResponse<ParameterGroup> updateParameter(ParameterGroupRequest request, ParameterGroup checkParamGroupDb,
			BaseResponse<ParameterGroup> response) {
		
		// collect parameter code
		 List<String> paramCodes = new ArrayList<>();
		 for (ParameterRequest pr : request.getParamList()) {
			 paramCodes.add(pr.getParamCode());
		 }
		 // update record flag data not in request
		 parameterRepo.updateRecordFlag("admin", request.getParamGroupCd(), paramCodes);
		 
		 // get param code that deleted
		 List<String> listParamCodeDeleted = parameterRepo.getParamCodeDeleted(request.getParamGroupCd(), paramCodes);
		 
		 // update record flag setting
		 settingRepo.updateRecordFlag("admin", listParamCodeDeleted);
		 
		 // get latest line no with paramGroupCd, then +1
		 Integer lineNo = parameterRepo.getMaxLineNo(request.getParamGroupCd());
		 lineNo += 1;
		 
		 // check every parameter
		 List<Parameter> params = new ArrayList<>();
		 for (ParameterRequest pr : request.getParamList()) {
			 Parameter parameter = new Parameter();
			 // check every  parameter request in DB
			 Parameter param = parameterRepo.findById(pr.getParamCode()).orElse(null);
			 
			 parameter.setParameterGroup(checkParamGroupDb);
			 parameter.setParameterName(pr.getParamName());
			 parameter.setParameterDesc(pr.getParamDesc());
			 
			 if (param == null) {// new parameter
				 parameter.setParameterCd(pr.getParamCode());
				 parameter.setRecordFlag(Constants.FLAG_NEW);
				 parameter.setLineNo(lineNo);
				 parameter.setCreatedBy("admin");
				 parameter.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				 
				 lineNo++;
			 }
			 else if (param.getRecordFlag().equals(Constants.FLAG_DELETE)) {// parameter have delete flag
				 response.setStatus(Constants.ERROR_STATUS);
				 response.setMessage(String.format("Data with code: %s already exist with delete flag", 
						 pr.getParamCode()));
				 return response;
			 }
			 else {// exist parameter
				 parameter.setRecordFlag(Constants.FLAG_EDIT);
				 parameter.setUpdatedBy("admin");
				 parameter.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
				 parameter.setParameterCd(param.getParameterCd());
				 parameter.setLineNo(param.getLineNo());
				 parameter.setCreatedBy(param.getCreatedBy());
				 parameter.setCreatedTime(param.getCreatedTime());
			 }
			 params.add(parameter);
		 }
		 // set and save parameter group (header)
		 ParameterGroup parameterGroup = new ParameterGroup();
		 parameterGroup.setParameterGroupCd(request.getParamGroupCd());
		 parameterGroup.setParameterGroupName(request.getParamGroupName());
		 parameterGroup.setParameters(params);
		 parameterGroup.setRecordFlag(Constants.FLAG_EDIT);
		 parameterGroup.setCreatedBy(checkParamGroupDb.getCreatedBy());
		 parameterGroup.setCreatedTime(checkParamGroupDb.getCreatedTime());
		 parameterGroup.setUpdatedBy("admin");
		 parameterGroup.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		 parameterGroupRepo.save(parameterGroup);
		 
		 response.setData(parameterGroup);
		 response.setStatus(Constants.SUCCESS_STATUS);
		 response.setMessage(Constants.SUCCESS_MESSAGE);
		 return response;
	}
	
	private BaseResponse<ParameterGroup> addParameter(ParameterGroupRequest request,
			BaseResponse<ParameterGroup> response) {
		
		ParameterGroup parameterGroup = new ParameterGroup();
		Integer lineNo = 1;
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
		
		parameterGroup.setParameterGroupCd(request.getParamGroupCd().trim());
		parameterGroup.setParameterGroupName(request.getParamGroupName().trim());
		parameterGroup.setRecordFlag(Constants.FLAG_NEW);
		parameterGroup.setParameters(listParameter);
		parameterGroup.setCreatedBy("admin");
		parameterGroup.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		parameterGroupRepo.save(parameterGroup);
		
		response.setData(parameterGroup);
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setStatus(Constants.SUCCESS_STATUS);
		return response;
	}

	@Override
	public BaseResponse<DownloadResponse> downloadParameter(InputStream input, String parameterGroupCode,
			String parameterGroupName, String parameterName, String extension) {

		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		
		try {
			List<Object[]> listObjectParameter = parameterGroupRepo.searchParameterGroup(this.convertValueForLike(parameterGroupCode), 
					this.convertValueForLike(parameterGroupName), this.convertValueForLike(parameterName));
			
			if (extension.equals(Constants.TYPE_CSV)) {
    			response = generateCsv(listObjectParameter);
    		}
			else if (extension.equals(Constants.TYPE_XLSX) || extension.equals(Constants.TYPE_XLS)) {
    			response = generateExcel(input, listObjectParameter, extension);
    		}
    		else {
    			response.setMessage("extension file not recognized");
    			response.setStatus(Constants.ERROR_STATUS);
    		}
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
        	response.setStatus(Constants.SUCCESS_STATUS);
        	return response;
		}
		
		return response;
	}
	
	private BaseResponse<DownloadResponse> generateCsv(List<Object[]> listData) {
		
		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "PARAMETER GROUP CODE" + s + "PARAMETER GROUP NAME" + s + "PARAMETER DETAIL" + " \n";
		String body = "";
		
		for (int i = 0; i < listData.size(); i++) {
			Object[] obj = listData.get(i);
			body += (String)obj[0] + s + (String)obj[1] + s + (String)obj[9] + " \n";
		}
		
		csvString += header;
		csvString += body;
		
		try {
			byte[] bytes = csvString.getBytes();
	        bais = new ByteArrayInputStream(bytes);
	        bais.close();
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
			return response;
		}
       
        byte[] byteData = bais.readAllBytes();
        
        result.setBase64Data(byteData);
        result.setFileName(Constants.TEMPLATE_DATA_PARAMETER + strDate + "." + Constants.TYPE_CSV);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
		return response;
	}
	
	private BaseResponse<DownloadResponse> generateExcel(InputStream input, List<Object[]> listData, String extension) {
		
		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
        
        Workbook wb = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        
        try {
        	if (extension.equals(Constants.TYPE_XLSX)) {
        		wb = new XSSFWorkbook(input);
        	}
        	else {
        		wb = new HSSFWorkbook(input);
        	}
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_PARAMETER);

            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 5 * 256);
            sheet.setColumnWidth(2, 2 * 256);


            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 32 * 256);
            sheet.setColumnWidth(2, 64 * 256);


            // HEADER
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.MEDIUM);
            headerStyle.setBorderRight(BorderStyle.MEDIUM);
            headerStyle.setWrapText(true);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fontHeader = wb.createFont();
//    		fontHeader.setColor(HSSFColorPredefined.ORANGE.getIndex());
            fontHeader.setBold(true);
            fontHeader.setFontHeightInPoints((short) 14);
            fontHeader.setFontName("Calibri");
            headerStyle.setFont(fontHeader);

            // SET alignment Left
            CellStyle alignLeft = wb.createCellStyle();
            alignLeft.setAlignment(HorizontalAlignment.LEFT);
            alignLeft.setVerticalAlignment(VerticalAlignment.CENTER);
            alignLeft.setBorderBottom(BorderStyle.THIN);
            alignLeft.setBorderTop(BorderStyle.THIN);
            alignLeft.setBorderLeft(BorderStyle.THIN);
            alignLeft.setBorderRight(BorderStyle.THIN);
            alignLeft.setWrapText(true);


            Integer rowNumber = 0;
            Cell cell = null;
            Row row = null;


            // ADD HEADER
            rowNumber += 2;
            row = sheet.createRow(rowNumber);
            row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Parameter Group Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Parameter Group Name");

            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Parameter Detail");
            
        	// ADD BODY
            for (int i = 0; i < listData.size(); i++) {
            	Object[] obj = listData.get(i);
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue((String)obj[0]);

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue((String)obj[1]);

                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue((String)obj[9]);
            }
            
            baos = new ByteArrayOutputStream();
            wb.write(baos);
            
            bais = new ByteArrayInputStream(baos.toByteArray());
            
            bais.close();
            baos.close();
            input.close();
            wb.close();
        }
        catch (Exception e) {
        	response.setMessage(e.getMessage());
        	response.setStatus(Constants.ERROR_STATUS);
			return response;
        }
        
        byte[] byteData = bais.readAllBytes();
        
        result.setBase64Data(byteData);
        result.setFileName(Constants.TITLE_FILE_PARAMETER + strDate + "." + extension);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
        return response;
	}
}
