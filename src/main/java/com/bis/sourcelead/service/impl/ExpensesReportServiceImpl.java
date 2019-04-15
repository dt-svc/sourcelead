package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesReportDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExpensesReportServiceImpl implements ExpensesReportService {

	@Autowired
	ExpensesReportDao expensesReportDao;

	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesReportServiceImpl.class);
	
	@Override
	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId,Long loginOrgLocationId) throws Exception {
		return expensesReportDao.getAllExpensesReportListByLoginUserIdAndOrgLocationId(loginUserId, loginOrgLocationId);
	}

	@Override
	public Map<String, Map<String,String>> getYearsAndQuartersComboList(String dateFormat) throws JSONException, ParseException {
		 LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(1);
		 Map<String, Map<String,String>> yearsAndQuartersComboList = new LinkedHashMap<String, Map<String,String>>();
		 if(tsrYearsList != null && !tsrYearsList.isEmpty()){
			 for(String year : tsrYearsList){
				 Map<String, String> quarterlyDate = getStartAndEndDate(Integer.parseInt(year),dateFormat);
				 yearsAndQuartersComboList.put(year, quarterlyDate);
			 }
		 }
		 return yearsAndQuartersComboList;
	}
	
	
	private Map<String, String> getStartAndEndDate(int years,String dateFormat) {
		      Map<String, String> startEndDate = new LinkedHashMap<>();
		      int count = 1;
		     Calendar YearStartDate = Calendar.getInstance();
		     Calendar YearEndDate = Calendar.getInstance();
			for(int i =0;i<12;i=i+3){
			    Calendar Q1StartDate=Calendar.getInstance();
			    Q1StartDate.set(Calendar.YEAR,years);
			    Q1StartDate.set(Calendar.MONTH,i);
			    Q1StartDate.set(Calendar.DATE,1);
			   
			    if(count==1)
			    YearStartDate = Q1StartDate;
			   
			    Calendar Q1EndDate =Calendar.getInstance();
			    Q1EndDate.set(Calendar.YEAR,years);
			    Q1EndDate.set(Calendar.MONTH, i+2);
			    Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			    
			    if(count==4)
			    YearEndDate = Q1EndDate;
			    
			    String StartDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat,Q1StartDate.getTime());
			    String endDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat,Q1EndDate.getTime());
			    startEndDate.put("Q"+count,StartDate+" TO "+endDate);
			    count++;
		   }
		    startEndDate.put("All",commonUtil.dateStrFromOrgDateFormatter(dateFormat,YearStartDate.getTime())+" TO "+commonUtil.dateStrFromOrgDateFormatter(dateFormat,YearEndDate.getTime()));
			return startEndDate;
		
	}
	
	@Override
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date startDate, Date endtDate) throws ParseException {
		  return expensesReportDao.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(proMembersId,startDate,endtDate);
	}
	
	@Override
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(Long proMembersId,Date startDate, Date endtDate) throws ParseException {
		  return expensesReportDao.getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(proMembersId,startDate,endtDate);
	}

	@Override
	public Map<String, String> getmonthStringNamefromStartAndEndDate(Date convertedStartDate, Date convertedEndDate,List<ProjectMembers> projectMembers) {
		Map<String,String> monthStringDate = new LinkedHashMap<>();
		 LocalDate date1 = new LocalDate(convertedStartDate);
		 //LocalDate date2 = new LocalDate(convertedEndDate);
		 DateFormat format =Constants.M;
		 for(int i=0;i<3;i++){
			 Calendar startCal = Calendar.getInstance();  
			 startCal.setTime(date1.toDate());
			 startCal.add(Calendar.MONTH, i);
			 
			 Calendar endCal = Calendar.getInstance();
			 endCal.setTime(date1.toDate());
			 endCal.add(Calendar.MONTH, i);
			 endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			 
			 	/*List<Long> proMembersIds = new ArrayList<>();
				for(ProjectMembers proMembers:projectMembers){
					List<ExpensesApproverHierarchy> expensesRecoreds = expensesReportDao.getExpensesTotalMontWiseCountBasedOnprojectMemberIdStartDateAndEndDate(convertedStartDate,convertedEndDate,proMembers.getId());
						for( ExpensesApproverHierarchy expRecored:expensesRecoreds){
							 expRecored.getExpenseId().getTotalAmount();
						}
					proMembersIds.add(proMembers.getId());
				}*/
			 monthStringDate.put(format.format(startCal.getTime()),"");
		 }
		return monthStringDate;
	}

	@Override
	public File expensesReportExportListPdf(String frequencyMonth, OrgLocations orgLoc, User loginUser) throws ParseException, IOException {
		List<ProjectMembers> projectMembers = null;
		//SimpleDateFormat simpleDateFormat = orgLoc.getLocFullFormDateFormatter();
		String[] dates = frequencyMonth.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		Date convertedStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
		Date convertedEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endtDate);
		 LinkedHashSet<String> dateformat = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate,convertedEndDate);
		 	 String[] headersDetails = Constants.EXPENSES_REPORTS_HEADERS;
		     String[] monthHedders = dateformat.toArray(new String[dateformat.size()]);
		     String[] headersAmont = Constants.EXPENSES_REPORTS_HEADERS_1;
		     String[] headerOne = (String[])ArrayUtils.addAll(headersDetails, monthHedders);
		     String[] headers = (String[])ArrayUtils.addAll(headerOne, headersAmont);
		     Document document = null;
		     if(headers.length > 14){
		    	  document = new Document(PageSize.A3.rotate());
		     }else{
		    	  document = new Document(PageSize.LETTER.rotate()); 
		     }DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
 		     StringBuilder exprortReports =  new StringBuilder();
 		     exprortReports.append("EXPORT_REPORTS_").append(dateFormat.format(new Date()));
			 StringBuilder fileSB = new StringBuilder();
			 File file = File.createTempFile(fileSB.append("EXPORT_REPORTS_").append(dateFormat.format(new Date())).toString(),".pdf");
			 file.setReadable(true);
			 
			 try {
			        // Get an instance of PdfWriter and create a Table.pdf file as an output.
			        PdfWriter.getInstance(document, new FileOutputStream(file));
			        document.open();

			        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			        @SuppressWarnings("deprecation")
					BaseColor myColor = WebColors.getRGBColor("#000000");
			        boldFont.setColor(myColor);
			        StringBuilder userStyle = new StringBuilder();
			        userStyle.append(orgLoc.getOrg().getOrgName()).append(" ").append("_EXPORT_REPORTS");
		          

			        StringBuilder orgNameSB = new StringBuilder();
		            Paragraph para = new Paragraph(orgNameSB.append("EXPORT_REPORTS : ").append(orgLoc.getOrg().getOrgName()).toString());
		            para.setAlignment(Element.ALIGN_CENTER);
		            document.add(para);
			        
			        
			        // Create an instance of PdfPTable. After that we transform the header and
			        PdfPTable table = new PdfPTable(headers.length);
			        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
			        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
			        if(headers.length > 14){
			        	cell1.setColspan(28);
			        }else{
			        	cell1.setColspan(20);
			        }
			        headerCellStyle(cell1);
			        table.setWidthPercentage(100);
			        if(headers.length > 14){
			        	table.setWidths(new int[]{3,5,5,5,3,3,3,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5});			        		
			        }else{
			        	table.setWidths(new int[]{3,5,5,5,3,3,3,5,5,5,5,5,5,5});
			        }
		            
		            table.addCell(cell1);
			        for (int i = 0; i < headers.length; i++) {
			            String header = headers[i];
			            PdfPCell cell = new PdfPCell();
			            cell.setGrayFill(0.9f);
			            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
			            @SuppressWarnings("deprecation")
						BaseColor myBorderColor = WebColors.getRGBColor("#808080");
			            cell.setBackgroundColor(myBorderColor);
			            table.addCell(cell);
			        }
			        table.completeRow();
			        java.util.List<String[]> expensesList = new ArrayList<>();
			        projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(convertedStartDate, convertedEndDate, orgLoc.getId(), loginUser.getId());
			        for(ProjectMembers proMembers : projectMembers){
			        	expensesList.add(prepareUserStringArray(proMembers,headers,frequencyMonth,orgLoc));
			        }
			        for (String[] userArray: expensesList) {
		                for (int i = 0; i < userArray.length; i++) {
		                    String datum = userArray[i];
		                    PdfPCell cell = new PdfPCell();
		                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
		                    table.addCell(cell);
		                }
		                table.completeRow();
		            }
			        document.addTitle("Expenses Reports");
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

	private String[] prepareUserStringArray(ProjectMembers projectMember, String[] headers, String frequencyMonth,OrgLocations locations) throws ParseException {
		//SimpleDateFormat simpleDateFormat = locations.getLocFullFormDateFormatter();
		String[] dates = frequencyMonth.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		Date convertedStartDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),startDate);
		Date convertedEndDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),endtDate);
		LinkedHashSet<String> dateList = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate, convertedEndDate);
		DateFormat format =Constants.Y;
		String year = format.format(convertedEndDate);
		
		 Map<String,Date> janStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(0, year);
		 Map<String,Date> febStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(1, year);
		 Map<String,Date> marStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(2, year);
		 Map<String,Date> aprStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(3, year);
		 Map<String,Date> mayStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(4, year);
		 Map<String,Date> junStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(5, year);
		 Map<String,Date> julStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(6, year);
		 Map<String,Date> augStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(7, year);
		 Map<String,Date> sepStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(8, year);
		 Map<String,Date> octStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(9, year);
		 Map<String,Date> novStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(10,year);
		 Map<String,Date> decStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(11,year);
		 
		 Date janStartDate = janStartDateAndEndDate.get("STARTDATE");
		 Date janEndDate =   janStartDateAndEndDate.get("ENDDATE");
		 
		 Date febStartDate = febStartDateAndEndDate.get("STARTDATE");
		 Date febEndDate =   febStartDateAndEndDate.get("ENDDATE");
		 
		 Date marStartDate = marStartDateAndEndDate.get("STARTDATE");
		 Date marEndDate =   marStartDateAndEndDate.get("ENDDATE");
		 
		 Date aprStartDate = aprStartDateAndEndDate.get("STARTDATE");
		 Date aprEndDate =   aprStartDateAndEndDate.get("ENDDATE");
		 
		 Date mayStartDate = mayStartDateAndEndDate.get("STARTDATE");
		 Date mayEndDate =   mayStartDateAndEndDate.get("ENDDATE");
		 
		 Date junStartDate = junStartDateAndEndDate.get("STARTDATE");
		 Date junEndDate =   junStartDateAndEndDate.get("ENDDATE");
		 
		 Date julStartDate = julStartDateAndEndDate.get("STARTDATE");
		 Date julEndDate =   julStartDateAndEndDate.get("ENDDATE");
		 
		 Date augStartDate = augStartDateAndEndDate.get("STARTDATE");
		 Date augEndDate =   augStartDateAndEndDate.get("ENDDATE");
		 
		 Date sepStartDate = sepStartDateAndEndDate.get("STARTDATE");
		 Date sepEndDate =   sepStartDateAndEndDate.get("ENDDATE");
		 
		 Date octStartDate = octStartDateAndEndDate.get("STARTDATE");
		 Date octEndDate =   octStartDateAndEndDate.get("ENDDATE");
		 
		 Date novStartDate = novStartDateAndEndDate.get("STARTDATE");
		 Date novEndDate =   novStartDateAndEndDate.get("ENDDATE");
		 
		 Date decStartDate = decStartDateAndEndDate.get("STARTDATE");
		 Date decEndDate =   decStartDateAndEndDate.get("ENDDATE");
		int count = 0;
		 String expensesArray[] = new String[headers.length];
		if(projectMember != null){
				 Float Q1ExpenseTotal = 0f;
				 Float Q2ExpenseTotal = 0f;
				 Float Q3ExpenseTotal = 0f;
				 Float Q4ExpenseTotal = 0f;
				 Float expenseAmount = 0f;
				 Float expenseApprovedAmount = 0f;
				 Float expenseUnApprovedAmount = 0f;
				 Float claimedExpenseApprovedAmount = 0f;
				 Float claimedExpenseUnApprovedAmount = 0f;
				 if(count == 0)
					 projectMember.setMonthsStringFormat(dateList);
				 List<Expenses> expenses  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
				 List<Expenses> expensesClaimendAmount  = getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
				 if(expenses != null && !expenses.isEmpty()){
					 BigDecimal janApprovedTotal = new BigDecimal(0);
					 BigDecimal janUnApprovedTotal = new BigDecimal(0);
					 BigDecimal febApprovedTotal = new BigDecimal(0);
					 BigDecimal febUnApprovedTotal = new BigDecimal(0);
					 BigDecimal marApprovedTotal = new BigDecimal(0);
					 BigDecimal marUnApprovedTotal = new BigDecimal(0);
					 BigDecimal aprApprovedTotal = new BigDecimal(0);
					 BigDecimal aprUnApprovedTotal = new BigDecimal(0);
					 BigDecimal mayApprovedTotal = new BigDecimal(0);
					 BigDecimal mayUnApprovedTotal = new BigDecimal(0);
					 BigDecimal junApprovedTotal = new BigDecimal(0);
					 BigDecimal junUnApprovedTotal = new BigDecimal(0);
					 BigDecimal julApprovedTotal = new BigDecimal(0);
					 BigDecimal julUnApprovedTotal = new BigDecimal(0);
					 BigDecimal augApprovedTotal = new BigDecimal(0);
					 BigDecimal augUnApprovedTotal = new BigDecimal(0);
					 BigDecimal sepApprovedTotal = new BigDecimal(0);
					 BigDecimal sepUnApprovedTotal = new BigDecimal(0);
					 BigDecimal octApprovedTotal = new BigDecimal(0);
					 BigDecimal octUnApprovedTotal = new BigDecimal(0);
					 BigDecimal novApprovedTotal = new BigDecimal(0);
					 BigDecimal novUnApprovedTotal = new BigDecimal(0);
					 BigDecimal decApprovedTotal = new BigDecimal(0);
					 BigDecimal decUnApprovedTotal = new BigDecimal(0);
				 for(Expenses exp:expenses){
					 if(projectMember!=  null && null != projectMember.getProjectMemberCandidteId()){
							if(projectMember.getEmployee()!= null && null != projectMember.getEmployee().getEmployeeId()){
								expensesArray[0] = (projectMember.getEmployee().getEmployeeId());
							}else {
								expensesArray[0] = ("--");
							}
							if(null != projectMember.getProjectMemberCandidteId()){
								if(null != projectMember.getProjectMemberCandidteId().getLastName()){
									StringBuilder nameBuilder = new StringBuilder();
									expensesArray[1] = nameBuilder.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(projectMember.getProjectMemberCandidteId().getLastName()).toString();
								}else{
									expensesArray[1] = projectMember.getProjectMemberCandidteId().getFirstName();
								}
								
								if(null !=projectMember.getProjectMemberCandidteId().getEmail()){
									expensesArray[2] = projectMember.getProjectMemberCandidteId().getEmail();
								}else{
									expensesArray[2] = "--";
								}
								if(null != projectMember.getProjectMemberCandidteId().getMobilePhone()){
									expensesArray[3] = projectMember.getProjectMemberCandidteId().getMobilePhone();
								}else{
									expensesArray[3] = "--";
								}
							}
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType()){
								expensesArray[4] = projectMember.getProjectMemberCandidteId().getDesignationType();
							}else{
								expensesArray[4] = "--";
							}

							if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
			     			  OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
			     			  	expensesArray[5] = orgRelation.getResponderLocationId().getOrgLocationName();
			     			  	expensesArray[6] = orgRelation.getResponderLocationId().getUser().getFullName();
			           	     }else{
			           	    	expensesArray[5] = "--";
								expensesArray[6] = "--";
			           	     }
							
							
							
							 /*if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
									expensesArray[5] = orgRelation.getResponderLocationId().getOrg().getOrgName();
									expensesArray[6] = orgRelation.getResponderLocationId().getOrg().getUser().getFirstName();
							 }else{
								 expensesArray[5] = "--";
								 expensesArray[6] = "--";
							 }*/
							
							if(null != projectMember.getProjects()){
								expensesArray[7] = projectMember.getProjects().getProjectName();
							}else{
								expensesArray[7] = "--";
							}
						}
					 
						 if(projectMember.getProjectClientsId() != null && projectMember.getProjectClientsId().getOrgRelationId() !=null && projectMember.getProjectClientsId().getOrgRelationId().getClientName() != null){
							 expensesArray[8] = projectMember.getProjectClientsId().getOrgRelationId().getClientName();
						 }else{
							 expensesArray[8] ="--"; 
						 }
						 
						 Date d = new Date(exp.getEndDate().getTime());
						if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getJanExpenseApprovedTotal() != null){
									 janApprovedTotal = new BigDecimal(projectMember.getJanExpenseApprovedTotal());
								 }
								  janApprovedTotal=janApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setJanExpenseApprovedTotal(janApprovedTotal.floatValue());
								  expenseApprovedAmount += janApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getJanExpenseUnApprovedTotal() != null){
									 janUnApprovedTotal = new BigDecimal(projectMember.getJanExpenseUnApprovedTotal());
								 }
								 janUnApprovedTotal=janUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setJanExpenseUnApprovedTotal(janUnApprovedTotal.floatValue());
								 expenseUnApprovedAmount += janUnApprovedTotal.floatValue();
							 }
							 projectMember.setJanExpenseTotal(janApprovedTotal.floatValue()+janUnApprovedTotal.floatValue());
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getFebExpenseApprovedTotal() != null){
									 febApprovedTotal = new BigDecimal(projectMember.getFebExpenseApprovedTotal());
								 }
								  febApprovedTotal=febApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setFebExpenseApprovedTotal(febApprovedTotal.floatValue());
								  expenseApprovedAmount += febApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getFebExpenseUnApprovedTotal() != null){
									 febUnApprovedTotal = new BigDecimal(projectMember.getFebExpenseUnApprovedTotal());
								 }
								 febUnApprovedTotal=febUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setFebExpenseUnApprovedTotal(febUnApprovedTotal.floatValue());
								  expenseUnApprovedAmount +=febUnApprovedTotal.floatValue();
							 }
							 projectMember.setFebExpenseTotal(febApprovedTotal.floatValue()+febUnApprovedTotal.floatValue());
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getMarExpenseApprovedTotal() != null){
									 marApprovedTotal = new BigDecimal(projectMember.getMarExpenseApprovedTotal());
								 }
								  marApprovedTotal=marApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setMarExpenseApprovedTotal(marApprovedTotal.floatValue());
								  expenseApprovedAmount +=marApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getMarExpenseUnApprovedTotal() != null){
									 marUnApprovedTotal = new BigDecimal(projectMember.getMarExpenseUnApprovedTotal());
								 }
								 marUnApprovedTotal =marUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setMarExpenseUnApprovedTotal(marUnApprovedTotal.floatValue());
								  expenseUnApprovedAmount += marUnApprovedTotal.floatValue();
							 }
							 projectMember.setMarExpenseTotal(marApprovedTotal.floatValue()+marUnApprovedTotal.floatValue());
							
						 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAprExpenseApprovedTotal() != null){
								 aprApprovedTotal = new BigDecimal(projectMember.getAprExpenseApprovedTotal());
							 }
							  aprApprovedTotal=aprApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setAprExpenseApprovedTotal(aprApprovedTotal.floatValue());
							  expenseApprovedAmount = expenseApprovedAmount.floatValue()+aprApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getAprExpenseUnApprovedTotal() != null){
								 aprUnApprovedTotal = new BigDecimal(projectMember.getAprExpenseUnApprovedTotal());
							 }
							 aprUnApprovedTotal=aprUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setAprExpenseUnApprovedTotal(aprUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=aprUnApprovedTotal.floatValue();
						 }
						 projectMember.setAprExpenseTotal(aprApprovedTotal.floatValue()+aprUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getMayExpenseApprovedTotal() != null){
								 mayApprovedTotal = new BigDecimal(projectMember.getMayExpenseApprovedTotal());
							 }
							  mayApprovedTotal=mayApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setMayExpenseApprovedTotal(mayApprovedTotal.floatValue());
							  expenseApprovedAmount += mayApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getMayExpenseUnApprovedTotal() != null){
								 mayUnApprovedTotal = new BigDecimal(projectMember.getMayExpenseUnApprovedTotal());
							 }
							 mayUnApprovedTotal =mayUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setMayExpenseUnApprovedTotal(mayUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=mayUnApprovedTotal.floatValue();
						 }
						 projectMember.setMayExpenseTotal(mayApprovedTotal.floatValue()+mayUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJunExpenseApprovedTotal() != null){
								 junApprovedTotal = new BigDecimal(projectMember.getJunExpenseApprovedTotal());
							 }
							  junApprovedTotal=junApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setJunExpenseApprovedTotal(junApprovedTotal.floatValue());
							  expenseApprovedAmount +=junApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getJunExpenseUnApprovedTotal() != null){
								 junUnApprovedTotal = new BigDecimal(projectMember.getJunExpenseUnApprovedTotal());
							 }
							 junUnApprovedTotal=junUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setJunExpenseUnApprovedTotal(junUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += junUnApprovedTotal.floatValue();
						 }
						 projectMember.setJunExpenseTotal(junApprovedTotal.floatValue()+junUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJulExpenseApprovedTotal() != null){
								 julApprovedTotal = new BigDecimal(projectMember.getJulExpenseApprovedTotal());
							 }
							  julApprovedTotal=julApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setJulExpenseApprovedTotal(julApprovedTotal.floatValue());
							  expenseApprovedAmount +=julApprovedTotal.floatValue();
						 }
						 else{
							 
							 if(projectMember.getJulExpenseUnApprovedTotal() != null){
								 julUnApprovedTotal = new BigDecimal(projectMember.getJulExpenseUnApprovedTotal());
							 }
							 julUnApprovedTotal=julUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setJulExpenseUnApprovedTotal(julUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=julUnApprovedTotal.floatValue();
						 }
						 projectMember.setJulExpenseTotal(julApprovedTotal.floatValue()+julUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAugExpenseApprovedTotal() != null){
								 augApprovedTotal = new BigDecimal(projectMember.getAugExpenseApprovedTotal());
							 }
							  augApprovedTotal=augApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setAugExpenseApprovedTotal(augApprovedTotal.floatValue());
							  expenseApprovedAmount += augApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getAugExpenseUnApprovedTotal() != null){
								 augUnApprovedTotal = new BigDecimal(projectMember.getAugExpenseUnApprovedTotal());
							 }
							 augUnApprovedTotal=augUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setAugExpenseUnApprovedTotal(augUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += augUnApprovedTotal.floatValue();
						 }
						 projectMember.setAugExpenseTotal(augApprovedTotal.floatValue()+augUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getSepExpenseApprovedTotal() != null){
								 sepApprovedTotal = new BigDecimal(projectMember.getSepExpenseApprovedTotal());
							 }
							  sepApprovedTotal=sepApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setSepExpenseApprovedTotal(sepApprovedTotal.floatValue());
							  expenseApprovedAmount += sepApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getSepExpenseUnApprovedTotal() != null){
								 sepUnApprovedTotal = new BigDecimal(projectMember.getSepExpenseUnApprovedTotal());
							 }
							 sepUnApprovedTotal=sepUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setSepExpenseUnApprovedTotal(sepUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += sepUnApprovedTotal.floatValue();
						 }
						 projectMember.setSepExpenseTotal(sepApprovedTotal.floatValue()+sepUnApprovedTotal.floatValue());
						
					 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getOctExpenseApprovedTotal() != null){
								 octApprovedTotal = new BigDecimal(projectMember.getOctExpenseApprovedTotal());
							 }
							  octApprovedTotal=octApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setOctExpenseApprovedTotal(octApprovedTotal.floatValue());
							  expenseApprovedAmount += octApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getOctExpenseUnApprovedTotal() != null){
								 octUnApprovedTotal = new BigDecimal(projectMember.getOctExpenseUnApprovedTotal());
							 }
							 octUnApprovedTotal=octUnApprovedTotal .add(exp.getTotalAmount());
							 projectMember.setOctExpenseUnApprovedTotal(octUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += octUnApprovedTotal.floatValue();
						 }
						 projectMember.setOctExpenseTotal(octApprovedTotal.floatValue()+octUnApprovedTotal.floatValue());
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getNovExpenseApprovedTotal() != null){
								 novApprovedTotal = new BigDecimal(projectMember.getNovExpenseApprovedTotal());
							 }
							  novApprovedTotal=novApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setNovExpenseApprovedTotal(novApprovedTotal.floatValue());
							  expenseApprovedAmount = expenseApprovedAmount.floatValue()+novApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getNovExpenseUnApprovedTotal() != null){
								 novUnApprovedTotal = new BigDecimal(projectMember.getNovExpenseUnApprovedTotal());
							 }
							 novUnApprovedTotal=novUnApprovedTotal .add(exp.getTotalAmount());
							 projectMember.setNovExpenseUnApprovedTotal(novUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += novUnApprovedTotal.floatValue();
						 }
						 projectMember.setNovExpenseTotal(novApprovedTotal.floatValue()+novUnApprovedTotal.floatValue());
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getDecExpenseApprovedTotal() != null){
								 decApprovedTotal = new BigDecimal(projectMember.getDecExpenseApprovedTotal());
							 }
							  decApprovedTotal=decApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setDecExpenseApprovedTotal(decApprovedTotal.floatValue());
							  expenseApprovedAmount += decApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getDecExpenseUnApprovedTotal() != null){
								 decUnApprovedTotal = new BigDecimal(projectMember.getDecExpenseUnApprovedTotal());
							 }
							 decUnApprovedTotal =decUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setDecExpenseUnApprovedTotal(decUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += decUnApprovedTotal.floatValue();
						 }
						 projectMember.setDecExpenseTotal(decApprovedTotal.floatValue()+decUnApprovedTotal.floatValue());
						 
					  } 
				     }
				 
				      Q1ExpenseTotal = projectMember.getJanExpenseTotal()+projectMember.getFebExpenseTotal()+projectMember.getMarExpenseTotal();
				      Q2ExpenseTotal = projectMember.getAprExpenseTotal()+projectMember.getMayExpenseTotal()+projectMember.getJunExpenseTotal();
				      Q3ExpenseTotal = projectMember.getJulExpenseTotal()+projectMember.getAugExpenseTotal()+projectMember.getSepExpenseTotal();
				      Q4ExpenseTotal = projectMember.getOctExpenseTotal()+projectMember.getNovExpenseTotal()+projectMember.getDecExpenseTotal();
					  expenseAmount = Q1ExpenseTotal+Q2ExpenseTotal+Q3ExpenseTotal+Q4ExpenseTotal;
				      projectMember.setQ1ExpenseTotal(Q1ExpenseTotal);
				      projectMember.setQ2ExpenseTotal(Q2ExpenseTotal);
				      projectMember.setQ3ExpenseTotal(Q3ExpenseTotal);
				      projectMember.setQ4ExpenseTotal(Q4ExpenseTotal);
				      projectMember.setExpenseTotal(expenseAmount);
				      projectMember.setExpenseApprovedTotal(expenseApprovedAmount);
				      projectMember.setExpenseUnApprovedTotal(expenseUnApprovedAmount);
				      projectMember.setProjectStatus("ACTION REQUIRED");
				     
				      
				      for(Expenses exp:expensesClaimendAmount){
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 BigDecimal approvedClaimedTotal = new BigDecimal(0);
								 if(projectMember.getClaimedExpenseApprovedTotal() != null){
									 approvedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseApprovedTotal());
								 }
								 approvedClaimedTotal .add(exp.getTotalAmount());
								  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal.floatValue());
								  claimedExpenseApprovedAmount +=approvedClaimedTotal.floatValue();
							 }
							 else{
								 BigDecimal unApprovedClaimedTotal = new BigDecimal(0);
								 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
									 unApprovedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseUnApprovedTotal());
								 }
								 unApprovedClaimedTotal.add(exp.getTotalAmount());
								 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal.floatValue());
								 claimedExpenseUnApprovedAmount += unApprovedClaimedTotal.floatValue();
							 }
							 
							 Float expenseClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseTotal() != null){
								 expenseClaimedTotal = projectMember.getClaimedExpenseTotal();
							 }
							 if(exp.getExpenseStatus().equals(Submission_Status.APPROVED)) {
								 expenseClaimedTotal += exp.getTotalAmount().floatValue();
								 projectMember.setClaimedExpenseTotal(expenseClaimedTotal);
							 }
					   }
				      int countArray = 8;
				      for(String monthHeader :headers){
				    	  if(monthHeader.equalsIgnoreCase("January")){
				    		  expensesArray[countArray+1] = projectMember.getJanExpenseTotal().toString();
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("February")){
				    		  expensesArray[countArray+2] = projectMember.getFebExpenseTotal().toString();
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("March")){
				    		  expensesArray[countArray+3] = projectMember.getMarExpenseTotal().toString();
				    	  }
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("April")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+4] = projectMember.getAprExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+1] = projectMember.getAprExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("May")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+5] = projectMember.getMayExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+2] = projectMember.getMayExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("June")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+6] = projectMember.getJunExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+3] = projectMember.getJunExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("July")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+7] = projectMember.getJulExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+1] = projectMember.getJulExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("August")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+8] = projectMember.getAugExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+2] = projectMember.getAugExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("September")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+9] = projectMember.getSepExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+3] = projectMember.getSepExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("October")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+10] = projectMember.getOctExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+1] = projectMember.getOctExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("November")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+11] = projectMember.getNovExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+2] = projectMember.getNovExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("December")){
				    		  if(headers.length >14){
				    			  expensesArray[countArray+12] = projectMember.getDecExpenseTotal().toString();
				    		  }
				    		  else{
				    			  expensesArray[countArray+3] = projectMember.getDecExpenseTotal().toString();
				    		  }
				    		 
				    	  }
				    	  
				      }
				      
				      if(headers.length >14){
				    	  expensesArray[headers.length-2]= projectMember.getExpenseTotal().toString();
				    	  expensesArray[headers.length-1]= projectMember.getClaimedExpenseTotal().toString();
				      }else{
				    	  expensesArray[headers.length-2] =  projectMember.getExpenseTotal().toString();
				    	  if(projectMember.getClaimedExpenseTotal() != null)
				    	  expensesArray[headers.length-1] =  projectMember.getClaimedExpenseTotal().toString();
				      }
				   }
			    
		 
		}
		
		
		return expensesArray;
	}

	private void headerCellStyle(PdfPCell cell) {
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setPaddingTop(0f);
		    cell.setPaddingBottom(7f);
		    cell.setBackgroundColor(new BaseColor(0,121,182));
		    cell.setBorder(0);
		    cell.setBorderWidthBottom(2f);
	}

	@Override
	public File expensesReportExportListExcel(String frequencyMonth, OrgLocations orgLoc, User loginUser) throws IOException, ParseException {
		//SimpleDateFormat simpleDateFormat = orgLoc.getLocFullFormDateFormatter();
		File file = null;
		String[] dates = frequencyMonth.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		Date convertedStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
		Date convertedEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endtDate);
		LinkedHashSet<String> dateformat = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate,convertedEndDate);
		String[] headersDetails = Constants.EXPENSES_REPORTS_HEADERS;
	     String[] monthHedders = dateformat.toArray(new String[dateformat.size()]);
	     String[] headersAmont = Constants.EXPENSES_REPORTS_HEADERS_1;
	     String[] headerOne = (String[])ArrayUtils.addAll(headersDetails, monthHedders);
		String[] headers = (String[])ArrayUtils.addAll(headerOne, headersAmont);
		
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrg().getOrgName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,convertedStartDate,convertedEndDate);
		 int column = 0;
		 List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(convertedStartDate, convertedEndDate, orgLoc.getId(), loginUser.getId());
		 if(projectMembers != null && !projectMembers.isEmpty()){
			 for (ProjectMembers proMembers : projectMembers) {
					writeRow = createExcelsheet.createRow(column+1);
					 List<Expenses> expenses  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(proMembers.getId(),convertedStartDate,convertedEndDate);
					 if(expenses !=null && !expenses.isEmpty()){
						 createExcelsheet = createXlsSheet(createExcelsheet, writeRow, proMembers,frequencyMonth,headers,orgLoc);
							column++; 
					 }
					
				} 
		 }
		 file = File.createTempFile(orgLoc.getOrg().getOrgName() + "_EXPENSES_REPORSTS_" + dateFormat.format(new Date()),".xls");
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

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ProjectMembers projectMember, String frequencyMonth, String[] headers,OrgLocations orgLoc) throws ParseException {
		//SimpleDateFormat simpleDateFormat = orgLoc.getLocFullFormDateFormatter();
		String[] dates = frequencyMonth.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		Date convertedStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
		Date convertedEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endtDate);
		LinkedHashSet<String> dateList = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate, convertedEndDate);
		DateFormat format =Constants.Y;
		String year = format.format(convertedEndDate);
		
		 Map<String,Date> janStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(0, year);
		 Map<String,Date> febStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(1, year);
		 Map<String,Date> marStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(2, year);
		 Map<String,Date> aprStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(3, year);
		 Map<String,Date> mayStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(4, year);
		 Map<String,Date> junStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(5, year);
		 Map<String,Date> julStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(6, year);
		 Map<String,Date> augStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(7, year);
		 Map<String,Date> sepStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(8, year);
		 Map<String,Date> octStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(9, year);
		 Map<String,Date> novStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(10,year);
		 Map<String,Date> decStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(11,year);
		 
		 Date janStartDate = janStartDateAndEndDate.get("STARTDATE");
		 Date janEndDate =   janStartDateAndEndDate.get("ENDDATE");
		 
		 Date febStartDate = febStartDateAndEndDate.get("STARTDATE");
		 Date febEndDate =   febStartDateAndEndDate.get("ENDDATE");
		 
		 Date marStartDate = marStartDateAndEndDate.get("STARTDATE");
		 Date marEndDate =   marStartDateAndEndDate.get("ENDDATE");
		 
		 Date aprStartDate = aprStartDateAndEndDate.get("STARTDATE");
		 Date aprEndDate =   aprStartDateAndEndDate.get("ENDDATE");
		 
		 Date mayStartDate = mayStartDateAndEndDate.get("STARTDATE");
		 Date mayEndDate =   mayStartDateAndEndDate.get("ENDDATE");
		 
		 Date junStartDate = junStartDateAndEndDate.get("STARTDATE");
		 Date junEndDate =   junStartDateAndEndDate.get("ENDDATE");
		 
		 Date julStartDate = julStartDateAndEndDate.get("STARTDATE");
		 Date julEndDate =   julStartDateAndEndDate.get("ENDDATE");
		 
		 Date augStartDate = augStartDateAndEndDate.get("STARTDATE");
		 Date augEndDate =   augStartDateAndEndDate.get("ENDDATE");
		 
		 Date sepStartDate = sepStartDateAndEndDate.get("STARTDATE");
		 Date sepEndDate =   sepStartDateAndEndDate.get("ENDDATE");
		 
		 Date octStartDate = octStartDateAndEndDate.get("STARTDATE");
		 Date octEndDate =   octStartDateAndEndDate.get("ENDDATE");
		 
		 Date novStartDate = novStartDateAndEndDate.get("STARTDATE");
		 Date novEndDate =   novStartDateAndEndDate.get("ENDDATE");
		 
		 Date decStartDate = decStartDateAndEndDate.get("STARTDATE");
		 Date decEndDate =   decStartDateAndEndDate.get("ENDDATE");
		 
		 int count = 0;
		if(projectMember != null){
				 Float Q1ExpenseTotal = 0f;
				 Float Q2ExpenseTotal = 0f;
				 Float Q3ExpenseTotal = 0f;
				 Float Q4ExpenseTotal = 0f;
				 Float expenseAmount = 0f;
				 Float expenseApprovedAmount = 0f;
				 Float expenseUnApprovedAmount = 0f;
				 Float claimedExpenseApprovedAmount = 0f;
				 Float claimedExpenseUnApprovedAmount = 0f;
				 if(count == 0)
					 projectMember.setMonthsStringFormat(dateList);
				 List<Expenses> expenses  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
				 List<Expenses> expensesClaimendAmount  = getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
				 if(expenses != null && !expenses.isEmpty()){
					 BigDecimal janApprovedTotal = new BigDecimal(0);
					 BigDecimal janUnApprovedTotal = new BigDecimal(0);
					 BigDecimal febApprovedTotal = new BigDecimal(0);
					 BigDecimal febUnApprovedTotal = new BigDecimal(0);
					 BigDecimal marApprovedTotal = new BigDecimal(0);
					 BigDecimal marUnApprovedTotal = new BigDecimal(0);
					 BigDecimal aprApprovedTotal = new BigDecimal(0);
					 BigDecimal aprUnApprovedTotal = new BigDecimal(0);
					 BigDecimal mayApprovedTotal = new BigDecimal(0);
					 BigDecimal mayUnApprovedTotal = new BigDecimal(0);
					 BigDecimal junApprovedTotal = new BigDecimal(0);
					 BigDecimal junUnApprovedTotal = new BigDecimal(0);
					 BigDecimal julApprovedTotal = new BigDecimal(0);
					 BigDecimal julUnApprovedTotal = new BigDecimal(0);
					 BigDecimal augApprovedTotal = new BigDecimal(0);
					 BigDecimal augUnApprovedTotal = new BigDecimal(0);
					 BigDecimal sepApprovedTotal = new BigDecimal(0);
					 BigDecimal sepUnApprovedTotal = new BigDecimal(0);
					 BigDecimal octApprovedTotal = new BigDecimal(0);
					 BigDecimal octUnApprovedTotal = new BigDecimal(0);
					 BigDecimal novApprovedTotal = new BigDecimal(0);
					 BigDecimal novUnApprovedTotal = new BigDecimal(0);
					 BigDecimal decApprovedTotal = new BigDecimal(0);
					 BigDecimal decUnApprovedTotal = new BigDecimal(0);
					
					 
				 for(Expenses exp:expenses){
					 if(null != projectMember.getProjectMemberCandidteId()){
							if(projectMember.getEmployee()!= null && null != projectMember.getEmployee().getEmployeeId()){
								writeRow.createCell(0).setCellValue(exp.getProjectMembers().getEmployee().getEmployeeId());
								 createExcelsheet.autoSizeColumn(0);
							}
							if(null != projectMember.getProjectMemberCandidteId()){
								if(null !=projectMember.getProjectMemberCandidteId().getLastName()){
									StringBuilder nameBuilder = new StringBuilder();
									writeRow.createCell(1).setCellValue(nameBuilder.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
									 createExcelsheet.autoSizeColumn(1);
								}else{
									writeRow.createCell(1).setCellValue(projectMember.getProjectMemberCandidteId().getFirstName());
								}
								
								if(null != projectMember.getProjectMemberCandidteId().getEmail()){
									writeRow.createCell(2).setCellValue( projectMember.getProjectMemberCandidteId().getEmail());
									 createExcelsheet.autoSizeColumn(2);
								}else{
									writeRow.createCell(2).setCellValue("--");
								}
								if(null != projectMember.getProjectMemberCandidteId().getMobilePhone()){
									writeRow.createCell(3).setCellValue(projectMember.getProjectMemberCandidteId().getMobilePhone());
									 createExcelsheet.autoSizeColumn(3);
								}else{
									writeRow.createCell(3).setCellValue("--");
								}
							}
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType()){
								writeRow.createCell(4).setCellValue(projectMember.getProjectMemberCandidteId().getDesignationType());
								 createExcelsheet.autoSizeColumn(4);
							}else{
								writeRow.createCell(4).setCellValue("--");
							}
							
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
								 OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
								writeRow.createCell(5).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName());
								 createExcelsheet.autoSizeColumn(5);
								writeRow.createCell(6).setCellValue(orgRelation.getResponderLocationId().getUser().getFullName());
								 createExcelsheet.autoSizeColumn(6);
							}else{
								 writeRow.createCell(5).setCellValue("--");
								 writeRow.createCell(6).setCellValue("--");
							}
							
							if(null != projectMember.getProjects()){
								writeRow.createCell(7).setCellValue(projectMember.getProjects().getProjectName());
								 createExcelsheet.autoSizeColumn(7);
							}else{
								writeRow.createCell(7).setCellValue("--");
							}
						}
					 
					 	if(projectMember.getProjectClientsId() != null && projectMember.getProjectClientsId().getOrgRelationId() !=null && projectMember.getProjectClientsId().getOrgRelationId().getClientName() != null){
							 writeRow.createCell(8).setCellValue( projectMember.getProjectClientsId().getOrgRelationId().getClientName());
							 createExcelsheet.autoSizeColumn(8);
						}else{
							 writeRow.createCell(8).setCellValue("--"); 
						}
					 	
					 	 
						 Date d = new Date(exp.getEndDate().getTime());
						if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getJanExpenseApprovedTotal() != null){
									 janApprovedTotal = new BigDecimal(projectMember.getJanExpenseApprovedTotal());
								 }
								  janApprovedTotal=janApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setJanExpenseApprovedTotal(janApprovedTotal.floatValue());
								  expenseApprovedAmount += janApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getJanExpenseUnApprovedTotal() != null){
									 janUnApprovedTotal = new BigDecimal(projectMember.getJanExpenseUnApprovedTotal());
								 }
								 janUnApprovedTotal=janUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setJanExpenseUnApprovedTotal(janUnApprovedTotal.floatValue());
								 expenseUnApprovedAmount += janUnApprovedTotal.floatValue();
							 }
							 projectMember.setJanExpenseTotal(janApprovedTotal.floatValue()+janUnApprovedTotal.floatValue());
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getFebExpenseApprovedTotal() != null){
									 febApprovedTotal = new BigDecimal(projectMember.getFebExpenseApprovedTotal());
								 }
								  febApprovedTotal=febApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setFebExpenseApprovedTotal(febApprovedTotal.floatValue());
								  expenseApprovedAmount += febApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getFebExpenseUnApprovedTotal() != null){
									 febUnApprovedTotal = new BigDecimal(projectMember.getFebExpenseUnApprovedTotal());
								 }
								 febUnApprovedTotal=febUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setFebExpenseUnApprovedTotal(febUnApprovedTotal.floatValue());
								  expenseUnApprovedAmount +=febUnApprovedTotal.floatValue();
							 }
							 projectMember.setFebExpenseTotal(febApprovedTotal.floatValue()+febUnApprovedTotal.floatValue());
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getMarExpenseApprovedTotal() != null){
									 marApprovedTotal = new BigDecimal(projectMember.getMarExpenseApprovedTotal());
								 }
								  marApprovedTotal=marApprovedTotal.add(exp.getTotalAmount());
								  projectMember.setMarExpenseApprovedTotal(marApprovedTotal.floatValue());
								  expenseApprovedAmount +=marApprovedTotal.floatValue();
							 }
							 else{
								 if(projectMember.getMarExpenseUnApprovedTotal() != null){
									 marUnApprovedTotal = new BigDecimal(projectMember.getMarExpenseUnApprovedTotal());
								 }
								 marUnApprovedTotal =marUnApprovedTotal.add(exp.getTotalAmount());
								 projectMember.setMarExpenseUnApprovedTotal(marUnApprovedTotal.floatValue());
								  expenseUnApprovedAmount += marUnApprovedTotal.floatValue();
							 }
							 projectMember.setMarExpenseTotal(marApprovedTotal.floatValue()+marUnApprovedTotal.floatValue());
							
						 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAprExpenseApprovedTotal() != null){
								 aprApprovedTotal = new BigDecimal(projectMember.getAprExpenseApprovedTotal());
							 }
							  aprApprovedTotal=aprApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setAprExpenseApprovedTotal(aprApprovedTotal.floatValue());
							  expenseApprovedAmount = expenseApprovedAmount.floatValue()+aprApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getAprExpenseUnApprovedTotal() != null){
								 aprUnApprovedTotal = new BigDecimal(projectMember.getAprExpenseUnApprovedTotal());
							 }
							 aprUnApprovedTotal=aprUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setAprExpenseUnApprovedTotal(aprUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=aprUnApprovedTotal.floatValue();
						 }
						 projectMember.setAprExpenseTotal(aprApprovedTotal.floatValue()+aprUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getMayExpenseApprovedTotal() != null){
								 mayApprovedTotal = new BigDecimal(projectMember.getMayExpenseApprovedTotal());
							 }
							  mayApprovedTotal=mayApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setMayExpenseApprovedTotal(mayApprovedTotal.floatValue());
							  expenseApprovedAmount += mayApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getMayExpenseUnApprovedTotal() != null){
								 mayUnApprovedTotal = new BigDecimal(projectMember.getMayExpenseUnApprovedTotal());
							 }
							 mayUnApprovedTotal =mayUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setMayExpenseUnApprovedTotal(mayUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=mayUnApprovedTotal.floatValue();
						 }
						 projectMember.setMayExpenseTotal(mayApprovedTotal.floatValue()+mayUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJunExpenseApprovedTotal() != null){
								 junApprovedTotal = new BigDecimal(projectMember.getJunExpenseApprovedTotal());
							 }
							  junApprovedTotal=junApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setJunExpenseApprovedTotal(junApprovedTotal.floatValue());
							  expenseApprovedAmount +=junApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getJunExpenseUnApprovedTotal() != null){
								 junUnApprovedTotal = new BigDecimal(projectMember.getJunExpenseUnApprovedTotal());
							 }
							 junUnApprovedTotal=junUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setJunExpenseUnApprovedTotal(junUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += junUnApprovedTotal.floatValue();
						 }
						 projectMember.setJunExpenseTotal(junApprovedTotal.floatValue()+junUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJulExpenseApprovedTotal() != null){
								 julApprovedTotal = new BigDecimal(projectMember.getJulExpenseApprovedTotal());
							 }
							  julApprovedTotal=julApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setJulExpenseApprovedTotal(julApprovedTotal.floatValue());
							  expenseApprovedAmount +=julApprovedTotal.floatValue();
						 }
						 else{
							 
							 if(projectMember.getJulExpenseUnApprovedTotal() != null){
								 julUnApprovedTotal = new BigDecimal(projectMember.getJulExpenseUnApprovedTotal());
							 }
							 julUnApprovedTotal=julUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setJulExpenseUnApprovedTotal(julUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount +=julUnApprovedTotal.floatValue();
						 }
						 projectMember.setJulExpenseTotal(julApprovedTotal.floatValue()+julUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAugExpenseApprovedTotal() != null){
								 augApprovedTotal = new BigDecimal(projectMember.getAugExpenseApprovedTotal());
							 }
							  augApprovedTotal=augApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setAugExpenseApprovedTotal(augApprovedTotal.floatValue());
							  expenseApprovedAmount += augApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getAugExpenseUnApprovedTotal() != null){
								 augUnApprovedTotal = new BigDecimal(projectMember.getAugExpenseUnApprovedTotal());
							 }
							 augUnApprovedTotal=augUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setAugExpenseUnApprovedTotal(augUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += augUnApprovedTotal.floatValue();
						 }
						 projectMember.setAugExpenseTotal(augApprovedTotal.floatValue()+augUnApprovedTotal.floatValue());
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getSepExpenseApprovedTotal() != null){
								 sepApprovedTotal = new BigDecimal(projectMember.getSepExpenseApprovedTotal());
							 }
							  sepApprovedTotal=sepApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setSepExpenseApprovedTotal(sepApprovedTotal.floatValue());
							  expenseApprovedAmount += sepApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getSepExpenseUnApprovedTotal() != null){
								 sepUnApprovedTotal = new BigDecimal(projectMember.getSepExpenseUnApprovedTotal());
							 }
							 sepUnApprovedTotal=sepUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setSepExpenseUnApprovedTotal(sepUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += sepUnApprovedTotal.floatValue();
						 }
						 projectMember.setSepExpenseTotal(sepApprovedTotal.floatValue()+sepUnApprovedTotal.floatValue());
						
					 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getOctExpenseApprovedTotal() != null){
								 octApprovedTotal = new BigDecimal(projectMember.getOctExpenseApprovedTotal());
							 }
							  octApprovedTotal=octApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setOctExpenseApprovedTotal(octApprovedTotal.floatValue());
							  expenseApprovedAmount += octApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getOctExpenseUnApprovedTotal() != null){
								 octUnApprovedTotal = new BigDecimal(projectMember.getOctExpenseUnApprovedTotal());
							 }
							 octUnApprovedTotal=octUnApprovedTotal .add(exp.getTotalAmount());
							 projectMember.setOctExpenseUnApprovedTotal(octUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += octUnApprovedTotal.floatValue();
						 }
						 projectMember.setOctExpenseTotal(octApprovedTotal.floatValue()+octUnApprovedTotal.floatValue());
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getNovExpenseApprovedTotal() != null){
								 novApprovedTotal = new BigDecimal(projectMember.getNovExpenseApprovedTotal());
							 }
							  novApprovedTotal=novApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setNovExpenseApprovedTotal(novApprovedTotal.floatValue());
							  expenseApprovedAmount = expenseApprovedAmount.floatValue()+novApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getNovExpenseUnApprovedTotal() != null){
								 novUnApprovedTotal = new BigDecimal(projectMember.getNovExpenseUnApprovedTotal());
							 }
							 novUnApprovedTotal=novUnApprovedTotal .add(exp.getTotalAmount());
							 projectMember.setNovExpenseUnApprovedTotal(novUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += novUnApprovedTotal.floatValue();
						 }
						 projectMember.setNovExpenseTotal(novApprovedTotal.floatValue()+novUnApprovedTotal.floatValue());
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getDecExpenseApprovedTotal() != null){
								 decApprovedTotal = new BigDecimal(projectMember.getDecExpenseApprovedTotal());
							 }
							  decApprovedTotal=decApprovedTotal.add(exp.getTotalAmount());
							  projectMember.setDecExpenseApprovedTotal(decApprovedTotal.floatValue());
							  expenseApprovedAmount += decApprovedTotal.floatValue();
						 }
						 else{
							 if(projectMember.getDecExpenseUnApprovedTotal() != null){
								 decUnApprovedTotal = new BigDecimal(projectMember.getDecExpenseUnApprovedTotal());
							 }
							 decUnApprovedTotal =decUnApprovedTotal.add(exp.getTotalAmount());
							 projectMember.setDecExpenseUnApprovedTotal(decUnApprovedTotal.floatValue());
							  expenseUnApprovedAmount += decUnApprovedTotal.floatValue();
						 }
						 projectMember.setDecExpenseTotal(decApprovedTotal.floatValue()+decUnApprovedTotal.floatValue());
						 
					  } 
				     }
				 
				      Q1ExpenseTotal = projectMember.getJanExpenseTotal()+projectMember.getFebExpenseTotal()+projectMember.getMarExpenseTotal();
				      Q2ExpenseTotal = projectMember.getAprExpenseTotal()+projectMember.getMayExpenseTotal()+projectMember.getJunExpenseTotal();
				      Q3ExpenseTotal = projectMember.getJulExpenseTotal()+projectMember.getAugExpenseTotal()+projectMember.getSepExpenseTotal();
				      Q4ExpenseTotal = projectMember.getOctExpenseTotal()+projectMember.getNovExpenseTotal()+projectMember.getDecExpenseTotal();
					  expenseAmount = Q1ExpenseTotal+Q2ExpenseTotal+Q3ExpenseTotal+Q4ExpenseTotal;
				      projectMember.setQ1ExpenseTotal(Q1ExpenseTotal);
				      projectMember.setQ2ExpenseTotal(Q2ExpenseTotal);
				      projectMember.setQ3ExpenseTotal(Q3ExpenseTotal);
				      projectMember.setQ4ExpenseTotal(Q4ExpenseTotal);
				      projectMember.setExpenseTotal(expenseAmount);
				      projectMember.setExpenseApprovedTotal(expenseApprovedAmount);
				      projectMember.setExpenseUnApprovedTotal(expenseUnApprovedAmount);
				      projectMember.setProjectStatus("ACTION REQUIRED");
				     
				      
				      for(Expenses exp:expensesClaimendAmount){
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 BigDecimal approvedClaimedTotal = new BigDecimal(0);
								 if(projectMember.getClaimedExpenseApprovedTotal() != null){
									 approvedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseApprovedTotal());
								 }
								 approvedClaimedTotal .add(exp.getTotalAmount());
								  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal.floatValue());
								  claimedExpenseApprovedAmount +=approvedClaimedTotal.floatValue();
							 }
							 else{
								 BigDecimal unApprovedClaimedTotal = new BigDecimal(0);
								 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
									 unApprovedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseUnApprovedTotal());
								 }
								 unApprovedClaimedTotal.add(exp.getTotalAmount());
								 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal.floatValue());
								 claimedExpenseUnApprovedAmount += unApprovedClaimedTotal.floatValue();
							 }
							 
							 Float expenseClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseTotal() != null){
								 expenseClaimedTotal = projectMember.getClaimedExpenseTotal();
							 }
							 if(exp.getExpenseStatus().equals(Submission_Status.APPROVED)) {
								 expenseClaimedTotal += exp.getTotalAmount().floatValue();
								 projectMember.setClaimedExpenseTotal(expenseClaimedTotal);
							 }
					   }
				      
				      int countArray = 8;
				      for(String monthHeader :headers){
				    	  if(monthHeader.equalsIgnoreCase("January")){
				    		  writeRow.createCell(countArray+1).setCellValue(projectMember.getJanExpenseTotal().toString());
				    		  createExcelsheet.autoSizeColumn(countArray+1);
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("February")){
				    		  writeRow.createCell(countArray+2).setCellValue(projectMember.getFebExpenseTotal().toString());
				    		  createExcelsheet.autoSizeColumn(countArray+2);
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("March")){
				    		  writeRow.createCell(countArray+3).setCellValue(projectMember.getMarExpenseTotal().toString());
				    		  createExcelsheet.autoSizeColumn(countArray+3);
				    	  }
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("April")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+4).setCellValue( projectMember.getAprExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+4);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+1).setCellValue( projectMember.getAprExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+1);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("May")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+5).setCellValue(projectMember.getMayExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+5);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+2).setCellValue( projectMember.getMayExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+2);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("June")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+6).setCellValue(projectMember.getJunExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+6);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+3).setCellValue(projectMember.getJunExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+3);
				    		  }
				    		 
				    	  }
				    	  
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("July")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+7).setCellValue(projectMember.getJulExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+7);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+1).setCellValue(projectMember.getJulExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+1);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("August")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+8).setCellValue(projectMember.getAugExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+8);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+2).setCellValue( projectMember.getAugExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+2);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("September")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+9).setCellValue(projectMember.getSepExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+9);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+3).setCellValue(projectMember.getSepExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+3);
				    		  }
				    		 
				    	  }
				    	  
				    	  else if(monthHeader.equalsIgnoreCase("October")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+10).setCellValue(projectMember.getOctExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+10);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+1).setCellValue(projectMember.getOctExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+1);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("November")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+11).setCellValue(projectMember.getNovExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+11);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+2).setCellValue( projectMember.getNovExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+2);
				    		  }
				    		 
				    	  }
				    	  else if(monthHeader.equalsIgnoreCase("December")){
				    		  if(headers.length >14){
				    			  writeRow.createCell(countArray+12).setCellValue(projectMember.getDecExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+12);
				    		  }
				    		  else{
				    			  writeRow.createCell(countArray+3).setCellValue(projectMember.getDecExpenseTotal().toString());
				    			  createExcelsheet.autoSizeColumn(countArray+3);
				    		  }
				    		 
				    	  }
				    	  
				      }
				      
				      if(headers.length >14){
				    	  writeRow.createCell(headers.length-2).setCellValue(projectMember.getExpenseTotal().toString());
				    	  createExcelsheet.autoSizeColumn(headers.length-2);
				    	  writeRow.createCell(headers.length-1).setCellValue(projectMember.getClaimedExpenseTotal().toString());
				    	  createExcelsheet.autoSizeColumn(headers.length-1);
				      }else{
				    	  writeRow.createCell(headers.length-2).setCellValue(projectMember.getExpenseTotal().toString());
				    	  createExcelsheet.autoSizeColumn(headers.length-2);
				    	  writeRow.createCell(headers.length-1).setCellValue(projectMember.getClaimedExpenseTotal().toString());
				    	  createExcelsheet.autoSizeColumn(headers.length-1);
				      }
				   }
			    
		 
		}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, Date convertedStartDate, Date convertedEndDate) {
		LinkedHashSet<String> dateformat = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate,convertedEndDate);
	 	 String[] headersDetails = Constants.EXPENSES_REPORTS_HEADERS;
	     String[] monthHedders = dateformat.toArray(new String[dateformat.size()]);
	     String[] headersAmont = Constants.EXPENSES_REPORTS_HEADERS_1;
	     String[] headerOne = (String[])ArrayUtils.addAll(headersDetails, monthHedders);
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers = (String[])ArrayUtils.addAll(headerOne, headersAmont);
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
		return createExcelsheet;
	}

	

	


	
	
}
