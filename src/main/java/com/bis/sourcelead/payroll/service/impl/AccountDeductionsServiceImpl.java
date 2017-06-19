package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.AccountDeductionsDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;

@Service
public class AccountDeductionsServiceImpl implements AccountDeductionsService{

	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsServiceImpl.class);
	
	@Autowired
	private AccountDeductionsDao  payCycleDao;
	@Override
	public String savePayCycle(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception {
		
		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		List<AccountDeductions> accountDeductionsList = new ArrayList<>();
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
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
				/*	if (true) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							AccountDeductions  accountDeductions = new AccountDeductions();
							Set<AccountDeductionsDetails> accountDeductionsDetailsSet = new HashSet<>();
							/*float grossWages =0f;
							float fica =0f;
							float futa =0f;
							float sui =0f;
							float erLocals =0f; 
							float wcCost =0f;
							float adminFee =0f; 
							float totalErCost =0f; */
							// start AccountDeductions
							
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empId = getCellValueAsString(row.getCell(0), fileName);
								accountDeductions.setEmployeeId(empId);
							} 
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String empName = getCellValueAsString(row.getCell(1), fileName);
							} 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								String deductionAmount = getCellValueAsString(row.getCell(2), fileName);
							} 
							
							// end AccountDeductions
							
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								float march15ThAMount = Float.parseFloat(getCellValueAsString(row.getCell(3), fileName));
								AccountDeductionsDetails accountDeductionsDetails= getAccountDeductionDetails(march15ThAMount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								float april1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(april1stAmount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								float april15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(april15thAmount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								float may1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(may1stAmount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								float may15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(may15thAmount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								float june1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								AccountDeductionsDetails accountDeductionsDetails = getAccountDeductionDetails(june1stAmount,accountDeductions);
								accountDeductionsDetailsSet.add(accountDeductionsDetails);
							} 
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								float june15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
							} 
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float july1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float july15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							if (row.getCell(12) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float aug1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(13) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float aug15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							
							if (row.getCell(14) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float sep1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(15) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float sep15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							
							if (row.getCell(16) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float oct1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(17) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float oct15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							
							if (row.getCell(18) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float nov1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(19) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float nov15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							
							if (row.getCell(20) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								float dec1stAmount = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
							} 
							if (row.getCell(21) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								float dec15thAmount = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
							} 
							
							if(jsonObj.has("fromDate") && jsonObj.getString("fromDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("fromDate"))){
							}
							if(jsonObj.has("toDate") && jsonObj.getString("toDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("toDate"))){
							}
							if(jsonObj.has("batchNumber")){
								if(jsonObj.getString("batchNumber") !=null && !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))){
								}
							}
							/*employerPayrollCost.setCreatedBy(user.getUserCode());
							employerPayrollCost.setCreatedDate(new Date());
							employerPayrollCost.setUpdatedBy(user.getUserCode());
							employerPayrollCost.setUpdatedDate(new Date());
							employerPayrollCost.setOrgLocationId(orgLocation);
							EmployerPayrollCost empPayrollCost = saveEmpPayrollCost(employerPayrollCost);
							if(empPayrollCost != null){
								count++;
							}*/
							accountDeductionsList.add(accountDeductions);
						}
					}
				}

			
			
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}
		payCycleDao.savePayCycle(new AccountDeductions());
		return "";
		
		
	}

	private AccountDeductionsDetails getAccountDeductionDetails(float dedAmount, AccountDeductions accountDeductions) {
		AccountDeductionsDetails accountDeductionsDetails = new AccountDeductionsDetails();
		accountDeductionsDetails.setDedAmountPerCycle(dedAmount);
		accountDeductionsDetails.setAccountDeductions(accountDeductions);
		return null;
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
}
