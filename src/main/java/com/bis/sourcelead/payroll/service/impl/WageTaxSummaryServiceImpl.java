package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.WagesTaxSummaryDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.PayrollHelper;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.rest.EmployeePayrollRestController;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import org.apache.commons.lang3.StringUtils;


@Service
public class WageTaxSummaryServiceImpl implements WageTaxSummaryService{
	
	private static final Logger logger = LoggerFactory.getLogger(WageTaxSummaryServiceImpl.class);

	@Autowired
	WageTaxSummaryDao wageTaxSummaryDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	PayrollHelper payrollHelper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CandidateService candidateService;
	
	@Override
	public WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary) throws Exception {
		return wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
	}

	@Override
	public Integer saveWagesTaxSummary(MultipartFile multipartFile, OrgLocations orgLocation, User user,
			JSONObject jsonObj) throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		List<WageTaxSummary> wageTaxSummaryList = new ArrayList<>();
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
			Map<String, String> map = new HashMap<String, String>();
			int isValid = 0;
			
			DateTime zoned = new DateTime();
			DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			DateTime oneDay = zoned;
			String oneDayStartDate = outputFormatter1.print(oneDay);
			String oneDayStartDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, oneDayStartDate);
			Date startDate = ts.parse(oneDayStartDateString);
			String oneDayEndDate = outputFormatter.print(oneDay);
			String oneDayEndDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, oneDayEndDate);
			Date endDate = ts.parse(oneDayEndDateString);
			
			int version;
			version = getWageTaxSummaryVersionByLocationId(orgLocation.getId());
			version += 1;
			
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				String emppID = null;
				
				if(i == 0){
					isValid =  payrollHelper.isValidTemplateForWagesTaxSummary(rowIterator);
				}
				if(isValid == 1){
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();

					if (row.getRowNum() > 0) { // Excluding column names
						WageTaxSummary wageTaxSummary = new WageTaxSummary();
						String ssnNumber = getSsnNumber(row.getCell(1), fileName);
						if (!StringUtils.containsIgnoreCase(ssnNumber, "SSN:")
								&& !StringUtils.containsIgnoreCase(ssnNumber, "Total")) {
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empID = getCellValueAsString(row.getCell(0), fileName);
								emppID = empID.split("\\.")[0];
							}
							
							if(emppID != null){
								wageTaxSummary.setVersion(version);
							}

							float gross = 0f;
							float fwt = 0f;
							float ssec = 0f;
							float med = 0f;
							float swt = 0f;
							float sdiSui = 0f;
							float local = 0f;
							float deds = 0f;
							float net = 0f;
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empId = getCellValueAsString(row.getCell(0), fileName);
								wageTaxSummary.setEmployeeId(empId.split("\\.")[0]);

							}

							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String employeeName = getCellValueAsString(row.getCell(1), fileName);
								wageTaxSummary.setEmployeeName(employeeName);

							}
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								Date checkDate = getCellDate(row.getCell(2), fileName);
								if(checkDate != null){
									wageTaxSummary.setCheckDate(checkDate);
								}else{
									String checkDateStr = getCellValueAsString(row.getCell(2), fileName);
									wageTaxSummary.setCheckDate(conversionUtil.getDateFromMMDDYYY(checkDateStr));
								}
							}
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								String checkNo = getCellValueAsString(row.getCell(3), fileName);
								wageTaxSummary.setCheckNo(checkNo);

							}
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								gross = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								wageTaxSummary.setGross(gross);

							}
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								fwt = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								wageTaxSummary.setFwt(fwt);

							}
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								ssec = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								wageTaxSummary.setSsec(ssec);

							}
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								med = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								wageTaxSummary.setMed(med);

							}
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								swt = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								wageTaxSummary.setSwt(swt);

							}
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								sdiSui = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
								wageTaxSummary.setSdiSui(sdiSui);

							}
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								local = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
								wageTaxSummary.setLocal(local);

							}
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								deds = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
								wageTaxSummary.setDeds(deds);

							}
							if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
								net = Float.parseFloat(getCellValueAsString(row.getCell(12), fileName));
								wageTaxSummary.setNet(net);
							}
							if (jsonObj.has("fromDate")) {
								if (jsonObj.getString("fromDate") != null
										&& !"".equalsIgnoreCase(jsonObj.getString("fromDate"))) {
									wageTaxSummary.setFromDate(
											conversionUtil.getDateFromString(jsonObj.getString("fromDate")));
								}

							}
							if (jsonObj.has("toDate")) {
								if (jsonObj.getString("toDate") != null
										&& !"".equalsIgnoreCase(jsonObj.getString("toDate"))) {
									wageTaxSummary
											.setToDate(conversionUtil.getDateFromString(jsonObj.getString("toDate")));
								}
							}
							if (jsonObj.has("batchNumber")) {
								if (jsonObj.getString("batchNumber") != null
										&& !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))) {
									wageTaxSummary.setBatchNumber(jsonObj.getString("batchNumber"));
								}
							}
							wageTaxSummary.setCreatedBy(user.getUserCode());
							wageTaxSummary.setCreatedDate(new Date());
							wageTaxSummary.setUpdatedBy(user.getUserCode());
							wageTaxSummary.setUpdatedDate(new Date());
							wageTaxSummary.setOrgLocationId(orgLocation);
							wageTaxSummaryList.add(wageTaxSummary);
						} else {
							if (StringUtils.containsIgnoreCase(ssnNumber, "SSN:")) {
								if (emppID != null) {
									map.put(emppID, ssnNumber);
								}
							}
						}

					}
				}
				}else{
					return count;
				}
				for (WageTaxSummary wageTaxSummary : wageTaxSummaryList) {
					StringBuilder sb = new StringBuilder();
					String ssnNumber = map.get(wageTaxSummary.getEmployeeId());
					String[] arr = ssnNumber.split(":");
					String ssn = arr[1];
					String last4DigitsSsn = ssn.substring(ssn.length() - 4);
					String enCodedSsnNumber = sourceleadPasswordEncoder.encode(ssn);
					sb.append(last4DigitsSsn).append("-").append(enCodedSsnNumber);
					wageTaxSummary.setSsnNumber(sb.toString());
					WageTaxSummary wageTax = wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
					if (wageTax != null) {
						count++;
					}
				}
			}

			fis.close();
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return count;

	}
	

	private  String getSsnNumber(Cell cell, String fileName) {
		return getCellValueAsString(cell, fileName);
	
}
	
	
	
	@Override
	public ResponseList<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation,WagesTaxSummaryDTO wagesTaxSummaryDTO) throws Exception
	{
		return wageTaxSummaryDao.getWagesAndTaxListByOrgLocationId(orgLocation,wagesTaxSummaryDTO);
	//TODO 	please do not remove
//		if(wagesAndTaxList != null){
//			for(WageTaxSummary wts : wagesAndTaxList){
//				StringBuilder sb = new StringBuilder();
//				if(null !=  wts.getFromDate()){
//					wts.setFromDateStr(conversionUtil.getDateAsString(wts.getFromDate()));
//				}
//				if(null != wts.getToDate()){
//					wts.setToDateStr(conversionUtil.getDateAsString(wts.getToDate()));
//				}
//				
//				if(wts.getCheckDate() !=null   ){
//					wts.setCheckDateStr(conversionUtil.getDateAsString(wts.getCheckDate()));
//				}
//				if(wts.getCheckNo() != null){
//					wts.setCheckNo(wts.getCheckNo());
//				}
//				String ssn = wts.getSsnNumber();
//				String[] arr = ssn.split("-");
//				sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
//				wts.setSsnNumber(sb.toString());
//			}
//			
//			Collections.sort(wagesAndTaxList, new Comparator() {  
//	            @Override  
//	            public int compare(Object obj1, Object obj2) {  
//	            	WageTaxSummary wageTaxSummary1 = (WageTaxSummary)obj1;  
//	            	WageTaxSummary wageTaxSummary2 = (WageTaxSummary)obj2; 
//	            	return wageTaxSummary1.getEmployeeName().compareToIgnoreCase(wageTaxSummary2.getEmployeeName());  
//	            }  
//	        });
//		}
//		return new ResponseList<>();
	}

	@Override
	public Integer saveWagesTaxSummaryBulk(MultipartFile multipartFile, OrgLocations orgLocation, User user,JSONObject jsonObject) throws Exception {


		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		List<WageTaxSummary> wageTaxSummaryList = new ArrayList<>();
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
			Map<String,String> map = new HashMap<String,String>();
			
			int version;
			version = getWageTaxSummaryVersionByLocationId(orgLocation.getId());
			version += 1;
			
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				String employeeId = null;
				String ssn = null;
				String employeeName = null;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
						if (row.getRowNum() > 0) { // Excluding column names
							WageTaxSummary  wageTaxSummary = new WageTaxSummary();
							String ssnNumber = getSsnNumber(row.getCell(1),fileName);
							if(!StringUtils.containsIgnoreCase(ssnNumber, "Total")){
								
								float gross = 0f;
								float fwt = 0f;
								float ssec = 0f;
								float med = 0f;
								float swt = 0f; 
								float sdiSui = 0f;
								float local = 0f; 
								float deds = 0f; 
								float net = 0f;
								if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
									String empId = getCellValueAsString(row.getCell(0), fileName);
									employeeId = empId.split("\\.")[0];
									
								} 
								if(employeeId !=null){
									wageTaxSummary.setEmployeeId(employeeId);
								}
								
								wageTaxSummary.setVersion(version);
								
								if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
									String empName = getCellValueAsString(row.getCell(1), fileName);
									if(StringUtils.containsIgnoreCase(empName, "SSN:")){
										ssn = empName;
									}else{
										 employeeName = empName;
									}
									
								} 
								if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
									Date checkDate = getCellDate(row.getCell(2), fileName);
									if(checkDate != null){
										wageTaxSummary.setCheckDate(checkDate);
									}else{
										String checkDateStr = getCellValueAsString(row.getCell(2), fileName);
										wageTaxSummary.setCheckDate(conversionUtil.getDateFromMMDDYYY(checkDateStr));
									}
								} 
								if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
									String checkNo = getCellValueAsString(row.getCell(3), fileName);
									wageTaxSummary.setCheckNo(checkNo.split("\\.")[0]);
									
								} 
								if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
									gross = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
									wageTaxSummary.setGross(gross);
									
								} 
								if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
									fwt = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
									wageTaxSummary.setFwt(fwt);
									
								} 
								if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
									ssec = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
									wageTaxSummary.setSsec(ssec);
									
								} 
								if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
									med = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
									wageTaxSummary.setMed(med);
									
								} 
								if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
									swt = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
									wageTaxSummary.setSwt(swt);
									
								} 
								if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
									sdiSui = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
									wageTaxSummary.setSdiSui(sdiSui);
									
								} 
								if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
									local = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
									wageTaxSummary.setLocal(local);
									
								} 
								if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
									deds = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
									wageTaxSummary.setDeds(deds);
									
								} 
								if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
									net = Float.parseFloat(getCellValueAsString(row.getCell(12), fileName));
									wageTaxSummary.setNet(net);
								}
								
								if (jsonObject.has("fromDate")) {
									if (jsonObject.getString("fromDate") != null
											&& !"".equalsIgnoreCase(jsonObject.getString("fromDate"))) {
										wageTaxSummary.setFromDate(
												conversionUtil.getDateFromString(jsonObject.getString("fromDate")));
									}

								}
								if (jsonObject.has("toDate")) {
									if (jsonObject.getString("toDate") != null
											&& !"".equalsIgnoreCase(jsonObject.getString("toDate"))) {
										wageTaxSummary
												.setToDate(conversionUtil.getDateFromString(jsonObject.getString("toDate")));
									}
								}
								
								wageTaxSummary.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
								wageTaxSummary.setEmployeeName(employeeName);
								wageTaxSummary.setEmployeeId(employeeId);
								wageTaxSummary.setSsnNumber(ssn);
								wageTaxSummary.setCreatedBy(user.getUserCode());
								wageTaxSummary.setCreatedDate(new Date());
								wageTaxSummary.setUpdatedBy(user.getUserCode());
								wageTaxSummary.setUpdatedDate(new Date());
								wageTaxSummary.setOrgLocationId(orgLocation);
								wageTaxSummaryList.add(wageTaxSummary);
								}
						}
					}
			for(WageTaxSummary wageTaxSummary:wageTaxSummaryList){
				StringBuilder sb = new StringBuilder();
				String ssnNumber = null;
				if(wageTaxSummary.getSsnNumber() != null){
					 ssnNumber = wageTaxSummary.getSsnNumber();
				}else{
					ssnNumber = wageTaxSummaryList.get(2).getSsnNumber();
				}
				String[] arr = ssnNumber.split(":");
				String ssNbr = arr[1];
				String last4DigitsSsn = ssNbr.substring(ssNbr.length() - 4);
				String enCodedSsnNumber = sourceleadPasswordEncoder.encode(ssNbr);
				sb.append(last4DigitsSsn).append("-").append(enCodedSsnNumber);
				wageTaxSummary.setSsnNumber(sb.toString());
				WageTaxSummary wageTax = wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
				if (wageTax != null) {
					count++;
				}
			}
				}

			
			fis.close();
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return count;
	}
	
	private String getCellValueAsString(Cell cell, String fileName) {
		String cellValue = "";
		double xssfCellValue = 0;
		if (fileName.toLowerCase().endsWith("xls")) {
			HSSFCell hssfCell = (HSSFCell) cell;
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				hssfCell.setCellType(Cell.CELL_TYPE_STRING);
				String hssfCellValue = hssfCell.getStringCellValue();
				if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
					cellValue = hssfCellValue;
				} 
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
	public List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		

		List<WageTaxSummary> wagesAndTaxList = wageTaxSummaryDao.getWagesAndTaxListByEmployeeId(orgLocations,empId);
		
		if(wagesAndTaxList != null){
			for(WageTaxSummary wts : wagesAndTaxList){
				StringBuilder sb = new StringBuilder();
				if(null !=  wts.getFromDate()){
					wts.setFromDateStr(conversionUtil.getDateAsString(wts.getFromDate()));
				}
				if(null != wts.getToDate()){
					wts.setToDateStr(conversionUtil.getDateAsString(wts.getToDate()));
				}
				
				if(wts.getCheckDate() !=null   ){
					wts.setCheckDateStr(conversionUtil.getDateAsString(wts.getCheckDate()));
				}
				if(wts.getCheckNo() != null){
					wts.setCheckNo(wts.getCheckNo());
				}
				String ssn = wts.getSsnNumber();
				String[] arr = ssn.split("-");
				sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
				wts.setSsnNumber(sb.toString());
			}
			
			Collections.sort(wagesAndTaxList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	WageTaxSummary wageTaxSummary1 = (WageTaxSummary)obj1;  
	            	WageTaxSummary wageTaxSummary2 = (WageTaxSummary)obj2; 
	            	return wageTaxSummary1.getEmployeeName().compareToIgnoreCase(wageTaxSummary2.getEmployeeName());  
	            }  
	        });
		}
		return wagesAndTaxList;
	}

	@Override
	public int mergeUser(JSONObject jsonObj, OrgLocations orgLocation) throws Exception {
		BisUserMapping bisuser = null;
		int count =0;
		if(jsonObj.has("slUserId") && jsonObj.has("importedUserId")){
			Long sluserId = Long.parseLong(jsonObj.getString("slUserId"));
			Long importedUserId = Long.parseLong(jsonObj.getString("importedUserId"));
			User slUser = userService.getUserById(sluserId);
			List<WageTaxSummary> importUserList = wageTaxSummaryDao.getWagesAndTaxListByEmployeeId(orgLocation, importedUserId);
			for(WageTaxSummary wageTaxSummary:importUserList){
				wageTaxSummary.setUserId(slUser);
				wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
				count++;
			}

	}
		return count;
	}

	@Override
	public List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations) throws Exception {
		return wageTaxSummaryDao.getAllImportedUsers(orgLocations);
	}

	@Override
	public List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId) throws Exception {
		return wageTaxSummaryDao.getMappedUsersListByOrgLocationId(locationId);
	}

	@Override
	public List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation) throws Exception {
		return wageTaxSummaryDao.getUniqueWagesAndTaxListByOrgLocationId(orgLocation);
	}

	@Override
	public WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String EmpId,Long locationId,Date startDate, Date endDate)
	{
		return wageTaxSummaryDao.getWageTaxSummaryByEmpIdandDuration(EmpId,locationId,startDate,endDate);
	}

	@Override
	public int getWageTaxSummaryVersionByLocationId(Long locationId)
	{
		return wageTaxSummaryDao.getWageTaxSummaryVersionByLocationId(locationId);
	}

	@Override
	public ResponseList<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationIdAndPagination(OrgLocations orgLocation,PayrollBalance payrollBalance) throws Exception {
		return wageTaxSummaryDao.getUniqueWagesAndTaxListByOrgLocationIdAndPagination(orgLocation,payrollBalance);
	}

	@Override
	public String getWagesAndTaxEmployeeId(Long candidateId) throws Exception
	{
		String empId = null;
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		WageTaxSummary  wageTaxSummary  = wageTaxSummaryDao.getEmployeeIdByUserId(candidate.getConsultantUser());
		if(wageTaxSummary != null){
			if(wageTaxSummary.getEmployeeId() !=null){
				empId = wageTaxSummary.getEmployeeId();
			}
		}
		return empId;
	}

	@Override
	public ResponseList<WageTaxSummary> getWagesAndTaxByEmpId(OrgLocations orgLocation, WagesTaxSummaryDTO wagesTaxSummaryDTO)
	{
		return wageTaxSummaryDao.getWagesAndTaxByEmpId(orgLocation, wagesTaxSummaryDTO);
	}
}
