package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.annotations.Check;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.EmployeeListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.BisUserMappingDao;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class BisUserMappingServiceImpl implements BisUserMappingService {

	private static final Logger logger = LoggerFactory.getLogger(BisUserMappingServiceImpl.class);

	@Autowired
	private BisUserMappingDao bisUserMappingDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConversionUtil conversionUtil;

	@Override
	public Integer saveBisUserData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)
			throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = (Workbook) new HSSFWorkbook();
		}

		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			int version;
			version = getBisUserMappingVersionByLocationId(orgLocation.getId());
			version += 1;
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) { // Excluding column names
						BisUserMapping bisUserMapping = new BisUserMapping();
						float rate = 0;
						float fixedRate = 0;
						float payRate = 0;
						float fixedPay = 0;
						float insurance = 0;
						float ded = 0;
						float effectiveRate = 0;
						float payrollDiff = 0;
						float advanceHp = 0;
						float balance = 0;
						if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
							String company = getCellValueAsString(row.getCell(0), fileName);
							bisUserMapping.setCompany(company);
						}
						if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
							String branch = getCellValueAsString(row.getCell(1), fileName);
							bisUserMapping.setBranch(branch);
						}
						if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
							String empId = getCellValueAsString(row.getCell(2), fileName);
							bisUserMapping.setEmpId(empId.split("\\.")[0]);
							bisUserMapping.setVersion(version);
						}
						if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
							String candidateName = getCellValueAsString(row.getCell(3), fileName);
							bisUserMapping.setCandidateName(candidateName);
						}
						if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
							rate = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
							bisUserMapping.setRate(Math.round(rate * 100D) / 100D);
						}
						if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
							fixedRate = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
							bisUserMapping.setFixedRate(Math.round(fixedRate * 100D) / 100D);
						}
						if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
							payRate = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
							bisUserMapping.setPayRate(Math.round(payRate * 100D) / 100D);
						}
						if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
							fixedPay = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
							bisUserMapping.setFixedPay(Math.round(fixedPay * 100D) / 100D);
						}
						if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
							insurance = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
							bisUserMapping.setInsurance(Math.round(insurance * 100D) / 100D);
						}
						if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
							ded = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
							bisUserMapping.setDed(Math.round(ded * 100D) / 100D );
						}
						if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
							effectiveRate = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							bisUserMapping.setEffectiveRate(Math.round(effectiveRate * 100D) / 100D);
						}
						if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
							payrollDiff = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							bisUserMapping.setPayrollDifference(Math.round(payrollDiff * 100D) / 100D);
						}
						if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
							String immigration = getCellValueAsString(row.getCell(12), fileName);
							bisUserMapping.setImmigration(immigration);
						}
						if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
							String comments = getCellValueAsString(row.getCell(13), fileName);
							bisUserMapping.setComments(comments);
						}
						if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
						 advanceHp = Float.parseFloat(getCellValueAsString(row.getCell(14), fileName));
							bisUserMapping.setAdvanceHcp(Math.round(advanceHp * 100D) / 100D);
						}
						if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(15).toString())) {
							balance = Float.parseFloat(getCellValueAsString(row.getCell(15), fileName));
							bisUserMapping.setBalance(Math.round(balance * 100D) / 100D);
						}
						bisUserMapping.setCreatedBy(user.getUserCode());
						bisUserMapping.setCreatedDate(new Date());
						bisUserMapping.setUpdatedBy(user.getUserCode());
						bisUserMapping.setUpdatedDate(new Date());
						bisUserMapping.setOrgLocationId(orgLocation);
						BisUserMapping bisUserList = saveBisUserMapping(bisUserMapping);
						if (bisUserList != null) {
							count++;
						}
					}
				}

			}

			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}

		return count;

	}

	private BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception {
		return bisUserMappingDao.saveBisUserMapping(bisUserMapping);
	}
	
	
	@Override
	public Integer saveChecksData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)
			throws Exception {
		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = (Workbook) new HSSFWorkbook();
		}
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			
			int version;
			version = getChecksVersionByLocationId(orgLocation.getId());
			version += 1;
			
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) { // Excluding column names
						Checks checks = new Checks();
						double legalFees = 0;
						double advance = 0;
						double vendorPayments = 0;
						double generalExpenses = 0;
						double payrollChecks = 0;
						double amount = 0;
						boolean notEmpty = checkRowEmptyOrNot(row);
						if(notEmpty) {
						if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
							String empId = getCellValueAsString(row.getCell(0), fileName);
							checks.setEmpId(empId.split("\\.")[0]);
							checks.setVersion(version);
						}
						if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
							String empName = getCellValueAsString(row.getCell(1), fileName);
							checks.setEmpName(empName);
						}
						if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
							Date date = getCellDate(row.getCell(2), fileName);
							if (date != null) {
								checks.setDate(date);
							} else {
								String checkDate = getCellValueAsString(row.getCell(2), fileName);
								checks.setDate(conversionUtil.getDateFromMMDDYYY(checkDate));
							}
						}
						if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
							String typeOfExpenses = getCellValueAsString(row.getCell(3), fileName);
							checks.setTypeOfExpense(typeOfExpenses);
						}
						if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
							String bankName = getCellValueAsString(row.getCell(4), fileName);
							checks.setBankName(bankName);
						}
						if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
							
							String month_name = getCellValueAsString(row.getCell(5), fileName);
//							SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
//							String month_name = month_date.format(date);
							checks.setMonth(month_name);
						}
						if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
							legalFees = Double.parseDouble(getCellValueAsString(row.getCell(6), fileName));
							checks.setLegalFees(Math.round(legalFees * 100D) / 100D);
						}
						if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
							advance = Double.parseDouble(getCellValueAsString(row.getCell(7), fileName));
							checks.setAdvance(Math.round(advance * 100D) / 100D);
						}
						if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
							 generalExpenses = Double.parseDouble(getCellValueAsString(row.getCell(8), fileName));
							checks.setGeneralExpenses(generalExpenses);
						}
						if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
							vendorPayments = Double.parseDouble(getCellValueAsString(row.getCell(9), fileName));
							checks.setVendorPayments(Math.round(vendorPayments * 100D) / 100D);
						}
						if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
							checks.setElectronicWithdrawals(getCellValueAsString(row.getCell(10), fileName));
						}
						if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
							checks.setHealthInsurance(getCellValueAsString(row.getCell(11), fileName));
						}
						if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
							checks.setImmigration(getCellValueAsString(row.getCell(12), fileName));
						}
						if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
							payrollChecks = Double.parseDouble(getCellValueAsString(row.getCell(13), fileName));
							checks.setPayrollChecks(Math.round(payrollChecks * 100D) / 100D);
						}
						if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
							amount = Double.parseDouble(getCellValueAsString(row.getCell(14), fileName));
							checks.setAmount(Math.round(amount * 100D) / 100D);
						}
						if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(15).toString())) {
							String checkNumber = getCellValueAsString(row.getCell(15), fileName);
							checks.setCheckNo(checkNumber.split("\\.")[0]);
						}
						checks.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						checks.setCreatedBy(user.getUserCode());
						checks.setCreatedDate(new Date());
						checks.setUpdatedBy(user.getUserCode());
						checks.setUpdatedDate(new Date());
						checks.setOrgLocationId(orgLocation);
						Checks savedChecks = saveChecks(checks);
						if (savedChecks != null) {
							count++;
						}
					}
					}
				}

			}

			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}

		return count;
	}

	private Checks saveChecks(Checks checks) throws Exception {
		return bisUserMappingDao.saveChecks(checks);
	}

	private String getCellValueAsString(Cell cell, String fileName) {
		String cellValue = "";
		double xssfCellValue = 0;
		if (fileName.toLowerCase().endsWith("xls")) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				xssfCellValue = hssfCell.getNumericCellValue();
				cellValue = String.valueOf(xssfCellValue);
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA && hssfCell.getCachedFormulaResultType() == HSSFCell.CELL_TYPE_NUMERIC){
				cellValue = String.valueOf(hssfCell.getNumericCellValue());
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA && hssfCell.getCachedFormulaResultType() == HSSFCell.CELL_TYPE_STRING){
				cellValue = String.valueOf(hssfCell.getStringCellValue());	
			}else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				cellValue = cell.getStringCellValue();
			}
		} else {
			XSSFCell xssfCell = (XSSFCell) cell;
			if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				xssfCellValue = xssfCell.getNumericCellValue();
				cellValue = String.valueOf(xssfCellValue);
			} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA
					&& xssfCell.getCachedFormulaResultType() == XSSFCell.CELL_TYPE_NUMERIC) {
				cellValue = String.valueOf(xssfCell.getNumericCellValue());
			} else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA
					&& xssfCell.getCachedFormulaResultType() == XSSFCell.CELL_TYPE_STRING) {
				cellValue = String.valueOf(xssfCell.getStringCellValue());
			} else if(xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}

	@Override
	public List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId) throws Exception {
		return bisUserMappingDao.getBisUserListByOrgLocationId(orgLocationId);
	}

	@Override
	public BisUserMapping mergeUser(JSONObject jsonObj,Long orgLocationId) throws Exception {
		BisUserMapping bisuser = null;
		if(jsonObj.has("slUserId") && jsonObj.has("importedUserId")){
			Long userId = Long.parseLong(jsonObj.getString("slUserId"));
			Long importedUserId = Long.parseLong(jsonObj.getString("importedUserId"));
			User slUser = userService.getUserById(userId);
			BisUserMapping bisUser = bisUserMappingDao.getBisUserById(importedUserId);
			if(slUser != null && bisUser !=null){
				bisUser.setUserId(slUser);
				bisUser.setStatus("MAPPED");
				 bisuser = bisUserMappingDao.saveBisUserMapping(bisUser);
			}
		}
		return bisuser;
	}

	@Override
	public List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		return bisUserMappingDao.getMappedUsersListByOrgLocationId(orgLocationId);
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

	@Override
	public ResponseList<BisUserMapping> getEmployeeList(EmployeeListDTO employeeListDTO,Long orgLocationId) throws Exception {
		return bisUserMappingDao.getEmployeeList(employeeListDTO,orgLocationId);
	}

	@Override
	public List<BisUserMapping> getMasterListByEmpId(Long empId, OrgLocations orgLocations) throws Exception {
		return bisUserMappingDao.getMasterListByEmpId(empId,orgLocations);
	}

	@Override
	public int getBisUserMappingVersionByLocationId(Long locationId)
	{
		return bisUserMappingDao.getBisUserMappingVersionByLocationId(locationId);
	}
	
	@Override
	public int getChecksVersionByLocationId(Long locationId)
	{
		return bisUserMappingDao.getChecksVersionByLocationId(locationId);
	}
  
	/**
	 * check the rows empty or not
	 * 
	 * @param row
	 * @return boolean
	 */

	private boolean checkRowEmptyOrNot(Row row) {
		if     (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString()) 
				|| row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString()) 
				|| row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())
				|| row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString()) 
				|| row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString()) 
				|| row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())
				|| row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString()) 
				|| row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString()) 
				|| row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString()) 
				|| row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())
				|| row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())
				|| row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString()) 
				|| row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString()) 
				|| row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {

			return true;
		}
		return false;
	}
}
