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
				 
				 prepareTimeSheetDiv.append("<div class='clearfix'></div>");
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
		  

        StringBuilder timesheetApprovalDiv = new StringBuilder();
        List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startDate, endDate, orgLocations.getId(), loginUser.getId());
        if(projectMembers != null && !projectMembers.isEmpty()){

        	int tdSpanLength = 6;
        	timesheetApprovalDiv.append("			<div class='timesheetRepTab overflow_inherint'>");
			timesheetApprovalDiv.append("	    	<table id='timesheetReportsPageNation' style='width:100%'>");
			timesheetApprovalDiv.append("				<thead>");   
			timesheetApprovalDiv.append("	            	<tr>");
			timesheetApprovalDiv.append("	                	<th>Emp Id</th>           ");
			timesheetApprovalDiv.append("	                	<th>Employee</th>           ");
			timesheetApprovalDiv.append("	                    <th>Project</th>            ");
			timesheetApprovalDiv.append("	                    <th>Frequency</th>             ");
			
			LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate));
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
				 timesheetApprovalDiv.append("<td><div class=''><i title='").append(projectMember.getEmployee().getUser().getId()).append("'>").append(projectMember.getEmployee().getUser().getId()).append("</i></div></td>");
					 timesheetApprovalDiv.append("  <td>")
					                       .append("<div class='lg clearfix'>")
					                                 .append("   <div class='uimg'><img  id='userImage' onerror='predefineProfileImage(this);' src='").append(sourceleadUrl).append("/displayUserImage/").append(projectMember.getEmployee().getUser().getId()).append("' alt='Profile Image' class='img-circle img-responsive'/>")
					                                 .append("</div>")
					                                 .append("    <div class='Utext'>")
				                                             .append("<i title='").append(projectMember.getEmployee().getUser().getFullName()).append("'>").append(projectMember.getEmployee().getUser().getFullName()).append("</i>")
				                                             /*.append("<i title='").append(projectMember.getEmployee().getUser().getEmail()).append("'>").append(projectMember.getEmployee().getUser().getEmail()).append("</i>")*/
		                                             .append("</div>")
				                               .append("   <div>")
					                        .append(" </td>");
				     timesheetApprovalDiv.append("<td><div class='md'><i title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</i></div></td>");
			    	 timesheetApprovalDiv.append("<td><div class='sm'>").append(projectMember.getProjects().getInvoiceFrequency()).append("</div></td>");
		        
			    	 SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
			    	 Submission_ApprovalStatus combinedApprovalStatus = null; 
				     float monthTotalHours            = 0f;
					 float monthBillableTotalHours    = 0f;
					 float monthNonBillableTotalHours = 0f;
				     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
				    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
				    	    	
				    	    	Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
				    	    	if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
				    	    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
				    	    	}
				    	    	Float  weekBillableHours = 0f;
				    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
				    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
				    	    	}
				    	    	Float  weekNonBillableHours = 0f;
				    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
				    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
				    	    	}
				    	    	Float  weekTotalHours = 0f;
				    	    	if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
				    	    		 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
				    	    	}
				    	    	 String approvalClass = "class='draft'";
				    	         if(approvalStatus.equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
				    	        	  approvalClass = "class='subm'";
				    	    	 }
				    	    	 else  if(approvalStatus.equals(Submission_ApprovalStatus.APPROVED)){
				    	    		 approvalClass = "class='appr'";
				    	    	 }
				    	    	 else  if(approvalStatus.equals(Submission_ApprovalStatus.REJECTED)){
				    	    		 approvalClass = "class='reje'";
				    	    	 }
				    	         
				    	         
				    	        if(approvalStatus == Submission_ApprovalStatus.REJECTED){
				    	        	if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.REJECTED){
				    	        		combinedApprovalStatus = Submission_ApprovalStatus.REJECTED;
				    	        	}
				    	        	else{
				    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
				    	        	}
				 					
				 				}
				 				else if(approvalStatus == Submission_ApprovalStatus.NEW){
				 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.NEW){
				 						combinedApprovalStatus = Submission_ApprovalStatus.NEW;
				 					}
				 					else{
				    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
				    	        	}
				 					
				 				}
				 				else if(approvalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
				 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
				 						combinedApprovalStatus = Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL;
				 					}
				 					else{
				    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
				    	        	}
				 					
				 				}
				 				else if(approvalStatus == Submission_ApprovalStatus.APPROVED){
				 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.APPROVED){
				 						combinedApprovalStatus = Submission_ApprovalStatus.APPROVED;
				 					}
				 					else{
				    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
				    	        	}
				 					
				 				}
				    	        monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
				    	        monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
				    	        monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
				    	    	 timesheetApprovalDiv.append("<td>")
				    	    	                        .append(" <div class='week'>")
				    	    	                          .append(" <span "+approvalClass+">").append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).append("</span>")
				    	    	                             .append(" <div class='progress'>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
				    	    	                                        .append(CommonUtil.getHoursDisplayFormat(weekBillableHours))
				    	    	                                     .append("</div>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
				    	    	                                        .append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours))
				    	    	                                     .append("</div>")
				    	    	                             .append(" </div>")
				    	    	                           
				    	    	                        .append(" </div>")
				    	    	                     .append("</td>");
				    	    	 
				    	    	 
				    	    	
				    	     }
				     }
			    	 
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(CommonUtil.getHoursDisplayFormat(monthTotalHours)).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(monthBillableTotalHours))
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours))
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours())).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours()))
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours()))
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours())).append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours()))
                                  .append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours()))
                                  .append("</div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     
				     String combinedApprovalClass = "class='action'";
				     String combinedApprStatus = "Action Required";
	    	         if(combinedApprovalStatus.equals(Submission_ApprovalStatus.APPROVED)){
	    	    		 combinedApprovalClass = "class='proj_active'";
	    	    		  combinedApprStatus = "Approved";
	    	    	 }
	    	    	 else  if(combinedApprovalStatus.equals(Submission_ApprovalStatus.REJECTED)){
	    	    		 combinedApprovalClass = "class='proj_closed'";
	    	    		  combinedApprStatus = "Rejected";
	    	    	 }
				     
				    timesheetApprovalDiv.append("<td><div class='sm'><b "+combinedApprovalClass+">"+combinedApprStatus+"</b></div></td>");
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
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate));
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
						weekFrequencyDates.append(shortormat.format(weekStartDate)).append(",");
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
		String[] firstheaders = {"Emp Id","Employee Name","Employee Email","Project","Frequency"};
		String[] secondHeader ={"Total","Total Billable","Total Non-Billable","ProjectToDate","Project Hours Billable","Project Hours Non-Billable","LastMonth","LastMonth Billable","LastMonth  Non-Billable","Stage"};
		
		
				headersLinkedList.addAll(Arrays.asList(firstheaders));
				headersLinkedList.addAll(Arrays.asList(dateHeaders));
				headersLinkedList.addAll(Arrays.asList(secondHeader));
		List<String> headderList = new ArrayList<String>();
		for(String headerArray :headersLinkedList){
			headderList.add(headerArray);
		}
		String [] head = new String[headderList.size()];
		String [] header = headderList.toArray(head);
		
	    Document document = new Document(PageSize.LETTER_LANDSCAPE.rotate());
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
	        cell1.setColspan(23);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
	        if(header.length > 20){
            table.setWidths(new int[]{3,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});
	        }else{
	        table.setWidths(new int[]{3,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});	
	        }
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
            	reportList.add(prepareTimesheetReportData(promembers,header,displayWeeksList,loginUser,startDate,endDate));
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
	
	private String[] prepareTimesheetReportData(ProjectMembers projectMember, String[] header, LinkedHashMap<Date, Date> displayWeeksList, User loginUser, Date startDate, Date endDate) throws Exception {
		String timeSheetReportsArray[] = new String[header.length];
			if(projectMember.getEmployee().getUser().getFullName() !=null){
				timeSheetReportsArray[0] = projectMember.getEmployee().getUser().getId().toString();
			 }else{
				 timeSheetReportsArray[0] = "--";
			 }
			if(projectMember.getEmployee().getUser().getFullName() !=null){
				timeSheetReportsArray[1] = projectMember.getEmployee().getUser().getFullName();
			 }else{
				 timeSheetReportsArray[1] = "--";
			 }
			if(projectMember.getEmployee().getUser().getEmail() !=null){
				timeSheetReportsArray[2] = projectMember.getEmployee().getUser().getEmail();
			 }else{
				 timeSheetReportsArray[2] = "--";
			 }if(projectMember.getProjects().getProjectName() !=null){
				timeSheetReportsArray[3] = projectMember.getProjects().getProjectName();
			 }else{
				 timeSheetReportsArray[3] = "--";
			 }
			if(projectMember.getProjects().getInvoiceFrequency() !=null){
				timeSheetReportsArray[4] = projectMember.getProjects().getInvoiceFrequency();
			 }else{
				 timeSheetReportsArray[4] = "--";
			 }
			int count = 5;
			SubmissionApproverHierarchy sah= null;
			 float monthTotalHours = 0f;
			 float weekTotalHours = 0f;
			 float  weekBillableHours = 0f;
			 float  monthBillableTotalHours = 0f;
			 float weekNonBillableHours = 0f;
			 float monthNonBillableTotalHours = 0f;
			 
			if(displayWeeksList != null && !displayWeeksList.isEmpty()){
	    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
	    	    	 sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
	    	    	
	    	    	 //String approvalClass = "class='draft'";
	    	    	 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
		    	     monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
		    	     monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
	    	    	
		    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
		    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
		    	    }
	    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
	    	    	}
	    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
	    	    	}
	    	    	 
	    	    	 StringBuilder listSb = new StringBuilder();
	    	         timeSheetReportsArray[count] = listSb.append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).toString();
	    	         timeSheetReportsArray[count+1] = listSb.append(CommonUtil.getHoursDisplayFormat(weekBillableHours)).toString();
	    	         timeSheetReportsArray[count+2] = listSb.append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours)).toString();
	    	         count++;
	    	     }
	     }
				timeSheetReportsArray[count] =  CommonUtil.getHoursDisplayFormat(monthTotalHours);
				timeSheetReportsArray[count+1] = CommonUtil.getHoursDisplayFormat(monthBillableTotalHours);
				timeSheetReportsArray[count+2] = CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours);
				
				timeSheetReportsArray[count+3] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours());
				timeSheetReportsArray[count+4] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours());
				timeSheetReportsArray[count+5] = CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours());
				
				timeSheetReportsArray[count+6] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours());
				timeSheetReportsArray[count+7] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours());
				timeSheetReportsArray[count+8] =CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours());
				
				timeSheetReportsArray[count+9] ="ACTION REQUIRED";
			
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
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startDate), format.format(endDate));
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
						weekFrequencyDates.append(shortormat.format(weekStartDate)).append(",");
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
			 createExcelsheet = prepareTimesheetReportDataExcel(promembers,displayWeeksList,loginUser,createExcelsheet,writeRow,startDate,endDate);
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
		String[] firstheaders = {"Emp Id","Employee Name","Employee Email","Project","Frequency"};
		String[] secondHeader ={"Total","Total Billable","Total Non-Billable","ProjectToDate","Project Hours Billable","Project Hours Non-Billable","LastMonth","LastMonth Billable","LastMonth  Non-Billable","Stage"};
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
	
	private Sheet prepareTimesheetReportDataExcel(ProjectMembers projectMember, LinkedHashMap<Date, Date> displayWeeksList,User loginUser, Sheet createExcelsheet, Row writeRow, Date startDate, Date endDate) throws Exception {
		
		
		if(projectMember.getEmployee().getUser().getFullName() != null){
			 writeRow.createCell(0).setCellValue(projectMember.getEmployee().getUser().getId()); 
		}
		if(projectMember.getEmployee().getUser().getFullName() != null){
			StringBuilder sb = new StringBuilder();
			 writeRow.createCell(1).setCellValue(sb.append(projectMember.getEmployee().getUser().getFullName()).toString()); 
		}
		if(projectMember.getEmployee().getUser().getEmail() != null){
			 writeRow.createCell(2).setCellValue(projectMember.getEmployee().getUser().getEmail()); 
		}
		if(null != projectMember.getProjects().getProjectName()){
			 writeRow.createCell(3).setCellValue(projectMember.getProjects().getProjectName()); 
		}
		if(null != projectMember.getProjects().getInvoiceFrequency()){
			 writeRow.createCell(4).setCellValue(projectMember.getProjects().getInvoiceFrequency()); 
		}
		int count = 5;
		SubmissionApproverHierarchy sah = null;
		 Float weekTotalHours = 0f;
    	 Float weekBillableHours = 0f;
    	 Float weekNonBillableHours = 0f;
    	 float monthTotalHours =0f; 
    	 float monthBillableTotalHours =0f;
    	 float monthNonBillableTotalHours  =0f;
    	 
		if(displayWeeksList != null && !displayWeeksList.isEmpty()){
    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
    	    	 sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
    	    	 
    	    	 
    	    	 monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
	    	     monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
	    	     monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
    	    	
	    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
	    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
	    	    }
    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
    	    	}
    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
    	    	}
    	    	 
    	    	 StringBuilder listSb = new StringBuilder();
    	    	 writeRow.createCell(count).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).toString());
    	    	 writeRow.createCell(count+1).setCellValue( listSb.append(CommonUtil.getHoursDisplayFormat(weekBillableHours)).toString());
    	    	 writeRow.createCell(count+2).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours)).toString());
    	         count++;
    	     }
     }
		
		
		writeRow.createCell(count).setCellValue( CommonUtil.getHoursDisplayFormat(monthTotalHours));
		writeRow.createCell(count+1).setCellValue( CommonUtil.getHoursDisplayFormat(monthBillableTotalHours));
		writeRow.createCell(count+2).setCellValue( CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours));
		
		writeRow.createCell(count+3).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours()));
		writeRow.createCell(count+4).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours()));
		writeRow.createCell(count+5).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours()));
		
		writeRow.createCell(count+6).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours()));
		writeRow.createCell(count+7).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours()));
		writeRow.createCell(count+8).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours()));
		
		writeRow.createCell(count+9).setCellValue("ACTION REQUIRED");
		
		return createExcelsheet;
	}
	
}
