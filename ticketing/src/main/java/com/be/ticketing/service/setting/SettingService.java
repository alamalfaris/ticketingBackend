package com.be.ticketing.service.setting;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.entity.Setting;
import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.mapper.setting.SettingMapper;
import com.be.ticketing.repository.parameter.ParameterRepository;
import com.be.ticketing.repository.setting.SettingRepository;
import com.be.ticketing.repository.settinggroup.SettingGroupRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.setting.SettingRequest;
import com.be.ticketing.request.setting.SettingSearchRequest;
import com.be.ticketing.request.setting.SettingUploadRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.setting.ParameterCdResponse;
import com.be.ticketing.response.setting.SettingGroupCdResponse;
import com.be.ticketing.response.setting.SettingResponse;
import com.be.ticketing.response.setting.SettingSearchResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class SettingService extends CommonService implements ISettingService {
	
	@Autowired
	private SettingGroupRepository settingGroupRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;
	
	@Autowired
	private SettingRepository settingRepo;

	@Override
	public List<SettingGroupCdResponse> getSettingGroupCode() {
		
		List<SettingGroupCdResponse> listData = new ArrayList<>();
		
		List<SettingGroup> settingGroupCodes = settingGroupRepo.getSettingGroupCode();
		if (settingGroupCodes == null || settingGroupCodes.isEmpty()) {
			throw new BadRequestException("No one setting group code found");
		}
		
		for (SettingGroup sg : settingGroupCodes) {
			SettingGroupCdResponse settingGroupCdResponse = new SettingGroupCdResponse();
			settingGroupCdResponse.setSettingGroupCd(sg.getSettingGroupCd());
			settingGroupCdResponse.setSettingGroupName(sg.getSettingGroupName());
			
			listData.add(settingGroupCdResponse);
		}
		return listData;
	}

	@Override
	public List<ParameterCdResponse> getSettingValueType() {

		List<ParameterCdResponse> listData = new ArrayList<>();
		
		List<Parameter> parameters = parameterRepo.getParameterCode();
		if (parameters == null || parameters.isEmpty()) {
			throw new BadRequestException("No one parameter code found");
		}
		
		for (Parameter p : parameters) {
			ParameterCdResponse parameterCdResponse = new ParameterCdResponse();
			parameterCdResponse.setSettingValueType(p.getParameterName());
			parameterCdResponse.setParamCode(p.getParameterCd());
			
			listData.add(parameterCdResponse);
		}
		
		return listData;
	}
	
	@Override
	public SettingResponse insertSetting(SettingRequest request) {
		
		// check mandatory
		ValidationUtil.validationAddEditSetting(request);

		SettingResponse settingResponse = new SettingResponse();
		Setting setting = new Setting();
		
		Setting existSetting = settingRepo.findById(request.getSettingCode()).orElse(null);
		if (existSetting != null) {
			throw new BadRequestException(String.format("Data with code: %s already exist", request.getSettingCode()));
		}
		
		SettingGroup settingGroup = settingGroupRepo.findById(request.getSettingGroupCode()).orElse(null);
		Parameter parameter = parameterRepo.findById(request.getSettingValueType()).orElse(null);
		if (settingGroup == null || parameter == null) {
			throw new BadRequestException(Constants.MESSAGE_SETTINGGROUPCODE_PARAMETERCODE);
		}
		
		setting = SettingMapper.mapToEntity(null, request, settingGroup, parameter, false);
		settingRepo.save(setting);
		
		settingResponse = SettingMapper.mapToResponse(request, setting, false);
		
		return settingResponse;
	}

	@Override
	public SettingResponse updateSetting(SettingRequest request) {
		
		// check mandatory
		ValidationUtil.validationAddEditSetting(request);

		SettingResponse settingResponse = new SettingResponse();
		
		Setting setting = settingRepo.findById(request.getSettingCode()).orElse(null);
		if (setting == null) {
			throw new BadRequestException(String.format("Data with code: %s not found", request.getSettingCode()));
		}
		
		if (setting != null && setting.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			throw new BadRequestException(String.format("Data with code: %s already exist with delete flag", setting.getSettingCd()));
		}
		
		SettingGroup settingGroup = settingGroupRepo.findById(request.getSettingGroupCode()).orElse(null);
		Parameter parameter = parameterRepo.findById(request.getSettingValueType()).orElse(null);
		if (settingGroup == null || parameter == null) {
			throw new BadRequestException(Constants.MESSAGE_SETTINGGROUPCODE_PARAMETERCODE);
		}
		
		setting = SettingMapper.mapToEntity(setting, request, settingGroup, parameter, true);
		settingRepo.save(setting);
		
		settingResponse = SettingMapper.mapToResponse(request, setting, true);
		
		return settingResponse;
	}

	@Override
	public BaseResponse<List<SettingSearchResponse>> searchSetting(SettingSearchRequest request) {

		var response = new BaseResponse<List<SettingSearchResponse>>();
		List<SettingSearchResponse> listData = new ArrayList<>();
		
		try {
			List<Object[]> searchResults = settingRepo.search(this.convertValueForLike(request.getSettingCode()), 
					this.convertValueForLike(request.getSettingGroup()), 
					this.convertValueForLike(request.getValue()), "");
			
			for (int i = 0; i < searchResults.size(); i++) {
				Object[] obj = searchResults.get(i);
				SettingSearchResponse settingResponse = SettingMapper.mapToSearchResponse(obj);
				
				listData.add(settingResponse);
			}
			
			Pageable pageable = this.getPageable(request);
			final int start = (int) pageable.getOffset();
			final int end = Math.min(start + pageable.getPageSize(), listData.size());
			Page<SettingSearchResponse> page = new PageImpl<>(listData.subList(start, end), 
					pageable, listData.size());
			List<SettingSearchResponse> data = page.toList();
			
			response.setData(data);
			response.setCountData(page.getTotalElements());
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
			return response;
		}
		
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setStatus(Constants.SUCCESS_STATUS);
		return response;
	}

	@Override
	public List<String> deleteSetting(ListCodeRequest request) {
		
		// check list code
		ValidationUtil.checkListCode(request);

		List<String> results = new ArrayList<>();
		
		for (Code i : request.getListCode()) {
			Setting setting = settingRepo.findById(i.getCode()).orElse(null);
			
			if (setting == null) {
				results.add(String.format("Data %s not found", i.getCode()));
			}
			else {
				setting = SettingMapper.mapToEntityDelete(setting);
				settingRepo.save(setting);
				
				results.add(String.format("Delete %s success", i.getCode()));
			}
		}
		
		return results;
	}

	@Override
	public DownloadResponse downloadTemplateSetting() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_SETTING);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_SETTING);
	}

	@Override
	public BaseResponse<List<String>> uploadSetting(MultipartFile fileUpload) {

		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> result = new ArrayList<>();
		
		try {
			List<SettingUploadRequest> settings = getDataFromExcel(fileUpload.getInputStream());
			if (settings == null || settings.isEmpty()) {
				response.setMessage("No data in excel file");
				response.setStatus(Constants.ERROR_STATUS);
				return response;
			}
			
			for (SettingUploadRequest s : settings) {
				Setting settingResult = settingRepo.findById(s.getSettingCode()).orElse(null);
				Setting setting = new Setting();
				
				if (settingResult == null) {//add new data
					SettingGroup settingGroup = settingGroupRepo.findById(s.getSettingGroupCode()).orElse(null);
					Parameter parameter = parameterRepo.findById(s.getDataType()).orElse(null);
					if (settingGroup == null || parameter == null) {
						response.setMessage(Constants.MESSAGE_SETTINGGROUPCODE_PARAMETERCODE);
						response.setStatus(Constants.ERROR_STATUS);
						return response;
					}
					
					setting = SettingMapper.mapToEntityUpload(setting, s, settingGroup, parameter, false);
					settingRepo.save(setting);
					result.add(String.format("New data %s uploaded", s.getSettingCode()));
				}
				else if (settingResult != null && settingResult.getRecordFlag().equals(Constants.FLAG_DELETE)) {
					result.add(String.format("Setting with code: %s already exist with delete flag", s.getSettingCode()));
				}
				else {//update exist data
					SettingGroup settingGroup = settingGroupRepo.findById(s.getSettingGroupCode()).orElse(null);
					Parameter parameter = parameterRepo.findById(s.getDataType()).orElse(null);
					if (settingGroup == null || parameter == null) {
						response.setMessage(Constants.MESSAGE_SETTINGGROUPCODE_PARAMETERCODE);
						response.setStatus(Constants.ERROR_STATUS);
						return response;
					}
					
					settingResult = SettingMapper.mapToEntityUpload(settingResult, s, settingGroup, parameter, true);
					settingRepo.save(settingResult);
					result.add(String.format("Data %s updated", s.getSettingCode()));
				}
			}
			
			response.setMessage(Constants.SUCCESS_MESSAGE);
			response.setStatus(Constants.SUCCESS_STATUS);
			response.setData(result);
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
		}
		
		return response;
	}
	
	private static List<SettingUploadRequest> getDataFromExcel(InputStream is) throws Exception {
        
		try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<SettingUploadRequest> settingList = new ArrayList<>();

            int rowNumber = 0;

            while(rows.hasNext()){
                Row currentRow = (Row) rows.next();

                //SKIP HEADER
                if(rowNumber == 0){
                    rowNumber++;
                    continue;
                }

                //DATA
                Iterator<Cell> cellInRow = currentRow.iterator();
                SettingUploadRequest settingExcel = new SettingUploadRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                        	settingExcel.setSettingCode(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                        	settingExcel.setSettingGroupCode(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 2:
                        	settingExcel.setDescription(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 3:
                        	settingExcel.setDataType(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 4:
                        	settingExcel.setValue(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                settingList.add(settingExcel);
            }
            
            wb.close();
            
            return settingList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("fail to parse Excel file : " + e.getMessage());
        }
    }
	
	@Override
	public BaseResponse<DownloadResponse> downloadSetting(InputStream input, String settingGroup, String settingName,
			String value, String extension) {

		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		
		try {
			List<Object[]> searchResults = settingRepo.search(this.convertValueForLike(settingGroup), 
					this.convertValueForLike(settingName), 
					this.convertValueForLike(value), "");
			
			if (extension.equals(Constants.TYPE_CSV)) {
    			response = generateCsv(searchResults);
    		}
			else if (extension.equals(Constants.TYPE_XLSX) || extension.equals(Constants.TYPE_XLS)) {
    			response = generateExcel(input, searchResults, extension);
    		}
			else {
    			response.setMessage("extension file not recognized");
    			response.setStatus(Constants.ERROR_STATUS);
    		}
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
        	response.setStatus(Constants.ERROR_STATUS);
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
		String header = "SETTING GROUP" + s + "SETTING GROUP CODE" + s + "DESCRIPTION" + s + "DATA TYPE" + s + "VALUE" + " \n";
		String body = "";
		
		for (int i = 0; i < listData.size(); i++) {
			Object[] obj = listData.get(i);
			body += (String)obj[0] + s + (String)obj[1] + s + (String)obj[2] + s + (String)obj[4] + s + (String)obj[3] + " \n";
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
        result.setFileName(Constants.TITLE_FILE_SETTING_GROUP + strDate + "." + Constants.TYPE_CSV);
        
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_SETTING);

            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 5 * 256);
            sheet.setColumnWidth(2, 2 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 2 * 256);


            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 32 * 256);
            sheet.setColumnWidth(2, 64 * 256);
            sheet.setColumnWidth(3, 32 * 256);
            sheet.setColumnWidth(4, 64 * 256);


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
            cell.setCellValue("Setting Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Setting Group Code");

            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Description");
            
            cell = row.createCell(3);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Type");
            
            cell = row.createCell(4);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Value");
            
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
                cell.setCellValue((String)obj[2]);
                
                cell = row.createCell(3);
                cell.setCellStyle(alignLeft);
                cell.setCellValue((String)obj[4]);
                
                cell = row.createCell(4);
                cell.setCellStyle(alignLeft);
                cell.setCellValue((String)obj[3]);
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
        result.setFileName(Constants.TITLE_FILE_SETTING + strDate + "." + extension);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
        return response;
	}
}
