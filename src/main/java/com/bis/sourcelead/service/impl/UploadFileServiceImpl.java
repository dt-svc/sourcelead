package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.payroll.dao.PayrollDeductionDao;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
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
	private UserRoleService userRoleService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	MasterCandidateDao masterCandidateDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	AssociateGradesService associateGradesService;
	
	@Autowired
	ShiftService shiftService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	LookUpDataDao lookUpDataDao;
	
	@Autowired
	PayrollDeductionDao payrollDeductionDao;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user,String isMailChecked, Org org, Long roleHierarchyId) throws Exception {

		String isFileCreated;
		String fileName = multipartFile.getOriginalFilename();

		if (!fileName.toLowerCase().endsWith("csv")) {
			isFileCreated = parseXlsxandXlsData(multipartFile, orgLocations,user,isMailChecked,org,roleHierarchyId);
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
		 int savedRecordCount = 0;
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
					
					if(record.get(Constants.EXTENSION_NUMBER) !=null || !"".equalsIgnoreCase(record.get(Constants.EXTENSION_NUMBER))){
						userImport.setExtensionNumber(record.get(Constants.EXTENSION_NUMBER));
					}else{
						userImport.setExtensionNumber("");
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
						userImport.setDateofJoin(date.toString());
					}else{
						userImport.setDateofJoin(null);
					}
					userImport.setRole(record.get(Constants.ROLE));
					userImport.setGrade(record.get(Constants.ASSOCIATE_GRADE));
					
					userImport.setManager(record.get(Constants.MANAGER));
					
					
					/*if(record.get(Constants.INVOICE_FREQUENCY) !=null || !"".equalsIgnoreCase(record.get(Constants.INVOICE_FREQUENCY))){
						userImport.setInvoiceFrequency(record.get(Constants.INVOICE_FREQUENCY));
					}else{
						userImport.setInvoiceFrequency("");
					}*/
					if(record.get(Constants.DAY_HOURS) !=null || !"".equalsIgnoreCase(record.get(Constants.DAY_HOURS))){
						userImport.setDayHours(record.get(Constants.DAY_HOURS));
					}else{
						userImport.setDayHours("");
					}
					
					  isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport.getEmployeeId(),userImport,orgLocations);
					 if(isUserFound){
								createXlsxSheetFromCsv(userImport, createExcelsheet, i,record);
					 }else{
						 savedRecordCount = saveBulkUser(userImport,orgLocations,user,isMailChecked,savedRecordCount);
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
			User user1 = userService.findByEmailAndOrgLocationsId(email,orgLocations.getId());
			if(user1 !=null){
				isUserFound = true;
				userImport.setReason("duplicate data");
			}else{
				isUserFound = false;
				userImport.setReason("INSERT");
			}
		}
		else{
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
	 * @param roleHierarchyId 
	 * @param org 
	 * @return List<UsersImport>
	 * @throws FileNotFoundException
	 */
	private String parseXlsxandXlsData(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked, Org org, Long roleHierarchyId) throws Exception {

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
			int savedRecordCount = 0;
			for (int i = 0; i < numberOfSheets; i++) {
				int count = 0;
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					UsersImport userImport = new UsersImport();
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) {
						isValidated = validateExeclData(row,userImport,fileName,orgLocations);
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
							
							if(row.getCell(5) != null){
								String extensionNumber =getCellValueAsString(row.getCell(5), fileName);
								userImport.setExtensionNumber(extensionNumber); 
							}else{
								userImport.setExtensionNumber("");
							}
							
							if (row.getCell(6) != null) {
								String rate = getCellValueAsString(row.getCell(6),fileName);
								userImport.setRate(rate);
							} else {
								userImport.setRate("");
							}
							if (row.getCell(7) == null || "".equalsIgnoreCase(row.getCell(7).toString())) {
								userImport.setRatePer("");
							} else {
								
								userImport.setRatePer(row.getCell(7).toString());
							}
							
							if (row.getCell(8) == null || "".equalsIgnoreCase(row.getCell(8).toString())) {
								userImport.setDateofJoin(null);
							} else {
								Date hireDate = getCellDate(row.getCell(8),fileName);
								userImport.setDateofJoin(hireDate.toString());
							}
							userImport.setRole(row.getCell(9).toString());
							
							/*if (row.getCell(9) == null || "".equalsIgnoreCase(row.getCell(9).toString())) {
								userImport.setInvoiceFrequency("");
							} else {
								userImport.setInvoiceFrequency(row.getCell(9).toString());
							}*/
							if (row.getCell(10) != null) {
								String dayHours = getCellValueAsString(row.getCell(10),fileName);
								userImport.setDayHours(dayHours);
							} else {
								userImport.setDayHours("");
							}
							
							userImport.setManager(row.getCell(11).toString());
							
							if(row.getCell(12) != null) {
						    String employmentType = getCellValueAsString(row.getCell(12),fileName);
							userImport.setEmploymentType(employmentType);
							}else {
								userImport.setEmploymentType("");
							}
							
							// Cell Vendor
							if(row.getCell(13) != null) {
								String vendor = getCellValueAsString(row.getCell(13), fileName);
								userImport.setVendor(vendor);
							}else {
								userImport.setVendor("");
							}
							
							// Cell Vendor Contact
							
							if(row.getCell(14) != null) {
								String vendorContact = getCellValueAsString(row.getCell(14), fileName);
								userImport.setVendorContact(vendorContact);
							}else {
								userImport.setVendorContact("");
							}
							
							if(row.getCell(15) != null) {
								String grade = getCellValueAsString(row.getCell(15), fileName);
								userImport.setGrade(grade);
							}else {
								userImport.setGrade("");
							}
							
							if(row.getCell(16) != null) {
								String designation = getCellValueAsString(row.getCell(16), fileName);
								userImport.setDesignation(designation);
							}else {
								userImport.setDesignation("");
							}
							
							if(row.getCell(17) != null) {
								String shift = getCellValueAsString(row.getCell(17), fileName);
								userImport.setShift(shift);
							}
							
							  isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport.getEmployeeId(),userImport,orgLocations);
							 if(isUserFound){
								 if (row.getRowNum() > 0) {
										count++;
										 createXlsSheet(row, createExcelsheet, count,userImport,fileName,org,roleHierarchyId,orgLocations);
									}
							 }else{
								 if(isMailChecked != null){
									 userImport.setIsMailChecked(Status.Y);
								 }else{
									 userImport.setIsMailChecked(Status.N);
								 }
								 savedRecordCount = saveBulkUser(userImport,orgLocations,user,isMailChecked,savedRecordCount);
							 }
							 
							 
						}
					} else {
						if (row.getRowNum() > 0) {
							count++;
							createXlsSheet(row, createExcelsheet, count,userImport,fileName,org,roleHierarchyId,orgLocations);
						}
					}
				}
				if (count >= 0) {
					FileOutputStream fos = null;
					if(!fileName.endsWith("xlsx")){
						fos = new FileOutputStream("FailureAssociatesForCheck.xls");
						isFileCreated = "FailureAssociatesForCheck.xls";
					}else{
						 fos = new FileOutputStream("FailureAssociatesForCheck.xlsx");
						 isFileCreated = "FailureAssociatesForCheck.xlsx";
					}
					
					createExcelworkbook.write(fos);
					fos.close();
				}if(savedRecordCount >0){
					isFileCreated = "failure";
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
 * @param savedRecordCount 
 * @param fileName 
 * @throws Exception
 */
	private int saveBulkUser(UsersImport userImport, OrgLocations orgLocations, User loginUser, String isMailChecked, int savedRecordCount) throws Exception {
		UserRole userRole = new UserRole();
		Employees employee = new Employees();
		OrgRelations orgRelation = null;
		OrgSpoc orgspoc = null;
		
		Roles role = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(userImport.getRole(), orgLocations.getId());
		User reportUser = userService.findByEmailAndOrgLocationsId(userImport.getManager(), orgLocations.getId());
		if(userImport.getShift() != null) {
			Shift shift = shiftService.getShiftListByShiftNameAndOrglocationId(userImport.getShift(), orgLocations.getId());
			if(shift != null) {
				userRole.setShift(shift);
			}
		}
		userRole.setReportingManager(reportUser);
		userRole.setResourceManager(reportUser);
		userRole.setRoles(role);
		userRole.setUserType(role.getDisplayName());
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(loginUser.getUserCode());
		userRole.setOrgLocations(orgLocations);
		if(StringUtils.isNotBlank(userImport.getGrade())) {
			AssociateGrades grades = associateGradesService.getAssociateGradesByGradeNameAndOrgLocationId(userImport.getGrade(), orgLocations.getId());
			if(grades != null) {
				userRole.setGradeId(grades);
			}
		}
		if(StringUtils.isNotBlank(userImport.getDesignation())) {
			userRole.setDesignation(userImport.getDesignation());
		}
		
		employee.setEmployeeId(userImport.getEmployeeId());
		employee.setRate(userImport.getRate());
		employee.setRatePer(userImport.getRatePer());
		employee.setDailyHours(userImport.getDayHours());
		employee.setOrgLocations(orgLocations);
		
		employee.setUserEmploymentType(userImport.getEmploymentType());
		
		if("C2C".equalsIgnoreCase(userImport.getEmploymentType())) {
			Map<String,Long> vendors = getVendorAndVendorContacts(userImport.getEmploymentType(), userImport.getVendor(), userImport.getVendorContact(), orgLocations.getId());
			if(vendors != null) {
				orgRelation = orgRelationsService.getOrRelationsById(vendors.get("vendorId"));
				employee.setOrgRelationId(orgRelation);
				
				orgspoc = orgSpocService.getOrgSpocById(vendors.get("spocId"));
				employee.setSpocId(orgspoc);
			}
			
		}
		employee.setHireDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), userImport.getDateofJoin()));

		String password = null;
		
		User userExisted = userService.findByEmail(userImport.getEmailId());
		
		if(null !=  userExisted){
			UserRole userRole1 = userRoleService.getLoginUserPrimaryRole(userExisted.getId());
			if(userRole1 != null && userRole1.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRole1.getUserState().toString())){
				password = SourceLeadRandomCodeHelper.generateRandomPassword();
				userExisted.setPassword(sourceleadPasswordEncoder.encode(password));
				userService.addUser(userExisted);
				userRole.setIsPrimary(0);
			}else{
				userRole.setIsPrimary(1);
			}
			userRole.setUserState(Status.NOT_LOGGEDIN);
			userRole.setUser(userExisted);
			userRole.setIsMailSent(userImport.getIsMailChecked());
			userRoleService.addUserRole(userRole);
			employee.setUser(userExisted);
			employeesService.addEmployee(employee);
			
			if(isMailChecked !=null){
				userService.sendEmail(userExisted, loginUser, password,orgLocations.getId());
			}
			
			MasterCandidate masterCandidate = null;
			masterCandidate = getMasterCandidateByInteligence(userExisted);
			if(null == masterCandidate){
				masterCandidate = storeMasterCandidate(userExisted);
			}
			
			storeCandidate(userExisted, orgLocations, masterCandidate,orgRelation,orgspoc,userImport);
			savedRecordCount++;
		}else{
			User user  = new User();
			user.setFirstName(userImport.getFirstName());
			user.setLastName(userImport.getLastName());
			user.setEmail(userImport.getEmailId());
			user.setMobilePhone(userImport.getMobileNumber());
			user.setExtensionNumber(userImport.getExtensionNumber());
			user.setEmploymentType(userImport.getEmploymentType());
			user.setCreatedDate(new Date());
			user.setCreatedBy(loginUser.getUserCode());
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			user.setUserName(userImport.getEmailId());
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
	 		user.setUrlSentTime(new Date());
	 		user.setIsVerified(Constants.NO);
			userRole.setIsPrimary(1);
			userRole.setUserState(Status.NOT_LOGGEDIN);
			userRole.setUser(user);
			userRole.setIsMailSent(userImport.getIsMailChecked());
		    userService.addUser(user);	
		    userRoleService.addUserRole(userRole);
		   
			employee.setUser(user);
			employeesService.addEmployee(employee);
			if(isMailChecked !=null){
				userService.sendEmail(user, loginUser, password,orgLocations.getId());
				registrationService.sendActivationEmail(user,orgLocations);
			}
			userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
			
		    MasterCandidate masterCandidate = null;
			masterCandidate = getMasterCandidateByInteligence(user);
			if(null == masterCandidate){
				masterCandidate = storeMasterCandidate(user);
			}
			storeCandidate(user, orgLocations, masterCandidate,orgRelation,orgspoc,userImport);
			savedRecordCount++;
		}
		return savedRecordCount;

	}
	
	
	private MasterCandidate getMasterCandidateByInteligence(User user){
		
		String email = null;
		String phoneNumber = null;
		
		if(!isNullOrEmpty(user.getEmail())){
			email = user.getEmail();
		}
		
		if(!isNullOrEmpty(user.getMobilePhone())){
			phoneNumber = user.getMobilePhone();
		}
		
		MasterCandidate masterCandidate = null;
		try {
			masterCandidate = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(email, phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return masterCandidate;
	}
	
	private MasterCandidate storeMasterCandidate(User user){
		
		MasterCandidate masterCandidate = new MasterCandidate();
		masterCandidate.setFirstName(user.getFirstName());
		
		if(!isNullOrEmpty(user.getLastName())){
			masterCandidate.setLastName(user.getLastName());
		}
		
		masterCandidate.setEmail(user.getEmail());
		
		if(!isNullOrEmpty(user.getMobilePhone())){
			masterCandidate.setPhoneNumber(user.getMobilePhone());
		}
		
		
	  // add employmentType,vendor,vendor contact
		
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		try {
			masterCandidateDao.saveMasterCandidate(masterCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return masterCandidate;
	}
	
	private void storeCandidate(User user,OrgLocations orgLocations,MasterCandidate masterCandidate,OrgRelations orgRelations, OrgSpoc orgspoc,UsersImport userImport) throws Exception{
		    Candidate candidate = candidateService.getCandidateByEmailAndOrgLocationId(user.getEmail(), orgLocations.getId());
		    if(null == candidate){
		    	candidate = new Candidate();
				candidate.setConsultantUser(user);
				candidate.setMasterCandidateId(masterCandidate);
				candidate.setOrgLocationId(orgLocations);
				candidate.setCandidateType(Constants.CANDIDATE);
				candidate.setFirstName(user.getFirstName());
				candidate.setLastName(user.getLastName());
				candidate.setMiddelName(userImport.getMiddleName());
				candidate.setCountry(orgLocations.getCountry());
				candidate.setIsAssociate(Constants.YES);
				candidate.setEmail(user.getEmail());
				if(StringUtils.isNotBlank(userImport.getDesignationType())) {
					candidate.setDesignationType(userImport.getDesignationType());
				}else {
					candidate.setDesignationType(userImport.getEmploymentType());
				}
				candidate.setStatus(Constants.RECORD_ACTIVE);
				candidate.setWorkLocation(orgLocations);
				candidate.setCreatedBy(user.getUserCode());
				candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				candidate.setCreatedDate(commonUtil.currentDate());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidate.setMobilePhone(user.getMobilePhone());
				candidate.setGeneratedId(candidateService.getCandidateId(orgLocations));
				candidate.setProfilePercentage(Constants.MIN_PROFILE_PERCENETAGE);
				candidate.setMaritalStatus(userImport.getMaritalStatus());
				
				
		    }else{
		    	candidate.setConsultantUser(user);
				candidate.setMasterCandidateId(masterCandidate);
		    }
		    
		    if(orgRelations != null) {
		    	OrgLocations vendorOrgLocations = null;
		    	if(orgRelations.getLocationId().getId().equals(orgLocations.getId())) {
		    		vendorOrgLocations = orgRelations.getResponderLocationId();
		    	}else {
		    		vendorOrgLocations = orgRelations.getLocationId();
		    	}
		    	 Candidate vendorCandidate = candidateService.getCandidateByEmailAndOrgLocationId(user.getEmail(), vendorOrgLocations.getId());
		    	 if(vendorCandidate == null) {
		    		 vendorCandidate = new Candidate();
		    		 vendorCandidate.setMasterCandidateId(masterCandidate);
		    		 vendorCandidate.setOrgLocationId(vendorOrgLocations);
		    		 vendorCandidate.setCandidateType(Constants.CANDIDATE);
		    		 vendorCandidate.setFirstName(user.getFirstName());
		    		 vendorCandidate.setLastName(user.getLastName());
		    		 vendorCandidate.setCountry(vendorOrgLocations.getCountry());
		    		 vendorCandidate.setIsAssociate(Constants.YES);
		    		 vendorCandidate.setEmail(user.getEmail());
		    		 vendorCandidate.setWorkLocation(candidate.getWorkLocation());
		    		 vendorCandidate.setStatus(Constants.RECORD_ACTIVE);
		    		 vendorCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		    		 vendorCandidate.setCreatedDate(commonUtil.currentDate());
		    		 vendorCandidate.setUpdatedDate(commonUtil.currentDate());
		    		 vendorCandidate.setMobilePhone(user.getMobilePhone());
		    		 vendorCandidate.setDesignationType("Full Time");
		    		 vendorCandidate.setGeneratedId(candidateService.getCandidateId(vendorOrgLocations));
		    		 vendorCandidate =  candidateService.addCandidate(vendorCandidate);
		    		 candidateService.saveOrUpdateCandidateElkDocument(vendorCandidate);
		    		 
		    	 }
		    	 
		    	 candidate.setParentCandidateId(vendorCandidate);
		    	 if(StringUtils.isNotBlank(userImport.getDesignationType())) {
						candidate.setDesignationType(userImport.getDesignationType());
					}else {
						candidate.setDesignationType(userImport.getEmploymentType());
					}
		    	 
		    	 candidate.setOrgRelationId(orgRelations);
		    	 candidate.setSpocId(orgspoc);
		    }
		    
		    candidateService.addCandidate(candidate);
		    candidateService.saveOrUpdateCandidateElkDocument(candidate);
		    
		}
	
/**
 * write excel sheet when validation fails
 * @param row
 * @param createExcelsheet
 * @param rowIndex
 * @param userImport 
 * @param fileName 
 * @param orgLocations 
 * @param roleHierarchyId 
 * @param org 
 * @throws Exception 
 */
	private void createXlsSheet(Row row, Sheet createExcelsheet, int rowIndex, UsersImport userImport, String fileName, Org org, Long roleHierarchyId, OrgLocations orgLocations) throws Exception {
		String[] rolesList = getRolesList(org,orgLocations.getId(),roleHierarchyId);
		String[] gradesList = getGradesList(orgLocations.getId());
	
		//	String[] usersList = getUsersList(orgLocations.getId());
		
		String[] employmentTyesList = getEmploymentTypeList();
		
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
			String extensionNumber = getCellValueAsString(row.getCell(5), fileName);
		      writeRow.createCell(5).setCellValue(extensionNumber);
		} else {
		    writeRow.createCell(5).setCellValue("");
		}
			
			if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
				String rate = getCellValueAsString(row.getCell(6), fileName);
				 writeRow.createCell(6).setCellValue(rate);
			} else {
			  writeRow.createCell(6).setCellValue("");
			}
		if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST,7,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.RATEPER_LIST,7,createExcelsheet));
			}
			
		    writeRow.createCell(7).setCellValue(row.getCell(7).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST,7,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(Constants.RATEPER_LIST,7,createExcelsheet));
			}
			 writeRow.createCell(7).setCellValue("");
		}
		if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
			
			boolean isValidate = isCellValueDate(row.getCell(8), fileName,orgLocations);
			if(isValidate){
				 Date date = getCellDate(row.getCell(8), fileName);
				 String userDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), date);
				 /*DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
	             String strDate = dateFormat.format(date);*/
				 writeRow.createCell(8).setCellValue(userDate);
			}else{
				 writeRow.createCell(8).setCellValue(row.getCell(8).toString());
			}
		} else {
			writeRow.createCell(8).setCellValue("");
		}
		
		if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(rolesList,9,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(rolesList,9,createExcelsheet));
			}
	        writeRow.createCell(9).setCellValue(row.getCell(9).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(rolesList,9,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(rolesList,9,createExcelsheet));
			}
			writeRow.createCell(9).setCellValue("");
		}
		
		if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
            String dayHours = getCellValueAsString(row.getCell(10), fileName);
              writeRow.createCell(10).setCellValue(dayHours);
		} else {
			writeRow.createCell(10).setCellValue("");
		}
		
		if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
			 writeRow.createCell(11).setCellValue(row.getCell(11).toString());
		} else {
			 writeRow.createCell(11).setCellValue("");
		}
		
		if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(employmentTyesList,12,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(employmentTyesList,12,createExcelsheet));
			}
	        writeRow.createCell(12).setCellValue(row.getCell(12).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(employmentTyesList,12,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(employmentTyesList,12,createExcelsheet));
			}
			writeRow.createCell(12).setCellValue("");
		}
		
		if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
			String vendor = getCellValueAsString(row.getCell(13), fileName);
			 writeRow.createCell(13).setCellValue(vendor);
		} else {
		  writeRow.createCell(13).setCellValue("");
		}
		
		if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
			String vendorContact = getCellValueAsString(row.getCell(14), fileName);
			 writeRow.createCell(14).setCellValue(vendorContact);
		} else {
		  writeRow.createCell(14).setCellValue("");
		}
		
		
		if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(15).toString())) {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(gradesList,15,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(gradesList,15,createExcelsheet));
			}
	        writeRow.createCell(15).setCellValue(row.getCell(15).toString());
		} else {
			if(!fileName.endsWith("xlsx")){
				createExcelsheet.addValidationData(getDropDownListForCell(gradesList,15,fileName));
			}else{
				createExcelsheet.addValidationData(getXssfDropDownlist(gradesList,15,createExcelsheet));
			}
			writeRow.createCell(15).setCellValue("");
		}
		
		if (row.getCell(16) != null && !"".equalsIgnoreCase(row.getCell(16).toString())) {
			
			String designation = getCellValueAsString(row.getCell(16), fileName);
			writeRow.createCell(16).setCellValue(designation);
		} else {
			writeRow.createCell(16).setCellValue("");
		}
		
		if (row.getCell(17) != null && !"".equalsIgnoreCase(row.getCell(17).toString())) {
			 writeRow.createCell(17).setCellValue(row.getCell(17).toString());
		} else {
			 writeRow.createCell(17).setCellValue("");
		}
			
		if (userImport.getReason() !=null) {
			writeRow.createCell(18).setCellValue(userImport.getReason());
		} 
	}
	/**
 * Validate Excel data while storing it into Database
 * @param row
 * @param userImport 
 * @param fileName 
	 * @param orgLocations 
 * @return
	 * @throws Exception 
 */
	private boolean validateExeclData(Row row, UsersImport userImport, String fileName, OrgLocations orgLocations) throws Exception {

		boolean isValidated; 
		if (row.getRowNum() > 0) { // Excluding column names
			
           
			if(row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString()))
			{
				isValidated = isIdValid(row.getCell(0).toString());
				if(isValidated == false){
					userImport.setReason("should contain 2-20 characters");
					return isValidated;
				}
			}
			
			if (row.getCell(1) == null || "".equalsIgnoreCase(row.getCell(1).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}else if(row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())){
				isValidated = isFnameLnameValid(row.getCell(1).toString());
				if(isValidated == false){
					userImport.setReason("should contain above 2 char and should not allowed special charecters");
					return isValidated;
				}
			}
			
			
					
			if (row.getCell(3) == null || "".equalsIgnoreCase(row.getCell(3).toString())) {
				userImport.setReason("Email fields is not filled");
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
				if(row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString().trim())){
					String rate = getCellValueAsString(row.getCell(5), fileName);
					if (rate != null && !"".equalsIgnoreCase(rate)) {
						isValidated = isRateLengthValid(rate);
						if(isValidated == false){
							userImport.setReason("Incorrect Rate");
							return isValidated;
						}
					}
				}else{
					userImport.setReason("Rate Per Required");
					return false;
				}
			}
			if(row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString().trim())){
				if(row.getCell(6) == null || "".equalsIgnoreCase(row.getCell(6).toString().trim())){
					userImport.setReason("Rate is Required");
					return false;
				}
			}
			if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
				/*isValidated = isCellValueDate(row.getCell(8),fileName,orgLocations);
				if(isValidated == false){
					userImport.setReason("Invalid Date");
					return isValidated;
				}*/
			}
			
			
			if (row.getCell(9) == null || "".equalsIgnoreCase(row.getCell(9).toString())) {
				userImport.setReason("Role is Required");
				return false;
			}
			
			
			if (row.getCell(11) == null || "".equalsIgnoreCase(row.getCell(11).toString())) {
				userImport.setReason("Manager is Required");
				return false;
			}else if(row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString()) && !validateEmail(row.getCell(11).toString())){
				userImport.setReason("Invalid Email");
				return false;
			}else {
				User user = userService.findByEmailAndOrgLocationsId(row.getCell(11).toString(), orgLocations.getId());
				if(user == null) {
					userImport.setReason("Manager is not a current org user");
					return false;
				}
			}
			
			if (row.getCell(12) == null || "".equalsIgnoreCase(row.getCell(12).toString())) {
				userImport.setReason("Employment Type is Required");
				return false;
			}
			
			
			
			if (row.getCell(16) == null || "".equalsIgnoreCase(row.getCell(16).toString())) {
				userImport.setReason("Designation field is not filled");
				return false;
			}else if(row.getCell(16) != null && !"".equalsIgnoreCase(row.getCell(16).toString())){
				isValidated = isFnameLnameValid(row.getCell(16).toString());
				if(isValidated == false){
					userImport.setReason("Should contain above 2 char and should not allowed special charecters");
					return isValidated;
				}
			}
			
			if (row.getCell(17) == null || "".equalsIgnoreCase(row.getCell(17).toString())) {
				userImport.setReason("Shift field is not filled");
				return false;
			}else if(row.getCell(17) != null && !"".equalsIgnoreCase(row.getCell(17).toString())){
				isValidated = shiftNameValidation(row.getCell(17).toString(),orgLocations.getId());
				if(isValidated == false){
					userImport.setReason("Shift is not a current org user");
					return isValidated;
				}
			}
			
			
		// Employment Type,Vendor,Vendor Contact Name
			
			if("C2C".equalsIgnoreCase(row.getCell(12).getStringCellValue())) {
				
				if(row.getCell(13) !=null && !"".equalsIgnoreCase(row.getCell(13).toString())){
					List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
					Long vendorId = 0l;
					boolean flag = false;
					for(OrgRelations rel : vendorsList) {
						if((row.getCell(13).toString().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (row.getCell(13).toString().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
							vendorId = rel.getId();
							flag = true;
							break;
						}else {
							flag = false;
						}
					}
					if(!flag) {
						userImport.setReason("No vendor exists..");
						return flag;
					}
					
					if(row.getCell(14) !=null && !"".equalsIgnoreCase(row.getCell(14).toString())){
						List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);
						
						for(OrgSpoc vendorContacts : vendorContactsList) {
							if(row.getCell(14).toString().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
								return true;
							}else {
								userImport.setReason("No contact exists with entered email");
								return false;
							}
						}
						if(vendorContactsList == null || vendorContactsList.size() == 0) {
							userImport.setReason("No Active contacts exists with this vendor");
							return false;
						}
					}else if(vendorId != 0) {
						userImport.setReason("Contact is mandatory");
						return false;
					}
					
				}else {
					if(row.getCell(14) !=null && !"".equalsIgnoreCase(row.getCell(14).toString())){
						userImport.setReason("vendor is mandatory if you have entered contact");
						return false;
					}else {
						userImport.setReason("vendor details are mandatory.");
						return false;
					}
				}
			}else {
				if(row.getCell(13) !=null && !"".equalsIgnoreCase(row.getCell(13).toString())){
					if(row.getCell(14) !=null && !"".equalsIgnoreCase(row.getCell(14).toString())){
						userImport.setReason("Employment type should be C2C if you have entered vendor details");
						return false;
					}else {
						userImport.setReason("Employment type should be C2C if you have entered vendor details");
						return false;
					}
				}
			}
			
			
		}
		return true;
	}


	private boolean shiftNameValidation(String shiftName, Long loginLoationId) {
		
		List<Shift> shiftList  = shiftService.getShiftList(loginLoationId);
		Shift shift = shiftList.stream().filter(x -> shiftName.equalsIgnoreCase(x.getShiftName())).findAny().orElse(null); 
		 boolean flag = false;
		 if(shift != null)
			 flag= true;
		return flag;
	}

	private boolean isCellValueDate(Cell cell, String fileName, OrgLocations orgLocations) {
	
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
		 
		  String emailId =email.trim();
		  Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		  Matcher matcher = pattern.matcher(emailId);
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
		excelHeader.createCell(5).setCellValue(Constants.EXTENSION_NUMBER);
		excelHeader.createCell(6).setCellValue(Constants.RATE);
		excelHeader.createCell(7).setCellValue(Constants.RATEPER);
		excelHeader.createCell(8).setCellValue(Constants.DATEOFJOIN);
		excelHeader.createCell(9).setCellValue(Constants.ROLE);
		excelHeader.createCell(10).setCellValue(Constants.DAY_HOURS);
		excelHeader.createCell(11).setCellValue(Constants.MANAGER);
		excelHeader.createCell(12).setCellValue(Constants.EMPLOYMENT_TYPE);
		excelHeader.createCell(13).setCellValue(Constants.ASSOCIATE_VENDOR);
		excelHeader.createCell(14).setCellValue(Constants.ASSOCIATE_VENDOR_CONTACT);
		excelHeader.createCell(15).setCellValue(Constants.ASSOCIATE_GRADE);
		excelHeader.createCell(16).setCellValue(Constants.ASSOCIATE_DESIGNATION);
		excelHeader.createCell(17).setCellValue(Constants.ASSOCIATE_SHIFT);
		excelHeader.createCell(18).setCellValue(Constants.FAILED_REASON);
		return createExcelsheet;
	}
	
	/**
	 * Prepares Headers for xls template
	 * @param orglocation 
	 * @param createExcelsheet
	 * @return
	 */
	private Sheet setTemplateHeaders(Sheet sheet, OrgLocations orglocation) {
		
		Row excelHeader = sheet.createRow(0);
		StringBuilder format = new StringBuilder();
//		String dateFormate = format.append("( ").append(orglocation.getDateFormat()).append(" )").toString();
		excelHeader.createCell(0).setCellValue(Constants.EMPLOYEE_ID);
		excelHeader.createCell(1).setCellValue(Constants.FIRST_NAME);
		excelHeader.createCell(2).setCellValue(Constants.LAST_NAME);
		excelHeader.createCell(3).setCellValue(Constants.EMAIL_ID);
		excelHeader.createCell(4).setCellValue(Constants.PHONE_NUMBER);
		excelHeader.createCell(5).setCellValue(Constants.EXTENSION_NUMBER);
		excelHeader.createCell(6).setCellValue(Constants.RATE);
		excelHeader.createCell(7).setCellValue(Constants.RATEPER);
		excelHeader.createCell(8).setCellValue(Constants.DATEOFJOIN);
		excelHeader.createCell(9).setCellValue(Constants.ROLE);
		excelHeader.createCell(10).setCellValue(Constants.DAY_HOURS);
		excelHeader.createCell(11).setCellValue(Constants.MANAGER+"(Email)");
		excelHeader.createCell(12).setCellValue(Constants.EMPLOYMENT_TYPE);
		excelHeader.createCell(13).setCellValue(Constants.ASSOCIATE_VENDOR);
		excelHeader.createCell(14).setCellValue(Constants.ASSOCIATE_VENDOR_CONTACT);
		excelHeader.createCell(15).setCellValue(Constants.ASSOCIATE_GRADE);
		excelHeader.createCell(16).setCellValue(Constants.ASSOCIATE_DESIGNATION);
		excelHeader.createCell(17).setCellValue(Constants.ASSOCIATE_SHIFT);
		return sheet;
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
		
		
		if (record.get(Constants.EXTENSION_NUMBER) != null && !"".equalsIgnoreCase(record.get(Constants.EXTENSION_NUMBER)) ) {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue(record.get(Constants.EXTENSION_NUMBER));
		} else {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue("");
		}
		
		
		if (record.get(Constants.RATE) != null && !"".equalsIgnoreCase(record.get(Constants.RATE))) {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue(record.get(Constants.RATE));
		} else {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue("");
		}
		
		if (record.get(Constants.RATEPER) != null && !"".equalsIgnoreCase(record.get(Constants.RATEPER))) {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue(record.get(Constants.RATEPER));
		} else {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue("");
		}
		
		if (record.get(Constants.DATEOFJOIN) != null && !"".equalsIgnoreCase(record.get(Constants.DATEOFJOIN))) {
			
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue(record.get(Constants.DATEOFJOIN));
		} else {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue("");
		}
		if (record.get(Constants.ROLE) != null && !"".equalsIgnoreCase(record.get(Constants.ROLE))) {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue(record.get(Constants.ROLE));
		} else {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue("");
		}
		/*if (record.get(Constants.INVOICE_FREQUENCY) != null && !"".equalsIgnoreCase(record.get(Constants.INVOICE_FREQUENCY))) {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue(record.get(Constants.INVOICE_FREQUENCY));
		} else {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue("");
		}*/
		if (record.get(Constants.DAY_HOURS) != null && !"".equalsIgnoreCase(record.get(Constants.DAY_HOURS))) {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue(record.get(Constants.DAY_HOURS));
		} else {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue("");
		}
		
		if (record.get(Constants.MANAGER) != null && !"".equalsIgnoreCase(record.get(Constants.MANAGER))) {
			Cell cell11 = writeRow.createCell(11);
			cell11.setCellValue(record.get(Constants.MANAGER));
		} else {
			Cell cell11 = writeRow.createCell(11);
			cell11.setCellValue("");
		}
		
		// Employment Type,Vendor,Vendor Contact
		
		if (record.get(Constants.EMPLOYMENT_TYPE) != null && !"".equalsIgnoreCase(record.get(Constants.EMPLOYMENT_TYPE))) {
			Cell cell12 = writeRow.createCell(12);
			cell12.setCellValue(record.get(Constants.EMPLOYMENT_TYPE));
		} else {
			Cell cell12 = writeRow.createCell(12);
			cell12.setCellValue("");
		}
		
		
		if (record.get(Constants.ASSOCIATE_VENDOR) != null && !"".equalsIgnoreCase(record.get(Constants.ASSOCIATE_VENDOR))) {
			Cell cell13 = writeRow.createCell(13);
			cell13.setCellValue(record.get(Constants.ASSOCIATE_VENDOR));
		} else {
			Cell cell13 = writeRow.createCell(13);
			cell13.setCellValue("");
		}
		
		if (record.get(Constants.ASSOCIATE_VENDOR_CONTACT) != null && !"".equalsIgnoreCase(record.get(Constants.ASSOCIATE_VENDOR_CONTACT))) {
			Cell cell14 = writeRow.createCell(14);
			cell14.setCellValue(record.get(Constants.ASSOCIATE_VENDOR_CONTACT));
		} else {
			Cell cell14 = writeRow.createCell(13);
			cell14.setCellValue("");
		}
		
		if (record.get(Constants.ASSOCIATE_GRADE) != null && !"".equalsIgnoreCase(record.get(Constants.ASSOCIATE_GRADE))) {
			Cell cell15 = writeRow.createCell(15);
			cell15.setCellValue(record.get(Constants.ASSOCIATE_GRADE));
		} else {
			Cell cell15 = writeRow.createCell(15);
			cell15.setCellValue("");
		}
		
		if (record.get(Constants.ASSOCIATE_DESIGNATION) != null && !"".equalsIgnoreCase(record.get(Constants.ASSOCIATE_DESIGNATION))) {
			Cell cell16 = writeRow.createCell(16);
			cell16.setCellValue(record.get(Constants.ASSOCIATE_DESIGNATION));
		} else {
			Cell cell16 = writeRow.createCell(16);
			cell16.setCellValue("");
		}
		
		if (record.get(Constants.ASSOCIATE_SHIFT) != null && !"".equalsIgnoreCase(record.get(Constants.ASSOCIATE_SHIFT))) {
			Cell cell17 = writeRow.createCell(17);
			cell17.setCellValue(record.get(Constants.ASSOCIATE_SHIFT));
		} else {
			Cell cell17 = writeRow.createCell(17);
			cell17.setCellValue("");
		}
		
		if (userImport.getReason() !=null) {
			writeRow.createCell(18).setCellValue(userImport.getReason());
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
		
		String phoneNumber = mobileNmber.trim();
		String regex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		if(phoneNumber.length() > 9 && phoneNumber.length() <= 20){
			flag = phoneNumber.matches(regex);
		}else{
			flag=false;
		}
		return flag;
	}
	
	
	private boolean isFnameLnameValid(String name){
		boolean flag = false;
		boolean istabSpace = name.matches("^[A-Za-z][A-Z a-z 0-9]*$");
		if(istabSpace==false){
			flag = false;
		}else{
			if(name.length() > 2 && name.length() <= 200){
				flag = true;
			}
	}
		
		return flag;
	}
	

	private boolean isIdValid(String id)
	{		
		String Id=id.substring(0, id.length() - 2);
		boolean flag = false;
		if(Id.length()<2){
			flag = false;
		}else{
			flag = true;
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
		String regex = "\\d+";
		if (rate.matches(regex)) {
			if (rate.length() > 1 && rate.length() <= 8) {
				flag = true;
			} else {
				flag = false;
			}
			
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

	@Override
	public File getUserTemplate(Org org, Long orgLocationId, Long roleHierarchyId) throws Exception {
		OrgLocations orglocation = null;
		try {
			orglocation = orgLocationService.getOrgLocations(orgLocationId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Workbook wb = (Workbook) new HSSFWorkbook();
		Sheet sheet = wb.createSheet("createAssociates");
		sheet = setTemplateHeaders(sheet,orglocation);
		sheet.addValidationData(getDropDownListForCell(Constants.RATEPER_LIST,7,"xls"));
		
		CreationHelper creationHelper = wb.getCreationHelper();
		
//		Row dateRow = sheet.createRow(1);
		 
		// Create Cell and set value as new Date()
//		Cell dateCell = dateRow.createCell((short) 7);
//		dateCell.setCellValue(new Date());
//		CellStyle style1 = wb.createCellStyle();
//		String dtaeForm = orglocation.getDateFormat();
//		style1.setDataFormat(creationHelper.createDataFormat().getFormat(dtaeForm));
//		dateCell.setCellStyle(style1);
//		sheet.setDefaultColumnStyle(8, style1);
		
		
		
		
//		sheet.addValidationData(Constants.RATEPER_LIST,7,"xls");
		//Roles list 
		String[] rolesList = getRolesList(org,orgLocationId,roleHierarchyId); 
		HSSFSheet rolehidden = (HSSFSheet) wb.createSheet("rolehidden");
		HSSFRow roleRow = rolehidden.createRow(0);
		for (int i = 0, length= rolesList.length; i < length; i++) {
		   String name = rolesList[i];
		   try {
		   HSSFCell cell = roleRow.createCell(i);
		   cell.setCellValue(name);
		   }catch (Exception e) {
		    }
		 }
		 Name namedRoleCel = wb.createName();
		 namedRoleCel.setNameName("rolehidden");
		 namedRoleCel.setRefersToFormula("rolehidden!$A$1:$DZ$1" + rolesList.length);
		 DVConstraint rolConstraint = DVConstraint.createFormulaListConstraint("rolehidden");
		 CellRangeAddressList roleAddressList = new CellRangeAddressList(1, 1000, 9, 9);
		 HSSFDataValidation roleValidation = new HSSFDataValidation(roleAddressList, rolConstraint);
		 wb.setSheetHidden(1, true);
		 sheet.addValidationData(roleValidation);
		 
		 // manager user list
		 	/* String[] usersList = getUsersList(orgLocationId); 
			HSSFSheet userhidden = (HSSFSheet) wb.createSheet("userhidden");
			HSSFRow userRow = userhidden.createRow(0);
			for (int i = 0, length= usersList.length; i < length; i++) {
			   String name = usersList[i];
			   try {
			   HSSFCell cell = userRow.createCell(i);
			   cell.setCellValue(name);
			   }catch (Exception e) {
				
			    }
			 }
			 Name namedUserCel = wb.createName();
			 namedUserCel.setNameName("userhidden");
			 namedUserCel.setRefersToFormula("userhidden!$A$1:$DZ$1" + usersList.length);
			 DVConstraint userConstraint = DVConstraint.createFormulaListConstraint("userhidden");
			 CellRangeAddressList userAddressList = new CellRangeAddressList(1, 1000, 11, 11);
			 HSSFDataValidation userValidation = new HSSFDataValidation(userAddressList, userConstraint);
			 wb.setSheetHidden(2, true);
			 sheet.addValidationData(userValidation);*/
			 
			 // Employment Type List
			 
		 String[] employmentTypesList = getEmploymentTypeList();
		 HSSFSheet employmentTypehidden = (HSSFSheet) wb.createSheet("employmentTypehidden");
			HSSFRow employmentTypeRow = employmentTypehidden.createRow(0);
			for (int i = 0, length= employmentTypesList.length; i < length; i++) {
			   String name = employmentTypesList[i];
			   try {
			   HSSFCell cell = employmentTypeRow.createCell(i);
			   cell.setCellValue(name);
			   }catch (Exception e) {
				
			    }
			 }
			
			 Name namedEmploymentTypeCel = wb.createName();
			 namedEmploymentTypeCel.setNameName("employmentTypehidden");
			 namedEmploymentTypeCel.setRefersToFormula("employmentTypehidden!$A$1:$DZ$1" + employmentTypesList.length);
			 DVConstraint employmentTypeConstraint = DVConstraint.createFormulaListConstraint("employmentTypehidden");
			 CellRangeAddressList empTypeAddressList = new CellRangeAddressList(1, 1000, 12, 12);
			 HSSFDataValidation empTypeValidation = new HSSFDataValidation(empTypeAddressList, employmentTypeConstraint);
			 wb.setSheetHidden(2, true);
			 sheet.addValidationData(empTypeValidation);
			 
		 
	    //Day hours list
	    HSSFSheet hidden = (HSSFSheet) wb.createSheet("hidden");
	    HSSFRow row = hidden.createRow(0);
		for (int i = 0, length= Constants.DAY_HOURS_LIST.length; i < length; i++) {
		   String name = Constants.DAY_HOURS_LIST[i];
		   HSSFCell cell = row.createCell(i);
		   cell.setCellValue(name);
		 }
		 Name namedCell = wb.createName();
		 namedCell.setNameName("hidden");
		 namedCell.setRefersToFormula("hidden!$A$1:$AU$1" + Constants.DAY_HOURS_LIST.length);
		 DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
		 CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, 10, 10);
		 HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
		 
		 wb.setSheetHidden(3, true);
		 sheet.addValidationData(validation);
		 
		 
		 
			String[] gradesList = getGradesList(orgLocationId); 
			HSSFSheet gradehidden = (HSSFSheet) wb.createSheet("gradehidden");
			HSSFRow gradeRow = gradehidden.createRow(0);
			for (int i = 0, length= gradesList.length; i < length; i++) {
			   String name = gradesList[i];
			   try {
			   HSSFCell cell = gradeRow.createCell(i);
			   cell.setCellValue(name);
			   }catch (Exception e) {
			    }
			 }
			 Name gradeRoleCel = wb.createName();
			 gradeRoleCel.setNameName("gradehidden");
			 gradeRoleCel.setRefersToFormula("gradehidden!$A$1:$DZ$1" + rolesList.length);
			 DVConstraint gradeConstraint = DVConstraint.createFormulaListConstraint("gradehidden");
			 CellRangeAddressList gradeAddressList = new CellRangeAddressList(1, 1000, 15, 15);
			 HSSFDataValidation gradeValidation = new HSSFDataValidation(gradeAddressList, gradeConstraint);
			 wb.setSheetHidden(4, true);
			 sheet.addValidationData(gradeValidation);
			 
		 
		File file = new File("createAssociates.xls");
		FileOutputStream fos = new FileOutputStream(file);
		try {
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			logger.error("template download failed due to "+e.getMessage());
		}
		return file;
	}
/**
 * @author Rajashekar
 * Reading roles from ROLE table and converted into an Array
 * @param org
 * @param orgLocationId
 * @param roleHierarchyId
 * @return String[]
 */
	private String[] getRolesList(Org org, Long orgLocationId, Long roleHierarchyId) {
		List<String> list = new ArrayList<>();
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocationId,roleHierarchyId);
		for(Roles roles :rolesList){
			list.add(roles.getDisplayName());
		}
		String [] roleList = list.toArray(new String[list.size()]);
		 return roleList;
	}
	
	private String[] getGradesList(Long orgLocationId) {
		List<String> list = new ArrayList<>();
		List<AssociateGrades> gradesList = null;
		String [] associatesGradesList = null;
		try {
			gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(gradesList != null && !gradesList.isEmpty()) {
			for(AssociateGrades grades :gradesList){
				list.add(grades.getGradeName());
			}
		}
		 associatesGradesList = list.toArray(new String[list.size()]);
		 return associatesGradesList;
	}
	
	private String[] getUsersList(Long orgLocationId) throws Exception {
		List<String> list = new ArrayList<>();  
		List<User> usersList = userService.getAllUsersByOrgLocationCode(orgLocationId);
		for(User user :usersList){
			list.add(user.getEmail());
		}
		String [] userList = list.toArray(new String[list.size()]);
		 return userList;
	}
	
	private String[] getEmploymentTypeList() throws Exception {
		List<String> list = new ArrayList<>();  
		String[] employmentTypeList = {"W-2","C2H","C2H-1099","C2C","C2C-1099","Part Time","Full Time","Other"};
		for(String empType :employmentTypeList){
			list.add(empType);
		}
		String [] employmentTypesList = list.toArray(new String[list.size()]);
		 return employmentTypesList;
	}
	
	private Map<String, Long> getVendorAndVendorContacts(String empType, String vendor, String vendorContact,
			Long orgLocationId) throws Exception {
		Map<String, Long> vendors = new HashMap<>();

		// Employment Type,Vendor,Vendor Contact Name

		if ("C2C".equalsIgnoreCase(empType)) {

			if (vendor != null && !"".equalsIgnoreCase(vendor)) {
				List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocationId);
				Long vendorId = 0l;
				for (OrgRelations rel : vendorsList) {
					if ((vendor.toString().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (vendor
							.toString().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
						vendorId = rel.getId();
						vendors.put("vendorId", vendorId);
						break;
					}
				}

				if (vendorContact != null && !"".equalsIgnoreCase(vendorContact.toString())) {
					List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);

					for (OrgSpoc vendorContacts : vendorContactsList) {
						if (vendorContact.toString().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
							vendors.put("spocId", vendorContacts.getId());
						}
					}

				}

			}else {
				return null;
			}
		}

		return vendors;
	}

	@Override
	public void saveBulkAssociate(UsersImport userImport, OrgLocations orgLocations, User loginUser) throws Exception {
		UserRole userRole = new UserRole();
		Employees employee = new Employees();
		OrgRelations orgRelation = null;
		OrgSpoc orgspoc = null;
		Roles role = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(userImport.getRoleType(), orgLocations.getId());
		User reportUser = userService.findByEmailAndOrgLocationsId(userImport.getReportingManager(), orgLocations.getId());
		if(userImport.getShiftId() != null) {
			Shift shift = shiftService.getShiftListByShiftNameAndOrglocationId(userImport.getShiftId(), orgLocations.getId());
			if(shift != null) {
				userRole.setShift(shift);
			}
		}
		
		if(StringUtils.isNotBlank(userImport.getIsGlobalAccess())) {
			userRole.setIsGlobal(userImport.getIsGlobalAccess());
		}
		
		userRole.setReportingManager(reportUser);
		userRole.setResourceManager(reportUser);
		userRole.setRoles(role);
		userRole.setUserType(role.getDisplayName());
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(loginUser.getUserCode());
		userRole.setOrgLocations(orgLocations);
		if(StringUtils.isNotBlank(userImport.getGrade())) {
			AssociateGrades grades = associateGradesService.getAssociateGradesByGradeNameAndOrgLocationId(userImport.getGrade(), orgLocations.getId());
			if(grades != null) {
				userRole.setGradeId(grades);
			}
		}
		if(StringUtils.isNotBlank(userImport.getDesignation())) {
			userRole.setDesignation(userImport.getDesignation());
		}
		
		employee.setEmployeeId(userImport.getEmployeeId());
		employee.setRate(userImport.getRate());
		employee.setRatePer(userImport.getRatePer());
		employee.setDailyHours(userImport.getDayHours());
		employee.setOrgLocations(orgLocations);
		employee.setUserEmploymentType(userImport.getDesignationType());
		if(StringUtils.isNotBlank(userImport.getDateofJoin())){
			try {
				employee.setHireDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), userImport.getDateofJoin()));
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		
		if(StringUtils.isNotBlank(userImport.getDepartment())) {
			Department department = departmentService.getDepartmentByOrgLocationIdAndDepartmentName(orgLocations.getId(), userImport.getDepartment());
			if(department != null) {
				employee.setDepartmentId(department);
			}
		}
		
		if("C2C".equalsIgnoreCase(userImport.getDesignationType())) {
			Map<String,Long> vendors = getVendorAndVendorContacts(userImport.getDesignationType(), userImport.getVendors(), userImport.getVendorcontact(), orgLocations.getId());
			if(vendors != null) {
				orgRelation = orgRelationsService.getOrRelationsById(vendors.get("vendorId"));
				employee.setOrgRelationId(orgRelation);
				
				orgspoc = orgSpocService.getOrgSpocById(vendors.get("spocId"));
				employee.setSpocId(orgspoc);
			}
			
		}
		if(userImport.getDateofJoin() != null) {
			employee.setHireDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), userImport.getDateofJoin()));
		}

		
		String password = null;
		
		User userExisted = userService.findByEmail(userImport.getEmailId());
		
		if(null !=  userExisted){
			UserRole userRole1 = userRoleService.getLoginUserPrimaryRole(userExisted.getId());
			if(userRole1 != null && userRole1.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRole1.getUserState().toString())){
				password = SourceLeadRandomCodeHelper.generateRandomPassword();
				userExisted.setPassword(sourceleadPasswordEncoder.encode(password));
				userService.addUser(userExisted);
				userRole.setIsPrimary(0);
			}else{
				userRole.setIsPrimary(1);
			}
			userRole.setUserState(Status.NOT_LOGGEDIN);
			userRole.setUser(userExisted);
			if(userImport.getIsMailChecked() != null) {
				userRole.setIsMailSent(userImport.getIsMailChecked());
			}else {
				userRole.setIsMailSent(Status.N);
			}
			userRoleService.addUserRole(userRole);
			employee.setUser(userExisted);
			employeesService.addEmployee(employee);
			
			/*if(userImport.getIsMailChecked() !=null){
				userService.sendEmail(userExisted, loginUser, password,orgLocations.getId());
			}*/
			
			userService.sendEmail(userExisted, loginUser, password,orgLocations.getId());
			
			MasterCandidate masterCandidate = null;
			masterCandidate = getMasterCandidateByInteligence(userExisted);
			if(null == masterCandidate){
				masterCandidate = storeMasterCandidate(userExisted);
			}
			associateBalanceLeaveService.addAssociateLeaveType(userExisted,orgLocations.getId(),employee,userRole);
			storeCandidate(userExisted, orgLocations, masterCandidate,orgRelation,orgspoc,userImport);
		}else{
			User user  = new User();
			user.setFirstName(userImport.getFirstName());
			user.setLastName(userImport.getLastName());
			user.setMiddelName(userImport.getMiddleName());
			user.setEmail(userImport.getEmail());
			user.setMaritalStatus(userImport.getMaritalStatus());
			user.setGender(userImport.getGenderValue());
			user.setMobilePhone(userImport.getPhoneNumber());
			user.setExtensionNumber(userImport.getExtensionNumber());
			user.setEmploymentType(userImport.getEmploymentType());
			user.setCreatedDate(new Date());
			user.setCreatedBy(loginUser.getUserCode());
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			user.setUserName(userImport.getEmail());
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
	 		user.setUrlSentTime(new Date());
	 		user.setIsVerified(Constants.NO);
			userRole.setIsPrimary(1);
			userRole.setUserState(Status.NOT_LOGGEDIN);
			userRole.setUser(user);
			if(userImport.getIsMailChecked() != null) {
				userRole.setIsMailSent(userImport.getIsMailChecked());
			}else {
				userRole.setIsMailSent(Status.N);
			}
			
		    userService.addUser(user);	
		    userRoleService.addUserRole(userRole);
		   
			employee.setUser(user);
			employeesService.addEmployee(employee);
			
			/*if(userImport.getIsMailChecked() !=null){
				userService.sendEmail(user, loginUser, password,orgLocations.getId());
				registrationService.sendActivationEmail(user,orgLocations);
			}*/
			
			userService.sendEmail(user, loginUser, password,orgLocations.getId());
			registrationService.sendActivationEmail(user,orgLocations);
			
			associateBalanceLeaveService.addAssociateLeaveType(user,orgLocations.getId(),employee,userRole);
			
			userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
			
		    MasterCandidate masterCandidate = null;
			masterCandidate = getMasterCandidateByInteligence(user);
			if(null == masterCandidate){
				masterCandidate = storeMasterCandidate(user);
			}
			storeCandidate(user, orgLocations, masterCandidate,orgRelation,orgspoc,userImport);
		}
		
		userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
		
	}
	
	@Override
	public File getAssociateBankDetailsTemplate()
	{
		File file = new File("associatesBankDetails.xls");
		try {
			Workbook wb = (Workbook) new HSSFWorkbook();
			Sheet sheet = wb.createSheet("createAssociates");
			sheet = setAssociateTemplateHeader(sheet,Constants.ASSOCIATE_BANK_DETAIL_HEADERS);
			sheet.addValidationData(getDropDownListForCell(Constants.BANK_TYPE_DROPDOWN,2,"xls"));
			FileOutputStream fos = new FileOutputStream(file);
				wb.write(fos);
				fos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return file;	
	}
	
	/**
	 * create associate bank details template
	 * @param sheet
	 * @param associateBankDetailHeaders 
	 * @return Sheet
	 */
	 private Sheet setAssociateTemplateHeader(Sheet sheet, String[] headers)
	{
		Row headerSheet = sheet.createRow(0);
		for(int i=0;i<headers.length;i++) {
			headerSheet.createCell(i).setCellValue(headers[i]);
		}
		return sheet;
	}
    @Override
	public void changeDocumentNamesWithWhiteSpacesToUnderScore(String staticPath) {
		try {
			masterCandidateDao.changeDocumentNamesWithWhiteSpacesToUnderScore(staticPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @Override
	public File getAssociateDedAndContriDetailsTemplate(OrgLocations orgLocations)
	{
		File file = new File("associatesDeductionAndContributionDetails.xls");
		try {
			Workbook wb = (Workbook) new HSSFWorkbook();
			Sheet sheet = wb.createSheet("createAssociates");
			sheet = setAssociateTemplateHeader(sheet,Constants.ASSOCIATE_DEDUCTION_CONTRIBUTION_DETAIL_HEADERS);
			
			List<PayrollDeduction> deductionList = 	payrollDeductionDao.getAllDeductionTypeByLocationId(orgLocations);
			String[] list = new String[deductionList.size()];
			int count = 0;
			for(PayrollDeduction pd: deductionList) {
				list[count] = pd.getPayrollDeductionType();
				count++;
			}
			sheet.addValidationData(getDropDownListForCell(list,2,"xls"));
			FileOutputStream fos = new FileOutputStream(file);
				wb.write(fos);
				fos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return file;	
	}
    
   /* private Sheet setAssociateTemplateHeader1(Sheet sheet, String[] headers)
	{
		Row headerSheet = sheet.createRow(0);
		for(int i=0;i<headers.length;i++) {
			headerSheet.createCell(i).setCellValue(headers[i]);
		}
		return sheet;
	}*/
}
