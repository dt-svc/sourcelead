package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author : Pradeep Kumar Reddy
 *
 */

@Service
public class ExpensesServiceImpl implements ExpensesService{
	
	@Autowired
	ExpensesDao<Expenses> expensesDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;	
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private ExpensesApproverHierarchyDao expensesApproverHierarchyDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private CountriesService countriesService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesServiceImpl.class);
	
	@Override
	public List<Expenses> getAllExpenses(OrgLocations orgLoc,User loginUser, String expenseStatus, String startDate, String endDate) throws Exception {
			Date endDateUTC = null;
			Date startDateUTC = null;
			
			Timezone timezone = timeZoneService.getTimezone(orgLoc.getTimezone().getId());
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
			if(StringUtils.isNotEmpty(startDate)){
				Date startTime = commonUtil.getDateFormat(startDate);
				String customStartTimeStr = TZ_DF_CSD.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			}
			
			if(StringUtils.isNotEmpty(endDate)){
				Date endTime = commonUtil.getDateFormat(endDate);
				String customEndTimeStr = TZ_DF_CED.format(endTime);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
				endDateUTC = TZ_DF_TS.parse(endDateString);
			}
		
		List<Expenses> expensesList =  expensesDao.getAllExpenses(orgLoc.getId(),loginUser.getId(), expenseStatus, startDateUTC, endDateUTC);

		return expensesList;

	}
	
	@Override
	public List<Expenses> getIndividualExpense(OrgLocations orgLoc,User loginUser,String expenseId) {
		List<Expenses> expensesList =  expensesDao.getIndividualExpense(orgLoc.getId(),loginUser.getId(),expenseId);

		return expensesList;

	}
	
	@Override
	public List<ExpensesEntries> getExpensesEntries(String expenseId) {
		List<ExpensesEntries> expensesEntriesList =  expensesDao.getExpensesEntries(expenseId);

		return expensesEntriesList;

	}
	
	@Override
	public Expenses getExpenseByExpenseId(long expenseId) {
		return expensesDao.getExpenseByExpenseId(expenseId);
	}
	
	@Override
	public Expenses getExpenseByExpenseUniqueId(String expenseId,Long OrgLocationID) throws Exception {
		return expensesDao.getExpenseByExpenseUniqueId(expenseId,OrgLocationID);
	}
	@Override
	public File exportMyExpensesToPdf(Expenses expenseDetails, OrgLocations orgLoc, User loginUser) throws Exception {
		String[] myExpensesHeaders = Constants.MY_EXPENSES_HEADERS;
		Document document = new Document(PageSize.LETTER.rotate());
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		    StringBuilder sbExpenses =  new StringBuilder();
		    StringBuilder hedderLineString =  new StringBuilder();
		    sbExpenses.append("Expenses_").append(dateFormat.format(new Date()));
		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("Expenses_").append(dateFormat.format(new Date())).toString(),".pdf");
		    file.setReadable(true);
		    
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			    Paragraph para = new Paragraph(hedderLineString.append(orgLoc.getOrg().getOrgName()).append(" Expenses.").toString(),boldFont);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
		        
		        
		       // Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append("MY EXPENSES").append("-").append(orgLoc.getOrg().getOrgName()).append(" ");
	            PdfPTable table = new PdfPTable(myExpensesHeaders.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(11);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{5,3,5,3,3,3,7});
	            table.addCell(cell1);
		        for (int i = 0; i < myExpensesHeaders.length; i++) {
		            String header = myExpensesHeaders[i];
		            PdfPCell cell = new PdfPCell();
		            cell.setGrayFill(0.9f);
		            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
		            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
		            cell.setBackgroundColor(myBorderColor);
		            table.addCell(cell);
		        }
		            table.completeRow();
			        List<String[]> expensespdfArrayList = new ArrayList<>();
			        List<ExpensesEntries> expenseEntries = getExpensesEntries(expenseDetails.getId().toString());
			        for(ExpensesEntries expEntries :expenseEntries){
			        	expensespdfArrayList.add(prepareMyExpensesStringArray(expEntries,myExpensesHeaders,orgLoc,loginUser));
			        }
			        	
			        for (String[] expensesArray: expensespdfArrayList) {
		                for (int i = 0; i < expensesArray.length; i++) {
		                    String datum = expensesArray[i];
		                    PdfPCell cell = new PdfPCell();
		                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
		                    table.addCell(cell);
		                }
		                table.completeRow();
		            }
			        document.addTitle("My Expenses");
			        table.setSpacingBefore(15f);
			        
			        com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
		            StringBuilder reqNameBuilder=new StringBuilder();
		            StringBuilder locBuilder=new StringBuilder();
		            StringBuilder Client=new StringBuilder();
		            StringBuilder StDate=new StringBuilder();
		            StringBuilder edDate=new StringBuilder();
		            StringBuilder purposeStringBuilder =new StringBuilder();
			        orderedList.add(new ListItem(reqNameBuilder.append("Expense Id :").append(expenseDetails.getExpenseId()).toString()));
			        orderedList.add(new ListItem(locBuilder.append("Project :").append(expenseDetails.getProjectMembers().getProjects().getProjectName()).toString()));
			       if(null != expenseDetails.getClientName()){
			    	   orderedList.add(new ListItem(Client.append("Client :").append(expenseDetails.getClientName()).toString()));
			       }
			        if(null != expenseDetails.getStartDate()){
			        	String StartDate = Constants.DMY.format(expenseDetails.getStartDate());
			        	orderedList.add(new ListItem(StDate.append("Start Date :").append(StartDate).toString()));
			        }
			        if(null != expenseDetails.getEndDate()){
			        	String EndDate = Constants.DMY.format(expenseDetails.getEndDate());
			        	orderedList.add(new ListItem(edDate.append("End Date :").append(EndDate).toString()));
			        }
			        if(null != expenseDetails.getEndDate()){
			        	orderedList.add(new ListItem(purposeStringBuilder.append("Purpose :").append(expenseDetails.getPurpose()).toString()));
			        }
			        document.add(orderedList);
			        
			        document.add(table);
			        
			        
			        
			        
		    }catch (DocumentException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } catch (FileNotFoundException e) {
		    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		    } finally {
		        document.close();
		    }
			return file;
	}

	private String[] prepareMyExpensesStringArray(ExpensesEntries expEntries, String[] myExpensesHeaders, OrgLocations orgLoc, User loginUser) throws Exception {
		String userArray[] = new String[myExpensesHeaders.length];
				if(null != expEntries.getExpensesCategoryId()){
					if(expEntries.getExpensesCategoryId().getExpensesName() != null && !"".equalsIgnoreCase(expEntries.getExpensesCategoryId().getExpensesName())){	
						   userArray[0] = expEntries.getExpensesCategoryId().getExpensesName();
					   }else{
						   userArray[0] = "--";
					  }	
				}
			if(null != expEntries.getExpenseDate())
			{
				expEntries.setExpenseDateString(Constants.DF_DMY.format(expEntries.getExpenseDate()));
				 userArray[1] = expEntries.getExpenseDateString();
			}
			if(expEntries.getReceiptIssuedBy() != null && !"".equalsIgnoreCase(expEntries.getReceiptIssuedBy())){	
				   userArray[2] = expEntries.getReceiptIssuedBy();
			   }else{
				   userArray[2] = "--";
			 }
			if(expEntries.getPaymentMode() != null && !"".equalsIgnoreCase(expEntries.getPaymentMode().toString())){	
				   userArray[3] = expEntries.getPaymentMode().getValue();
			   }else{
				   userArray[3] = "--";
			 }
			if(expEntries.getCurrencyType() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType())){	
				   userArray[4] = expEntries.getCurrencyType();
			   }else{
				   userArray[4] = "--";
			 }
			if(expEntries.getAmount() != 0){
				StringBuilder amount = new StringBuilder();
				   userArray[5] = amount.append(expEntries.getAmount()).toString();
			   }else{
				   userArray[5] = "--";
			 }
			
			/*Countries country = countriesService.getCountriesByCountryName(orgLoc.getCountry());
			if(!country.getCurrency().equalsIgnoreCase(expEntries.getCurrencyType())){
				 Float amount = conversionUtil.convert(expEntries.getCurrencyType(),country.getCurrency(),expEntries.getAmount());
				 String countryCode = country.getCurrency();
				 userArray[6] =countryCode+""+amount.toString();
				}else{
					userArray[6] ="--";	
				}*/
			 if(expEntries.getDescription() != null && !"".equalsIgnoreCase(expEntries.getDescription())){	
				   userArray[6] = expEntries.getDescription();
			   }else{
				   userArray[6] = "--";
			 }
	return userArray;
	}

	@Override
	public File exportMyExpensesToExcel(Expenses expenseDetails, OrgLocations orgLoc, User loginUser) throws Exception {
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrg().getOrgName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,expenseDetails);
		int column = 0;
		
		List<ExpensesEntries> expenseEntries = getExpensesEntries(expenseDetails.getId().toString());
		
		for (ExpensesEntries expensesEntries : expenseEntries) {
			writeRow = createExcelsheet.createRow(column + 8);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expensesEntries);
			column++;
		}
		 file = File.createTempFile(orgLoc.getOrg().getOrgName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
		 file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ExpensesEntries expEntries) {
		if(null != expEntries.getExpensesCategoryId()){
			if(expEntries.getExpensesCategoryId().getExpensesName() != null && !"".equalsIgnoreCase(expEntries.getExpensesCategoryId().getExpensesName())){	
				writeRow.createCell(0).setCellValue(expEntries.getExpensesCategoryId().getExpensesName());
				createExcelsheet.autoSizeColumn(0);
			   }
		}
		
	if(null != expEntries.getExpenseDate()){
		expEntries.setExpenseDateString(Constants.DF_DMY.format(expEntries.getExpenseDate()));
		writeRow.createCell(1).setCellValue( expEntries.getExpenseDateString());
		createExcelsheet.autoSizeColumn(1);
	}
	if(expEntries.getReceiptIssuedBy() != null && !"".equalsIgnoreCase(expEntries.getReceiptIssuedBy())){	
		writeRow.createCell(2).setCellValue( expEntries.getReceiptIssuedBy());
		createExcelsheet.autoSizeColumn(2);
	}
	if(expEntries.getPaymentMode() != null && !"".equalsIgnoreCase(expEntries.getPaymentMode().toString())){	
		writeRow.createCell(3).setCellValue(expEntries.getPaymentMode().getValue());
		createExcelsheet.autoSizeColumn(3);
	   }
	if(expEntries.getCurrencyType() != null && !"".equalsIgnoreCase(expEntries.getCurrencyType())){	
		writeRow.createCell(4).setCellValue(expEntries.getCurrencyType());
		createExcelsheet.autoSizeColumn(4);
	   }
	if(expEntries.getAmount() != 0){
		StringBuilder amount = new StringBuilder();
		writeRow.createCell(5).setCellValue(amount.append(expEntries.getAmount()).toString());
		createExcelsheet.autoSizeColumn(5);
	   }
	 if(expEntries.getDescription() != null && !"".equalsIgnoreCase(expEntries.getDescription())){	
		 writeRow.createCell(6).setCellValue( expEntries.getDescription());
		 createExcelsheet.autoSizeColumn(6);
	   }
		return createExcelsheet;
	}
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, Expenses exp) {
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		writeRow = createExcelsheet.createRow(0);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Expense id :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
	    cell2.setCellValue(exp.getExpenseId());
		cell2.setCellStyle(style);

		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Project :");
		cell3.setCellStyle(style);

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(exp.getProjectMembers().getProjects().getProjectName());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Client : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue("client");
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Start Date : ");
		cell7.setCellStyle(style);

		Cell cell8 = writeRow.createCell(1);
		String StrtDate = Constants.DMY.format(exp.getStartDate());
		cell8.setCellValue(StrtDate);
		cell8.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell9 = writeRow.createCell(0);
		cell9.setCellValue("End Date : ");
		cell9.setCellStyle(style);

		Cell cell10 = writeRow.createCell(1);
		
		String EndDate = null;
		if(null != exp.getEndDate() && !"".equalsIgnoreCase(exp.getEndDate().toString())){
			EndDate = Constants.DMY.format(exp.getEndDate());	
		}else{
			EndDate  = ("--");	
		}
		cell10.setCellValue(EndDate);
		cell10.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(5);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell11 = writeRow.createCell(0);
		cell11.setCellValue("Purpose : ");
		cell11.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(exp.getPurpose());
		cell12.setCellStyle(style);
		String headers [] = Constants.MY_EXPENSES_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,7);

		return createExcelsheet;
	}
	
	private static void headerCellStyle(PdfPCell cell){
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setPaddingTop(0f);
	    cell.setPaddingBottom(7f);
	    cell.setBackgroundColor(new BaseColor(0,121,182));
	    cell.setBorder(0);
	    cell.setBorderWidthBottom(2f);
	    }
	
	
	
	@Override
	public File exportAllMyExpensesToPdf(List<Expenses> expenseList, OrgLocations orgLoc, User loginUser) throws Exception {
		String[] headers = Constants.ALL_MY_EXPENSES_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("Expenses_").append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("_Expenses_").append(dateFormat.format(new Date())).toString(),".pdf");
	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLoc.getOrg().getOrgName()).append(" ").append("- My Expenses");
	       
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(18);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{5,5,5,7,6,6,4,5,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> expenseAllList = prepareExpenseListStringArray(expenseList,headers);
	        for (String[] expenseArray: expenseAllList) {
                for (int i = 0; i < expenseArray.length; i++) {
                    String datum = expenseArray[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
	        document.addTitle("All Expenses Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	    }
		return file;
	}
	
	private List<String[]> prepareExpenseListStringArray(List<Expenses> expList, String[] headers) {
		
		List<String[]> arrayList = new ArrayList<>();
		for(Expenses ex:expList){
			String expenseArray[] = new String[headers.length];
			if(ex.getExpenseId() !=null){
				Expenses expenseId = getExpenseByExpenseId(ex.getId());
				
							if(null != expenseId.getExpenseId()){
								expenseArray[0] = expenseId.getExpenseId();
							}else{
								expenseArray[0] = "--";
							}
							if(null != expenseId.getPurpose()){

								expenseArray[1] = expenseId.getPurpose();
							}else{
								expenseArray[1] = "--";
							}
							
							if(null != expenseId.getProjectMembers().getProjects()){
								if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
									expenseArray[2] = expenseId.getProjectMembers().getProjects().getProjectName();
								}
							}else{
								expenseArray[2] = "--";
							}
							
							if(null != expenseId.getClientName()){

								expenseArray[3] = expenseId.getClientName();
							}else{
								expenseArray[3] = "--";
							}
							
							if(null != expenseId.getStartDate()){
								expenseId.setStartDateStr(Constants.DF_DMY.format(expenseId.getStartDate()));
								expenseArray[4] = expenseId.getStartDateStr();
							}else{
								expenseArray[4] = "--";
							}
							
							if(null != expenseId.getEndDate()){
								expenseId.setEndDateStr(Constants.DF_DMY.format(expenseId.getEndDate()));
								expenseArray[5] = expenseId.getEndDateStr();
							}else{
								expenseArray[5] = "--";
							}
							
							if(null != expenseId.getNoOfExpenses()){

								expenseArray[6] = expenseId.getNoOfExpenses();
							}else{
								expenseArray[6] = "--";
							}
							

							if(expenseId.getTotalAmount() != 0){
				                StringBuilder str = new StringBuilder();
								expenseArray[7] = str.append(expenseId.getTotalAmount()).toString();
							}else{
								expenseArray[7] = "--";
							}
							
							if(null != expenseId.getStatus()){
								expenseArray[8] = expenseId.getExpenseStatus().getValue();
							}else{
								expenseArray[8] = "--";
							}
							
			}
			arrayList.add(expenseArray);
		}
		return arrayList;
	}

	@Override
	public File exportAllMyExpensesToExcel(List<Expenses> expenseList,OrgLocations orgLoc) throws IOException {
		
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrg().getOrgName()+ "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		TimeSheetSubmissionCount requirementSubCount=null;
		createExcelsheet = getAllExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
		int column = 0;
		for (Expenses expensesList : expenseList) {
			writeRow = createExcelsheet.createRow(column+1);
			createExcelsheet = createAllXlsSheet(createExcelsheet, writeRow, expensesList);
			column++;
		}
		 try {
			file = File.createTempFile(orgLoc.getOrg().getOrgName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return file;
	}
	
	private Sheet getAllExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, TimeSheetSubmissionCount requirementSubCount) {
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

		String[] headers = Constants.ALL_MY_EXPENSES_HEADERS;;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);

		return createExcelsheet;
	}
	
	private Sheet createAllXlsSheet(Sheet createExcelsheet, Row writeRow, Expenses ex) {
		if(ex.getExpenseId() !=null){
			Expenses expenseId =getExpenseByExpenseId(ex.getId());
			
						if(null != expenseId.getExpenseId()){
							writeRow.createCell(0).setCellValue(expenseId.getExpenseId());
							createExcelsheet.autoSizeColumn(0);
						}
						if(null != expenseId.getPurpose()){
							writeRow.createCell(1).setCellValue(expenseId.getPurpose());
							createExcelsheet.autoSizeColumn(1);
						}
						if(null != expenseId.getProjectMembers().getProjects()){
							if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
								writeRow.createCell(2).setCellValue(expenseId.getProjectMembers().getProjects().getProjectName());
								createExcelsheet.autoSizeColumn(2);
							}
						}
						
						if(null != expenseId.getClientName()){
							writeRow.createCell(3).setCellValue(expenseId.getClientName());
							createExcelsheet.autoSizeColumn(3);
						}
						
						if(null != expenseId.getStartDate()){
							expenseId.setStartDateStr(Constants.DF_DMY.format(expenseId.getStartDate()));
							writeRow.createCell(4).setCellValue(expenseId.getStartDateStr());
							createExcelsheet.autoSizeColumn(4);
						}
						if(null != expenseId.getEndDate()){
							expenseId.setEndDateStr(Constants.DF_DMY.format(expenseId.getEndDate()));
							writeRow.createCell(5).setCellValue(expenseId.getEndDateStr());
							createExcelsheet.autoSizeColumn(5);
						}
						
						if(null != expenseId.getNoOfExpenses()){
							writeRow.createCell(6).setCellValue(expenseId.getNoOfExpenses());
							createExcelsheet.autoSizeColumn(6);
						}
						if(expenseId.getTotalAmount() != 0){
			                StringBuilder str = new StringBuilder();
			                writeRow.createCell(7).setCellValue(str.append(expenseId.getTotalAmount()).toString());
			                createExcelsheet.autoSizeColumn(7);
						}
						if(null != expenseId.getExpenseStatus()){
							writeRow.createCell(8).setCellValue(expenseId.getExpenseStatus().getValue());
							createExcelsheet.autoSizeColumn(8);
						}
		}
	return createExcelsheet;
}

	@Override
	public List<ExpensesApproverHierarchy> getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId, Long orgLocationId, String expenseStatus) throws Exception
	{
		String submissionStatus = null;
		if ("submitted".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "SUBMITTEDFORAPPROVAL";
		}
		if ("approved".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "APPROVED";
		}
		if ("rejected".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REJECTED";
		}
		if ("reopen".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REOPENED";
		}
		
		return expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, submissionStatus);
	}

	@Override
	public List<Expenses> getTotalMyExpensesListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId, Long orgLocationId, String expenseStatus) throws Exception
	{
		String submissionStatus = null;
		if ("submitted".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "SUBMITTED";
		}
		if ("approved".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "APPROVED";
		}
		if ("rejected".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "REJECTED";
		}
		if ("draft".equalsIgnoreCase(expenseStatus)) {
			submissionStatus = "DRAFT";
		}
		return expensesApproverHierarchyDao.getTotalMyExpensesListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, submissionStatus);
	}
	
		
}
