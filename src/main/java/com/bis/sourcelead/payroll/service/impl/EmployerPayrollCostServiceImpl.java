package com.bis.sourcelead.payroll.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

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

@Service
public class EmployerPayrollCostServiceImpl implements EmployerPayrollCostService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostServiceImpl.class);
	
	@Autowired
	EmployerPayrollCostDao employerPayrollCostDao;

	@Override
	public String saveEmployerPayrollCost(MultipartFile multipartFile,OrgLocations orgLocation,User user ) throws Exception {
		
		String fileName = multipartFile.getOriginalFilename();
		Workbook workbook = null ;

		if (fileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (fileName.endsWith("xls")) {
			workbook = (Workbook) new HSSFWorkbook();
		}

//		Sheet createExcelsheet = createExcelworkbook.createSheet("createuser");
//		createExcelsheet = setExcelHeader(createExcelsheet);
	
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
//			Workbook workbook;
			logger.debug("===========================FIle Extension is: " + multipartFile.getOriginalFilename());
//				boolean isValidated = false;
//				boolean isUserFound = false;
				workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				int count = 0;
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
//					if (row.getRowNum() > 0) {
//						isValidated = validateExeclData(row,userImport,fileName);
//					}
					if (true) {
						if (row.getRowNum() > 0) { // Excluding column names
							logger.info("Reading Row :" + row.getRowNum() + " records");
							if (row.getCell(0) != null && !"".equalsIgnoreCase(row.getCell(0).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(0),fileName));
							} else {
								logger.info("*******Cell(0)*********************Empty");
							}
							if (row.getCell(1) != null && !"".equalsIgnoreCase(row.getCell(1).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(1),fileName));
							} else {
								logger.info("*******Cell(1)*********************Empty");
							}
							if (row.getCell(2) != null && !"".equalsIgnoreCase(row.getCell(2).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(2),fileName));
							} else {
								logger.info("*******Cell(2)*********************Empty");
							}
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								objFormulaEvaluator.evaluate(row.getCell(3)); // This will evaluate the cell, And any type of cell will return string value
							    String cellValueStr = objDefaultFormat.formatCellValue(row.getCell(3),objFormulaEvaluator);
								logger.info("++++++++++++++++++++++ "+cellValueStr);
							} else {
								logger.info("*******Cell(3)*********************Empty");
							}
							if (row.getCell(4) != null && !"".equalsIgnoreCase(row.getCell(4).toString())) {
								objFormulaEvaluator.evaluate(row.getCell(4)); // This will evaluate the cell, And any type of cell will return string value
							    String cellValueStr = objDefaultFormat.formatCellValue(row.getCell(4),objFormulaEvaluator);
								logger.info("****************************"+cellValueStr);
							} else {
								logger.info("*******Cell(4)*********************Empty");
							}
							if (row.getCell(5) != null && !"".equalsIgnoreCase(row.getCell(5).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(5),fileName));
							} else {
								logger.info("*******Cell(5)*********************Empty");
							}
							if (row.getCell(6) != null && !"".equalsIgnoreCase(row.getCell(6).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(6),fileName));
							} else {
								logger.info("*******Cell(6)*********************Empty");
							}
							
							if (row.getCell(7) != null && !"".equalsIgnoreCase(row.getCell(7).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(7),fileName));
							} else {
								logger.info("*******Cell(7)*********************Empty");
							}
							if (row.getCell(8) != null && !"".equalsIgnoreCase(row.getCell(8).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(8),fileName));
							} else {
								logger.info("*******Cell(8)*********************Empty");
							}
							if (row.getCell(9) != null && !"".equalsIgnoreCase(row.getCell(9).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(9),fileName));
							} else {
								logger.info("*******Cell(9)*********************Empty");
							}
							if (row.getCell(10) != null && !"".equalsIgnoreCase(row.getCell(10).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(10),fileName));
							} else {
								logger.info("*******Cell(10)*********************Empty");
							}
							if (row.getCell(11) != null && !"".equalsIgnoreCase(row.getCell(11).toString())) {
								logger.info("****************************"+getCellValueAsString(row.getCell(11),fileName));
							} else {
								logger.info("*******Cell(11)*********************Empty");
							}
							
						}
					} 
				}
			

			}
			fis.close();
		} catch (IOException e) {
			 logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
		EmployerPayrollCost employerPayrollCost = employerPayrollCostDao.saveEmployerPayrollCost(new EmployerPayrollCost());
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
