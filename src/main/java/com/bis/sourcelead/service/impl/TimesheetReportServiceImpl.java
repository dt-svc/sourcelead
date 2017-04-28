package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class TimesheetReportServiceImpl implements TimesheetReportService {


	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
    String sourceleadUrl;
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetReportServiceImpl.class);
	
	@Override
	public String prepareTimeSheetDiv(String frequencyYear, String frequencyMonth,User loginUser,OrgLocations orgLocations) throws Exception {
		StringBuilder prepareTimeSheetDiv = new StringBuilder("");
		StringBuilder selectedYear = new StringBuilder("");
		StringBuilder selectedMonth = new StringBuilder("");
		
		prepareTimeSheetDiv.append("<div class='content-area content-new clearfix'>");
		prepareTimeSheetDiv.append("<div class='timesheet_head timesheetRep'>");
		          prepareTimeSheetDiv.append("<div class='timeReFi'>");
			  	          LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(2, 2);
				          prepareTimeSheetDiv.append("		                <select id='frequencyYear' name='frequencyYear' class='form-control chosen-select'   >");
							for (String yrStr : tsrYearsList) {
								    StringBuilder isSelected = new StringBuilder("");
								    String currentYear = CalendarUtil.getCurrentYear();
								    if(StringUtils.isEmpty(frequencyYear) && currentYear.equalsIgnoreCase(yrStr)){
								    	isSelected.append("selected='selected'");
								    	selectedYear.append(yrStr);
								    }
								    else if(StringUtils.isNotEmpty(frequencyYear) && frequencyYear.equalsIgnoreCase(yrStr)){
								    	isSelected.append("selected='selected'");
								    	selectedYear.append(yrStr);
								    }  
								    prepareTimeSheetDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+yrStr+"'>");
								    prepareTimeSheetDiv.append(yrStr);
								    prepareTimeSheetDiv.append("		                  </option>");
							}
							prepareTimeSheetDiv.append("		                </select>");
					 prepareTimeSheetDiv.append("	            </div>");
					 prepareTimeSheetDiv.append("<div class='timeReFi'>");
					 LinkedList<String>  tsrMonthsList = CalendarUtil.getAllMonths();
			          prepareTimeSheetDiv.append("		                <select id='frequencyMonth' name='frequencyMonth' class='form-control chosen-select'  >");
						for (String monthStr : tsrMonthsList) {
							    StringBuilder isSelected = new StringBuilder("");
							    String currentMonth = CalendarUtil.getCurrentMonth();
							    if(StringUtils.isEmpty(frequencyMonth) && currentMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    }
							    else if(StringUtils.isNotEmpty(frequencyMonth) && frequencyMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    }  
							    prepareTimeSheetDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+monthStr+"'>");
							    prepareTimeSheetDiv.append(monthStr);
							    prepareTimeSheetDiv.append("		                  </option>");
						}
						prepareTimeSheetDiv.append("		                </select>");
				prepareTimeSheetDiv.append("	            </div>");
				 prepareTimeSheetDiv.append("<div class='timeReFi'>");
					prepareTimeSheetDiv.append("	            <input type='button' onclick='timesheetReportsDiv();' class='btn btn-success' value='View'>	");
				 prepareTimeSheetDiv.append("	            </div>");
				 
				 String pdf = "pdf";
				 String excel = "excel";
				 prepareTimeSheetDiv.append("<ul>");
				 prepareTimeSheetDiv.append("<li title='Export' class='mrPOP' id='mypop' onclick='exportToggle();'><i class='glyphicon glyphicon-export'></i>");
				 prepareTimeSheetDiv.append("<div id='exportPop'>");
				 prepareTimeSheetDiv.append(" <ul>");
				 prepareTimeSheetDiv.append("<li onclick='exportTimeSheetReportsToPdf(\""+pdf+"\");'><i class='fa fa-file-pdf-o'></i>&nbsp;&nbsp;PDF</li>");
				 prepareTimeSheetDiv.append("<li onclick='exportTimeSheetReportsToPdf(\""+excel+"\");'><i class='fa fa-file-excel-o text-primary'></i>&nbsp;&nbsp;Excel</li>");
				 prepareTimeSheetDiv.append("</ul>");
				 prepareTimeSheetDiv.append("</div>");
				 prepareTimeSheetDiv.append("</li>");
		         prepareTimeSheetDiv.append("</ul>");
				 
				 prepareTimeSheetDiv.append("<div class='clearfix mb10'></div>");
				 StringBuilder statusDiv = timesheetReportsTableDiv(selectedYear.toString(),selectedMonth.toString(),orgLocations,loginUser);
				 prepareTimeSheetDiv.append(statusDiv);		
		prepareTimeSheetDiv.append("</div>");
		prepareTimeSheetDiv.append("</div>");
		return prepareTimeSheetDiv.toString(); 
		
	}
	
	
	 public StringBuilder timesheetReportsTableDiv(String year,String month,OrgLocations orgLocations,User loginUser) throws Exception{
		 DateFormat format = Constants.DF_DMY;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(month,year);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();

        StringBuilder timesheetApprovalDiv = new StringBuilder();
        List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startMondayDate, endSundayDate, orgLocations.getId(), loginUser.getId());
        if(projectMembers != null && !projectMembers.isEmpty()){

        	int tdSpanLength = 6;
        	timesheetApprovalDiv.append("			<div class='timesheetRepTab overflow_inherint'>");
			timesheetApprovalDiv.append("	    	<table id='timesheetReportsPageNation' style='width:100%'>");
			timesheetApprovalDiv.append("				<thead>");   
			timesheetApprovalDiv.append("	            	<tr>");
			timesheetApprovalDiv.append("	                	<th>Employee</th>           ");
			timesheetApprovalDiv.append("	                    <th>Project</th>            ");
			timesheetApprovalDiv.append("	                    <th>Frequency</th>             ");
			
			LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListForTsReports(startMondayDate, endSundayDate);
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
					tdSpanLength =tdSpanLength+1;
				        Date weekStartDate;
				        Date weekEndDate;
					try {
						 weekStartDate = format.parse(k.replaceAll("/", "-"));
						 weekEndDate = format.parse(v.replaceAll("/", "-"));
						 displayWeeksList.put(weekStartDate, weekEndDate);
						 if(weekStartDate.equals(weekEndDate)){
							 timesheetApprovalDiv.append("	                    <th>").append(shortormat.format(weekStartDate)).append("</th>  ");
						 }else{
							 timesheetApprovalDiv.append("	                    <th>").append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate)).append("</th>  ");
						 }
	   					
					} catch (Exception e) {}
				}
			}
			timesheetApprovalDiv.append("	                    <th>Total</th>             ");
			timesheetApprovalDiv.append("	                    <th>Project to Date</th>   ");
			timesheetApprovalDiv.append("	                    <th>Last month</th>        ");
			timesheetApprovalDiv.append("	                    <th>Stage</th>             ");
			
			 	 	 	
			timesheetApprovalDiv.append("	                </tr>");
			timesheetApprovalDiv.append("				</thead>");
			timesheetApprovalDiv.append("	        	<tbody>");
			for (ProjectMembers projectMember : projectMembers)
			{
				 timesheetApprovalDiv.append("<tr id='row").append(projectMember.getId()).append("'>");
					 timesheetApprovalDiv.append("  <td>")
					                       .append("<div class='lg clearfix'>")
					                                 .append("   <div class='uimg'><img  id='userImage' onerror='predefineProfileImage(this);' src='").append(sourceleadUrl).append("/displayUserImage/").append(projectMember.getEmployee().getUser().getId()).append("' alt='Profile Image' class='img-circle img-responsive'/>")
					                                 .append("</div>")
					                                 .append("    <div class='Utext'>")
				                                             .append("<i title='").append(projectMember.getEmployee().getUser().getFullName()).append("'>").append(projectMember.getEmployee().getUser().getFullName()).append("</i>")
				                                             .append("<i title='").append(projectMember.getEmployee().getUser().getEmail()).append("'>").append(projectMember.getEmployee().getUser().getEmail()).append("</i>")
		                                             .append("</div>")
				                               .append("   <div>")
					                        .append(" </td>");
				     timesheetApprovalDiv.append("<td><div class='md'><i title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</i></div></td>");
			    	 timesheetApprovalDiv.append("<td><div class='sm'>").append(projectMember.getProjects().getInvoiceFrequency()).append("</div></td>");
		        
				     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
				    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
				    	    	 SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),entry.getKey(), entry.getValue());
				    	    	
				    	    	 String approvalClass = "class='draft'";
				    	    	 Float weekTotalHours = 0f;
				    	    	 Float weekBillableHours = 0f;
				    	    	 Float weekNonBillableHours = 0f;
				    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
				    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
				    	    	 }
				    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
				    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
				    	    	 }
				    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
				    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
				    	    	 }
				    	         if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
				    	        	  approvalClass = "class='subm'";
				    	    	 }
				    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.APPROVED)){
				    	    		 approvalClass = "class='appr'";
				    	    	 }
				    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.REJECTED)){
				    	    		 approvalClass = "class='reje'";
				    	    	 }
				    	    	  
				    	    	 timesheetApprovalDiv.append("<td>")
				    	    	                        .append(" <div class='week'>")
				    	    	                          .append(" <span "+approvalClass+">").append(weekTotalHours).append("</span>")
				    	    	                             .append(" <div class='progress'>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
				    	    	                                        .append(weekBillableHours)
				    	    	                                     .append("</div>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
				    	    	                                        .append(weekNonBillableHours)
				    	    	                                     .append("</div>")
				    	    	                             .append(" </div>")
				    	    	                           
				    	    	                        .append(" </div>")
				    	    	                     .append("</td>");
				    	    	 
				    	    	 
				    	    	
				    	     }
				     }
			    	 
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(0.0).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(0.0)
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(0.0)
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(0.0).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(0.0)
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(0.0)
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(0.0).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(0.0)
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(0.0)
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				    timesheetApprovalDiv.append("<td><div class='sm'><b class='action'>Action Required</b></div></td>");
				     
			    	 
			        timesheetApprovalDiv.append("</tr>");
		  }
		
		  timesheetApprovalDiv.append("	        </tbody>");
		  timesheetApprovalDiv.append("	     </table> ");
	      timesheetApprovalDiv.append("	</div>");
		
        	
        	
        }
        else{
        	timesheetApprovalDiv.append("   <div class='dataTables_empty'>"); 
			timesheetApprovalDiv.append("  </div> "); 
        }

     return timesheetApprovalDiv;
  }


	@SuppressWarnings("deprecation")
	@Override
	public File prepareTimeSheetExport(String frequencyYear, String frequencyMonth, User loginUser,OrgLocations orgLocations) throws Exception {
		File file = null;
		StringBuilder weekFrequencyDates = new StringBuilder(); 
		DateFormat format = Constants.DF_DMY;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(frequencyMonth,frequencyYear);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
		 int tdSpanLength = 6;
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListForTsReports(startMondayDate, endSundayDate);
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
				  tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
				try {
					 weekStartDate = format.parse(k.replaceAll("/", "-"));
					 weekEndDate = format.parse(v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
					 if(weekStartDate.equals(weekEndDate)){
						weekFrequencyDates.append(weekStartDate);
					 }else{
						 weekFrequencyDates.append((shortormat.format(weekStartDate))).append("-").append(shortormat.format(weekEndDate)).append(",");
					 }
				} catch (Exception e) {
					logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
				}
			}
		}
		
		String hed = weekFrequencyDates.toString();
		String[] dateHeaders =hed.split(",");
		LinkedList<String> headersLinkedList = new LinkedList<>();
		String[] firstheaders = {"Employee","Project","Frequency"};
		String[] secondHeader ={"Total","ProjectToDate","LastMonth","Stage"};
				headersLinkedList.addAll(Arrays.asList(firstheaders));
				headersLinkedList.addAll(Arrays.asList(dateHeaders));
				headersLinkedList.addAll(Arrays.asList(secondHeader));
		List<String> headderList = new ArrayList<String>();
		for(String headerArray :headersLinkedList){
			headderList.add(headerArray);
		}
		String [] head = new String[headderList.size()];
		String [] header = headderList.toArray(head);
		
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    
	    StringBuilder sbTsReports =  new StringBuilder();
	    sbTsReports.append("timesheetSheet_").append(dateFormat.format(new Date()));
	    StringBuilder fileSB = new StringBuilder();
	    file = File.createTempFile(fileSB.append(orgLocations.getOrg().getOrgName()).append("_TimeSheetReports_").append(dateFormat.format(new Date())).toString(),".pdf");
	    file.setReadable(true);

	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        StringBuilder userStyle = new StringBuilder();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        PdfPTable table = new PdfPTable(header.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(14);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{5,5,3,3,3,3,3,3,3,3,3,5});
            table.addCell(cell1);
            for (int i = 0; i < header.length; i++) {
	            String headerString = header[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(1f);
	            cell.setPhrase(new Phrase(headerString.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
            table.completeRow();
            List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startMondayDate, endSundayDate, orgLocations.getId(), loginUser.getId());
            java.util.List<String[]> reportList = new ArrayList<>();
            for(ProjectMembers promembers:projectMembers){
            	reportList.add(prepareTimesheetReportData(promembers,header,displayWeeksList,loginUser));
            }
            for (String[] tsreportList: reportList) {
                for (int i = 0; i < tsreportList.length; i++) {
                    String datum = tsreportList[i];
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
                    table.addCell(cell);
                }
                table.completeRow();
            }
            document.addTitle("TimeSheet Reports Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	    }catch (DocumentException e){
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    }finally {
	        document.close();
	    }
		return file;
	}
	
	private String[] prepareTimesheetReportData(ProjectMembers projectMember, String[] header, LinkedHashMap<Date, Date> displayWeeksList, User loginUser) throws Exception {
		String timeSheetReportsArray[] = new String[header.length];
			if(projectMember.getEmployee().getUser().getFullName() !=null){
				StringBuilder sb = new StringBuilder();
				timeSheetReportsArray[0] = sb.append(projectMember.getEmployee().getUser().getFullName()).append(projectMember.getEmployee().getUser().getEmail()).toString();
			 }else{
				 timeSheetReportsArray[0] = "--";
			 }
			if(projectMember.getProjects().getProjectName() !=null){
				timeSheetReportsArray[1] = projectMember.getProjects().getProjectName();
			 }else{
				 timeSheetReportsArray[1] = "--";
			 }
			if(projectMember.getProjects().getInvoiceFrequency() !=null){
				timeSheetReportsArray[2] = projectMember.getProjects().getInvoiceFrequency();
			 }else{
				 timeSheetReportsArray[2] = "--";
			 }
			int count = 3;
			if(displayWeeksList != null && !displayWeeksList.isEmpty()){
	    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
	    	    	 SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),entry.getKey(), entry.getValue());
	    	    	
	    	    	 //String approvalClass = "class='draft'";
	    	    	 Float weekTotalHours = 0f;
	    	    	 Float weekBillableHours = 0f;
	    	    	 Float weekNonBillableHours = 0f;
	    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
	    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
	    	    	 }
	    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
	    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
	    	    	 }
	    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
	    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
	    	    	 }
	    	         /*if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
	    	        	  approvalClass = "class='subm'";
	    	    	 }
	    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.APPROVED)){
	    	    		 approvalClass = "class='appr'";
	    	    	 }
	    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.REJECTED)){
	    	    		 approvalClass = "class='reje'";
	    	    	 }*/
	    	         
	    	    	 StringBuilder listSb = new StringBuilder();
	    	         timeSheetReportsArray[count] = listSb.append(weekTotalHours).append("Billable :"+weekBillableHours).append("NonBillabe :"+weekNonBillableHours).toString();
	    	         
	    	         count++;
	    	     }
	     }
				timeSheetReportsArray[count] ="0.0";
				timeSheetReportsArray[count+1] ="0.0";
				timeSheetReportsArray[count+2] ="0.0";
				timeSheetReportsArray[count+3] ="ACTION REQUIRED";
			
		return timeSheetReportsArray;
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
	public File prepareTimeSheetExportExcel(String frequencyYear, String frequencyMonth, User loginUser,OrgLocations orgLocations) throws IOException, Exception {
		File file = null;
		
		StringBuilder weekFrequencyDates = new StringBuilder(); 
		DateFormat format = Constants.DF_DMY;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(frequencyMonth,frequencyYear);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
		 int tdSpanLength = 6;
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListForTsReports(startMondayDate, endSundayDate);
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
				  tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
				try {
					 weekStartDate = format.parse(k.replaceAll("/", "-"));
					 weekEndDate = format.parse(v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
					 if(weekStartDate.equals(weekEndDate)){
						weekFrequencyDates.append(weekStartDate);
					 }else{
						 weekFrequencyDates.append((shortormat.format(weekStartDate))).append("-").append(shortormat.format(weekEndDate)).append(",");
					 }
				} catch (Exception e) {
					logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
				}
			}
		}
		
		
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		StringBuilder sheetName = new StringBuilder();
		Sheet createExcelsheet = createExcelworkbook.createSheet(sheetName.append("timesheetSheet_").append(dateFormat.format(new Date())).toString());
		Row writeRow = null;
		createExcelsheet = gettimesheetReportsExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,weekFrequencyDates);
		
		
		int column = 0;
		 List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startMondayDate, endSundayDate, orgLocations.getId(), loginUser.getId());
		 for(ProjectMembers promembers:projectMembers){
			 writeRow = createExcelsheet.createRow(column + 2);
			 createExcelsheet = prepareTimesheetReportDataExcel(promembers,displayWeeksList,loginUser,createExcelsheet,writeRow);
			 column++;
         }
		 StringBuilder fileSB = new StringBuilder();
		 file = File.createTempFile(fileSB.append(orgLocations.getOrg().getOrgName()).append("_TimeSheetReports_").append(dateFormat.format(new Date())).toString(),".xls");
		 file.setReadable(true);
		 FileOutputStream fos = new FileOutputStream(file);
		 try {
			createExcelworkbook.write(fos);
			fos.close();
		 }catch (IOException e) {
			e.printStackTrace();
		 }
		return file;
	}

	private Sheet gettimesheetReportsExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow,Workbook createExcelworkbook, StringBuilder weekFrequencyDates) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String hed = weekFrequencyDates.toString();
		String[] dateHeaders =hed.split(",");
		LinkedList<String> headersLinkedList = new LinkedList<>();
		String[] firstheaders = {"Employee","Project","Frequency"};
		String[] secondHeader ={"Total","ProjectToDate","LastMonth","Stage"};
				headersLinkedList.addAll(Arrays.asList(firstheaders));
				headersLinkedList.addAll(Arrays.asList(dateHeaders));
				headersLinkedList.addAll(Arrays.asList(secondHeader));
		List<String> headderList = new ArrayList<String>();
		for(String headerArray :headersLinkedList){
			headderList.add(headerArray);
		}
		String [] head = new String[headderList.size()];
		String [] header = headderList.toArray(head);
		String headers [] = header;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,1);
	   return createExcelsheet;
	}
	
	private Sheet prepareTimesheetReportDataExcel(ProjectMembers projectMember, LinkedHashMap<Date, Date> displayWeeksList,User loginUser, Sheet createExcelsheet, Row writeRow) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		if(projectMember.getEmployee().getUser().getFullName() != null){
			 writeRow.createCell(0).setCellValue(sb.append(projectMember.getEmployee().getUser().getFullName()).append(projectMember.getEmployee().getUser().getEmail()).toString()); 
		}
		if(null != projectMember.getProjects().getProjectName()){
			 writeRow.createCell(1).setCellValue(projectMember.getProjects().getProjectName()); 
		}
		if(null != projectMember.getProjects().getInvoiceFrequency()){
			 writeRow.createCell(2).setCellValue(projectMember.getProjects().getInvoiceFrequency()); 
		}
		int count = 3;
		if(displayWeeksList != null && !displayWeeksList.isEmpty()){
    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
    	    	 SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),entry.getKey(), entry.getValue());
    	    	
    	    	 //String approvalClass = "class='draft'";
    	    	 Float weekTotalHours = 0f;
    	    	 Float weekBillableHours = 0f;
    	    	 Float weekNonBillableHours = 0f;
    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getTotalHours())){
    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getTotalHours());
    	    	 }
    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getBillableHours())){
    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getBillableHours());
    	    	 }
    	    	 if(sah != null && sah.getSubmissionId() != null && StringUtils.isNotEmpty(sah.getSubmissionId().getNonBillableHours())){
    	    		 weekTotalHours = Float.parseFloat(sah.getSubmissionId().getNonBillableHours());
    	    	 }
    	         /*if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
    	        	  approvalClass = "class='subm'";
    	    	 }
    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.APPROVED)){
    	    		 approvalClass = "class='appr'";
    	    	 }
    	    	 else  if(sah.getSubmissionApprovalStatus() != null && sah.getSubmissionApprovalStatus().equals(Submission_ApprovalStatus.REJECTED)){
    	    		 approvalClass = "class='reje'";
    	    	 }*/
    	         
    	    	 StringBuilder listSb = new StringBuilder();
    	    	 writeRow.createCell(count).setCellValue(listSb.append(weekTotalHours).append("Billable :"+weekBillableHours).append("NonBillabe :"+weekNonBillableHours).toString());
    	         
    	         count++;
    	     }
     }
		writeRow.createCell(count).setCellValue("0.0");
		writeRow.createCell(count+1).setCellValue("0.0");
		writeRow.createCell(count+2).setCellValue("0.0");
		writeRow.createCell(count+3).setCellValue("ACTION REQUIREDD");
		
		return createExcelsheet;
	}
	
}
