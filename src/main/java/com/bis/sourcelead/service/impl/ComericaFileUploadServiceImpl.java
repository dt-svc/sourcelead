package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.BOTransactions;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.BoComericaTransService;
import com.bis.sourcelead.service.ComericaFileUploadService;

@Service
public class ComericaFileUploadServiceImpl implements ComericaFileUploadService {
	
	
private static final Logger logger = LoggerFactory.getLogger(ComericaFileUploadServiceImpl.class);
	
	@Autowired
	BoComericaTransService boComericaTransService;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	@Override
	public String uploadComericaFile(MultipartFile multipartFile, User user, AmexUploadFile amexUploadFile)
			throws Exception {
		
		String isFileCreated;
		String fileName = multipartFile.getOriginalFilename();
		isFileCreated = parseComericaCSVData(multipartFile,user,amexUploadFile);
		return isFileCreated;
	}
	
	
	private String parseComericaCSVData(MultipartFile multipartFile, User user, AmexUploadFile amexUploadFile) {

		String isFileCreated = null;
		boolean isValidated = false;
		Workbook createExcelworkbook =  new XSSFWorkbook();
		Sheet createExcelsheet = createExcelworkbook.createSheet("failuredTransactions");
		createExcelsheet = setComericaExcelHeader(createExcelsheet);
		
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(Constants.COMERICA_FILE_HEADER_MAPPING);
		try {
			InputStream inputStream = multipartFile.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			csvFileParser = new CSVParser(bufferedReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords();
			int failureCount = 0;
			int count = 1;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				BOTransactions botrans = new BOTransactions();
				isValidated = validateComericaCsvData(record,botrans);
				if(isValidated){
					Date csvDate  = null;
					try{
						SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
						sdf1.setLenient(false);
						csvDate = sdf1.parse(record.get(0));
					}catch(Exception e){
						e.printStackTrace();
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = sdf.parse(amexUploadFile.getStartDate());
					Date endDate = sdf.parse(amexUploadFile.getEndDate());
					
					if((csvDate.equals(startDate) || csvDate.after(startDate)) && (csvDate.equals(endDate) || csvDate.before(endDate))){
						
						BoComericaTrans boComericaTrans = new BoComericaTrans();
						boComericaTrans.setTransDate(csvDate);
						
						if(!isNullOrEmpty(record.get(1))){
							boComericaTrans.setReferenceNo(record.get(1));
						}
						
						if(!isNullOrEmpty(record.get(2))){
							boComericaTrans.setTransDesc(record.get(2));
						}
						
						if(!isNullOrEmpty(record.get(3))){
							boComericaTrans.setAmount(Float.parseFloat(record.get(3)));
							boComericaTrans.setTransType("DEBIT");
						}
						
						if(!isNullOrEmpty(record.get(4))){
							boComericaTrans.setAmount(Float.parseFloat(record.get(4)));
							boComericaTrans.setTransType("CREDIT");
						}
						
						boComericaTrans.setStatus(1);
						boComericaTrans.setCreatedDate(new Date());
						boComericaTrans.setCreatedBy(user.getUserCode());
						boComericaTrans.setUpdatedBy(user.getUserCode());
						boComericaTrans.setUpdatedDate(new Date());
						boComericaTransService.saveBoComericaTrans(boComericaTrans);
						
					}
							
				}else{
					createComericaXlsxSheetFromCsv(botrans, createExcelsheet, count,record);
					count++;
					failureCount++;
				}
			}
			if(failureCount > 0){
				FileOutputStream fos = new FileOutputStream("FailureTransactionsCheck.xlsx");
				createExcelworkbook.write(fos);
				fos.close();
				isFileCreated = "FailureTransactionsCheck.xlsx";
			}else{
				isFileCreated = "failed";
			}
			
		} catch (Exception e) {
			 logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		return isFileCreated;

	}
	
private void createComericaXlsxSheetFromCsv(BOTransactions botrans, Sheet createExcelsheet, int rowIndex, CSVRecord record) {
		
		Row writeRow = createExcelsheet.createRow(rowIndex);
		
		if (record.get(0) != null && !"".equalsIgnoreCase(record.get(0))) {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue(record.get(0));
		} else {
			Cell cell0 = writeRow.createCell(0);
			cell0.setCellValue("");
		}
		
		if (record.get(1) != null && !"".equalsIgnoreCase(record.get(1))) {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue(record.get(1));
		} else {
			Cell cell1 = writeRow.createCell(1);
			cell1.setCellValue("");
		}
		
		if (record.get(2) != null && !"".equalsIgnoreCase(record.get(2))) {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue(record.get(2));
		} else {
			Cell cell2 = writeRow.createCell(2);
			cell2.setCellValue("");
		}
		
		if (record.get(3) != null && !"".equalsIgnoreCase(record.get(3))) {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue(record.get(3));
		} else {
			Cell cell3 = writeRow.createCell(3);
			cell3.setCellValue("");
		}
		
		if (record.get(4) != null && !"".equalsIgnoreCase(record.get(4))) {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue(record.get(4));
		} else {
			Cell cell4 = writeRow.createCell(4);
			cell4.setCellValue("");
		}
		
		if (botrans.getReason() != null) {
			writeRow.createCell(5).setCellValue(botrans.getReason());
		} 
	
	}

	private boolean validateComericaCsvData(CSVRecord record, BOTransactions botrans) {
	
		boolean isValidated;
			if (isNullOrEmpty(record.get(0))) {
				botrans.setReason("Required field mandatory");
				return false;
			}
			
			if (!isNullOrEmpty(record.get(0))) {
				isValidated = isThisDateValid(record.get(0), "MM/dd/yyyy");
				if(isValidated == false){
					botrans.setReason("Invalid Date");
					return isValidated;
				}
			}
			
			
		return true;
	}
	
   private Sheet setComericaExcelHeader(Sheet createExcelsheet) {
		
		Row excelHeader = createExcelsheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("No.");
		excelHeader.createCell(2).setCellValue("Description");
		excelHeader.createCell(3).setCellValue("Debit");
		excelHeader.createCell(4).setCellValue("Credit");
		excelHeader.createCell(5).setCellValue("Failure Reason");
		return createExcelsheet;
	}
	
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

}
