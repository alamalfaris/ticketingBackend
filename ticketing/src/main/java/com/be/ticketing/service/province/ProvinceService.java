package com.be.ticketing.service.province;

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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.entity.Province;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.mapper.province.ProvinceMapper;
import com.be.ticketing.repository.city.CityRepository;
import com.be.ticketing.repository.province.ProvinceRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.province.ProvinceRequest;
import com.be.ticketing.response.BaseResponse;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.province.ProvinceSearchResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class ProvinceService extends CommonService implements IProvinceService {
	
	@Autowired
	private ProvinceRepository provinceRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	private void checkMandatory(ProvinceRequest request) {
		
		if (request.getProvinceCd().trim().isEmpty() ||
				request.getProvinceCd().trim() == null || request.getProvinceCd().length() > 8) {
			throw new BadRequestException("Province Code must filled and less than 9 character");
		}
		if (request.getProvinceName().trim().isEmpty() ||
				request.getProvinceName().trim() == null || request.getProvinceName().length() > 64) {
			throw new BadRequestException("Province Name must filled and less than 65 character");
		}
	}

	@Override
	public Province insertProvince(ProvinceRequest request) {

		// check mandatory
		checkMandatory(request);
		
		// check province code
		String checkCode = provinceRepo.searchCode(request.getProvinceCd());
		if (checkCode != null) {
			throw new BadRequestException(String.format("Data with code: %s already exist", request.getProvinceCd()));
		}
		
		// check province name
		String checkName = provinceRepo.searchName(request.getProvinceName());
		if (checkName != null) {
			throw new BadRequestException(String.format("Data with name: %s already exist", request.getProvinceName()));
		}
		
		// set to entity
		Province province = new Province();
		
		province.setProvinceCode(request.getProvinceCd());
		province.setProvinceName(request.getProvinceName());
		
		this.setCreate(province, "admin");
		
		provinceRepo.save(province);
		
		return province;
	}
	
	@Override
	public Province updateProvince(ProvinceRequest request) {

		// check mandatory
		checkMandatory(request);
		
		// check province name
		String checkName = provinceRepo.searchName(request.getProvinceName());
		if (checkName != null) {
			throw new BadRequestException(String.format("Data with name: %s already exist", request.getProvinceName()));
		}
		
		//check province code
		String checkCode = provinceRepo.searchCode(request.getProvinceCd());
		if (checkCode == null) {
			throw new BadRequestException(String.format("Data with code: %s not exist", request.getProvinceName()));
		}
		
		// findById
		Long provinceId = provinceRepo.searchId(request.getProvinceCd());
		
		Province province = provinceRepo.findById(provinceId).orElse(null);
		
		province.setProvinceCode(request.getProvinceCd());
		province.setProvinceName(request.getProvinceName());
		
		this.setUpdate(province, "admin");
		
		provinceRepo.save(province);
		
		return province;
	}
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	
	@Override
	public SearchResponse<ProvinceSearchResponse> searchProvince(ProvinceRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search province
		Page<Province> page = provinceRepo.search(this.convertValueForLike(request.getProvinceCd()), 
				this.convertValueForLike(request.getProvinceName()), this.getPageable(request));
		
		List<Province> listProvince = page.toList();
		
		// set to response
		List<ProvinceSearchResponse> listResponse = new ArrayList<>();
		
		if (listProvince.size() > 0) {
			for (Province p : listProvince) {
				ProvinceSearchResponse psr = new ProvinceSearchResponse();
				
				psr.setProvinceCd(p.getProvinceCode());
				psr.setProvinceName(p.getProvinceName());
				psr.setCreatedBy(p.getCreatedBy());
				psr.setCreatedTime(dateFormatter.format(p.getCreatedTime()));
				psr.setUpdatedBy(p.getUpdatedBy() != null ? p.getUpdatedBy() : "");
				psr.setUpdatedTime(p.getUpdatedTime() != null ? dateFormatter.format(p.getUpdatedTime()) : "");
				
				listResponse.add(psr);
			}
		}
		
		return this.createSearchResponse2(listResponse, page.getTotalElements());
	}
	
	@Override
	public List<String> deleteProvince(ListCodeRequest request) {
		
		// check list code
		ValidationUtil.checkListCode(request);

		List<String> results = new ArrayList<>();
		
		// check list code
		if (request.getListCode().size() > 0) {
			for (Code c : request.getListCode()) {
				
				// check code in DB
				Long provinceId = provinceRepo.searchId(c.getCode());
				if (provinceId == null) {
					results.add(String.format("Data with code: %s not exist", c.getCode()));
				}
				// if code exist
				else {
					// update delete flag
					provinceRepo.updateDeleteProvince(provinceId);
					
					// update delete flag city
					cityRepo.updateDeleteByProvince(provinceId);
					
					results.add(String.format("Data with code: %s deleted", c.getCode()));
				}
			}
		}
		else {
			throw new BadRequestException("List code size 0");
		}
		
		return results;
	}

	@Override
	public DownloadResponse downloadTemplateProvince() {
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_PROVINCE);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_PROVINCE);
	}
	
	@Override
	public BaseResponse<List<String>> uploadProvince(MultipartFile fileUpload) {
		
		BaseResponse<List<String>> response = new BaseResponse<List<String>>();
		List<String> result = new ArrayList<>();
		
		try {
			List<ProvinceRequest> provinces = getDataFromExcel(fileUpload.getInputStream());
			if (provinces == null || provinces.isEmpty()) {
				response.setMessage("No data in excel file");
				response.setStatus(Constants.ERROR_STATUS);
				return response;
			}
			
			for (ProvinceRequest p : provinces) {
				Province province = new Province();
				Long provinceId = provinceRepo.searchId(p.getProvinceCd());
				if (provinceId == null) {
					province = ProvinceMapper.mapToEntityAdd(p);
					provinceRepo.save(province);
					
					result.add(String.format("New data %s uploaded", p.getProvinceCd()));
				}
				else {
					Province provinceResult = provinceRepo.findById(provinceId).orElse(null);
					if (provinceResult != null && provinceResult.getRecordFlag().equals(Constants.FLAG_DELETE)) {
						result.add(String.format("Province with code: %s already exist with delete flag", p.getProvinceCd()));
					}
					else if (provinceResult != null) {
						String provinceName = provinceRepo.searchName(p.getProvinceName());
						if (provinceName != null) {
							result.add(String.format("Province with name: %s already exist", p.getProvinceName()));
						}
						else {
							provinceResult = ProvinceMapper.mapToEntityUpdate(provinceResult, p, false);
							provinceRepo.save(provinceResult);
							
							result.add(String.format("Data %s updated", provinceResult.getProvinceCode()));
						}
					}
				}
				response.setMessage(Constants.SUCCESS_MESSAGE);
				response.setData(result);
				response.setStatus(Constants.SUCCESS_STATUS);
			}
			
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(Constants.ERROR_STATUS);
		}
		return response;
	}
	
	private static List<ProvinceRequest> getDataFromExcel(InputStream is) throws Exception {
        
		try {
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<ProvinceRequest> provinceList = new ArrayList<>();

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
                ProvinceRequest province = new ProvinceRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                        	province.setProvinceCd(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                        	province.setProvinceName(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                provinceList.add(province);
            }
            
            wb.close();
            
            return provinceList;
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("fail to parse Excel file : " + e.getMessage());
        }
    }
	
	@Override
	public BaseResponse<DownloadResponse> downloadProvince(InputStream input, String provinceCode,
			String provinceName, String extension) {

		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
        
        try {
        	List<Province> provinces = provinceRepo.searchByCodeOrByName(this.convertValueForLike(provinceCode), 
    				this.convertValueForLike(provinceName));
    		
    		if (extension.equals(Constants.TYPE_CSV)) {
    			response = generateCsv(provinces);
    		}
    		else if (extension.equals(Constants.TYPE_XLSX) || extension.equals(Constants.TYPE_XLS)) {
    			response = generateExcel(input, provinces, extension);
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
	
	private BaseResponse<DownloadResponse> generateCsv(List<Province> listData) {
		
		BaseResponse<DownloadResponse> response = new BaseResponse<DownloadResponse>();
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "PROVINCE CODE" + s + "PROVINCE NAME" + " \n";
		String body = "";
		
		for (Province p : listData) {
			body += p.getProvinceCode() + s + p.getProvinceName() + " \n";
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
        result.setFileName(Constants.TITLE_FILE_PROVINCE + strDate + "." + Constants.TYPE_CSV);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
		return response;
	}
	
	private BaseResponse<DownloadResponse> generateExcel(InputStream input, List<Province> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_PROVINCE);

            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 5 * 256);


            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 32 * 256);


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
            cell.setCellValue("Province Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Province Name");
            
        	// ADD BODY
            for (Province p : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(p.getProvinceCode());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(p.getProvinceName());
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
        result.setFileName(Constants.TITLE_FILE_PROVINCE + strDate + "." + extension);
        
        response.setMessage(Constants.SUCCESS_MESSAGE);
        response.setStatus(Constants.SUCCESS_STATUS);
        response.setData(result);
        
        return response;
	}

}
