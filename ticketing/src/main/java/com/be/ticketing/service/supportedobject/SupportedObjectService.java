package com.be.ticketing.service.supportedobject;

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
import com.be.ticketing.dao.supportedobject.ISupportedObjectDao;
import com.be.ticketing.entity.SupportedObject;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.repository.supportedobject.SupportedObjectRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.supportedobject.SupportedObjectRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.supportedobject.GetCompanyResponse;
import com.be.ticketing.response.supportedobject.GetObjectTypeResponse;
import com.be.ticketing.response.supportedobject.SupportedObjectResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class SupportedObjectService extends CommonService implements ISupportedObjectService {
	
	@Autowired
	private ISupportedObjectDao supportedObjectDao;
	
	@Autowired
	private SupportedObjectRepository supportedObjectRepo;

	@Override
	public List<GetObjectTypeResponse> getObjectTypeSupportedObject() {

		List<GetObjectTypeResponse> results = supportedObjectDao.getObjectTypeSupportedObject();
		
		return results;
	}

	@Override
	public List<GetCompanyResponse> getCompanySupportedObject() {

		List<GetCompanyResponse> results = supportedObjectDao.getCompanySupportedObject();
		
		return results;
	}

	@Override
	public SupportedObject insert(SupportedObjectRequest request) {

		// check request
		ValidationUtil.checkMandatory(request);
		
		// check code
		String soCode = supportedObjectRepo.findCode(request.getObjectCode());
		if (soCode != null) {
			throw new BadRequestException(String.format(Constants.EXIST_MESSAGE, "Supported object", soCode));
		}
		
		// check type
		String soType = supportedObjectRepo.findType(request.getObjectType());
		if (soType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Object type",
					request.getObjectType()));
		}
		
		// check company id
		Long companyId = supportedObjectRepo.findCompany(Long.parseLong(request.getCompanyId()));
		if (companyId == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company",
					request.getCompanyId()));
		}
		
		// call repository
		supportedObjectRepo.insert(request.getObjectCode(), request.getObjectName(), 
				soType, request.getDescription(), companyId);
		
		Long soId = supportedObjectRepo.findId(request.getObjectCode());
		SupportedObject so = supportedObjectRepo.findById(soId).orElse(null);
		
		return so;
	}

	@Override
	public SupportedObject update(SupportedObjectRequest request) {

		// check request
		ValidationUtil.checkMandatory(request);
		
		// check code
		String soCode = supportedObjectRepo.findCode(request.getObjectCode());
		if (soCode == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Supported object", soCode));
		}
		
		// check type
		String soType = supportedObjectRepo.findType(request.getObjectType());
		if (soType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Object type",
					request.getObjectType()));
		}
		
		// check company id
		Long companyId = supportedObjectRepo.findCompany(Long.parseLong(request.getCompanyId()));
		if (companyId == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company",
					request.getCompanyId()));
		}
		
		// call repository
		supportedObjectRepo.update(request.getObjectCode(), request.getObjectName(), 
				soType, request.getDescription(), companyId);
		
		Long soId = supportedObjectRepo.findId(request.getObjectCode());
		SupportedObject so = supportedObjectRepo.findById(soId).orElse(null);
		
		return so;
	}

	@Override
	public List<String> delete(ListCodeRequest request) {

		// check list code
		ValidationUtil.checkListCode(request);
		
		List<String> results = new ArrayList<>();
		
		// loop list code
		for (Code c : request.getListCode()) {
			
			Long soId = supportedObjectRepo.findId(c.getCode());
			if (soId == null) {
				results.add(String.format(Constants.NOTFOUND_MESSAGE, "Supported object", c.getCode()));
			}
			else {
				supportedObjectRepo.updateDelete(c.getCode(), "admin");
				results.add(String.format(Constants.DELETE_SUCCESS_MESSAGE, "Supported object", c.getCode()));
			}
		}
		
		return results;
	}

	@Override
	public SearchResponse<SupportedObjectResponse> search(SupportedObjectRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search through dao
		List<SupportedObjectResponse> listData = supportedObjectDao.search(request);
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listData.size());
		Page<SupportedObjectResponse> page = new PageImpl<>(listData.subList(start, end), pageable, 
				listData.size());
		
		return this.createSearchResponse(page);
	}

	@Override
	public DownloadResponse downloadTemplate() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_SUPPORTED_OBJECT);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_SUPPORTED_OBJECT);
	}

	@Override
	public List<String> upload(MultipartFile fileUpload) {

		// check extension file
		ValidationUtil.checkExtentionFile(fileUpload);
		
		List<String> results = new ArrayList<>();
		
		try {
			// get data from excel
			List<SupportedObjectRequest> listData = getDataFromExcel(fileUpload.getInputStream());
			
			// check data from excel
			if (listData == null || listData.isEmpty()) {
				results.add(Constants.EMPTY_EXCEL_MESSAGE);
				return results;
			}
			
			for (SupportedObjectRequest s : listData) {
				
				Long soId = supportedObjectRepo.findId(s.getObjectCode());
				if (soId == null) {
					results = uploadNewData(s, results);
				}
				else {
					results = uploadExistData(s, results, soId);
				}
			}
		}
		catch (Exception e) {
		}
		
		return results;
	}
	
	private List<String> uploadNewData(SupportedObjectRequest s, List<String> results) {
		
		// check code
		String soCode = supportedObjectRepo.findCode(s.getObjectCode());
		if (soCode != null) {
			results.add(String.format(Constants.EXIST_MESSAGE, "Supported object", soCode));
			return results;
		}
		
		// check type
		String soType = supportedObjectRepo.findType(s.getObjectType());
		if (soType == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Object type",
					s.getObjectType()));
			return results;
		}
		
		// check company id
		Long companyId = supportedObjectRepo.findCompanyByCode(s.getCompanyCode());
		if (companyId == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company",
					s.getCompanyCode()));
			return results;
		}
		
		// call repository
		supportedObjectRepo.insert(s.getObjectCode(), s.getObjectName(), 
				soType, s.getDescription(), companyId);
		
		results.add(String.format(Constants.UPLOAD_NEWDATA_MESSAGE, s.getObjectCode()));
		
		return results;
	}
	
	private List<String> uploadExistData(SupportedObjectRequest s, List<String> results, Long soId) {
		
		// check if deleted
		SupportedObject suppObject = supportedObjectRepo.findById(soId).orElse(null);
		if (suppObject.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			results.add(String.format(Constants.WASDELETE_MESSAGE, "Supported object", s.getObjectCode()));
			return results;
		}
		
		// check code
		String soCode = supportedObjectRepo.findCode(s.getObjectCode());
		if (soCode == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Supported object", soCode));
			return results;
		}
		
		// check type
		String soType = supportedObjectRepo.findType(s.getObjectType());
		if (soType == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Object type",
					s.getObjectType()));
			return results;
		}
		
		// check company id
		Long companyId = supportedObjectRepo.findCompanyByCode(s.getCompanyCode());
		if (companyId == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company",
					s.getCompanyCode()));
			return results;
		}
		
		// call repository
		supportedObjectRepo.update(s.getObjectCode(), s.getObjectName(), 
				soType, s.getDescription(), companyId);
		
		results.add(String.format(Constants.UPLOAD_EXISTDATA_MESSAGE, s.getObjectCode()));
		
		return results;
	}
	
	private List<SupportedObjectRequest> getDataFromExcel(InputStream is) {
        
		List<SupportedObjectRequest> listData = new ArrayList<>();
		
		try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            

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
                SupportedObjectRequest suppObject = new SupportedObjectRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                        	suppObject.setObjectCode(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                        	suppObject.setObjectName(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 2:
                        	suppObject.setObjectType(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 3:
                        	suppObject.setDescription(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 4:
                        	suppObject.setCompanyCode(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                listData.add(suppObject);
            }
            
            wb.close();
            
            
        } 
        catch (Exception e) {
        }
		
		return listData;
    }

	@Override
	public DownloadResponse download(String objectType, String objectName, String companyName, String extention) {

		// check extension
		ValidationUtil.checkExtentionFile2(extention);
		
		// search data
		List<SupportedObjectResponse> listData = supportedObjectDao.download(objectType, objectName, companyName);
		
		DownloadResponse result = new DownloadResponse();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(Constants.TEMPLATE_DATA_SUPPORTED_OBJECT + extention);
		
		// check type extension
		if (extention.equals(Constants.TYPE_CSV)) {
			result = generateCsv(listData);
		}
		else if (extention.equals(Constants.TYPE_XLSX) || extention.equals(Constants.TYPE_XLS)) {
			result = generateExcel(is, listData, extention);
		}
		else {
			throw new BadRequestException("extension file not recognized");
		}
		
		return result;
	}
	
	private DownloadResponse generateCsv(List<SupportedObjectResponse> listData) {
		
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "SUPPORTED OBJECT CODE" + s + "SUPPORTED OBJECT NAME" + s + "SUPPORTED OBJECT TYPE" + s + "DESCRIPTION" + s + "COMPANY" + " \n";
		String body = "";
		
		for (SupportedObjectResponse c : listData) {
			body += c.getObjectCode() + s + c.getObjectName() + s + c.getObjectTypeName() + s + c.getDescription() + s + c.getCompanyName() + " \n";
		}
		
		csvString += header;
		csvString += body;
		
		try {
			byte[] bytes = csvString.getBytes();
	        bais = new ByteArrayInputStream(bytes);
	        bais.close();
		}
		catch (Exception e) {
		}
       
        byte[] byteData = bais.readAllBytes();
        
        result.setBase64Data(byteData);
        result.setFileName(Constants.TITLE_FILE_SUPPORTED_OBJECT + strDate + "." + Constants.TYPE_CSV);
        
		return result;
	}
	
	private DownloadResponse generateExcel(InputStream input, List<SupportedObjectResponse> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_SUPPORTED_OBJECT);

            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 5 * 256);
            sheet.setColumnWidth(2, 5 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 5 * 256);


            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 32 * 256);
            sheet.setColumnWidth(2, 32 * 256);
            sheet.setColumnWidth(3, 32 * 256);
            sheet.setColumnWidth(4, 32 * 256);


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
            cell.setCellValue("SUPPORTED OBJECT CODE");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("SUPPORTED OBJECT NAME");
            
            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("SUPPORTED OBJECT TYPE");
            
            cell = row.createCell(3);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("DESCRIPTION");
            
            cell = row.createCell(4);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("COMPANY");
            
        	// ADD BODY
            for (SupportedObjectResponse c : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getObjectCode());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getObjectName());
                
                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getObjectTypeName());
                
                cell = row.createCell(3);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getDescription());
                
                cell = row.createCell(4);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCompanyName());
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
        }
        
        byte[] byteData = bais.readAllBytes();
        
        result.setBase64Data(byteData);
        result.setFileName(Constants.TITLE_FILE_SUPPORTED_OBJECT + strDate + "." + extension);
        
        return result;
	}
	
	
}
