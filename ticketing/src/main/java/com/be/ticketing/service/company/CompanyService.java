package com.be.ticketing.service.company;

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
import com.be.ticketing.dao.company.ICompanyDao;
import com.be.ticketing.entity.City;
import com.be.ticketing.entity.Company;
import com.be.ticketing.entity.Parameter;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.repository.city.CityRepository;
import com.be.ticketing.repository.company.CompanyRepository;
import com.be.ticketing.repository.parameter.ParameterRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.company.CompanyRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse;
import com.be.ticketing.response.company.CompanyResponse;
import com.be.ticketing.response.company.CompanySearchResponse;
import com.be.ticketing.response.company.GetBusinessResponse;
import com.be.ticketing.response.company.GetCityResponse;
import com.be.ticketing.response.company.GetTypeResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class CompanyService extends CommonService implements ICompanyService {
	
	@Autowired
	private CompanyRepository companyRepo;
	
	@Autowired
	private ParameterRepository parameterRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private ICompanyDao companyDao;

	@Override
	public CompanyResponse insert(CompanyRequest request) {

		// check mandatory
		ValidationUtil.checkMandatory(request);
		
		// check company code
		String companyCd = companyRepo.findCode(request.getCompanyCd());
		if (companyCd != null) {
			throw new BadRequestException(String.format(Constants.EXIST_MESSAGE, "Company", companyCd));
		}
		
		// check company type and company business
		Parameter companyType = parameterRepo.findById(request.getCompanyType()).orElse(null);
		if (companyType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company type", 
					request.getCompanyType()));
		}
		
		Parameter companyBusiness = parameterRepo.findById(request.getCompanyBusiness()).orElse(null);
		if (companyBusiness == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company business", 
					request.getCompanyBusiness()));
		}
		
		// check city
		City city = cityRepo.findById(Long.parseLong(request.getCityCd())).orElse(null);
		if (city == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "City", 
					request.getCityCd()));
		}
		
		// set to entity
		Company company = new Company();
		
		company.setCompanyCode(request.getCompanyCd());
		company.setCompanyName(request.getCompanyName());
		company.setCompanyType(companyType);
		company.setCompanyBusiness(companyBusiness);
		company.setAddress(request.getAddress());
		company.setCityId(city);
		
		this.setCreate(company, "admin");
		
		companyRepo.save(company);
		
		Long companyId = companyRepo.findId(request.getCompanyCd());
		
		// set to response
		CompanyResponse response = new CompanyResponse();
		response.setCompanyId(companyId);
		response.setCompanyCd(company.getCompanyCode());
		response.setCompanyName(company.getCompanyName());
		response.setCompanyType(companyType.getParameterCd());
		response.setCompanyBusiness(companyBusiness.getParameterCd());
		response.setCompanyAddress(company.getAddress());
		response.setCityId(Long.toString(city.getCityId()));
		response.setCreatedBy(company.getCreatedBy());
		response.setCreatedTime("" + company.getCreatedTime());
		response.setUpdatedBy(company.getUpdatedBy());
		response.setUpdatedTime("" + company.getUpdatedTime());
		response.setRecordFlag(company.getRecordFlag());
		
		return response;
	}
	
	@Override
	public CompanyResponse update(CompanyRequest request) {

		// check mandatory
		ValidationUtil.checkMandatory(request);
		
		// check company code
		String companyCd = companyRepo.findCode(request.getCompanyCd());
		if (companyCd == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company", companyCd));
		}
		
		// check company type and company business
		Parameter companyType = parameterRepo.findById(request.getCompanyType()).orElse(null);
		if (companyType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company type", 
					request.getCompanyType()));
		}
		
		Parameter companyBusiness = parameterRepo.findById(request.getCompanyBusiness()).orElse(null);
		if (companyBusiness == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company business", 
					request.getCompanyBusiness()));
		}
		
		// check city
		City city = cityRepo.findById(Long.parseLong(request.getCityCd())).orElse(null);
		if (city == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "City", 
					request.getCityCd()));
		}
		
		// find company
		Long companyId = companyRepo.findId(request.getCompanyCd());
		Company company = companyRepo.findById(companyId).orElse(null);
		
		company.setCompanyName(request.getCompanyName());
		company.setCompanyType(companyType);
		company.setCompanyBusiness(companyBusiness);
		company.setAddress(request.getAddress());
		company.setCityId(city);
		
		this.setUpdate(company, "admin");
		
		companyRepo.save(company);
		
		// set to response
		CompanyResponse response = new CompanyResponse();
		response.setCompanyId(companyId);
		response.setCompanyCd(company.getCompanyCode());
		response.setCompanyName(company.getCompanyName());
		response.setCompanyType(companyType.getParameterCd());
		response.setCompanyBusiness(companyBusiness.getParameterCd());
		response.setCompanyAddress(company.getAddress());
		response.setCityId(Long.toString(city.getCityId()));
		response.setCreatedBy(company.getCreatedBy());
		response.setCreatedTime("" + company.getCreatedTime());
		response.setUpdatedBy(company.getUpdatedBy());
		response.setUpdatedTime("" + company.getUpdatedTime());
		response.setRecordFlag(company.getRecordFlag());
		
		return response;
	}

	@Override
	public SearchResponse<CompanySearchResponse> search(CompanyRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search data
		List<CompanySearchResponse> listCompany = companyDao.search(request);
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listCompany.size());
		Page<CompanySearchResponse> page = new PageImpl<>(listCompany.subList(start, end), pageable, 
				listCompany.size());
		
		return this.createSearchResponse(page);
	}

	@Override
	public List<String> delete(ListCodeRequest request) {

		// check list code
		ValidationUtil.checkListCode(request);
		
		List<String> results = new ArrayList<>();
		
		// loop list code
		for (Code c : request.getListCode()) {
			
			Long companyId = companyRepo.findId(c.getCode());
			
			if (companyId == null) {
				results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company", c.getCode()));
			}
			else {
				Company company = companyRepo.findById(companyId).orElse(null);
				
				this.setDelete(company, "admin");
				
				companyRepo.save(company);
				
				results.add(String.format(Constants.DELETE_SUCCESS_MESSAGE, "Company", c.getCode()));
			}
		}
		
		return results;
	}

	@Override
	public List<GetCityResponse> getCityCompany() {

		return companyDao.getCityCompany();
	}

	@Override
	public List<GetBusinessResponse> getBusinessCompany() {

		return companyDao.getBusinessCompany();
	}

	@Override
	public List<GetTypeResponse> getTypeCompany() {

		return companyDao.getTypeCompany();
	}

	@Override
	public DownloadResponse downloadTemplateCompany() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_COMPANY);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_COMPANY);
	}
	
	@Override
	public List<String> upload(MultipartFile fileUpload) {

		// check extension file
		ValidationUtil.checkExtentionFile(fileUpload);
		
		List<String> results = new ArrayList<>();
		
		try {
			// get data from excel
			List<CompanyRequest> listCompany = getDataFromExcel(fileUpload.getInputStream());
			
			// check data from excel
			if (listCompany == null || listCompany.isEmpty()) {
				results.add(Constants.EMPTY_EXCEL_MESSAGE);
				return results;
			}
			
			// loop excel data
			for (CompanyRequest c : listCompany) {
				
				Long companyId = companyRepo.findId(c.getCompanyCd());
				if (companyId == null) {
					results = uploadNewData(c, results);
				}
				else {
					results = uploadExistData(c, results, companyId);
				}
				
			}
		}
		catch (Exception e) {
		}
		
		return results;
	}
	
	private List<String> uploadNewData(CompanyRequest c, List<String> results) {
		
		// check company code
		String companyCd = companyRepo.findCode(c.getCompanyCd());
		if (companyCd != null) {
			results.add(String.format(Constants.EXIST_MESSAGE, "Company", companyCd));
			return results;
		}
		
		// check company type and company business
		Parameter companyType = parameterRepo.findById(c.getCompanyType()).orElse(null);
		if (companyType == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company type", 
					c.getCompanyType()));
			return results;
		}
		
		Parameter companyBusiness = parameterRepo.findById(c.getCompanyBusiness()).orElse(null);
		if (companyBusiness == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company business", 
					c.getCompanyBusiness()));
			return results;
		}
		
		// check city
		Long cityId = cityRepo.searchId(c.getCityCd());
		if (cityId == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "City", 
					c.getCityCd()));
			return results;
		}
		City city = cityRepo.findById(cityId).orElse(null);
		
		// set to entity
		Company company = new Company();
		
		company.setCompanyCode(c.getCompanyCd());
		company.setCompanyName(c.getCompanyName());
		company.setCompanyType(companyType);
		company.setCompanyBusiness(companyBusiness);
		company.setAddress(c.getAddress());
		company.setCityId(city);
		
		this.setCreate(company, "admin");
		
		companyRepo.save(company);
		
		results.add(String.format(Constants.UPLOAD_NEWDATA_MESSAGE, c.getCompanyCd()));
		
		return results;
	}
	
	private List<String> uploadExistData(CompanyRequest c, List<String> results, Long companyId) {
		
		// check company if deleted
		Company company = companyRepo.findById(companyId).orElse(null);
		if (company.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			results.add(String.format(Constants.WASDELETE_MESSAGE, "Company", c.getCompanyCd()));
			return results;
		}
		
		// check company type and company business
		Parameter companyType = parameterRepo.findById(c.getCompanyType()).orElse(null);
		if (companyType == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company type", 
					c.getCompanyType()));
			return results;
		}
		
		Parameter companyBusiness = parameterRepo.findById(c.getCompanyBusiness()).orElse(null);
		if (companyBusiness == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "Company business", 
					c.getCompanyBusiness()));
			return results;
		}
		
		// check city
		Long cityId = cityRepo.searchId(c.getCityCd());
		if (cityId == null) {
			results.add(String.format(Constants.NOTFOUND_MESSAGE, "City", 
					c.getCityCd()));
			return results;
		}
		City city = cityRepo.findById(cityId).orElse(null);
		
		company.setCompanyName(c.getCompanyName());
		company.setCompanyType(companyType);
		company.setCompanyBusiness(companyBusiness);
		company.setAddress(c.getAddress());
		company.setCityId(city);
		
		this.setUpdate(company, "admin");
		
		companyRepo.save(company);
		
		results.add(String.format(Constants.UPLOAD_EXISTDATA_MESSAGE, c.getCompanyCd()));
		
		return results;
	}

	private List<CompanyRequest> getDataFromExcel(InputStream is) {
        
		List<CompanyRequest> listCompany = new ArrayList<>();
		
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
                CompanyRequest company = new CompanyRequest();

                int cellIdx = 0;
                while(cellInRow.hasNext()){
                    Cell currentCell = (Cell) cellInRow.next();

                    switch(cellIdx){
                        case 0:
                        	company.setCompanyCd(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 1:
                        	company.setCompanyName(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 2:
                        	company.setCompanyType(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 3:
                        	company.setCompanyBusiness(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 4:
                        	company.setAddress(ValidationUtil.checkExcelValue(currentCell));
                            break;
                        case 5:
                        	company.setCityCd(ValidationUtil.checkExcelValue(currentCell));
                            break;
                    }
                    cellIdx++;
                    rowNumber++;
                }

                listCompany.add(company);
            }
            
            wb.close();
            
            
        } 
        catch (Exception e) {
        }
		
		return listCompany;
    }

	@Override
	public DownloadResponse downloadCompany(String business, String companyCd, String companyName, String extention) {

		// check extension
		ValidationUtil.checkExtentionFile2(extention);
		
		// search data
		List<CompanySearchResponse> listCompany = companyDao.download(business, companyCd, companyName);
		
		DownloadResponse result = new DownloadResponse();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(Constants.TEMPLATE_DATA_COMPANY + extention);
		
		// check type extension
		if (extention.equals(Constants.TYPE_CSV)) {
			result = generateCsv(listCompany);
		}
		else if (extention.equals(Constants.TYPE_XLSX) || extention.equals(Constants.TYPE_XLS)) {
			result = generateExcel(is, listCompany, extention);
		}
		else {
			throw new BadRequestException("extension file not recognized");
		}
		
		return result;
	}
	
	private DownloadResponse generateCsv(List<CompanySearchResponse> listData) {
		
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = " | ";
		String header = "COMPANY CODE" + s + "COMPANY NAME" + s + "TYPE" + s + "BUSINESS" + s + "ADDRESS" + " \n";
		String body = "";
		
		for (CompanySearchResponse c : listData) {
			body += c.getCompanyCd() + s + c.getCompanyName() + s + c.getType() + s + c.getBusiness() + s + c.getAddress() + " \n";
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
        result.setFileName(Constants.TITLE_FILE_COMPANY + strDate + "." + Constants.TYPE_CSV);
        
		return result;
	}
	
	private DownloadResponse generateExcel(InputStream input, List<CompanySearchResponse> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_COMPANY);

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
            cell.setCellValue("COMPANY CODE");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("COMPANY NAME");
            
            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("TYPE");
            
            cell = row.createCell(3);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("BUSINESS");
            
            cell = row.createCell(4);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("ADDRESS");
            
        	// ADD BODY
            for (CompanySearchResponse c : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCompanyCd());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCompanyName());
                
                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getType());
                
                cell = row.createCell(3);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getBusiness());
                
                cell = row.createCell(4);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getAddress());
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
        result.setFileName(Constants.TITLE_FILE_COMPANY + strDate + "." + extension);
        
        return result;
	}

	
}
