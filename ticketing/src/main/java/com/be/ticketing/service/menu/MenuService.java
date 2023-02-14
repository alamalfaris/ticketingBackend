package com.be.ticketing.service.menu;

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
import org.springframework.web.multipart.MultipartFile;

import com.be.ticketing.constant.Constants;
import com.be.ticketing.dao.menu.IMenuDao;
import com.be.ticketing.entity.Menu;
import com.be.ticketing.exception.BadRequestException;
import com.be.ticketing.exception.TryCatchException;
import com.be.ticketing.repository.menu.MenuRepository;
import com.be.ticketing.request.Code;
import com.be.ticketing.request.ListCodeRequest;
import com.be.ticketing.request.menu.MenuRequest;
import com.be.ticketing.response.DownloadResponse;
import com.be.ticketing.response.SearchResponse3;
import com.be.ticketing.response.menu.MenuParent;
import com.be.ticketing.response.menu.MenuSearchResponse;
import com.be.ticketing.service.CommonService;
import com.be.ticketing.utils.ValidationUtil;

@Service
@Repository
public class MenuService extends CommonService implements IMenuService {
	
	@Autowired
	private MenuRepository menuRepo;
	
	@Autowired
	private IMenuDao menuDao;

	@Override
	public Menu insert(MenuRequest request) {

		// check request
		ValidationUtil.checkMandatory(request);
		
		// check menu code (unique) in DB
		String checkMenuCode = menuRepo.findMenuCode(request.getMenuCd());
		if (checkMenuCode != null) {
			throw new BadRequestException(String.format(Constants.EXIST_MESSAGE, "Menu", request.getMenuCd()));
		}
		
		// check menu type (fk) in DB
		String checkMenuType = menuRepo.findMenuType(request.getType());
		if (checkMenuType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu type", request.getType()));
		}
		
		// set to entity
		Menu menu = new Menu();
		menu.setMenuCode(request.getMenuCd());
		menu.setMenuName(request.getMenuName());
		
		if (request.getParent() != "") {
			menu.setParentId(Long.parseLong(request.getParent()));
		}
		
		menu.setMenuDesc(request.getMenuName());
		menu.setMenuCmd(request.getUrl());
		menu.setMenuType(request.getType());
		menu.setMenuLevel(0);
		menu.setLineNo(0);
		this.setCreate(menu, "admin");
		
		menuRepo.save(menu);
		
		Long menuId = menuRepo.findMenuIdByCode(request.getMenuCd());
		menu.setMenuId(menuId);
		
		return menu;
	}

	@Override
	public List<MenuParent> getMenuParent() {
		
		//search parent through dao
		List<MenuParent> results = menuDao.getMenuParent();
		
		return results;
	}

	@Override
	public Menu update(MenuRequest request) {

		// check request
		ValidationUtil.checkMandatory(request);
		
		// check menu type (fk) in DB
		String checkMenuType = menuRepo.findMenuType(request.getType());
		if (checkMenuType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu type", request.getType()));
		}
		
		Long menuId = menuRepo.findMenuIdByCode(request.getMenuCd());
		if (menuId == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu", request.getMenuCd()));
		}
		
		Menu menu = menuRepo.findById(menuId).orElse(null);
		if (menu.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			throw new BadRequestException(String.format(Constants.WASDELETE_MESSAGE, "Menu",
					request.getMenuCd()));
		}
		
		if (request.getParent() != "") {
			menu.setParentId(Long.parseLong(request.getParent()));
		}
		
		menu.setMenuName(request.getMenuName());
		menu.setMenuDesc(request.getMenuName());
		menu.setMenuCmd(request.getUrl());
		menu.setMenuType(request.getType());
		this.setUpdate(menu, "admin");
		
		menuRepo.save(menu);
		
		return menu;
	}

	@Override
	public SearchResponse3<MenuSearchResponse> search(MenuRequest request) {

		// check page
		ValidationUtil.checkPage(request);
		
		// search through dao
		List<MenuSearchResponse> listData = menuDao.search(request);
		
		// set paging
		Pageable pageable = this.getPageable(request);
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), listData.size());
		Page<MenuSearchResponse> page = new PageImpl<>(listData.subList(start, end), pageable, listData.size());
		
		return this.createSearchResponse4(page, request);
	}

	@Override
	public DownloadResponse downloadTemplate() {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_MENU);
		
		return this.downloadTemplate(is, Constants.TEMPLATE_NAME_MENU);
	}

	@Override
	public List<String> delete(ListCodeRequest request) {

		// check list code
		ValidationUtil.checkListCode(request);
		
		List<String> results = new ArrayList<>();
		List<Menu> listMenu = new ArrayList<>();
		
		for (Code c : request.getListCode()) {
			
			Long menuId = menuRepo.findMenuIdByCode(c.getCode());
			if (menuId == null) {
				//results.add(String.format(Constants.NOTFOUND_MESSAGE, "Menu", c.getCode()));
				throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, "Menu", c.getCode()));
			}
			else {
				
				Menu menu = menuRepo.findById(menuId).orElse(null);
				this.setDelete(menu, "admin");
				
				listMenu.add(menu);
				
				results.add(String.format(Constants.DELETE_SUCCESS_MESSAGE, "Menu", c.getCode()));
			}
		}
		
		menuRepo.saveAll(listMenu);
		
		return results;
	}

	@Override
	public DownloadResponse download(MenuRequest request) {

		// check extension
		ValidationUtil.checkExtentionFile2(request.getExtention());
		
		List<MenuSearchResponse> listData = menuDao.search(request);
		
		DownloadResponse result = new DownloadResponse();
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(Constants.TEMPLATE_DATA_MENU + request.getExtention());
		
		// check type extension
		if (request.getExtention().equals(Constants.TYPE_CSV)) {
			result = generateCsv(listData);
		}
		else if (request.getExtention().equals(Constants.TYPE_XLSX) 
				|| request.getExtention().equals(Constants.TYPE_XLS)) {
			result = generateExcel(is, listData, request.getExtention());
		}
		else {
			throw new BadRequestException("extension file not recognized");
		}
		
		return result;
	}
	
	@Override
	public List<String> upload(MultipartFile fileUpload) {

		// check extension file
		ValidationUtil.checkExtentionFile(fileUpload);
		
		List<String> result = new ArrayList<>();
		List<MenuRequest> listMenuExcel = new ArrayList<>();
		List<Menu> listMenu = new ArrayList<>();
		
		try {
			// get data from excel
			listMenuExcel = getDataFromExcel(fileUpload.getInputStream());
		}
		catch (Exception e) {
		}
		
		// check data from excel
		if (listMenuExcel == null || listMenuExcel.isEmpty()) {
			throw new BadRequestException(Constants.EMPTY_EXCEL_MESSAGE);
		}
		
		// loop data from excel
		for (MenuRequest request : listMenuExcel) {
			
			// check city id
			Long menuId = menuRepo.findMenuIdByCode(request.getMenuCd());
			if (menuId == null) {
				// upload new data
				uploadNew(request, result, listMenu);
			}
			else {
				// upload existing data
				uploadExist(request, result, listMenu);
			}
		}
		
		menuRepo.saveAll(listMenu);
		
		return result;
	}
	
	private void uploadExist(MenuRequest request, List<String> result, List<Menu> listMenu) {
		
		// check request
		ValidationUtil.checkMandatory(request);
		
		// check menu type (fk) in DB
		String checkMenuType = menuRepo.findMenuType(request.getType());
		if (checkMenuType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu type", request.getType()));
		}
		
		Long menuId = menuRepo.findMenuIdByCode(request.getMenuCd());
		if (menuId == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu", request.getMenuCd()));
		}
		
		Menu menu = menuRepo.findById(menuId).orElse(null);
		if (menu.getRecordFlag().equals(Constants.FLAG_DELETE)) {
			throw new BadRequestException(String.format(Constants.WASDELETE_MESSAGE, "Menu",
					request.getMenuCd()));
		}
		
		if (request.getParent() != "") {
			menu.setParentId(menuRepo.findMenuIdByName(request.getParent()));
		}
		
		menu.setMenuName(request.getMenuName());
		menu.setMenuDesc(request.getMenuName());
		menu.setMenuCmd(request.getUrl());
		menu.setMenuType(checkMenuType);
		this.setUpdate(menu, "admin");
		
		listMenu.add(menu);
		result.add(String.format(Constants.UPLOAD_EXISTDATA_MESSAGE, request.getMenuCd()));
	}
	
	private void uploadNew(MenuRequest request, List<String> result, List<Menu> listMenu) {
		
		// check request
		ValidationUtil.checkMandatory(request);
		
		// check menu code (unique) in DB
		String checkMenuCode = menuRepo.findMenuCode(request.getMenuCd());
		if (checkMenuCode != null) {
			throw new BadRequestException(String.format(Constants.EXIST_MESSAGE, "Menu", request.getMenuCd()));
		}
		
		// check menu type (fk) in DB
		String checkMenuType = menuRepo.findMenuType(request.getType());
		if (checkMenuType == null) {
			throw new BadRequestException(String.format(Constants.NOTFOUND_MESSAGE, 
					"Menu type", request.getType()));
		}
		
		// set to entity
		Menu menu = new Menu();
		menu.setMenuCode(request.getMenuCd());
		menu.setMenuName(request.getMenuName());
		
		if (request.getParent() != "") {
			menu.setParentId(menuRepo.findMenuIdByName(request.getParent()));
		}
		
		menu.setMenuDesc(request.getMenuName());
		menu.setMenuCmd(request.getUrl());
		menu.setMenuType(checkMenuType);
		menu.setMenuLevel(0);
		menu.setLineNo(0);
		this.setCreate(menu, "admin");
		
		listMenu.add(menu);
		result.add(String.format(Constants.UPLOAD_NEWDATA_MESSAGE, request.getMenuCd()));
	}
	
	private List<MenuRequest> getDataFromExcel(InputStream is) {
		
		List<MenuRequest> listMenu = new ArrayList<>();
		
		try {
			Workbook wb = new XSSFWorkbook(is);
			Row row = null;
			Cell cell = null;
			
			Sheet sheet = wb.getSheetAt(0);
			for (Integer indexRow = Constants.ROW_INDEX_START; indexRow <= sheet.getLastRowNum(); indexRow++) {
				
				row = sheet.getRow(indexRow);
				if (row != null) {
					
					MenuRequest menu = new MenuRequest();
					
					// Menu Code
					cell = row.getCell(0);
					menu.setMenuCd(ValidationUtil.checkExcelValue(cell));
					
					// Menu Name
					cell = row.getCell(1);
					menu.setMenuName(ValidationUtil.checkExcelValue(cell));
					
					// Parent
					cell = row.getCell(2);
					menu.setParent(ValidationUtil.checkExcelValue(cell));
					
					// Type
					cell = row.getCell(3);
					menu.setType(ValidationUtil.checkExcelValue(cell));
					
					// URL
					cell = row.getCell(4);
					menu.setUrl(ValidationUtil.checkExcelValue(cell));
					
					listMenu.add(menu);
				}
			}
			
			wb.close();
		}
		catch (Exception e) {
			throw new TryCatchException(e.getMessage());
		}
		
		return listMenu;
	}
	
	private DownloadResponse generateCsv(List<MenuSearchResponse> listData) {
		
		DownloadResponse result = new DownloadResponse();
		
		Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = dateFormat.format(date);
		
		ByteArrayInputStream bais = null;
		
		String csvString = "";
		String s = ",";
		String header = "Menu Code" + s + "Menu Name" + s + "Parent" + s + "Type" + s + "URL" + " \n";
		String body = "";
		
		for (MenuSearchResponse c : listData) {
			body += c.getMenuCd() + s + c.getMenuName() + s + c.getParent() + s + c.getType() + s + c.getUrl() + " \n";
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
        result.setFileName(Constants.TITLE_FILE_MENU + strDate + "." + Constants.TYPE_CSV);
        
		return result;
	}
	
	private DownloadResponse generateExcel(InputStream input, List<MenuSearchResponse> listData, 
			String extension) {
		
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
        	
        	Sheet sheet = wb.getSheet(Constants.SHEET_NAME_MENU);

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
            cell.setCellValue("Menu Code");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Menu Name");
            
            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Parent");
            
            cell = row.createCell(3);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Type");
            
            cell = row.createCell(4);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("URL");
            
        	// ADD BODY
            for (MenuSearchResponse c : listData) {
            	rowNumber += 1;
                row= sheet.createRow(rowNumber);
                cell = row.createCell(0);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getMenuCd());

                cell = row.createCell(1);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getMenuName());
                
                cell = row.createCell(2);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getParent());
                
                cell = row.createCell(3);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getType());
                
                cell = row.createCell(4);
                cell.setCellStyle(alignLeft);
                cell.setCellValue(c.getUrl());
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
        result.setFileName(Constants.TITLE_FILE_MENU + strDate + "." + extension);
        
        return result;
	}

	
	
	

}
