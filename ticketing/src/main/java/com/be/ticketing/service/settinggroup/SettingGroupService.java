package com.be.ticketing.service.settinggroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.entity.Setting;
import com.be.ticketing.entity.SettingGroup;
import com.be.ticketing.mapper.settingroup.SettingGroupMapper;
import com.be.ticketing.repository.parameter.ParameterRepository;
import com.be.ticketing.repository.setting.SettingRepository;
import com.be.ticketing.repository.settinggroup.SettingGroupRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.settinggroup.SettingGroupRequest;
import com.be.ticketing.request.settinggroup.SettingGroupSearchRequest;
import com.be.ticketing.request.settinggroup.SettingGroupUploadRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.settinggroup.SettingGroupResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class SettingGroupService extends CommonService implements ISettingGroupService {
	
	@Autowired
	private SettingGroupRepository settingGroupRepo;
	
	@Autowired
	private SettingRepository settingRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;

	@Override
	public BaseResponse<List<SettingGroupResponse>> searchSettingGroup(SettingGroupSearchRequest request) {
		
		BaseResponse<List<SettingGroupResponse>> response = new BaseResponse<List<SettingGroupResponse>>();
		List<SettingGroupResponse> results = new ArrayList<>();
		
		try {
			Page<SettingGroup> page = settingGroupRepo.search(this.convertValueForLike(request.getGroupCd()), 
					this.convertValueForLike(request.getGroupName()), this.getPageable(request));
			
			List<SettingGroup> data = page.toList();
			
			for (SettingGroup sg : data) {
				SettingGroupResponse settingGroupResponse = SettingGroupMapper.mapToResponse(sg);
				results.add(settingGroupResponse);
			}
			
			response.setData(results);
			response.setStatus(Constants.SUCCESS_STATUS);
			response.setMessage(Constants.SUCCESS_MESSAGE);
			response.setCountData(page.getTotalElements());
		}
		catch (Exception e) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@Override
	public BaseResponse<SettingGroup> addSettingGroup(SettingGroupRequest request) {

		BaseResponse<SettingGroup> response = new BaseResponse<SettingGroup>();
		
		try {
			SettingGroup existSettingGroup = settingGroupRepo.findById(request.getGroupCd()).orElse(null);
			if (existSettingGroup != null) {
				response.setMessage(String.format("Data with code: %s already exist", request.getGroupCd()));
				response.setStatus(Constants.ERROR_STATUS);
				return response;
			}
			
			SettingGroup settingGroup = SettingGroupMapper.mapToEntityAdd(request);
			settingGroupRepo.save(settingGroup);
			
			response.setStatus(Constants.SUCCESS_STATUS);
			response.setMessage(Constants.SUCCESS_MESSAGE);
			response.setData(settingGroup);
		}
		catch (Exception e) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}

	@Override
	public BaseResponse<SettingGroup> updateSettingGroup(SettingGroupRequest request) {

		BaseResponse<SettingGroup> response = new BaseResponse<SettingGroup>();
		
		try {
			SettingGroup settingGroup = settingGroupRepo.findById(request.getGroupCd()).orElse(null);
			
			if (settingGroup == null) {
				response.setStatus(Constants.ERROR_STATUS);
				response.setMessage(Constants.MESSAGE_DATANOTFOUND);
				
				return response;
			}
			
			if (settingGroup != null && settingGroup.getRecordFlag().equals(Constants.FLAG_DELETE)) {
				response.setStatus(Constants.ERROR_STATUS);
				response.setMessage(String.format("Data with code: %s already exist with delete flag", 
						settingGroup.getSettingGroupCd()));
				
				return response;
			}
			
			SettingGroup settingGroupWithMap = SettingGroupMapper.mapToEntityUpdate(settingGroup, request, false);
			settingGroupRepo.save(settingGroupWithMap);
			
			response.setStatus(Constants.SUCCESS_STATUS);
			response.setMessage(Constants.SUCCESS_MESSAGE);
			response.setData(settingGroup);
		}
		catch (Exception e) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(e.getMessage());
			
			return response;
		}
		
		return response;
	}

	@Override
	public BaseResponse<List<String>> deleteSettingGroup(ListCodeRequest request) {
		
		// check list code
		ValidationUtil.checkListCode(request);

		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> results = new ArrayList<>();
		SettingGroup settingGroup = new SettingGroup();
		
		try {
			for (Code i : request.getListCode()) {
				settingGroup = settingGroupRepo.findById(i.getCode()).orElse(null);
				
				if (settingGroup == null) {
					results.add(String.format("Data %s not found", i.getCode()));
				}
				else {
					SettingGroup settingGroupWithMap = SettingGroupMapper.mapToEntityUpdate(settingGroup, null, true);
					settingGroupRepo.save(settingGroupWithMap);
					
					// update flag setting to 'D'
					List<Object[]> settingGroups = settingRepo.search("", settingGroupWithMap.getSettingGroupCd(), "", "");
					for (int j = 0; j < settingGroups.size(); j++) {
						Object[] obj = settingGroups.get(j);
						Setting setting = new Setting();
						
						setting.setSettingCd((String)obj[0]);
						SettingGroup sg = settingGroupRepo.findById((String)obj[1]).orElse(null);
						setting.setSettingGroup(sg);
						setting.setSettingDescription((String)obj[2]);
						setting.setSettingValue((String)obj[3]);
						Parameter p = parameterRepo.findById((String)obj[4]).orElse(null);
						setting.setParameter(p);
						setting.setRecordFlag(Constants.FLAG_DELETE);
						setting.setCreatedBy((String)obj[6]);
						setting.setCreatedTime((Date)obj[7]);
						setting.setUpdatedBy("admin");
						setting.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
						
						settingRepo.save(setting);
					}
					
					results.add(String.format("Delete %s success", i.getCode()));
				}
			}
		}
		catch (Exception e) {
			response.setStatus(Constants.ERROR_STATUS);
			response.setMessage(e.getMessage());
			return response;
		}
		
		response.setStatus(Constants.SUCCESS_STATUS);
		response.setMessage(Constants.SUCCESS_MESSAGE);
		response.setData(results);
		
		return response;
	}

	@Override
	public DownloadResponse downloadTemplateSettingGroup() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_SETTING_GROUP);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_SETTING_GROUP);
	}

	@Override
	public BaseResponse<DownloadResponse> downloadSettingGroup(InputStream input, String settingGroupCode,
			String settingGroupName, String extension) {

		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
        
        try {
        	List<SettingGroup> settingGroups = settingGroupRepo.searchByCodeOrByName(this.convertValueForLike(settingGroupCode), 
    				this.convertValueForLike(settingGroupName));
    		
    		if (extension.equals(Constants.TYPE_CSV)) {
    			response = generateCsv(settingGroups);
    		}
    		else if (extension.equals(Constants.TYPE_XLSX) || extension.equals(Constants.TYPE_XLS)) {
    			response = generateExcel(input, settingGroups, extension);
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
	
	private BaseResponse<DownloadResponse> generateCsv(List<SettingGroup> listData) {
		
		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "SETTING GROUP CODE" + s + "SETTING GROUP NAME" + s + "DESCRIPTION" + " \n";
		String body = "";
		
		for (SettingGroup sg : listData) {
			body += sg.getSettingGroupCd() + s + sg.getSettingGroupName() + s + sg.getSettingGroupDesc() + " \n";
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
	
	private BaseResponse<DownloadResponse> generateExcel(InputStream input, List<SettingGroup> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_SETTING_GROUP);

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
            cell.setCellValue("Setting Group Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Setting Group Name");

            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Setting Group Desc");
            
        	// ADD BODY
            for (SettingGroup sg : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(sg.getSettingGroupCd());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(sg.getSettingGroupName());

                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(sg.getSettingGroupDesc());
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
        result.setFileName(Constants.TITLE_FILE_SETTING_GROUP + strDate + "." + extension);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
        return response;
	}
	
	@Override
	public BaseResponse<List<String>> uploadSettingGroup(MultipartFile fileUpload) {

		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> result = new ArrayList<>();
		
		try {
			List<SettingGroupUploadRequest> settingGroups = getDataFromExcel(fileUpload.getInputStream());
			if (settingGroups == null || settingGroups.isEmpty()) {
				response.setMessage("No data in excel file");
				response.setStatus(Constants.ERROR_STATUS);
				return response;
			}
			
			for (SettingGroupUploadRequest sg : settingGroups) {
				SettingGroup settingGroupResult = settingGroupRepo.findById(sg.getSettingGroupCode()).orElse(null);
				SettingGroup settingGroup = new SettingGroup();
				
				if (settingGroupResult == null) {
					settingGroup = SettingGroupMapper.mapToEntityUpload(settingGroup, sg, false);
					settingGroupRepo.save(settingGroup);
					
					result.add(String.format("New data %s uploaded", sg.getSettingGroupCode()));
				}
				else if (settingGroupResult != null && 
						settingGroupResult.getRecordFlag().equals(Constants.FLAG_DELETE)) {
					result.add(String.format("Data %s already exist with delete flag", sg.getSettingGroupCode()));
				}
				else {
					settingGroupResult = SettingGroupMapper.mapToEntityUpload(settingGroupResult, sg, true);
					settingGroupRepo.save(settingGroupResult);
					
					result.add(String.format("Data %s updated", sg.getSettingGroupCode()));
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
	
	private static List<SettingGroupUploadRequest> getDataFromExcel(InputStream is) throws Exception {
        
		try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<SettingGroupUploadRequest> settingGroupList = new ArrayList<>();

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
                SettingGroupUploadRequest settingGroupExcel = new SettingGroupUploadRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                            settingGroupExcel.setSettingGroupCode(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                            settingGroupExcel.setSettingGroupName(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 2:
                            settingGroupExcel.setSettingGroupDesc(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                settingGroupList.add(settingGroupExcel);
            }
            
            wb.close();
            
            return settingGroupList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("fail to parse Excel file : " + e.getMessage());
        }
    }
	
}
