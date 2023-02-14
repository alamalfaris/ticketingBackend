package com.be.ticketing.service.city;

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
import com.be.ticketing.dao.city.ICityDao;
import com.be.ticketing.entity.City;
import com.be.ticketing.entity.Province;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.repository.city.CityRepository;
import com.be.ticketing.repository.province.ProvinceRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.city.CityResponse;
import com.be.ticketing.response.city.CitySearchResponse;
import com.be.ticketing.response.province.ProvinceResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class CityService extends CommonService implements ICityService {
	
	@Autowired
	private ProvinceRepository provinceRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ICityDao cityDao;

	@Override
	public List<ProvinceResponse> getListProvince() {

		// get list province through dao
		 List<ProvinceResponse> listProvince = cityDao.getListProvince();
		 
		return listProvince;
	}
	
	private void checkMandatory(CityRequest request) {
		
		if (request.getCityCd().trim().isEmpty() ||
				request.getCityCd().trim() == null || request.getCityCd().length() > 8) {
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
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

	@Override
	public CityResponse insertCity(CityRequest request) {
		
		// check mandatory
		checkMandatory(request);
		
		// check city code whether exist or not
		String checkCode = cityRepo.searchCode(request.getCityCd());
		if (checkCode != null) {
			// throw error if code exist
			throw new BadRequestException(String.format("Data with code: %s already exist", request.getCityCd()));
		}
		
		// check city name whether exist or not 
		String checkName = cityRepo.searchName(request.getCityName());
		if (checkName != null) {
			throw new BadRequestException(String.format("Data with name: %s already exist", request.getCityName()));
		}
		
		// check province id whether exist or not
		Province province = provinceRepo.findById(Long.parseLong(request.getProvinceId())).orElse(null);
		if (province == null) {
			throw new BadRequestException(String.format("Data with id: %s not found", request.getProvinceId()));
		}
		
		// set to entity
		City city = new City();
		
		city.setCityCode(request.getCityCd());
		city.setCityName(request.getCityName());
		city.setProvince(province);
		
		this.setCreate(city, "admin");
		
		cityRepo.save(city);
		
		// search id new inserted data
		Long cityId = cityRepo.searchId(city.getCityCode());
		
		// set to response
		CityResponse cityResponse = new CityResponse();
		
		cityResponse.setCityId(cityId);
		cityResponse.setCityCd(city.getCityCode());
		cityResponse.setCityName(city.getCityName());
		cityResponse.setProvinceId(request.getProvinceId());
		cityResponse.setRecordFlag(city.getRecordFlag());
		cityResponse.setCreatedBy(city.getCreatedBy());
		cityResponse.setCreatedTime(dateFormatter.format(city.getCreatedTime()));
		
		return cityResponse;
	}

	@Override
	public CityResponse updateCity(CityRequest request) {

		// check mandatory
		checkMandatory(request);
		
		// check city name whether exist or not 
		String checkName = cityRepo.searchName(request.getCityName());
		if (checkName != null) {
			throw new BadRequestException(String.format("Data with name: %s already exist", request.getCityName()));
		}
		
		// check province id whether exist or not
		Province province = provinceRepo.findById(Long.parseLong(request.getProvinceId())).orElse(null);
		if (province == null) {
			throw new BadRequestException(String.format("Data with id: %s not found", request.getProvinceId()));
		}
		
		// search id
		Long cityId = cityRepo.searchId(request.getCityCd());
		
		// find data by id
		City city = cityRepo.findById(cityId).orElse(null);
		
		// update entity
		city.setCityName(request.getCityName());
		city.setProvince(province);
		
		this.setUpdate(city, "admin");
		
		cityRepo.save(city);
		
		// set to response
		CityResponse cityResponse = new CityResponse();
		
		cityResponse.setCityId(cityId);
		cityResponse.setCityCd(city.getCityCode());
		cityResponse.setCityName(city.getCityName());
		cityResponse.setProvinceId(request.getProvinceId());
		cityResponse.setRecordFlag(city.getRecordFlag());
		cityResponse.setCreatedBy(city.getCreatedBy());
		cityResponse.setCreatedTime(dateFormatter.format(city.getCreatedTime()));
		cityResponse.setUpdatedBy(city.getUpdatedBy());
		cityResponse.setUpdatedTime(dateFormatter.format(city.getUpdatedTime()));
		
		return cityResponse;
	}

	@Override
	public SearchResponse<CitySearchResponse> searchCity(CityRequest request) {

		// check mandatory
		ValidationUtil.checkPage(request);
		
		// search city through dao
		List<CitySearchResponse> listCity = cityDao.searchCity(request);
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listCity.size());
		Page<CitySearchResponse> page = new PageImpl<>(listCity.subList(start, end), pageable, listCity.size());
		
		return this.createSearchResponse(page);
	}

	@Override
	public List<String> deleteCity(ListCodeRequest request) {
		
		// check list code
		ValidationUtil.checkListCode(request);

		List<String> results = new ArrayList<>();
		
		// loop list code
		for (Code c : request.getListCode()) {
			
			// searchId
			Long cityId = cityRepo.searchId(c.getCode());
			
			// check cityId
			if (cityId == null) {
				results.add(String.format("Data with code: %s not found", c.getCode()));
			}
			else {
				City city = cityRepo.findById(cityId).orElse(null);
				
				this.setDelete(city, "admin");
				
				cityRepo.save(city);
				
				results.add(String.format("Data with code: %s deleted", c.getCode()));
			}
		}
		
		return results;
	}

	@Override
	public DownloadResponse downloadTemplateCity() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_CITY);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_CITY);
	}
	
	@Override
	public List<String> uploadCity(MultipartFile fileUpload) {

		// check extension file
		ValidationUtil.checkExtentionFile(fileUpload);
		
		List<String> result = new ArrayList<>();
		
		try {
			// get data from excel
			List<CityRequest> listCity = getDataFromExcel(fileUpload.getInputStream());
			
			// check data from excel
			if (listCity == null || listCity.isEmpty()) {
				result.add("No data in excel file");
				return result;
			}
			
			// loop data from excel
			for (CityRequest c : listCity) {
				
				// check city id
				Long cityId = cityRepo.searchId(c.getCityCd());
				if (cityId == null) {
					// upload new data
					result = uploadNewCity(c, result);
				}
				else {
					// upload existing data
					result = uploadExistCity(c, cityId, result);
				}
			}
		}
		catch (Exception e) {
			
		}
		
		return result;
	}
	
	private List<String> uploadNewCity(CityRequest c, List<String> result) {
		
		// check city code whether exist or not
		String checkCode = cityRepo.searchCode(c.getCityCd());
		if (checkCode != null) {
			result.add(String.format("Data with code: %s already exist", c.getCityCd()));
			return result;
		}
		
		// check city name whether exist or not 
		String checkName = cityRepo.searchName(c.getCityName());
		if (checkName != null) {
			result.add(String.format("Data with name: %s already exist", c.getCityName()));
			return result;
		}
		
		// check province id whether exist or not
		Long provinceId = provinceRepo.searchId(c.getProvinceCd());
		if (provinceId == null) {
			result.add(String.format("Province with code: %s not found", c.getProvinceCd()));
			return result;
		}
		
		// set to entity and save
		Province province = provinceRepo.findById(provinceId).orElse(null);
		
		City city = new City();
		
		city.setCityCode(c.getCityCd());
		city.setCityName(c.getCityName());
		city.setProvince(province);
		
		this.setCreate(city, "admin");
		
		cityRepo.save(city);
		
		result.add(String.format("New data %s uploaded", c.getCityCd()));
		
		return result;
	}
	
	private List<String> uploadExistCity(CityRequest c, Long cityId, List<String> result) {
		
		// check city if deleted
		City city = cityRepo.findById(cityId).orElse(null);
		if (city.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			result.add(String.format("City with code: %s already exist with delete flag", c.getCityCd()));
			return result;
		}
		
		// check city name
		String cityName = cityRepo.searchName(c.getCityName());
		if (cityName != null) {
			result.add(String.format("City with name: %s already exist", c.getCityName()));
			return result;
		}
		
		// check province id exist or not
		Long provinceId = provinceRepo.searchId(c.getProvinceCd());
		if (provinceId == null) {
			result.add(String.format("Province with code: %s not found", c.getProvinceCd()));
			return result;
		}
		
		Province province = provinceRepo.findById(provinceId).orElse(null);
		
		// update entity
		city.setCityName(c.getCityName());
		city.setProvince(province);
		
		this.setUpdate(city, "admin");
		
		cityRepo.save(city);
		
		result.add(String.format("Data %s updated", c.getCityCd()));
		return result;
	}
	
	private List<CityRequest> getDataFromExcel(InputStream is) {
        
		List<CityRequest> cityList = new ArrayList<>();
		
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
                CityRequest city = new CityRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                        	city.setProvinceCd(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                        	city.setCityCd(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 2:
                        	city.setCityName(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                cityList.add(city);
            }
            
            wb.close();
            
            
        } 
        catch (Exception e) {
        }
		return cityList;
    }
	
	@Override
	public DownloadResponse downloadCity(String provinceCode,
			String cityCode, String cityName, String extension) {

		// check extension
		ValidationUtil.checkExtentionFile2(extension);
		
		// set to CityRequest
		CityRequest request = new CityRequest();
		request.setCityCd(cityCode != null ? cityCode : "");
		request.setCityName(cityName != null ? cityName : "");
		request.setProvinceCd(provinceCode != null ? provinceCode : "");
		
		// search data through dao
		List<CitySearchResponse> listData = cityDao.downloadCity(request);
		
		DownloadResponse result = new DownloadResponse();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(Constants.TEMPLATE_DATA_CITY + extension);
		
		// check type extension
		if (extension.equals(Constants.TYPE_CSV)) {
			result = generateCsv(listData);
		}
		else if (extension.equals(Constants.TYPE_XLSX) || extension.equals(Constants.TYPE_XLS)) {
			result = generateExcel(is, listData, extension);
		}
		else {
			throw new BadRequestException("extension file not recognized");
		}
		
		return result;
	}
	
	private DownloadResponse generateCsv(List<CitySearchResponse> listData) {
		
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "PROVINCE" + s + "CITY CODE" + s + "CITY NAME" + " \n";
		String body = "";
		
		for (CitySearchResponse c : listData) {
			body += c.getProvinceCode() + s + c.getCityCd() + s + c.getCityName() + " \n";
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
        result.setFileName(Constants.TITLE_FILE_CITY + strDate + "." + Constants.TYPE_CSV);
        
		return result;
	}
	
	private DownloadResponse generateExcel(InputStream input, List<CitySearchResponse> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_CITY);

            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 5 * 256);
            sheet.setColumnWidth(2, 5 * 256);


            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 32 * 256);
            sheet.setColumnWidth(2, 32 * 256);


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
            cell.setCellValue("Province");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("City Code");
            
            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("City Name");
            
        	// ADD BODY
            for (CitySearchResponse c : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getProvinceCode());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCityCd());
                
                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCityName());
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
        result.setFileName(Constants.TITLE_FILE_CITY + strDate + "." + extension);
        
        return result;
	}

	
}
