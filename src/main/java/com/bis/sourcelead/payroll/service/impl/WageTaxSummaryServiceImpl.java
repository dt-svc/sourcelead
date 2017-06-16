package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.rest.EmployeePayrollRestController;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.util.ConversionUtil;

@Service
public class WageTaxSummaryServiceImpl implements WageTaxSummaryService{
	
	private static final Logger logger = LoggerFactory.getLogger(WageTaxSummaryServiceImpl.class);

	@Autowired
	WageTaxSummaryDao wageTaxSummaryDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Override
	public WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary) throws Exception {
		return wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
	}

	@Override
	public String saveWagesTaxSummary(MultipartFile multipartFile, OrgLocations orgLocation, User user,JSONObject jsonObj) throws Exception {

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
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
				/*	if (true) {*/
						if (row.getRowNum() > 0) { // Excluding column names
							WageTaxSummary  wageTaxSummary = new WageTaxSummary();
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
								wageTaxSummary.setEmployeeId(empId);
								
							} 
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								String employeeName = getCellValueAsString(row.getCell(1), fileName);
								wageTaxSummary.setEmployeeName(employeeName);
								
							} 
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								String checkDate = getCellValueAsString(row.getCell(2), fileName);
								wageTaxSummary.setCheckDate(conversionUtil.getDateFromMMDDYYY(checkDate));
								
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
							wageTaxSummaryList.add(wageTaxSummary);
						}
					}
				for(WageTaxSummary wageTaxSummary:wageTaxSummaryList){
					if(wageTaxSummary.getEmployeeId() != null){
					logger.info("empId== "+wageTaxSummary.getEmployeeId());
					}else{
						logger.info("empId== empId");
					}
					logger.info("employeeName== "+wageTaxSummary.getEmployeeName());
					if(wageTaxSummary.getCheckDate() != null){
						logger.info("checkDate== "+wageTaxSummary.getCheckDate());
					}else{
						logger.info("checkDate== empty");
					}
					if(wageTaxSummary.getCheckNo() != null){
						logger.info("checkNo== "+wageTaxSummary.getCheckNo());
					}else{
						logger.info("checkNo== empty");
					}
					logger.info("gross== "+wageTaxSummary.getGross());
					logger.info("fwt== "+wageTaxSummary.getFwt());
					logger.info("ssec== "+wageTaxSummary.getSsec());
					logger.info("med== "+wageTaxSummary.getMed());
					logger.info("swt== "+wageTaxSummary.getSwt());
					logger.info("sdiSui== "+wageTaxSummary.getSdiSui());
					logger.info("local== "+wageTaxSummary.getLocal());
					logger.info("deds== "+wageTaxSummary.getDeds());
					logger.info("net== "+wageTaxSummary.getNet());
					logger.info("****************************************************************");
				}
				}

			
			
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}

		return "";
	
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
