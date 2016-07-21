package com.bis.sourcelead.dao.impl;

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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UploadFileDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 *
 */
@Repository
public class UploadFileDaoImpl implements UploadFileDao {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;

	@Override
	public String uploadFile(MultipartFile multipartFile, OrgLocations orgLocations,User user,String isMailChecked) throws Exception {

		String isFileCreated = null;
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
		Workbook createExcelworkbook =  new XSSFWorkbook();
		Sheet createExcelsheet = createExcelworkbook.createSheet("createuser");
		createExcelsheet = setExcelHeader(createExcelsheet);
		
		String fileName = multipartFile.getOriginalFilename();
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
						userImport.setDateString(record.get(Constants.DATEOFJOIN));
					}else{
						userImport.setDateString("");
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
					
					 boolean isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport);
					 if(isUserFound){
							
								createXlsxSheetFromCsv(userImport, createExcelsheet, i,record);
					 }else{
						 saveBulkUser(userImport,orgLocations,user,isMailChecked,fileName);
					 }
				}else{
					createXlsxSheetFromCsv(userImport, createExcelsheet, i,record);
				}
			}
			FileOutputStream fos = new FileOutputStream("FailureUsersForCheck.xls");
			createExcelworkbook.write(fos);
			fos.close();
			isFileCreated = "FailureUsersForCheck.xls";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFileCreated;

	}

	private boolean validateDuplicateUser(String email, UsersImport userImport) throws Exception {
		boolean isUserFound = false;
		User user = userService.findByEmail(email);
		if(user != null){
			isUserFound = true;
			userImport.setReason("duplicate with");;
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
		Workbook createExcelworkbook = null;

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
			Workbook workbook = null;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
				
				int numberOfSheets = workbook.getNumberOfSheets();
				for (int i = 0; i < numberOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					int count = 0;
					Iterator<Row> rowIterator = sheet.iterator();
					while (rowIterator.hasNext()) {
						UsersImport userImport = new UsersImport();
						Row row = rowIterator.next();
						boolean isValidated = false;
						if (row.getRowNum() > 0) {
							isValidated = validateExeclData(row,userImport);
						}
						if (isValidated) {
							if (row.getRowNum() > 0) { // Excluding column names
								logger.info("Reading Row :" + row.getRowNum() + " records");
								if (row.getCell(0) == null || "".endsWith(row.getCell(0).toString())) {
									userImport.setEmployeeId("");
								} else {
									userImport.setEmployeeId(row.getCell(0).toString());
								}
								userImport.setFirstName(row.getCell(1).toString());
								//userImport.setLastName(row.getCell(2).toString());// LastName
								
								if (row.getCell(2) == null || "".endsWith(row.getCell(2).toString())) {
									userImport.setLastName("");
								} else {
									userImport.setLastName(row.getCell(2).toString());
								}
								userImport.setEmailId(row.getCell(3).toString());

								if (row.getCell(4) != null) {
									XSSFCell myCell = (XSSFCell) row.getCell(4);
									if (myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
										myCell.setCellType(Cell.CELL_TYPE_STRING);
										String mobileNumber = myCell.getStringCellValue();
										if (mobileNumber == null || "".endsWith(mobileNumber)) {
											userImport.setMobileNumber("");
										} else {
											userImport.setMobileNumber(mobileNumber);
										}
									}
								} else {
									userImport.setMobileNumber("");
								}

								if (row.getCell(5) != null) {
									XSSFCell myCell5 = (XSSFCell) row.getCell(5);
									if (myCell5.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
										myCell5.setCellType(Cell.CELL_TYPE_STRING);
										String rate = myCell5.getStringCellValue();
										if (rate == null || "".endsWith(rate)) {
											userImport.setRate("");
										} else {
											userImport.setRate(rate);
										}
									}
								} else {
									userImport.setRate("");
								}
								if (row.getCell(6) == null || "".endsWith(row.getCell(6).toString())) {
									userImport.setRatePer("");
								} else {
									userImport.setRatePer(row.getCell(6).toString());
								}
								if (row.getCell(7) == null || "".endsWith(row.getCell(7).toString())) {
									userImport.setDateString("");
								} else {
									userImport.setDateString(row.getCell(7).toString());

								}
								userImport.setRole(row.getCell(8).toString());
								if (row.getCell(9) == null || "".endsWith(row.getCell(9).toString())) {
									userImport.setInvoiceFrequency("");
								} else {
									userImport.setInvoiceFrequency(row.getCell(9).toString());
								}
								if (row.getCell(10) != null) {
									XSSFCell myCell10 = (XSSFCell) row.getCell(10);
									if (myCell10.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
										myCell10.setCellType(Cell.CELL_TYPE_STRING);
										String dayHours = myCell10.getStringCellValue();
										if (dayHours == null || "".endsWith(dayHours)) {
											userImport.setDayHours("");
										} else {
											userImport.setDayHours(dayHours);
										}
									}
								} else {
									userImport.setDayHours("");
								}
								 boolean isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport);
								 if(isUserFound){
									 if (row.getRowNum() > 0) {
											count++;
											createXlsxSheet(row, createExcelsheet, count,userImport);
										}
										
								 }else{
									 if(isMailChecked != null){
										 userImport.setIsMailChecked(Status.Y);
									 }else{
										 userImport.setIsMailChecked(Status.N);
									 }
									 saveBulkUser(userImport,orgLocations,user,isMailChecked,fileName);
								 }
//								saveBulkUser(userImport, orgLocations);
							}
						} else {
							if (row.getRowNum() > 0) {
								count++;
								createXlsxSheet(row, createExcelsheet, count,userImport);
							}
							
						}
					}
					if (count > 0) {
						FileOutputStream fos = new FileOutputStream("FailureUsersForCheck.xls");
						createExcelworkbook.write(fos);
						fos.close();
						isFileCreated = "FailureUsersForCheck.xls";
					}
				}
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = (Workbook) new HSSFWorkbook(fis);
//			}
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				int count = 0;
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					UsersImport userImport = new UsersImport();
					Row row = rowIterator.next();
					boolean isValidated = false;
					if (row.getRowNum() > 0) {
						isValidated = validateExeclData(row,userImport);
					}
					if (isValidated) {
						if (row.getRowNum() > 0) { // Excluding column names
							logger.info("Reading Row :" + row.getRowNum() + " records");
							if (row.getCell(0) == null || "".endsWith(row.getCell(0).toString())) {
								userImport.setEmployeeId("");
							} else {
								userImport.setEmployeeId(row.getCell(0).toString());
							}
							userImport.setFirstName(row.getCell(1).toString());
						//	userImport.setLastName(row.getCell(2).toString()); // LastName
							if (row.getCell(2) == null || "".endsWith(row.getCell(2).toString())) {
								userImport.setLastName("");
							} else {
								userImport.setLastName(row.getCell(2).toString());
							}
							userImport.setEmailId(row.getCell(3).toString());

							if (row.getCell(4) != null) {
								HSSFCell myCell = (HSSFCell) row.getCell(4);
								if (myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									myCell.setCellType(Cell.CELL_TYPE_STRING);
									String mobileNumber = myCell.getStringCellValue();
									if (mobileNumber == null || "".endsWith(mobileNumber)) {
										userImport.setMobileNumber("");
									} else {
										userImport.setMobileNumber(mobileNumber);
									}
								}
							} else {
								userImport.setMobileNumber("");
							}

							if (row.getCell(5) != null) {
								HSSFCell myCell5 = (HSSFCell) row.getCell(5);
								if (myCell5.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									myCell5.setCellType(Cell.CELL_TYPE_STRING);
									String rate = myCell5.getStringCellValue();
									if (rate == null || "".endsWith(rate)) {
										userImport.setRate("");
									} else {
										userImport.setRate(rate);
									}
								}
							} else {
								userImport.setRate("");
							}
							if (row.getCell(6) == null || "".endsWith(row.getCell(6).toString())) {
								userImport.setRatePer("");
							} else {
								userImport.setRatePer(row.getCell(6).toString());
							}
							if (row.getCell(7) == null || "".endsWith(row.getCell(7).toString())) {
								userImport.setDateString("");
							} else {
								userImport.setDateString(row.getCell(7).toString());

							}
							userImport.setRole(row.getCell(8).toString());
							if (row.getCell(9) == null || "".endsWith(row.getCell(9).toString())) {
								userImport.setInvoiceFrequency("");
							} else {
								userImport.setInvoiceFrequency(row.getCell(9).toString());
							}
							if (row.getCell(10) != null) {
								HSSFCell myCell10 = (HSSFCell) row.getCell(10);
								if (myCell10.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									myCell10.setCellType(Cell.CELL_TYPE_STRING);
									String dayHours = myCell10.getStringCellValue();
									if (dayHours == null || "".endsWith(dayHours)) {
										userImport.setDayHours("");
									} else {
										userImport.setDayHours(dayHours);
									}
								}
							} else {
								userImport.setDayHours("");
							}
							 boolean isUserFound = validateDuplicateUser(userImport.getEmailId(),userImport);
							 if(isUserFound){
								 if (row.getRowNum() > 0) {
										count++;
										 createXlsSheet(row, createExcelsheet, count,userImport);
									}
								
							 }else{
								 if(isMailChecked != null){
									 userImport.setIsMailChecked(Status.Y);
								 }else{
									 userImport.setIsMailChecked(Status.N);
								 }
								 saveBulkUser(userImport,orgLocations,user,isMailChecked,fileName);
							 }
//							saveBulkUser(userImport, orgLocations);
						}
					} else {
						if (row.getRowNum() > 0) {
							count++;
							createXlsSheet(row, createExcelsheet, count,userImport);
						}
						
					}
				}
				if (count > 0) {
					FileOutputStream fos = new FileOutputStream("FailureUsersForCheck.xls");
					createExcelworkbook.write(fos);
					fos.close();
					isFileCreated = "FailureUsersForCheck.xls";
				}
			}
		}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isFileCreated;

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
	private void saveBulkUser(UsersImport userImport, OrgLocations orgLocations, User loginUser, String isMailChecked, String fileName) throws Exception {
		User user = new User();
		UserRole userRole = new UserRole();
		Employees employee = new Employees();

		Roles role = rolesService.getRolesByDisplayName(userImport.getRole());

		user.setFirstName(userImport.getFirstName());
		user.setLastName(userImport.getLastName());
		user.setEmail(userImport.getEmailId());
		user.setMobilePhone(userImport.getMobileNumber());
		user.setCreatedDate(new Date());
		user.setUserState(Status.NOT_LOGGEDIN);
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		user.setIsMailSent(userImport.getIsMailChecked());
		employee.setEmployeeId(userImport.getEmployeeId());
		employee.setRate(userImport.getRate());
		employee.setRatePer(userImport.getRatePer());
		employee.setDailyHours(userImport.getDayHours());
//		if(!fileName.toLowerCase().endsWith("csv"))
		if (!"".equalsIgnoreCase(userImport.getDateString())) {
			SimpleDateFormat sdf = null;
			if(!fileName.toLowerCase().endsWith("csv")){
				sdf = new SimpleDateFormat("dd-MMM-yyyy");
			}else{
				sdf =  new SimpleDateFormat("dd/MM/yyyy");
			}
			Date date = sdf.parse(userImport.getDateString());
//			sdf = new SimpleDateFormat("MM/dd/yyyy");
			userImport.setDateOfJoin(date);
		} else {
			userImport.setDateOfJoin(null);
		}
		
		employee.setHireDate(userImport.getDateOfJoin());

		userRole.setUser(user);
		userRole.setRoles(role);

		userRole.setCreatedDate(new Date());
		userRole.setOrgLocations(orgLocations);

		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(userRole);
		user.setUserRole(userRoles);
		userService.addUser(user);
		employee.setUser(user);
		employeesService.addEmployee(employee);
		if(isMailChecked !=null){
			userService.sendEmail(user, loginUser, password);
		}
		

	}
/**
 * write excel sheet when validation fails
 * @param row
 * @param createExcelsheet
 * @param rowIndex
 * @param userImport 
 * @throws IOException
 */
	private void createXlsSheet(Row row, Sheet createExcelsheet, int rowIndex, UsersImport userImport) throws IOException {

		Row writeRow = createExcelsheet.createRow(rowIndex);

		if (row.getCell(0) != null && !"".endsWith(row.getCell(0).toString())) {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue(row.getCell(0).toString());
		} else {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue("");
		}
		if (row.getCell(1) != null && !"".endsWith(row.getCell(1).toString())) {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue(row.getCell(1).toString());
		} else {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue("");
		}
		if (row.getCell(2) != null && !"".endsWith(row.getCell(2).toString())) {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue(row.getCell(2).toString());
		} else {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue("");
		}
		if (row.getCell(3) != null && !"".endsWith(row.getCell(3).toString())) {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue(row.getCell(3).toString());
		} else {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue("");
		}
		if (row.getRowNum() == 0) {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue(row.getCell(4).toString());
		} else {
			if (row.getCell(4) != null && !"".endsWith(row.getCell(4).toString())) {
				HSSFCell myCell4 = (HSSFCell) row.getCell(4);
				if (myCell4.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					myCell4.setCellType(Cell.CELL_TYPE_STRING);
					String mobileNumber = myCell4.getStringCellValue();
					if (mobileNumber != null && !"".endsWith(mobileNumber)) {
						Cell cell4 = writeRow.createCell(4);
						cell4.setCellValue(mobileNumber);
					}
				}
			} else {
				Cell cell4 = writeRow.createCell(4);
				cell4.setCellValue("");
			}
		}
		if (row.getRowNum() == 0) {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue(row.getCell(5).toString());
		} else {
			if (row.getCell(5) != null && !"".endsWith(row.getCell(5).toString())) {

				HSSFCell myCell5 = (HSSFCell) row.getCell(5);
				if (myCell5.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					myCell5.setCellType(Cell.CELL_TYPE_STRING);
					String rate = myCell5.getStringCellValue();
					if (rate != null || !"".endsWith(rate)) {
						Cell cell5 = writeRow.createCell(5);
						cell5.setCellValue(rate);
					}
				}
			} else {
				Cell cell5 = writeRow.createCell(5);
				cell5.setCellValue("");
			}
		}
		if (row.getCell(6) != null && !"".endsWith(row.getCell(6).toString())) {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue(row.getCell(6).toString());
		} else {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue("");
		}
		if (row.getCell(7) != null && !"".endsWith(row.getCell(7).toString())) {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue(row.getCell(7).toString());
		} else {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue("");
		}
		if (row.getCell(8) != null && !"".endsWith(row.getCell(8).toString())) {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue(row.getCell(8).toString());
		} else {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue("");
		}
		if (row.getCell(9) != null && !"".endsWith(row.getCell(9).toString())) {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue(row.getCell(9).toString());
		} else {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue("");
		}
		if (row.getRowNum() == 0) {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue(row.getCell(10).toString());
		} else {
			if (row.getCell(10) != null && !"".endsWith(row.getCell(10).toString())) {

				HSSFCell myCell10 = (HSSFCell) row.getCell(10);
				if (myCell10.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					myCell10.setCellType(Cell.CELL_TYPE_STRING);
					String dayHours = myCell10.getStringCellValue();
					if (dayHours != null || !"".endsWith(dayHours)) {
						Cell cell10 = writeRow.createCell(10);
						cell10.setCellValue(dayHours);
					}
				}
			} else {
				Cell cell10 = writeRow.createCell(10);
				cell10.setCellValue("");
			}
		}
		if (userImport.getReason() !=null) {
			writeRow.createCell(11).setCellValue(userImport.getReason());
		} 
	}
	
	
	private void createXlsxSheet(Row row, Sheet createExcelsheet, int rowIndex, UsersImport userImport) throws IOException {

		Row writeRow = createExcelsheet.createRow(rowIndex);

		if (row.getCell(0) != null && !"".endsWith(row.getCell(0).toString())) {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue(row.getCell(0).toString());
		} else {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue("");
		}
		if (row.getCell(1) != null && !"".endsWith(row.getCell(1).toString())) {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue(row.getCell(1).toString());
		} else {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue("");
		}
		if (row.getCell(2) != null && !"".endsWith(row.getCell(2).toString())) {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue(row.getCell(2).toString());
		} else {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue("");
		}
		if (row.getCell(3) != null && !"".endsWith(row.getCell(3).toString())) {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue(row.getCell(3).toString());
		} else {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue("");
		}
		if (row.getRowNum() == 0) {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue(row.getCell(4).toString());
		} else {
			if (row.getCell(4) != null && !"".endsWith(row.getCell(4).toString())) {
				XSSFCell myCell4 = (XSSFCell) row.getCell(4);
				if (myCell4.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					myCell4.setCellType(Cell.CELL_TYPE_STRING);
					String mobileNumber = myCell4.getStringCellValue();
					if (mobileNumber != null && !"".endsWith(mobileNumber)) {
						Cell cell4 = writeRow.createCell(4);
						cell4.setCellValue(mobileNumber);
					}
				}
			} else {
				Cell cell4 = writeRow.createCell(4);
				cell4.setCellValue("");
			}
		}
		if (row.getRowNum() == 0) {
			Cell cell5 = writeRow.createCell(5);
			cell5.setCellValue(row.getCell(5).toString());
		} else {
			if (row.getCell(5) != null && !"".endsWith(row.getCell(5).toString())) {

				XSSFCell myCell5 = (XSSFCell) row.getCell(5);
				if (myCell5.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					myCell5.setCellType(Cell.CELL_TYPE_STRING);
					String rate = myCell5.getStringCellValue();
					if (rate != null || !"".endsWith(rate)) {
						Cell cell5 = writeRow.createCell(5);
						cell5.setCellValue(rate);
					}
				}
			} else {
				Cell cell5 = writeRow.createCell(5);
				cell5.setCellValue("");
			}
		}
		if (row.getCell(6) != null && !"".endsWith(row.getCell(6).toString())) {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue(row.getCell(6).toString());
		} else {
			Cell cell6 = writeRow.createCell(6);
			cell6.setCellValue("");
		}
		if (row.getCell(7) != null && !"".endsWith(row.getCell(7).toString())) {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue(row.getCell(7).toString());
		} else {
			Cell cell7 = writeRow.createCell(7);
			cell7.setCellValue("");
		}
		if (row.getCell(8) != null && !"".endsWith(row.getCell(8).toString())) {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue(row.getCell(8).toString());
		} else {
			Cell cell8 = writeRow.createCell(8);
			cell8.setCellValue("");
		}
		if (row.getCell(9) != null && !"".endsWith(row.getCell(9).toString())) {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue(row.getCell(9).toString());
		} else {
			Cell cell9 = writeRow.createCell(9);
			cell9.setCellValue("");
		}
		if (row.getRowNum() == 0) {
			Cell cell10 = writeRow.createCell(10);
			cell10.setCellValue(row.getCell(10).toString());
		} else {
			if (row.getCell(10) != null && !"".endsWith(row.getCell(10).toString())) {

				XSSFCell myCell10 = (XSSFCell) row.getCell(10);
				if (myCell10.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					myCell10.setCellType(Cell.CELL_TYPE_STRING);
					String dayHours = myCell10.getStringCellValue();
					if (dayHours != null || !"".endsWith(dayHours)) {
						Cell cell10 = writeRow.createCell(10);
						cell10.setCellValue(dayHours);
					}
				}
			} else {
				Cell cell10 = writeRow.createCell(10);
				cell10.setCellValue("");
			}
		}
		if (userImport.getReason() !=null) {
			writeRow.createCell(11).setCellValue(userImport.getReason());
		} 
	}
/**
 * Validate Excel data whle storing it into Database
 * @param row
 * @param userImport 
 * @return
 */
	private boolean validateExeclData(Row row, UsersImport userImport) {

		logger.info("Reading Row :" + row.getRowNum() + " records");
		boolean isDateValidated=false; 
		if (row.getRowNum() > 0) { // Excluding column names

			if (row.getCell(1) == null || "".equals(row.getCell(1).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}
			/*if (row.getCell(2) == null || "".equals(row.getCell(2).toString())) {
				return false;
			}*/
			if (row.getCell(3) == null || "".equals(row.getCell(3).toString()) || !validateEmail(row.getCell(3).toString())) {
				userImport.setReason("Invalid email");
				return false;
			}
			
			if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
				isDateValidated = isThisDateValid(row.getCell(7).toString(), "dd-MMM-yyyy");
				userImport.setReason("Invalid Date");
				return isDateValidated;
			}

			if (row.getCell(8) == null || "".endsWith(row.getCell(8).toString())) {
				userImport.setReason("Required fields is not filled");
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Validate CSV data whle storing it into Database
	 * @param userImport 
	 * @param userImport
	 * @return
	 */
	private boolean validateCsvData(CSVRecord record, UsersImport userImport) {

		boolean isDateValidated = false;
			if (record.get(Constants.FIRST_NAME) == null || "".equalsIgnoreCase(record.get(Constants.FIRST_NAME))) {
				userImport.setReason("Required field mandatory");
				return false;
			}
			/*if (row.getCell(2) == null || "".equals(row.getCell(2).toString())) {
				return false;
			}*/
			if (record.get(Constants.EMAIL_ID) == null || !validateEmail(record.get(Constants.EMAIL_ID))) {
				userImport.setReason("Required field is mandatory");
				return false;
			}
			if (record.get(Constants.DATEOFJOIN) != null && !"".equalsIgnoreCase(record.get(Constants.DATEOFJOIN))) {
				isDateValidated = isThisDateValid(record.get(Constants.DATEOFJOIN), "dd/MM/yyyy");
				userImport.setReason("Invalid Date");
				return isDateValidated;
			}
			if (record.get(Constants.ROLE) == null) {
				userImport.setReason("Required field is  mandatory");
				return false;
			}
		return true;
	}
/**
 * validate email
 * @param email
 * @return
 */
	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
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
	
	private  boolean isThisDateValid(String dateToValidate, String dateFromat){

		if(dateToValidate == null){
		return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

		//if not valid, it will throw ParseException
		Date date = sdf.parse(dateToValidate);
		System.out.println(date);

		} catch (ParseException e) {

		logger.error(""+e.getMessage());
		return false;
		}

		return true;
		}
//		public static void main(String[] args) throws ParseException {
//		System.out.println(isThisDateValid("31/05/2012", "dd/MM/yyyy"));

		

}
