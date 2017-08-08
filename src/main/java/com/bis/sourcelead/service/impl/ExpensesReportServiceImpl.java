package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;
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
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesReportServiceImpl.class);
	
	@Override
	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId,Long loginOrgLocationId) throws Exception {
		return expensesReportDao.getAllExpensesReportListByLoginUserIdAndOrgLocationId(loginUserId, loginOrgLocationId);
	}

	@Override
	public Map<String, Map<String,String>> getYearsAndQuartersComboList() throws JSONException, ParseException {
		 LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(1);
		 Map<String, Map<String,String>> yearsAndQuartersComboList = new LinkedHashMap<String, Map<String,String>>();
		 if(tsrYearsList != null && !tsrYearsList.isEmpty()){
			 for(String year : tsrYearsList){
				 Map<String, String> quarterlyDate = getStartAndEndDate(Integer.parseInt(year));
				 yearsAndQuartersComboList.put(year, quarterlyDate);
			 }
		 }
		 return yearsAndQuartersComboList;
	}
	
	
	private Map<String, String> getStartAndEndDate(int years) {
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
			    
			    String StartDate = LoginSessionController.DATEFORMAT.format(Q1StartDate.getTime());
			    String endDate = LoginSessionController.DATEFORMAT.format(Q1EndDate.getTime());
			    startEndDate.put("Q"+count,StartDate+" TO "+endDate);
			    count++;
		   }
		    startEndDate.put("All",LoginSessionController.DATEFORMAT.format(YearStartDate.getTime())+" TO "+LoginSessionController.DATEFORMAT.format(YearEndDate.getTime()));
			return startEndDate;
		
	}
	
	@Override
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date startDate, Date endtDate) throws ParseException {
		  return expensesReportDao.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(proMembersId,startDate,endtDate);
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
			 System.out.println(startCal.getTime());
			 System.out.println(endCal.getTime());
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
 		     exprortReports.append("EXPRORT_REPORTS_").append(dateFormat.format(new Date()));
			 StringBuilder fileSB = new StringBuilder();
			 File file = File.createTempFile(fileSB.append("EXPRORT_REPORTS_").append(dateFormat.format(new Date())).toString(),".pdf");
			 file.setReadable(true);
			 
			 try {
			        // Get an instance of PdfWriter and create a Table.pdf file as an output.
			        PdfWriter.getInstance(document, new FileOutputStream(file));
			        document.open();

			        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			        BaseColor myColor = WebColors.getRGBColor("#000000");
			        boldFont.setColor(myColor);
			        StringBuilder userStyle = new StringBuilder();
			        userStyle.append(orgLoc.getOrg().getOrgName()).append(" ").append("_EXPRORT_REPORTS");
		          

			        StringBuilder orgNameSB = new StringBuilder();
		            Paragraph para = new Paragraph(orgNameSB.append("EXPRORT_REPORTS : ").append(orgLoc.getOrg().getOrgName()).toString());
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
				 Float claimedExpenseAmount = 0f;
				 if(count == 0)
					 projectMember.setMonthsStringFormat(dateList);
				 List<Expenses> expenses  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
				 List<Expenses> expensesClaimendAmount  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
				 if(expenses != null && !expenses.isEmpty()){
				 for(Expenses exp:expenses){
					 if(null != projectMember.getEmployee()){
							if(null != projectMember.getEmployee().getEmployeeId()){
								expensesArray[0] = (exp.getProjectMembers().getEmployee().getEmployeeId());
							}
							if(null != projectMember.getEmployee().getUser()){
								if(null != projectMember.getEmployee().getUser().getLastName()){
									StringBuilder nameBuilder = new StringBuilder();
									expensesArray[1] = nameBuilder.append(projectMember.getEmployee().getUser().getFirstName()).append(projectMember.getEmployee().getUser().getLastName()).toString();
								}else{
									expensesArray[1] = projectMember.getEmployee().getUser().getFirstName();
								}
								
								if(null != projectMember.getEmployee().getUser().getEmail()){
									expensesArray[2] = projectMember.getEmployee().getUser().getEmail();
								}else{
									expensesArray[2] = "--";
								}
								if(null != projectMember.getEmployee().getUser().getMobilePhone()){
									expensesArray[3] = projectMember.getEmployee().getUser().getMobilePhone();
								}else{
									expensesArray[3] = "--";
								}
							}
							if(null != projectMember.getEmployee().getUserEmploymentType()){
								expensesArray[4] = exp.getProjectMembers().getEmployee().getUserEmploymentType();
							}else{
								expensesArray[4] = "--";
							}
							
							 if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
									expensesArray[5] = orgRelation.getResponderLocationId().getOrg().getOrgName();
									expensesArray[6] = orgRelation.getResponderLocationId().getOrg().getUser().getFirstName();
							 }else{
								 expensesArray[5] = "--";
								 expensesArray[6] = "--";
							 }
							
							if(null != projectMember.getProjects()){
								expensesArray[7] = projectMember.getProjects().getProjectName();
							}else{
								expensesArray[7] = "--";
							}
						}
					 
						 if(exp.getClientName() != null){
							 expensesArray[8] = exp.getClientName();
						 }else{
							 expensesArray[8] ="--"; 
						 }
						 Date d = new Date(exp.getEndDate().getTime());
						if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getJanExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getJanExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setJanExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getJanExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getJanExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setJanExpenseUnApprovedTotal(unApprovedTotal);
								 expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setJanExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getFebExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getFebExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setFebExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getFebExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getFebExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setFebExpenseUnApprovedTotal(unApprovedTotal);
								  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setFebExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getMarExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getMarExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setMarExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getMarExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getMarExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setMarExpenseUnApprovedTotal(unApprovedTotal);
								  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setMarExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAprExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getAprExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setAprExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getAprExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getAprExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setAprExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setAprExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getMayExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getMayExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setMayExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getMayExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getMayExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setMayExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setMayExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJunExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getJunExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setJunExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getJunExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getJunExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal + exp.getTotalAmount();
							 projectMember.setJunExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setJunExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJulExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getJulExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setJulExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 
							 if(projectMember.getJulExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getJulExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setJulExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setJulExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAugExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getAugExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setAugExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getAugExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getAugExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setAugExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setAugExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getSepExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getSepExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setSepExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getSepExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getSepExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setSepExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setSepExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getOctExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getOctExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setOctExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getOctExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getOctExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setOctExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setOctExpenseTotal(approvedTotal+unApprovedTotal);
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getNovExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getNovExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setNovExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getNovExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getNovExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setNovExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setNovExpenseTotal(approvedTotal+unApprovedTotal);
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getDecExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getDecExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setDecExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getDecExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getDecExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setDecExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setDecExpenseTotal(approvedTotal+unApprovedTotal);
						 
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
								 Float approvedClaimedTotal = 0f;
								 if(projectMember.getClaimedExpenseApprovedTotal() != null){
									 approvedClaimedTotal = projectMember.getClaimedExpenseApprovedTotal();
								 }
								 approvedClaimedTotal = approvedClaimedTotal+exp.getTotalAmount();
								  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal);
								  claimedExpenseApprovedAmount = claimedExpenseApprovedAmount+approvedClaimedTotal;
							 }
							 else{
								 Float unApprovedClaimedTotal = 0f;
								 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
									 unApprovedClaimedTotal = projectMember.getClaimedExpenseUnApprovedTotal();
								 }
								 unApprovedClaimedTotal = unApprovedClaimedTotal+exp.getTotalAmount();
								 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal);
								 claimedExpenseUnApprovedAmount = claimedExpenseUnApprovedAmount+unApprovedClaimedTotal;
							 }
							 
							 Float expenseClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseTotal() != null){
								 expenseClaimedTotal = projectMember.getClaimedExpenseTotal();
							 }
							 expenseClaimedTotal = expenseClaimedTotal+exp.getTotalAmount();
							 projectMember.setClaimedExpenseTotal(expenseClaimedTotal);
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
					 projectMember.setMonthsStringFormat(dateList);
				 List<Expenses> expenses  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
				 List<Expenses> expensesClaimendAmount  = getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
				 if(expenses != null && !expenses.isEmpty()){
				 for(Expenses exp:expenses){
					 if(null != projectMember.getEmployee()){
							if(null != projectMember.getEmployee().getEmployeeId()){
								writeRow.createCell(0).setCellValue(exp.getProjectMembers().getEmployee().getEmployeeId());
								 createExcelsheet.autoSizeColumn(0);
							}
							if(null != projectMember.getEmployee().getUser()){
								if(null != projectMember.getEmployee().getUser().getLastName()){
									StringBuilder nameBuilder = new StringBuilder();
									writeRow.createCell(1).setCellValue(nameBuilder.append(projectMember.getEmployee().getUser().getFirstName()).append(projectMember.getEmployee().getUser().getLastName()).toString());
									 createExcelsheet.autoSizeColumn(1);
								}else{
									writeRow.createCell(1).setCellValue(projectMember.getEmployee().getUser().getFirstName());
								}
								
								if(null != projectMember.getEmployee().getUser().getEmail()){
									writeRow.createCell(2).setCellValue( projectMember.getEmployee().getUser().getEmail());
									 createExcelsheet.autoSizeColumn(2);
								}else{
									writeRow.createCell(2).setCellValue("--");
								}
								if(null != projectMember.getEmployee().getUser().getMobilePhone()){
									writeRow.createCell(3).setCellValue(projectMember.getEmployee().getUser().getMobilePhone());
									 createExcelsheet.autoSizeColumn(3);
								}else{
									writeRow.createCell(3).setCellValue("--");
								}
							}
							if(null != projectMember.getEmployee().getUserEmploymentType()){
								writeRow.createCell(4).setCellValue(exp.getProjectMembers().getEmployee().getUserEmploymentType());
								 createExcelsheet.autoSizeColumn(4);
							}else{
								writeRow.createCell(4).setCellValue("--");
							}
							
							 if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
									writeRow.createCell(5).setCellValue( orgRelation.getResponderLocationId().getOrg().getOrgName());
									 createExcelsheet.autoSizeColumn(5);
									writeRow.createCell(6).setCellValue(orgRelation.getResponderLocationId().getOrg().getUser().getFirstName());
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
					 
						 if(exp.getClientName() != null){
							 writeRow.createCell(8).setCellValue(exp.getClientName());
							 createExcelsheet.autoSizeColumn(8);
						 }else{
							 writeRow.createCell(8).setCellValue("--"); 
						 }
						 Date d = new Date(exp.getEndDate().getTime());
						if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getJanExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getJanExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setJanExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getJanExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getJanExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setJanExpenseUnApprovedTotal(unApprovedTotal);
								 expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setJanExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getFebExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getFebExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setFebExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getFebExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getFebExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setFebExpenseUnApprovedTotal(unApprovedTotal);
								  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setFebExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
						 
						 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
							 Float approvedTotal = 0f;
							 Float unApprovedTotal = 0f;
							 
							 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
								 if(projectMember.getMarExpenseApprovedTotal() != null){
									 approvedTotal = projectMember.getMarExpenseApprovedTotal();
								 }
								  approvedTotal = approvedTotal+exp.getTotalAmount();
								  projectMember.setMarExpenseApprovedTotal(approvedTotal);
								  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
							 }
							 else{
								 if(projectMember.getMarExpenseUnApprovedTotal() != null){
									 unApprovedTotal = projectMember.getMarExpenseUnApprovedTotal();
								 }
								 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
								 projectMember.setMarExpenseUnApprovedTotal(unApprovedTotal);
								  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
							 }
							 projectMember.setMarExpenseTotal(approvedTotal+unApprovedTotal);
							
						 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAprExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getAprExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setAprExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getAprExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getAprExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setAprExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setAprExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getMayExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getMayExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setMayExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getMayExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getMayExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setMayExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setMayExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJunExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getJunExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setJunExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getJunExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getJunExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal + exp.getTotalAmount();
							 projectMember.setJunExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setJunExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getJulExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getJulExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setJulExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 
							 if(projectMember.getJulExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getJulExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setJulExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setJulExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getAugExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getAugExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setAugExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getAugExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getAugExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setAugExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setAugExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getSepExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getSepExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setSepExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getSepExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getSepExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setSepExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setSepExpenseTotal(approvedTotal+unApprovedTotal);
						
					 }
					 
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getOctExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getOctExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setOctExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getOctExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getOctExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setOctExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setOctExpenseTotal(approvedTotal+unApprovedTotal);
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getNovExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getNovExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setNovExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getNovExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getNovExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setNovExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setNovExpenseTotal(approvedTotal+unApprovedTotal);
						 
					 }
					 
					 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
						 Float approvedTotal = 0f;
						 Float unApprovedTotal = 0f;
						 
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 if(projectMember.getDecExpenseApprovedTotal() != null){
								 approvedTotal = projectMember.getDecExpenseApprovedTotal();
							 }
							  approvedTotal = approvedTotal+exp.getTotalAmount();
							  projectMember.setDecExpenseApprovedTotal(approvedTotal);
							  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
						 }
						 else{
							 if(projectMember.getDecExpenseUnApprovedTotal() != null){
								 unApprovedTotal = projectMember.getDecExpenseUnApprovedTotal();
							 }
							 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
							 projectMember.setDecExpenseUnApprovedTotal(unApprovedTotal);
							  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
						 }
						 projectMember.setDecExpenseTotal(approvedTotal+unApprovedTotal);
						 
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
								 Float approvedClaimedTotal = 0f;
								 if(projectMember.getClaimedExpenseApprovedTotal() != null){
									 approvedClaimedTotal = projectMember.getClaimedExpenseApprovedTotal();
								 }
								 approvedClaimedTotal = approvedClaimedTotal+exp.getTotalAmount();
								  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal);
								  claimedExpenseApprovedAmount = claimedExpenseApprovedAmount+approvedClaimedTotal;
							 }
							 else{
								 Float unApprovedClaimedTotal = 0f;
								 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
									 unApprovedClaimedTotal = projectMember.getClaimedExpenseUnApprovedTotal();
								 }
								 unApprovedClaimedTotal = unApprovedClaimedTotal+exp.getTotalAmount();
								 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal);
								 claimedExpenseUnApprovedAmount = claimedExpenseUnApprovedAmount+unApprovedClaimedTotal;
							 }
							 
							 Float expenseClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseTotal() != null){
								 expenseClaimedTotal = projectMember.getClaimedExpenseTotal();
							 }
							 expenseClaimedTotal = expenseClaimedTotal+exp.getTotalAmount();
							 projectMember.setClaimedExpenseTotal(expenseClaimedTotal);
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
