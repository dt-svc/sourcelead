package com.bis.sourcelead.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UploadFileDao;
import com.bis.sourcelead.entity.UsersImport;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 *
 */
@Repository
public class UploadFileDaoImpl implements UploadFileDao{
	private static final Logger logger = LoggerFactory.getLogger(UploadFileDaoImpl.class);
	@Override
	public List<UsersImport> uploadFile(MultipartFile multipartFile) throws Exception {
		
		List<UsersImport> userImports = null;
		String fileName = multipartFile.getOriginalFilename();
		
		if(!fileName.toLowerCase().endsWith("csv")){
			userImports = parseXlsxandXlsData(multipartFile);
		}else{
			userImports = parseCSVData(multipartFile);
		}
			return userImports;
		}

	
	/**
	 * Handles CSV files
	 * @param multipartFile
	 * @return List<UserImports>
	 * @throws FileNotFoundException
	 */
	private List<UsersImport> parseCSVData(MultipartFile multipartFile) throws Exception{
		
		List<UsersImport> userImportList = new ArrayList<UsersImport>();
		
		CSVParser csvFileParser = null;
		// CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(Constants.FILE_HEADER_MAPPING);
		try {
			InputStream inputStream = multipartFile.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			// initialize CSVParser object
			csvFileParser = new CSVParser(bufferedReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords();
			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				UsersImport userImport = new UsersImport();
				userImport.setEmployeeId(record.get(Constants.EMPLOYEE_ID));
				userImport.setFirstName(record.get(Constants.FIRST_NAME));
				userImport.setLastName(record.get(Constants.LAST_NAME));
				userImport.setEmailId(record.get(Constants.EMAIL_ID));
				userImport.setMobileNumber(record.get(Constants.PHONE_NUMBER));
				userImport.setRate(record.get(Constants.RATE));
				userImport.setRatePer(record.get(Constants.RATEPER));
				userImport.setDateOfJoin(record.get(Constants.DATEOFJOIN));
				userImport.setRole(record.get(Constants.ROLE));
				userImport.setInvoiceFrequency(record.get(Constants.INVOICE_FREQUENCY));
				userImport.setDayHours(record.get(Constants.DAY_HOURS));
				userImportList.add(userImport);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userImportList;

	}
/**
 * 
 * @param multipartFile
 * @return List<UsersImport>
 * @throws FileNotFoundException
 */
	private List<UsersImport> parseXlsxandXlsData(MultipartFile multipartFile) throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		List<UsersImport> userImportList = new ArrayList<UsersImport>();
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = null;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = (Workbook) new HSSFWorkbook(fis);
			}
			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();
			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {
				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					UsersImport userImport = new UsersImport();
					// Get the row object
					Row row = rowIterator.next();
					logger.info("Reading Row :" + row.getRowNum()+" records");
					if (row.getRowNum() > 0) { // Excluding column names
						userImport.setEmployeeId(row.getCell(0).toString());
						userImport.setFirstName(row.getCell(1).toString());
						userImport.setLastName(row.getCell(2).toString());
						userImport.setEmailId(row.getCell(3).toString());
						userImport.setMobileNumber(row.getCell(4).toString());
						userImport.setRate(row.getCell(5).toString());
						userImport.setRatePer(row.getCell(6).toString());
						userImport.setDateOfJoin(row.getCell(7).toString());
						userImport.setRole(row.getCell(8).toString());
						userImport.setInvoiceFrequency(row.getCell(9).toString());
						userImport.setDayHours(row.getCell(10).toString());
						userImportList.add(userImport);
					}
				} // end of rows iterator
			} // end of sheets for loop
				// close file input stream
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userImportList;

	}
	
	}

