package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.HrDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.HrDeductionsDao;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;
import com.bis.sourcelead.payroll.entity.PayrollHelper;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.HrDeductionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class HrDeductionsServiceImpl implements HrDeductionsService{
	
	private static final Logger logger = LoggerFactory.getLogger(HrDeductionsServiceImpl.class);
	
	@Autowired
	HrDeductionsDao hrDeductionsDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	PayrollHelper payrollHelper;

	@Override
	public Integer saveHRDeductions(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception {
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
			version = getHrDeductionsVersionByLocationId(orgLocation.getId());
			version += 1;
			
			for (int i = 0; i < 1; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				
				if(i == 0){
					isValid =  payrollHelper.isValidTemplateForHrDeductions(rowIterator);
				}
				if(isValid == 1){
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
				/*	if (true) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							HrDeductions hrDeductions = new HrDeductions();
							Set<HrDeductionsDetails> hrDeductionsDetailsSet = new HashSet<>();
							float  deductionAmount =0f;
							// start HrDeductions
							
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empId = getCellValueAsString(row.getCell(0), fileName);
								hrDeductions.setEmployeeId(empId.split("\\.")[0]);
							} 
							
							if(hrDeductions.getEmployeeId() != null){
								hrDeductions.setVersion(version);
							}
							
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String empName = getCellValueAsString(row.getCell(1), fileName);
								hrDeductions.setEmployeeName(empName);
							} 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								deductionAmount = Float.parseFloat(getCellValueAsString(row.getCell(2), fileName));
								hrDeductions.setDedAmount(deductionAmount);
							} else{
								hrDeductions.setDedAmount(deductionAmount);
							}
							// end HrDeductions
							// Start AccountDeductionsDetails
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								float march15ThAMount = Float.parseFloat(getCellValueAsString(row.getCell(3), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(march15ThAMount,hrDeductions,orgLocation,user,row.getCell(3),3);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								float april1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(april1stAmount,hrDeductions,orgLocation,user,row.getCell(4),4);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								float april15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(april15thAmount,hrDeductions,orgLocation,user,row.getCell(5),5);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								float may1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(may1stAmount,hrDeductions,orgLocation,user,row.getCell(6),6);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								float may15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(may15thAmount,hrDeductions,orgLocation,user,row.getCell(7),7);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								float june1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(june1stAmount,hrDeductions,orgLocation,user,row.getCell(8),8);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								float june15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(june15thAmount,hrDeductions,orgLocation,user,row.getCell(9),9);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float july1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(july1stAmount,hrDeductions,orgLocation,user,row.getCell(10),10);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float july15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(july15thAmount,hrDeductions,orgLocation,user,row.getCell(11),11);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(12).toString())) {
								float aug1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(12), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(aug1stAmount,hrDeductions,orgLocation,user,row.getCell(12),12);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(13).toString())) {
								float aug15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(13), fileName));
								HrDeductionsDetails hrDeductionsDetails = getAccountDeductionDetails(aug15thAmount,hrDeductions,orgLocation,user,row.getCell(13),13);
								hrDeductionsDetailsSet.add(hrDeductionsDetails);
							} 
							if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(14).toString())) {
								String commnet = getCellValueAsString(row.getCell(14), fileName);
								hrDeductions.setDescription(commnet);
							} 
							if(jsonObj.has("fromDate") && jsonObj.getString("fromDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("fromDate"))){
								hrDeductions.setFromDate(conversionUtil.getDateFromString(jsonObj.getString("fromDate")));
							}
							if(jsonObj.has("toDate") && jsonObj.getString("toDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("toDate"))){
								hrDeductions.setToDate(conversionUtil.getDateFromString(jsonObj.getString("toDate")));
							}
							if(jsonObj.has("batchNumber")){
								if(jsonObj.getString("batchNumber") !=null && !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))){
									hrDeductions.setBatchNumber(jsonObj.getString("batchNumber"));
								}
							}
							hrDeductions.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							hrDeductions.setCreatedBy(user.getUserCode());
							hrDeductions.setCreatedDate(new Date());
							hrDeductions.setUpdatedBy(user.getUserCode());
							hrDeductions.setUpdatedDate(new Date());
							hrDeductions.setOrgLocationId(orgLocation);
							hrDeductions.setStatus(Constants.MEMBER_ACTIVE);
							hrDeductions.setHrDeductionsDetails(hrDeductionsDetailsSet);
							HrDeductions savedHrDeductions = saveHrDetails(hrDeductions);
							if(savedHrDeductions != null){
								count++;
							}
//							accountDeductionsList.add(accountDeductions);
						}
					}
				}else{
					return count;
				}
				}
			
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}
		return count;
		
	}
	

	private HrDeductions saveHrDetails(HrDeductions hrDeductions) throws Exception {
		return hrDeductionsDao.saveHRDeductions(hrDeductions);
	}

	/*private String getCellValueAsString(Cell cell, String fileName) {
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
		}*/
	
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
	
	private HrDeductionsDetails getAccountDeductionDetails(float dedAmount, HrDeductions hrDeductions, OrgLocations orgLocation,User user, Cell cell,int cellNuber) {
		HrDeductionsDetails hrDeductionsDetails = new HrDeductionsDetails();
		hrDeductionsDetails.setDedAmountPerCycle(dedAmount);
		hrDeductionsDetails.setHrDeductions(hrDeductions);
		hrDeductionsDetails.setOrgLocationId(orgLocation);
		hrDeductionsDetails.setPayCycleName(cell.getSheet().getRow(0).getCell(cellNuber).getRichStringCellValue().toString());
		hrDeductionsDetails.setCreatedBy(user.getUserCode());
		hrDeductionsDetails.setCreatedDate(new Date());
		hrDeductionsDetails.setUpdatedBy(user.getUserCode());
		hrDeductionsDetails.setUpdatedDate(new Date());
		hrDeductionsDetails.setOrgLocationId(orgLocation);
		hrDeductionsDetails.setStatus(Constants.MEMBER_ACTIVE);
		return hrDeductionsDetails;
	}

	@Override
	public ResponseList<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation, HrDeductionsDTO hrDeductionsDTO) throws Exception{
		return hrDeductionsDao.getHrDeductionsListByOrgLocationid(orgLocation, hrDeductionsDTO);
	
	}

	@Override
	public List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId)
	{
		return hrDeductionsDao.getHrDeductionsDetailsbyId(hrDeductionId);
	
	}

	@Override
	public List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		return hrDeductionsDao.getHrDeductionsByEmployeeId(orgLocations,empId);
	}


	@Override

	public HrDeductions getDeductionsbyId(Long deductionId)
	{
		return hrDeductionsDao.getDeductionsbyId(deductionId);
	}
	@Override
	public HrDeductions getHrDeductionsByEmpIdandDuration(String EmpId, Long locationId, Date startDate, Date endDate)
	{
		return hrDeductionsDao.getHrDeductionsByEmpIdandDuration(EmpId, locationId, startDate, endDate);

	}


	@Override

	public HrDeductions saveHrDeductions(HrDeductions hrDeductions) throws Exception
	{
		return hrDeductionsDao.saveAccountDeductions(hrDeductions);
	}

    @Override
	public int getHrDeductionsVersionByLocationId(Long locationId)
	{
		return hrDeductionsDao.getHrDeductionsVersionByLocationId(locationId);
	}


	@Override
	public ResponseList<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocation,HrDeductionsDTO hrDeductionsDTO) {
		
		return hrDeductionsDao.getHrDeductionsByEmployeeId(orgLocation,hrDeductionsDTO);
	}

}
