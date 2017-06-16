package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
import com.bis.sourcelead.entity.UsersImport;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.payroll.dao.EmployerPayrollCostDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.util.ConversionUtil;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class EmployerPayrollCostServiceImpl implements EmployerPayrollCostService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostServiceImpl.class);
	
	@Autowired
	EmployerPayrollCostDao employerPayrollCostDao;
	
	@Autowired
	ConversionUtil conversionUtil;

	@Override
	public String saveEmployerPayrollCost(MultipartFile multipartFile, OrgLocations orgLocation, User user,JSONObject jsonObj)
			throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		int count = 0;
		Workbook workbook = null;
		List<EmployerPayrollCost> employerPayrollCostList = new ArrayList<>();
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
							EmployerPayrollCost  employerPayrollCost = new EmployerPayrollCost();
							float grossWages =0f;
							float fica =0f;
							float futa =0f;
							float sui =0f;
							float erLocals =0f; 
							float wcCost =0f;
							float adminFee =0f; 
							float totalErCost =0f; 
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								String empName = getCellValueAsString(row.getCell(0), fileName);
								employerPayrollCost.setEmployeeName(empName);
							} 
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String empId = getCellValueAsString(row.getCell(1), fileName);
								employerPayrollCost.setEmployeeId(empId);
							} 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								String ssn = getCellValueAsString(row.getCell(2), fileName);
								employerPayrollCost.setSsn(ssn);
							} 
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								String totalHrs = getCellValueAsString(row.getCell(3), fileName);
								employerPayrollCost.setTotalHrs(totalHrs);
							} 
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								 grossWages = Float.parseFloat(getCellValueAsString(row.getCell(4), fileName));
								employerPayrollCost.setGrossWages(grossWages);
							} 
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								 fica = Float.parseFloat(getCellValueAsString(row.getCell(5), fileName));
								employerPayrollCost.setFica(fica);
							} 
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								 futa = Float.parseFloat(getCellValueAsString(row.getCell(6), fileName));
								employerPayrollCost.setFuta(futa);
							} 
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								 sui = Float.parseFloat(getCellValueAsString(row.getCell(7), fileName));
								employerPayrollCost.setSui(sui);
							} 
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								 erLocals = Float.parseFloat(getCellValueAsString(row.getCell(8), fileName));
								employerPayrollCost.setErLocals(erLocals);
							} 
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								 wcCost = Float.parseFloat(getCellValueAsString(row.getCell(9), fileName));
								employerPayrollCost.setWcCost(wcCost);
							} 
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								 adminFee = Float.parseFloat(getCellValueAsString(row.getCell(10), fileName));
								employerPayrollCost.setAdminFee(adminFee);
							} 
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								 totalErCost = Float.parseFloat(getCellValueAsString(row.getCell(11), fileName));
								employerPayrollCost.setTotalErCost(totalErCost);
							} 
							if(jsonObj.has("fromDate") && jsonObj.getString("fromDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("fromDate"))){
								employerPayrollCost.setFromDate(conversionUtil.getDateFromString(jsonObj.getString("fromDate")));
							}
							if(jsonObj.has("toDate") && jsonObj.getString("toDate") !=null && !"".equalsIgnoreCase(jsonObj.getString("toDate"))){
								employerPayrollCost.setToDate(conversionUtil.getDateFromString(jsonObj.getString("toDate")));
							}
							if(jsonObj.has("batchNumber")){
								if(jsonObj.getString("batchNumber") !=null && !"".equalsIgnoreCase(jsonObj.getString("batchNumber"))){
									employerPayrollCost.setBatchNumber(jsonObj.getString("batchNumber"));
								}
							}
							employerPayrollCost.setCreatedBy(user.getUserCode());
							employerPayrollCost.setCreatedDate(new Date());
							employerPayrollCost.setUpdatedBy(user.getUserCode());
							employerPayrollCost.setUpdatedDate(new Date());
							employerPayrollCost.setOrgLocationId(orgLocation);
							EmployerPayrollCost empPayrollCost = saveEmpPayrollCost(employerPayrollCost);
							if(empPayrollCost != null){
								count++;
							}
							employerPayrollCostList.add(employerPayrollCost);
						}
					}
				}

			
			
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}

		return "";
	}

	/**
	 * save EmployerPayrollCost 
	 * @param employerPayrollCost
	 * @return EmployerPayrollCost
	 * @throws Exception 
	 */
	private EmployerPayrollCost saveEmpPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception {
		return employerPayrollCostDao.saveEmployerPayrollCost(employerPayrollCost);
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
