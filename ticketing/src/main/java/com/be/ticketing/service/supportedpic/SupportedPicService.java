package com.be.ticketing.service.supportedpic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
import com.be.ticketing.dao.supportedpic.ISupportedPicDao;
import com.be.ticketing.entity.Company;
import com.be.ticketing.entity.Pic;
import com.be.ticketing.entity.SupportedPic;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.repository.company.CompanyRepository;
import com.be.ticketing.repository.supportedpic.PicRepository;
import com.be.ticketing.repository.supportedpic.SupportedPicRepository;
import com.be.ticketing.request.supportedpic.PicRequest;
import com.be.ticketing.request.supportedpic.SupportedPicSearchRequest;
import com.be.ticketing.request.supportedpic.SupportedPicSubmitRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse2;
import com.be.ticketing.response.supportedpic.PicResponse;
import com.be.ticketing.response.supportedpic.SupportedPicAssignResponse;
import com.be.ticketing.response.supportedpic.SupportedPicDownload;
import com.be.ticketing.response.supportedpic.SupportedPicSearchResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class SupportedPicService extends CommonService implements ISupportedPicService {
	
	@Autowired
	private ISupportedPicDao supportedPicDao;
	
	@Autowired
	private PicRepository picRepo;
	
	@Autowired
	private SupportedPicRepository supportedPicRepo;
	
	@Autowired
	private CompanyRepository companyRepo;

	@Override
	public SearchResponse2<SupportedPicSearchResponse> search(SupportedPicSearchRequest request) {

		ValidationUtil.checkPage(request);
		
		List<SupportedPicSearchResponse> listData = supportedPicDao.search(request);
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listData.size());
		Page<SupportedPicSearchResponse> page = new PageImpl<>(listData.subList(start, end), pageable, 
				listData.size());
		
		return this.createSearchResponse3(page, request, start, end);
	}

	@Override
	public SupportedPicSubmitRequest submit(SupportedPicSubmitRequest request) {

		//1. validasi request
		ValidationUtil.checkMandatory(request);
		
		//2. set list pic to List<entity>
		List<Pic> listPic = new ArrayList<>();
		List<String> listPicName = new ArrayList<>();
		
		if (request.getPic().size() > 0) {
			for (PicRequest p : request.getPic()) {
				
				Pic pic = new Pic(); 
				
				if (p.getPicId() != null) {
					
					//2.a. check picId from request in app_pic
					Pic picExist = picRepo.findById(p.getPicId()).orElse(null);
					if (picExist == null) { //2.b. if not exist
						throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Pic", 
								p.getPicId()));
					}
					//2.c. if exist
					pic.setPicId(picExist.getPicId());
					pic.setFullName(p.getName());
					pic.setEmail(p.getEmail());
					pic.setMobileNo(p.getMobileNo());
					pic.setCreatedBy(picExist.getCreatedBy());
					pic.setCreatedTime(picExist.getCreatedTime());
					this.setUpdate(pic, "admin");
				}
				else {
					
					pic.setFullName(p.getName());
					pic.setEmail(p.getEmail());
					pic.setMobileNo(p.getMobileNo());
					this.setCreate(pic, "admin");
				}
				
				listPic.add(pic);
				listPicName.add(pic.getFullName());
			}
		}
		
		//3. save all list<entity>
		picRepo.saveAll(listPic);
		
		List<Long> listPicId = picRepo.findPicIdByName(listPicName);
		
		//4. check company_id in app_company
		Company company = companyRepo.findById(request.getCompanyId()).orElse(null);
		if (company == null) {//4.a. if not exist
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company", 
					request.getCompanyId()));
		}
		
		//5. check company_id in app_pic_company
		Long picCompanyId = supportedPicRepo.findId(request.getCompanyId());
		if (picCompanyId == null) {
			addPicCompany(listPicId, request.getCompanyId());
		}
		else {
			updatePicCompany(listPicId, request.getCompanyId());
		}
		
		return request;
	}
	
	private void updatePicCompany(List<Long> listPicId, Long companyId) {
		
		supportedPicRepo.updateDeleteFlag(companyId, "admin", listPicId);
		
		List<SupportedPic> listSupportedPic = new ArrayList<>();
		
		for (Long picId : listPicId) {
			
			SupportedPic supportedPic = new SupportedPic();
			
			SupportedPic checkSupportedPic = supportedPicRepo.searchByPicAndCompany(companyId, picId);
			if (checkSupportedPic == null) {
				//add new
				supportedPic.setCompanyId(companyId);
				supportedPic.setPicId(picId);
				this.setCreate(supportedPic, "admin");
			}
			else {
				//update exist data
				supportedPic.setPicCompanyId(checkSupportedPic.getPicCompanyId());
				supportedPic.setPicId(checkSupportedPic.getPicId());
				supportedPic.setCompanyId(checkSupportedPic.getCompanyId());
				supportedPic.setRecordFlag(Constants.FLAG_EDIT);
				supportedPic.setCreatedBy(checkSupportedPic.getCreatedBy());
				supportedPic.setCreatedTime(checkSupportedPic.getCreatedTime());
				this.setUpdate(supportedPic, "admin");
			}
			
			listSupportedPic.add(supportedPic);
		}
		
		supportedPicRepo.saveAll(listSupportedPic);
	}
	
	private void addPicCompany(List<Long> listPicId, Long companyId) {
		
		List<SupportedPic> listSupportedPic = new ArrayList<>();
		
		for (Long picId : listPicId) {
			
			SupportedPic supportedPic = new SupportedPic();
			supportedPic.setPicId(picId);
			supportedPic.setCompanyId(companyId);
			this.setCreate(supportedPic, "admin");
			
			listSupportedPic.add(supportedPic);
		}
		
		supportedPicRepo.saveAll(listSupportedPic);
	}

	@Override
	public SupportedPicAssignResponse assign(Long companyId) {

		//Validation
		ValidationUtil.checkMandatory(companyId);
		
		//check companyId in app_company
		Company company = companyRepo.findById(companyId).orElse(null);
		if (company == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Company", 
					companyId));
		}
		
		SupportedPicAssignResponse results = new SupportedPicAssignResponse();
		results.setCompanyId(company.getCompanyId());
		results.setCompanyCode(company.getCompanyCode());
		results.setCompanyName(company.getCompanyName());
		
		//search detail pic
		List<PicResponse> listPic = supportedPicDao.assign(companyId);
		
		results.setPic(listPic);
		
		return results;
	}

	@Override
	public List<PicResponse> getAllPic() {

		List<PicResponse> listPic = supportedPicDao.getAllPic();
		
		return listPic;
	}

	@Override
	public DownloadResponse download(SupportedPicSearchRequest request) {

		// check extension
		ValidationUtil.checkExtentionFile2(request.getExtension());
		
		// search data
		List<SupportedPicDownload> listData = supportedPicDao.download(request);
		
		DownloadResponse result = new DownloadResponse();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(Constants.TEMPLATE_DATA_SUPPORTED_PIC + request.getExtension());
		
		// check type extension
		if (request.getExtension().equals(Constants.TYPE_CSV)) {
			result = generateCsv(listData);
		}
		else if (request.getExtension().equals(Constants.TYPE_XLSX) || request.getExtension().equals(Constants.TYPE_XLS)) {
			result = generateExcel(is, listData, request.getExtension());
		}
		else {
			throw new BadRequestException("extension file not recognized");
		}
		
		return result;
	}
	
	private DownloadResponse generateCsv(List<SupportedPicDownload> listData) {
		
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = ",";
		String header = "Company Code" + s + "Company Name" + s + "Pic Name" + s + "Pic Mobil Phone" + s + "Pic Email" + " \n";
		String body = "";
		
		for (SupportedPicDownload c : listData) {
			body += c.getCompanyCode() + s + c.getCompanyName() + s + c.getFullName() + s + c.getMobileNo() + s + c.getEmailAddress() + " \n";
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
        result.setFileName(Constants.TITLE_FILE_SUPPORTED_PIC + strDate + "." + Constants.TYPE_CSV);
        
		return result;
	}
	
	private DownloadResponse generateExcel(InputStream input, List<SupportedPicDownload> listData, String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_SUPPORTED_PIC);

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
            cell.setCellValue("Company Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Company Name");
            
            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pic Name");
            
            cell = row.createCell(3);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pic Mobil Phone");
            
            cell = row.createCell(4);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pic Email");
            
        	// ADD BODY
            for (SupportedPicDownload c : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCompanyCode());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getCompanyName());
                
                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getFullName());
                
                cell = row.createCell(3);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getMobileNo());
                
                cell = row.createCell(4);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getEmailAddress());
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
        result.setFileName(Constants.TITLE_FILE_SUPPORTED_PIC + strDate + "." + extension);
        
        return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
