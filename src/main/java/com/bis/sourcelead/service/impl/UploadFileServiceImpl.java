package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.BOTransactions;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user,String isMailChecked) throws Exception {

		String isFileCreated;
		String fileName = multipartFile.getOriginalFilename();

		if (!fileName.toLowerCase().endsWith("csv")) {
			isFileCreated = parseXlsxandXlsData(multipartFile, orgLocations,user,isMailChecked);
		} else {
			isFileCreated = parseCSVData(multipartFile,orgLocations,user,isMailChecked);
		}
		return isFileCreated;
	}

	/**
	 * Handles CSV files
	 * 
	 * @param multipartFile
	 * @param orgLocations 
	 * @param user 
	 * @param isMailChecked 
	 * @return List<UserImports>
	 * @throws FileNotFoundException
	 */
	private String parseCSVData(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked) throws Exception {

		String isFileCreated = null;
		boolean isValidated = false;
		 boolean isUserFound = false;
		Workbook createExcelworkbook =  new XSSFWorkbook();
		Sheet createExcelsheet = createExcelworkbook.createSheet("createuser");
		createExcelsheet = setExcelHeader(createExcelsheet);
		
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(Constants.FILE_HEADER_MAPPING);
		try {
			InputStream inputStream = multipartFile.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			csvFileParser = new CSVParser(bufferedReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords();
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				UsersImport userImport = new UsersImport();
				isValidated = validateCsvData(record,userImport);
				if(isValidated){
					if(record.get(Constants.EMPLOYEE_ID)!= null ||!"".equalsIgnoreCase(record.get(Constants.EMPLOYEE_ID))){
						userImport.setEmployeeId(record.get(Constants.EMPLOYEE_ID));
					}else{
						userImport.setEmployeeId("");
					}
					userImport.setFirstName(record.get(Constants.FIRST_NAME));
					if(record.get(Constants.LAST_NAME)!=null || !"".equalsIgnoreCase(record.get(Constants.LAST_NAME))){
						userImport.setLastName(record.get(Constants.LAST_NAME));
					}else{
						userImport.setLastName("");
					}
					userImport.setEmailId(record.get(Constants.EMAIL_ID));
					if(record.get(Constants.PHONE_NUMBER) !=null || !"".equalsIgnoreCase(record.get(Constants.PHONE_NUMBER))){
						userImport.setMobileNumber(record.get(Constants.PHONE_NUMBER));
					}else{
						userImport.setMobileNumber("");
					}
					if(record.get(Constants.RATE) != null || !"".equalsIgnoreCase(record.get(Constants.RATE))){
						userImport.setRate(record.get(Constants.RATE));
					}else{
						userImport.setRate("");
					}if(record.get(Constants.RATEPER) != null || !"".equalsIgnoreCase(record.get(Constants.RATEPER))){
						userImport.setRatePer(record.get(Constants.RATEPER));
					}else{
						userImport.setRatePer("");
					}
					if(record.get(Constants.DATEOFJOIN) !=null || !"".equalsIgnoreCase(record.get(Constants.DATEOFJOIN))){
						Date date = getDateFormat(record.get(Constants.DATEOFJOIN),"dd/MM/yyyy");
						userImport.setDateOfJoin(date);
					}else{
						userImport.setDateOfJoin(null);
					}
					userImport.setRole(record.get(Constants.ROLE));
					if(record.get(Constants.INVOICE_FREQUENCY) !=null || !"".equalsIgnoreCase(record.get(Constants.INVOICE_FREQUENCY))){
						userImport.setInvoiceFrequency(record.get(Constants.INVOICE_FREQUENCY));
					}else{
						userImport.setInvoiceFrequency("");
					}if(record.get(Constants.DAY_HOURS) !=null || !"".equalsIgnoreCase(record.get(Constants.DAY_HOURS))){
						userImport.setDayHours(record.get(Constants.DAY_HOURS));
					}else{
						userImport.setDayHours("");
					}
					
					  isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport.getEmployeeId(),userImport,orgLocations);
					 if(isUserFound){
								createXlsxSheetFromCsv(userImport, createExcelsheet, i,record);
					 }else{
						 saveBulkUser(userImport,orgLocations,user,isMailChecked);
					 }
				}else{
					createXlsxSheetFromCsv(userImport, createExcelsheet, i,record);
				}
			}
			if(!isValidated || isUserFound){
				FileOutputStream fos = new FileOutputStream("FailureUsersForCheck.xlsx");
				createExcelworkbook.write(fos);
				fos.close();
				isFileCreated = "FailureUsersForCheck.xlsx";
			}else{
				isFileCreated = "failed";
			}
			
		} catch (Exception e) {
			 logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		return isFileCreated;

	}
/**
 * validate duplicate entry from excel data
 * @param email
 * @param EmployeeId 
 * @param userImport
 * @param orgLocations 
 * @return
 * @throws Exception
 */
	private boolean validateDuplicateUser(String email, String EmployeeId, UsersImport userImport, OrgLocations orgLocations) throws Exception {
		boolean isUserFound = false;
		User user = userService.findByEmail(email);
		if(user != null){
			isUserFound = true;
			userImport.setReason("duplicate data");
		}else{
			if(EmployeeId!=null && !"".equalsIgnoreCase(EmployeeId)){
				Boolean flag = employeesService.validateEmployeeId(EmployeeId, orgLocations.getId());
				userImport.setReason("duplicate data");
				isUserFound = flag;
			}
		}
		return isUserFound;
	}

	/**
	 * 
	 * @param multipartFile
	 * @param orgLocations
	 * @param user 
	 * @param isMailChecked 
	 * @return List<UsersImport>
	 * @throws FileNotFoundException
	 */
	private String parseXlsxandXlsData(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked) throws Exception {

		String isFileCreated = "failure";
		String fileName = multipartFile.getOriginalFilename();
		Workbook createExcelworkbook = null ;

		if (fileName.endsWith("xlsx")) {
			createExcelworkbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			createExcelworkbook = (Workbook) new HSSFWorkbook();
		}

		Sheet createExcelsheet = createExcelworkbook.createSheet("createuser");
		createExcelsheet = setExcelHeader(createExcelsheet);
	
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
				boolean isValidated = false;
				boolean isUserFound = false;
				workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				int count = 0;
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					UsersImport userImport = new UsersImport();
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) {
						isValidated = validateExeclData(row,userImport,fileName);
					}
					if (isValidated) {
						if (row.getRowNum() > 0) { // Excluding column names
							logger.info("Reading Row :" + row.getRowNum() + " records");
							if (row.getCell(0) != null) {
								String empId = getCellValueAsString(row.getCell(0),fileName);
								userImport.setEmployeeId(empId);
							} else {
								userImport.setEmployeeId("");
							}
							userImport.setFirstName(row.getCell(1).toString());
							if (row.getCell(2) == null || "".equalsIgnoreCase(row.getCell(2).toString())) {
								userImport.setLastName("");
							} else {
								userImport.setLastName(row.getCell(2).toString());
							}
							userImport.setEmailId(row.getCell(3).toString());

							if (row.getCell(4) != null) {
								String mobileNumber = getCellValueAsString(row.getCell(4),fileName);
								userImport.setMobileNumber(mobileNumber);
							} else {
								userImport.setMobileNumber("");
							}
							if (row.getCell(5) != null) {
								String rate = getCellValueAsString(row.getCell(5),fileName);
								userImport.setRate(rate);
							} else {
								userImport.setRate("");
							}
							if (row.getCell(6) == null || "".equalsIgnoreCase(row.getCell(6).toString())) {
								userImport.setRatePer("");
							} else {
								
								userImport.setRatePer(row.getCell(6).toString());
							}
							
							if (row.getCell(7) == null || "".equalsIgnoreCase(row.getCell(7).toString())) {
								userImport.setDateOfJoin(null);
							} else {
								Date hireDate = getCellDate(row.getCell(7),fileName);
								userImport.setDateOfJoin(hireDate);
							}
							userImport.setRole(row.getCell(8).toString());
							if (row.getCell(9) == null || "".equalsIgnoreCase(row.getCell(9).toString())) {
								userImport.setInvoiceFrequency("");
							} else {
								userImport.setInvoiceFrequency(row.getCell(9).toString());
							}
							if (row.getCell(10) != null) {
								String dayHours = getCellValueAsString(row.getCell(10),fileName);
								userImport.setDayHours(dayHours);
							} else {
								userImport.setDayHours("");
							}
							  isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport.getEmployeeId(),userImport,orgLocations);
							 if(isUserFound){
								 if (row.getRowNum() > 0) {
										count++;
										 createXlsSheet(row, createExcelsheet, count,userImport,fileName);
									}
							 }else{
								 if(isMailChecked != null){
									 userImport.setIsMailChecked(Status.Y);
								 }else{
									 userImport.setIsMailChecked(Status.N);
								 }
								 saveBulkUser(userImport,orgLocations,user,isMailChecked);
							 }
						}
					} else {
						if (row.getRowNum() > 0) {
							count++;
							createXlsSheet(row, createExcelsheet, count,userImport,fileName);
						}
					}
				}
				if (!isValidated || isUserFound) {
					FileOutputStream fos = null;
					if(!fileName.endsWith("xlsx")){
						fos = new FileOutputStream("FailureUsersForCheck.xls");
					}else{
						 fos = new FileOutputStream("FailureUsersForCheck.xlsx");
					}
					
					createExcelworkbook.write(fos);
					fos.close();
				}
				if(count > 0){
					if(!fileName.endsWith("xlsx")){
					isFileCreated = "FailureUsersForCheck.xls";
					}else{
						isFileCreated = "FailureUsersForCheck.xlsx";
					}
				}else{
					isFileCreated = "failed";
				}
			}
			fis.close();
		} catch (IOException e) {
			 logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		return isFileCreated;
	}

private Date getCellDate(Cell cell, String fileName) {
		Date date = null;
	   if(fileName.toLowerCase().endsWith("xls")){
		   HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) hssfCell)) {
					date = hssfCell.getDateCellValue();
			}
		}else{
			XSSFCell xssfFCell = (XSSFCell) cell;
			if (xssfFCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) xssfFCell)) {
					date = xssfFCell.getDateCellValue();
			}
		}
		return date;
	}

private String getCellValueAsString(Cell cell, String fileName) {
	String cellValue = "";
	if(fileName.toLowerCase().endsWith("xls")){
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				hssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String hssfCellValue = hssfCell.getStringCellValue();
				if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
					cellValue = hssfCellValue;
				} 
			}else{
				cellValue = cell.getStringCellValue();
			}
		}else{
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				xssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String xssfCellValue = xssfCell.getStringCellValue();
				if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
					cellValue = xssfCellValue;
				} 
			}else{
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}

/**
 * Handles bulk user savings
 * @param userImport
 * @param orgLocations
 * @param loginUser 
 * @param isMailChecked 
 * @param fileName 
 * @throws Exception
 */
	private void saveBulkUser(UsersImport userImport, OrgLocations orgLocations, User loginUser, String isMailChecked) throws Exception {
		User user = new User();
		UserRole userRole = new UserRole();
		Employees employee = new Employees();

		Roles role = rolesService.getRolesByDisplayName(userImport.getRole());

		user.setFirstName(userImport.getFirstName());
		user.setLastName(userImport.getLastName());
		user.setEmail(userImport.getEmailId());
		user.setMobilePhone(userImport.getMobileNumber());
		user.setCreatedDate(new Date());
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setUserName(userImport.getEmailId());
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		user.setIsMailSent(userImport.getIsMailChecked());
		employee.setEmployeeId(userImport.getEmployeeId());
		employee.setRate(userImport.getRate());
		employee.setRatePer(userImport.getRatePer());
		if(userImport.getInvoiceFrequency() !=null && !"".equalsIgnoreCase(userImport.getInvoiceFrequency())){
			employee.setInvoiceFrequency(userImport.getInvoiceFrequency());
		}else{
			employee.setInvoiceFrequency("Weekly");
		}
		employee.setDailyHours(userImport.getDayHours());
		employee.setOrgLocations(orgLocations);
		
		employee.setHireDate(userImport.getDateOfJoin());

		userRole.setUser(user);
		userRole.setIsPrimary(1);
		userRole.setRoles(role);
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(role.getDisplayName());
		userRole.setCreatedDate(new Date());
		userRole.setOrgLocations(orgLocations);

		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(userRole);
		user.setUserRole(userRoles);
		userService.addUser(user);
		employee.setUser(user);
		employeesService.addEmployee(employee);
		if(isMailChecked !=null){
			userService.sendEmail(user, loginUser, password,orgLocations.getId());
		}
		

	}
/**
 * write excel sheet when validation fails
 * @param row
 * @param createExcelsheet
 * @param rowIndex
 * @param userImport 
 * @param fileName 
 * @throws IOException
 */
	private void createXlsSheet(Row row, Sheet createExcelsheet, int rowIndex, UsersImport userImport, String fileName) throws IOException {

		Row writeRow = createExcelsheet.createRow(rowIndex);
		if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
			
			String empId = getCellValueAsString(row.getCell(0), fileName);
			writeRow.createCell(0).setCellValue(empId);
		} else {
			writeRow.createCell(0).setCellValue("");
		}
		
		if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
		        writeRow.createCell(1).setCellValue(row.getCell(1).toString());
		} else {
			writeRow.createCell(1).setCellValue("");
		}
		if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
		writeRow.createCell(2).setCellValue(row.getCell(2).toString());
		} else {
			 writeRow.createCell(2).setCellValue("");
		}
		if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
			 writeRow.createCell(3).setCellValue(row.getCell(3).toString());
		} else {
			 writeRow.createCell(3).setCellValue("");
		}
			if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
				String mobileNumber = getCellValueAsString(row.getCell(4), fileName);
			      writeRow.createCell(4).setCellValue(mobileNumber);
			} else {
			    writeRow.createCell(4).setCellValue("");
			}
			if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
				String rate = getCellValueAsString(row.getCell(5), fileName);
				 writeRow.createCell(5).setCellValue(rate);
			} else {
			  writeRow.createCell(5).setCellValue("");
			}
		if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST,6,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.RATEPER_LIST,6,createExcelsheet));
			}
			
		    writeRow.createCell(6).setCellValue(row.getCell(6).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST,6,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.RATEPER_LIST,6,createExcelsheet));
			}
			 writeRow.createCell(6).setCellValue("");
		}
		if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
			
			boolean isValidate = isCellValueDate(row.getCell(7), fileName);
			if(isValidate){
				 Date date = getCellDate(row.getCell(7), fileName);
				 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
	             String strDate = dateFormat.format(date);
				 writeRow.createCell(7).setCellValue(strDate);
			}else{
				 writeRow.createCell(7).setCellValue(row.getCell(7).toString());
			}
		} else {
			writeRow.createCell(7).setCellValue("");
		}
		if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.ROLE_LIST,8,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.ROLE_LIST,8,createExcelsheet));
			}
	        writeRow.createCell(8).setCellValue(row.getCell(8).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.ROLE_LIST,8,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.ROLE_LIST,8,createExcelsheet));
			}
			writeRow.createCell(8).setCellValue("");
		}
		if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.INVOICE_FREQUENCY_LIST,9,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.INVOICE_FREQUENCY_LIST,9,createExcelsheet));
			}
			writeRow.createCell(9).setCellValue(row.getCell(9).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.INVOICE_FREQUENCY_LIST,9,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.INVOICE_FREQUENCY_LIST,9,createExcelsheet));
			}
		     writeRow.createCell(9).setCellValue("");
		}
			if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {

                String dayHours = getCellValueAsString(row.getCell(10), fileName);
                  writeRow.createCell(10).setCellValue(dayHours);
			} else {
				writeRow.createCell(10).setCellValue("");
			}
		if (userImport.getReason() !=null) {
			writeRow.createCell(11).setCellValue(userImport.getReason());
		} 
	}
	/**
 * Validate Excel data while storing it into Database
 * @param row
 * @param userImport 
 * @param fileName 
 * @return
 */
	private boolean validateExeclData(Row row, UsersImport userImport, String fileName) {

		boolean isValidated; 
		if (row.getRowNum() > 0) { // Excluding column names

			if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())){
				isValidated = isFnameLnameValid(row.getCell(1).toString());
				if(isValidated == false){
					userImport.setReason("should contain above 2 char");
					return isValidated;
				}
			}
			
			if (row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString()) || !validateEmail(row.getCell(3).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}else if(row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()) && !validateEmail(row.getCell(3).toString())){
				userImport.setReason("Invalid Email");
				return false;
			}
			
			if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
				String mobileNumber = getCellValueAsString(row.getCell(4), fileName);
				if (mobileNumber != null && !"".equalsIgnoreCase(mobileNumber)) {
					isValidated = isMobileNumberValid(mobileNumber);
					if (isValidated == false) {
						userImport.setReason("Invalid MobileNumber");
						return isValidated;
					}
				}
			}
			/*code to check Rate validation*/
			if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString().trim())) {
				if(row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString().trim())){
					String rate = getCellValueAsString(row.getCell(5), fileName);
					if (rate != null && !"".equalsIgnoreCase(rate)) {
						isValidated = isRateLengthValid(rate);
						if(isValidated == false){
							userImport.setReason("Incorrect Value");
							return isValidated;
						}
					}
				}else{
					userImport.setReason("Rate Per Required");
					return false;
				}
			}
			if(row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString().trim())){
				if(row.getCell(5) == null || "".equalsIgnoreCase(row.getCell(5).toString().trim())){
					userImport.setReason("Rate is Required");
					return false;
				}
			}
			if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
				isValidated = isCellValueDate(row.getCell(7),fileName);
				if(isValidated == false){
					userImport.setReason("Invalid Date");
					return isValidated;
				}
			}
			if (row.getCell(8) == null || "".equalsIgnoreCase(row.getCell(8).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}
				if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
					String dayHours = getCellValueAsString(row.getCell(10), fileName);
					if (dayHours != null && !"".endsWith(dayHours)) {
						isValidated = isDayHourValid(dayHours);
						if (isValidated == false) {
							userImport.setReason("Invalid Day Hours");
							return isValidated;
						}
					}
				}
		}
		return true;
	}

	private boolean isCellValueDate(Cell cell, String fileName) {
	
		boolean isCellValueDate = false;
		   if(fileName.toLowerCase().endsWith("xls")){
			   HSSFCell hssfCell = (HSSFCell) cell;
				if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) hssfCell)) {
					isCellValueDate = true;
				}else{
					isCellValueDate = false;
				}
			}else{
				XSSFCell xssfFCell = (XSSFCell) cell;
				if (xssfFCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted((Cell) xssfFCell)) {
						isCellValueDate = true;
				}else{
					isCellValueDate = false;
				}
			}
			return isCellValueDate;
}

	/**
	 * Validate CSV data whle storing it into Database
	 * @param userImport 
	 * @param userImport
	 * @return
	 */
	private boolean validateCsvData(CSVRecord record, UsersImport userImport) {

		boolean isValidated;
			if (record.get(Constants.FIRST_NAME) == null || "".equalsIgnoreCase(record.get(Constants.FIRST_NAME))) {
				userImport.setReason("Required field mandatory");
				return false;
			}
			if (record.get(Constants.EMAIL_ID) == null || !validateEmail(record.get(Constants.EMAIL_ID))) {
				userImport.setReason("Required field is mandatory");
				return false;
			}
			if (record.get(Constants.PHONE_NUMBER) !=null && !"".equalsIgnoreCase(record.get(Constants.PHONE_NUMBER))) {
				isValidated = isMobileNumberValid(record.get(Constants.PHONE_NUMBER));
				if(isValidated == false){
					userImport.setReason("Invalid MobileNumber");
					return isValidated;
				}
			}
			if (record.get(Constants.DATEOFJOIN) != null && !"".equalsIgnoreCase(record.get(Constants.DATEOFJOIN))) {
				isValidated = isThisDateValid(record.get(Constants.DATEOFJOIN), "dd/MM/yyyy");
				if(isValidated == false){
					userImport.setReason("Invalid Date");
					return isValidated;
				}
			}
			if (record.get(Constants.ROLE) == null) {
				userImport.setReason("Required field is  mandatory");
				return false;
			}
			
			if (record.get(Constants.DAY_HOURS) != null && !"".equalsIgnoreCase(record.get(Constants.DAY_HOURS))) {
				isValidated = isDayHourValid(record.get(Constants.DAY_HOURS));
				if(isValidated == false){
					userImport.setReason("Invalid Date");
					return isValidated;
				}
			}
		return true;
	}
/**
 * validate email
 * @param email
 * @return boolean
 */
	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	/**
	 * Prepares Headers for xls and xlsx
	 * @param createExcelsheet
	 * @return
	 */
	private Sheet setExcelHeader(Sheet createExcelsheet) {
		
		Row excelHeader = createExcelsheet.createRow(0);
		excelHeader.createCell(0).setCellValue(Constants.EMPLOYEE_ID);
		excelHeader.createCell(1).setCellValue(Constants.FIRST_NAME);
		excelHeader.createCell(2).setCellValue(Constants.LAST_NAME);
		excelHeader.createCell(3).setCellValue(Constants.EMAIL_ID);
		excelHeader.createCell(4).setCellValue(Constants.PHONE_NUMBER);
		excelHeader.createCell(5).setCellValue(Constants.RATE);
		excelHeader.createCell(6).setCellValue(Constants.RATEPER);
		excelHeader.createCell(7).setCellValue(Constants.DATEOFJOIN);
		excelHeader.createCell(8).setCellValue(Constants.ROLE);
		excelHeader.createCell(9).setCellValue(Constants.INVOICE_FREQUENCY);
		excelHeader.createCell(10).setCellValue(Constants.DAY_HOURS);
		excelHeader.createCell(11).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
	}
	
	
	
	/**
	 * write csv data to excel fromat
	 * @param userImport
	 * @param createExcelsheet
	 * @param rowIndex
	 * @param record
	 */
	private void createXlsxSheetFromCsv(UsersImport userImport, Sheet createExcelsheet, int rowIndex, CSVRecord record) {
		
		Row writeRow = createExcelsheet.createRow(rowIndex);
		
		if (record.get(Constants.EMPLOYEE_ID) != null && !"".equalsIgnoreCase(record.get(Constants.EMPLOYEE_ID))) {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue(record.get(Constants.EMPLOYEE_ID));
		} else {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue("");
		}
		if (record.get(Constants.FIRST_NAME) != null && !"".equalsIgnoreCase(record.get(Constants.FIRST_NAME))) {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue(record.get(Constants.FIRST_NAME));
		} else {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue("");
		}
		if (record.get(Constants.LAST_NAME) != null && !"".equalsIgnoreCase(record.get(Constants.LAST_NAME))) {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue(record.get(Constants.LAST_NAME));
		} else {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue("");
		}
		if (record.get(Constants.EMAIL_ID) != null && !"".equalsIgnoreCase(record.get(Constants.EMAIL_ID))) {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue(record.get(Constants.EMAIL_ID));
		} else {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue("");
		}
		if (record.get(Constants.PHONE_NUMBER) != null && !"".equalsIgnoreCase(record.get(Constants.PHONE_NUMBER)) ) {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue(record.get(Constants.PHONE_NUMBER));
		} else {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue("");
		}
		if (record.get(Constants.RATE) != null && !"".equalsIgnoreCase(record.get(Constants.RATE))) {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue(record.get(Constants.RATE));
		} else {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue("");
		}
		if (record.get(Constants.RATEPER) != null && !"".equalsIgnoreCase(record.get(Constants.RATEPER))) {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue(record.get(Constants.RATEPER));
		} else {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue("");
		}
		if (record.get(Constants.DATEOFJOIN) != null && !"".equalsIgnoreCase(record.get(Constants.DATEOFJOIN))) {
			
			
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue(record.get(Constants.DATEOFJOIN));
		} else {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue("");
		}
		if (record.get(Constants.ROLE) != null && !"".equalsIgnoreCase(record.get(Constants.ROLE))) {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue(record.get(Constants.ROLE));
		} else {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue("");
		}
		if (record.get(Constants.INVOICE_FREQUENCY) != null && !"".equalsIgnoreCase(record.get(Constants.INVOICE_FREQUENCY))) {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue(record.get(Constants.INVOICE_FREQUENCY));
		} else {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue("");
		}
		if (record.get(Constants.DAY_HOURS) != null && !"".equalsIgnoreCase(record.get(Constants.DAY_HOURS))) {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue(record.get(Constants.DAY_HOURS));
		} else {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue("");
		}
		if (userImport.getReason() !=null) {
			writeRow.createCell(11).setCellValue(userImport.getReason());
		} 
	
	}
	/**
	 * Handles Date valid or not
	 * @param dateToValidate
	 * @param dateFromat
	 * @return
	 */
	private  boolean isThisDateValid(String dateToValidate, String dateFromat){
		if(dateToValidate == null){
		return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
		Date date = sdf.parse(dateToValidate);
		logger.debug("The Date is "+date);
		} catch (ParseException e) {

		logger.error(""+e.getMessage());
		return false;
		}

		return true;
		}
	
	
	
	
	private  Date getDateFormat(String dateToValidate, String dateFromat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		Date date = null;
		try {
		 date = sdf.parse(dateToValidate);

		} catch (ParseException e) {

		logger.error(""+e.getMessage());
		}

		return date;
		}
/**
 * Handles Mobile number valid or not
 * @param mobileNmber
 * @return
 */
	private boolean isMobileNumberValid(String mobileNmber){
		String regex = "([\\.\\-\\s+\\/()]*[0-9][\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		if(mobileNmber.length() > 9 && mobileNmber.length() <= 20){
			flag = mobileNmber.matches(regex);
		}else{
			flag=false;
		}
		return flag;
	}
	
	
	private boolean isFnameLnameValid(String name){
		boolean flag = false;
		boolean istabSpace = name.matches("\\s*");
		if(istabSpace){
			flag = false;
		}else{
			if(name.length() > 2 && name.length() <= 200){
				flag = true;
			}
	}
		return flag;
	}
	
	private boolean isDayHourValid(String dayHours) {
		String regex = "\\d+";
		boolean flag;
		int hours;
		if (dayHours.matches(regex)) {
			hours = Integer.parseInt(dayHours);
			if (hours <= 24) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	
	private boolean isRateLengthValid(String rate){
		boolean flag;
		if (rate.length() > 1 && rate.length() <= 8) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
		}
	
	private DataValidation getDropDownListForCell(String[] ratePerList, int column, String fileName) {
		@SuppressWarnings("deprecation")
		  CellRangeAddressList addressList = new CellRangeAddressList(1,500,column,column);
		  DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(ratePerList);
		  DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		  dataValidation.setSuppressDropDownArrow(false);
		  return dataValidation;
}
	
	private XSSFDataValidation getXssfDropDownlist(String[] dropDownList, int column, Sheet sheet){
	    XSSFDataValidationHelper dvHelper=new XSSFDataValidationHelper((XSSFSheet) sheet);
	    XSSFDataValidationConstraint dvConstraint=(XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(dropDownList);
	   CellRangeAddressList addressList=new CellRangeAddressList(1,500,column,column);
	   XSSFDataValidation validation=(XSSFDataValidation)dvHelper.createValidation(dvConstraint,addressList);
	   validation.setShowErrorBox(true);
	   return validation;
	}
	

}
