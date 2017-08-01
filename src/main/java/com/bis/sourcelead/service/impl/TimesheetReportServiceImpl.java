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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
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
public class TimesheetReportServiceImpl implements TimesheetReportService {


	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
    String sourceleadUrl;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetReportServiceImpl.class);
	
	@Override
	public String prepareTimeSheetDiv(String frequencyYear, String frequencyMonth,User loginUser,OrgLocations orgLocations) throws Exception {
		StringBuilder prepareTimeSheetDiv = new StringBuilder("");
		StringBuilder prepareMonthsHiddenDiv = new StringBuilder("");
		StringBuilder currentMonthsHiddenDiv = new StringBuilder("");
		StringBuilder selectedYear = new StringBuilder("");
		StringBuilder selectedMonth = new StringBuilder("");
		frequencyMonth = frequencyMonth.trim();
		prepareTimeSheetDiv.append("<div class='content-area content-new clearfix'>");
		prepareTimeSheetDiv.append("<div class='timesheet_head timesheetRep'>");
		          prepareTimeSheetDiv.append("<div class='timeReFi'>");
			  	          LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(1);
			  	          
			  	         LinkedList<String>  allMonthsList = CalendarUtil.getAllMonths();
						 LinkedList<String>  tsrMonthsList = new LinkedList<>();
						 LinkedList<String>  currentMonthsList = new LinkedList<>();
						 LinkedList<String>  nextyrMonthsList = new LinkedList<>();
						 
						 Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
						 int cMonth = Integer.parseInt(Constants.DF_MM.format(currentCal.getTime()));
						 String currentYear = CalendarUtil.getCurrentYear();
						 String currentMonth = CalendarUtil.getCurrentMonth();
						 
						 /**
						  * for to show months for current year
						  */
						 if(cMonth> 9){
							 currentMonthsList.addAll(allMonthsList);
						 }
						 else{
						     for(int i = 0;i< cMonth+2;i++){
						    	 currentMonthsList.add(allMonthsList.get(i));
						     }
						 }
						
						 /**
						  * for to show months for next year
						  */
						 if(cMonth == 11){
							 nextyrMonthsList.add(allMonthsList.get(0));
						 }
						 else if(cMonth == 12){
							 nextyrMonthsList.add(allMonthsList.get(0));
							 nextyrMonthsList.add(allMonthsList.get(1));
						 }
						 
				          prepareTimeSheetDiv.append("		                <select id='frequencyYear' name='frequencyYear' class='form-control chosen-select'  onchange='getMonths(this);'  >");
							for (String yrStr : tsrYearsList) {
								    StringBuilder isSelected = new StringBuilder("");
								    if(StringUtils.isEmpty(frequencyYear) && currentYear.equalsIgnoreCase(yrStr)){
								    	isSelected.append("selected='selected'");
								    	selectedYear.append(yrStr);
								    }
								    else if(StringUtils.isNotEmpty(frequencyYear) && frequencyYear.equalsIgnoreCase(yrStr)){
								    	isSelected.append("selected='selected'");
								    	selectedYear.append(yrStr);
								    } 
								    
								    /**
								     * for to highlight the current  year
								     */
								    if(currentYear.equalsIgnoreCase(yrStr)){
								    	 currentMonthsHiddenDiv.append("<input type='hidden' id='currentYear' value='"+yrStr.trim()+"'>");
								    }
								    prepareTimeSheetDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+yrStr+"'>");
								    prepareTimeSheetDiv.append(yrStr);
								    prepareTimeSheetDiv.append("		                  </option>");
								    
								    /**
									  * for to calculate months for selected year
									  */
								    if(Integer.parseInt(currentYear)>Integer.parseInt(yrStr)){
								    	if(StringUtils.isNotEmpty(frequencyYear) && frequencyYear.equalsIgnoreCase(yrStr)){
								    		tsrMonthsList.addAll(CalendarUtil.getAllMonths());
								    	}
								    		
								    	prepareMonthsHiddenDiv.append("<input type='hidden' id='"+yrStr+"MonthList' value='"+CalendarUtil.getAllMonths().toString()+"'>");
								    }
								    else if(Integer.parseInt(currentYear)==Integer.parseInt(yrStr)){
								    	if((StringUtils.isNotEmpty(frequencyYear) && frequencyYear.equalsIgnoreCase(yrStr)) || (StringUtils.isEmpty(frequencyYear))){
								    		tsrMonthsList.addAll(currentMonthsList);
								    	}
								    	prepareMonthsHiddenDiv.append("<input type='hidden' id='"+yrStr+"MonthList' value='"+currentMonthsList.toString()+"'>");
								    }
								    else if(Integer.parseInt(currentYear)<Integer.parseInt(yrStr)){
								    	if(StringUtils.isNotEmpty(frequencyYear) && frequencyYear.equalsIgnoreCase(yrStr)){
								    		tsrMonthsList.addAll(nextyrMonthsList);
								    	}
								    	prepareMonthsHiddenDiv.append("<input type='hidden' id='"+yrStr+"MonthList' value='"+nextyrMonthsList.toString()+"'>");
								    }
							}
							prepareTimeSheetDiv.append("		                </select>");
							prepareTimeSheetDiv.append(prepareMonthsHiddenDiv);
					 prepareTimeSheetDiv.append("	            </div>");
					 prepareTimeSheetDiv.append("<div class='timeReFi'>");
			          prepareTimeSheetDiv.append("		                <select id='frequencyMonth' name='frequencyMonth' class='form-control chosen-select'  >");
						for (String monthStr : tsrMonthsList) {
							    StringBuilder isSelected = new StringBuilder("");
							    if(StringUtils.isEmpty(frequencyMonth) && currentMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    }
							    else if(StringUtils.isNotEmpty(frequencyMonth) && frequencyMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    } 
							    
							    /**
							     * for to highlight the current month,if selected year is current year
							     */
							    if(currentMonth.equalsIgnoreCase(monthStr)){
							    	 currentMonthsHiddenDiv.append("<input type='hidden' id='currentMonth' value='"+monthStr.trim()+"'>");
							    }
							   
							     
							    prepareTimeSheetDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+monthStr+"'>");
							    prepareTimeSheetDiv.append(monthStr);
							    prepareTimeSheetDiv.append("		                  </option>");
							    
						}
						prepareTimeSheetDiv.append("		                </select>");
				prepareTimeSheetDiv.append("	            </div>");
				 prepareTimeSheetDiv.append("<div class='timeReFi'>");
					prepareTimeSheetDiv.append("	            <input type='button' onclick='timesheetReportsDiv();' class='btn btn-success' value='View'>	");
					prepareTimeSheetDiv.append(currentMonthsHiddenDiv);
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
		 //DateFormat format = Constants.DF_DMY;
		 DateFormat format= LoginSessionController.DATEFORMAT;
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
			timesheetApprovalDiv.append("	                	<th>Employee</th>           ");
			timesheetApprovalDiv.append("	                    <th>Project</th>            ");
			timesheetApprovalDiv.append("	                    <th>Frequency</th>             ");
			timesheetApprovalDiv.append("	                    <th>Rate</th>            ");
			
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
			timesheetApprovalDiv.append("	                    <th>Stage</th>             ");
			timesheetApprovalDiv.append("	                    <th>Project to Date</th>   ");
			timesheetApprovalDiv.append("	                    <th>Last month</th>        ");
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
				                                             .append("<i title='").append(projectMember.getEmployee().getUser().getFullName()).append("'>").append(projectMember.getEmployee().getUser().getFullName()).append("</i>");
				                                             if(null != projectMember.getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(projectMember.getEmployee().getUserEmploymentType()) && null != projectMember.getEmployee().getEmployeeId() && !"".equalsIgnoreCase(projectMember.getEmployee().getEmployeeId())){
				                                            	 timesheetApprovalDiv.append("<i title='").append("Emp Id :").append(projectMember.getEmployee().getEmployeeId()).append(" , ").append("Emp Type :").append(projectMember.getEmployee().getUserEmploymentType()).append("'>").append(projectMember.getEmployee().getEmployeeId()).append(",").append(projectMember.getEmployee().getUserEmploymentType()).append("</i>");	 
				                                             }else if(null != projectMember.getEmployee().getEmployeeId() && !"".equalsIgnoreCase(projectMember.getEmployee().getEmployeeId())){
				                                            	 timesheetApprovalDiv.append("<i title='").append("Emp Id :").append(projectMember.getEmployee().getEmployeeId()).append("'>").append(projectMember.getEmployee().getEmployeeId()).append("</i>");
				                                             }else if(null != projectMember.getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(projectMember.getEmployee().getUserEmploymentType())){
				                                            	 timesheetApprovalDiv.append("<i title='").append("Emp Type :").append(projectMember.getEmployee().getUserEmploymentType()).append("'>").append(projectMember.getEmployee().getUserEmploymentType()).append("</i>"); 
				                                             }
					 											timesheetApprovalDiv.append("</div>")
				                               .append("   <div>")
					                        .append(" </td>");
				     
				     if(null != projectMember.getProjects().getOrgLocationsId() && null != projectMember.getProjects().getOrgRelationId()){
				    	 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getProjects().getOrgLocationsId().getId(), projectMember.getProjects().getOrgRelationId().getId());
			    	 timesheetApprovalDiv.append("<td><div class='md'><i title='").append(projectMember.getProjects().getProjectName()).append(" ").append(",").append(" ").append(orgRelation.getResponderLocationId().getOrgLocationName()).append("'>").append(projectMember.getProjects().getProjectName()).append("<br>").append(orgRelation.getResponderLocationId().getOrgLocationName()).append("</i></div></td>");
				     }else{
				     timesheetApprovalDiv.append("<td><div class='md'><i title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</i></div></td>");
				     }
				     if(null != projectMember.getProjects().getInvoiceFrequency()){
				     timesheetApprovalDiv.append("<td><div class='sm'>").append(projectMember.getProjects().getInvoiceFrequency()).append("</div></td>");
				     }else{
				     timesheetApprovalDiv.append("<td><div class='sm'>").append("--").append("</div></td>"); 
				     }
				     if(null != projectMember.getEmployee().getRate() && !"".equalsIgnoreCase(projectMember.getEmployee().getRate())){
				    	 if(null != projectMember.getEmployee().getCurrentCurrency() && !"".equalsIgnoreCase(projectMember.getEmployee().getCurrentCurrency())){
				    		 timesheetApprovalDiv.append("<td><div class='sm'><i title='").append(projectMember.getEmployee().getCurrentCurrency()).append("-").append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).append("'>").append(projectMember.getEmployee().getCurrentCurrency()).append("-").append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).append("</i></div></td>"); 
				    	 }else{
				    		 timesheetApprovalDiv.append("<td><div class='sm'><i title='").append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).append("'>").append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).append("</i></div></td>");
				    	 }
				     
				     }else{
				    	 timesheetApprovalDiv.append("<td><div class='sm'><i title='").append("--").append("'>").append("--").append("</i></div></td>");	 
				     }
				     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
			    	 Submission_ApprovalStatus combinedApprovalStatus = null; 
				     float monthTotalHours            = 0f;
					 float monthBillableTotalHours    = 0f;
					 float monthNonBillableTotalHours = 0f;
				     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
				    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
				    	    	HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
				    	    	Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
				    	    	if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
				    	    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
				    	    	}
				    	    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
				    	    		approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
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
				    	    	                          .append(" <span "+approvalClass+">").append("<d title='").append("week-Total-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).append("'>").append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).append("</d>").append("</span>")
				    	    	                             .append(" <div class='progress'>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>").append("<d title='").append("Billable").append(":").append(CommonUtil.getHoursDisplayFormat(weekBillableHours)).append("'>")
				    	    	                                        .append(CommonUtil.getHoursDisplayFormat(weekBillableHours))
				    	    	                                     .append("</d>").append("</div>")
				    	    	                                     .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>").append("<d title='").append("Non-Billable").append(":").append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours)).append("'>")
				    	    	                                        .append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours))
				    	    	                                        .append("</d>").append("</div>")
				    	    	                             .append(" </div>")
				    	    	                           
				    	    	                        .append(" </div>")
				    	    	                     .append("</td>");
				    	    	 
				    	    	 
				    	    	
				    	     }
				     }
			    	 
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append("<d title='").append("Month-Total-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(monthTotalHours)).append("'>").append(CommonUtil.getHoursDisplayFormat(monthTotalHours)).append("</d>").append("</span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>").append("<d title='").append("Month-Billable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(monthBillableTotalHours)).append("'>")
                                     .append(CommonUtil.getHoursDisplayFormat(monthBillableTotalHours))
                                     .append("</d>").append("</div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>").append("<d title='").append("Month-NonBillable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours)).append("'>")
                                 .append(CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours))
                                 .append("</d>").append("</div>")
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
	    	    	 else  if(combinedApprovalStatus.equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
	    	    		 combinedApprovalClass = "class='proj_process'";
	    	    		  combinedApprStatus = "Submitted";
	    	    	 }
	    	    	 else  if(combinedApprovalStatus.equals(Submission_ApprovalStatus.NEW)){
	    	    		 combinedApprovalClass = "class='bgGReen'";
	    	    		  combinedApprStatus = "NEW";
	    	    	 }
				     
				     timesheetApprovalDiv.append("<td><div class='sm'><b "+combinedApprovalClass+">"+combinedApprStatus+"</b></div></td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append("<d title='").append("ProjectToDate-Total-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours())).append("'>").append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours())).append("</d></span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>").append("<d title='").append("ProjectToDate-Billable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours())).append("'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours()))
                                  .append("</d></div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>").append("<d title='").append("ProjectToDate-NonBillable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours())).append("'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours()))
                                  .append("</d></div>")
                          .append("</div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     timesheetApprovalDiv.append("<td>")
                     .append(" <div class='week'>")
                       .append(" <span class='draft'>").append("<d title='").append("PreMonth-Total-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours())).append("'>").append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours())).append("</d></span>")
                          .append(" <div class='progress'>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-info' role='progressbar' style='width:60%'>").append("<d title='").append("PreMonth-Billable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours())).append("'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours()))
                                  .append("</d></div>")
                                  .append(" <div class='progress-bar progress-bar-striped active progress-bar-warning' role='progressbar' style='width:40%'>").append("<d title='").append("PreMonth-NonBillable-Hours").append(":").append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours())).append("'>")
                                     .append(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours()))
                                  .append("</d></div>")
                          .append(" </div>")
                        
                     .append(" </div>")
                  .append("</td>");
				     
				     
				     
			        timesheetApprovalDiv.append("</tr>");
		  }
		
		  timesheetApprovalDiv.append("	        </tbody>");
		  timesheetApprovalDiv.append("	     </table> ");
	      timesheetApprovalDiv.append("	</div>");
		
        	
        	
        }
        else{
        	timesheetApprovalDiv.append("   <div class='dataTables_empty'>");
        	timesheetApprovalDiv.append("  <input type='hidden' id='hideExport' value='EmptyList'>");
			timesheetApprovalDiv.append("  </div> "); 
        }

     return timesheetApprovalDiv;
  }


	@SuppressWarnings("deprecation")
	@Override
	public File prepareTimeSheetExport(String frequencyYear, String frequencyMonth, User loginUser,OrgLocations orgLocations) throws Exception {
		File file = null;
		StringBuilder weekFrequencyDates = new StringBuilder(); 
		//DateFormat format = Constants.DF_DMY;
		 DateFormat  format = LoginSessionController.DATEFORMAT;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(frequencyMonth,frequencyYear);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 //Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 //Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
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
		String[] firstheaders = {"Employee Name","Employee Email","Phnone No","Emp Type","Rate","Project","Client","Vendor","Frequency"};
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
		
	    Document document = new Document(PageSize.A2.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    
	    StringBuilder sbTsReports =  new StringBuilder();
	    sbTsReports.append("timesheetSheet_").append(dateFormat.format(new Date()));
	    StringBuilder fileSB = new StringBuilder();
	    file = File.createTempFile(fileSB.append(orgLocations.getOrgLocationName()).append("_TimeSheetReports_").append(frequencyMonth).append("-").append(frequencyYear).append("-").toString(),".pdf");
	    file.setReadable(true);

	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        StringBuilder userStyle = new StringBuilder();
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			BaseColor myColor = WebColors.getRGBColor("#000000");
			userStyle.append(orgLocations.getOrgLocationName()).append("-").append(frequencyMonth).append("-").append(frequencyYear).append(" ").append("TimeSheetReports");
			
			StringBuilder orgNameSB = new StringBuilder();
            Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrgLocationName()).append("-").append(frequencyMonth).append("-").append(frequencyYear).append(" ").append("TimeSheetReports").toString());
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
			
	        boldFont.setColor(myColor);
	        PdfPTable table = new PdfPTable(header.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(26);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
	        if(header.length > 24){
            table.setWidths(new int[]{5,5,5,5,5,5,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});
	        }else{
	        table.setWidths(new int[]{5,5,5,5,5,5,5,5,5,3,3,3,3,3,3,3,3,3,3,3,3,3,3,5});	
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
            List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startDate, endDate, orgLocations.getId(), loginUser.getId());
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
		
		StringBuilder nameAndId = new StringBuilder();
			if(null != projectMember.getEmployee().getUser().getFullName() && null != projectMember.getEmployee().getEmployeeId()){
				timeSheetReportsArray[0] = nameAndId.append(projectMember.getEmployee().getUser().getFullName()).append("-").append(projectMember.getEmployee().getEmployeeId()).toString();
			}else if((null != projectMember.getEmployee().getUser().getFullName())){
				timeSheetReportsArray[0] = nameAndId.append(projectMember.getEmployee().getUser().getFullName()).toString();
				}else{
					timeSheetReportsArray[0] = nameAndId.append("--").toString();	
				}
			if(projectMember.getEmployee().getUser().getEmail() !=null){
				timeSheetReportsArray[1] = projectMember.getEmployee().getUser().getEmail();
			 }else{
				 timeSheetReportsArray[1] = "--";
			 }if(projectMember.getEmployee().getUser().getMobilePhone() !=null){
				 timeSheetReportsArray[2] = projectMember.getEmployee().getUser().getMobilePhone();
			 }else{
				 timeSheetReportsArray[2] = "--";
			 }
			 if(projectMember.getEmployee().getUserEmploymentType() !=null){
				 timeSheetReportsArray[3] = projectMember.getEmployee().getUserEmploymentType();
			 }else{
				 timeSheetReportsArray[3] = "--";
			 }
			 if(projectMember.getEmployee().getRate() !=null){
				 StringBuilder rateString = new StringBuilder();
				 timeSheetReportsArray[4] = rateString.append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).toString();
			 }else{
				 timeSheetReportsArray[4] = "--";
			 }
			if(projectMember.getProjects().getProjectName() !=null){
				timeSheetReportsArray[5] = projectMember.getProjects().getProjectName();
			 }else{
				 timeSheetReportsArray[5] = "--";
			 }
			
			if(null != projectMember.getProjects().getOrgLocationsId() && null != projectMember.getProjects().getOrgRelationId()){
				OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getProjects().getOrgLocationsId().getId(), projectMember.getProjects().getOrgRelationId().getId());
				 timeSheetReportsArray[6] = orgRelation.getResponderLocationId().getOrgLocationName(); 
				 
			 }else{
				 timeSheetReportsArray[6] = "--";
			 }
			if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
				OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
				timeSheetReportsArray[7] = orgRelation.getResponderLocationId().getOrgLocationName();
				
			 }else{
				 timeSheetReportsArray[7] = "--";
			 }
			
			if(projectMember.getProjects().getInvoiceFrequency() !=null){
				timeSheetReportsArray[8] = projectMember.getProjects().getInvoiceFrequency();
			 }else{
				 timeSheetReportsArray[8] = "--";
			 }
			int count = 9;
			 SubmissionApproverHierarchy sah= null;
			 float monthTotalHours = 0f;
			 float  monthBillableTotalHours = 0f;
			 float monthNonBillableTotalHours = 0f;
			 Submission_ApprovalStatus combinedApprovalStatus = null; 
			 sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
			if(displayWeeksList != null && !displayWeeksList.isEmpty()){
	    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
	    	    	 //String approvalClass = "class='draft'";
	    	    	 float weekTotalHours = 0f;
	    	    	 float  weekBillableHours = 0f;
	    	    	 float weekNonBillableHours = 0f;
	    	    	 HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
		    	     Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
	    	    	 if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
	    	    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
	    	    	}
	    	    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
		    	    	approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
		    	    }
		    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
		    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
		    	    }
	    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
	    	    	}
	    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
	    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
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
				
				timeSheetReportsArray[count+9] = combinedApprovalStatus.toString();
			
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
		//DateFormat format = Constants.DF_DMY;
		 DateFormat  format = LoginSessionController.DATEFORMAT;
		 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(frequencyMonth,frequencyYear);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
		  
	     Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(startDate);
		 //Date startMondayDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON").getTime();
		 
		 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
		 endCal.setLenient(false);
		 endCal.setTime(endDate);
		 //Date endSundayDate = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN").getTime();
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
		
		String replacedOrgName = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		timesheetReportsExcelBorder(createExcelworkbook);
		StringBuilder sheetName = new StringBuilder();
		
		Pattern special = Pattern.compile ("[a-zA-Z0-9]*");
		Matcher hasSpecial = special.matcher(orgLocations.getOrgLocationName());
		if(!hasSpecial.matches()){
		 replacedOrgName = orgLocations.getOrgLocationName().replaceAll("\\\\", " ").replaceAll("/", " ").replaceAll("[^a-zA-Z0-9][-]", "");
		}else{
		 replacedOrgName = orgLocations.getOrgLocationName();
		}
		Sheet createExcelsheet = createExcelworkbook.createSheet(sheetName.append(replacedOrgName).append("-").append(frequencyMonth).append("-").append(frequencyYear).append(" ").append("TimeSheetReports").toString());
		Row writeRow = null;
		createExcelsheet = gettimesheetReportsExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,weekFrequencyDates);
		
		
		int column = 0;
		 List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(startDate, endDate, orgLocations.getId(), loginUser.getId());
		 for(ProjectMembers promembers:projectMembers){
			 writeRow = createExcelsheet.createRow(column + 1);
			 createExcelsheet = prepareTimesheetReportDataExcel(promembers,displayWeeksList,loginUser,createExcelsheet,writeRow,startDate,endDate,orgLocations);
			 column++;
         }
		 StringBuilder fileSB = new StringBuilder();
		 file = File.createTempFile(fileSB.append(replacedOrgName).append("_TimeSheetReports_").append(frequencyMonth).append("-").append(frequencyYear).append("-").toString(),".xls");
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
		String[] firstheaders = {"Emp Id","Employee Name","Employee Email","Phnone No","Emp Type","Rate","Project","Client","Vendor","Frequency"};
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
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
	   return createExcelsheet;
	}
	
	private Sheet prepareTimesheetReportDataExcel(ProjectMembers projectMember, LinkedHashMap<Date, Date> displayWeeksList,User loginUser, Sheet createExcelsheet, Row writeRow, Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		createExcelsheet.getDefaultRowHeight();
		StringBuilder sbid = new StringBuilder();
		if(null != (projectMember.getEmployee().getEmployeeId()) && !"".equalsIgnoreCase(projectMember.getEmployee().getEmployeeId())){
			 writeRow.createCell(0).setCellValue(sbid.append(projectMember.getEmployee().getEmployeeId()).toString());
			 createExcelsheet.autoSizeColumn(0);
		}
		StringBuilder sbname = new StringBuilder();
		if(null != projectMember.getEmployee().getUser().getLastName() && !"".equalsIgnoreCase(projectMember.getEmployee().getUser().getLastName())){
			 writeRow.createCell(1).setCellValue(sbname.append(projectMember.getEmployee().getUser().getFirstName()).append(" ").append(projectMember.getEmployee().getUser().getLastName()).toString()); 
			 createExcelsheet.autoSizeColumn(1);
		}else if(null != projectMember.getEmployee().getUser().getFirstName() && !"".equalsIgnoreCase(projectMember.getEmployee().getUser().getFirstName())){
			writeRow.createCell(1).setCellValue(sbname.append(projectMember.getEmployee().getUser().getFirstName()).toString()); 
			 createExcelsheet.autoSizeColumn(1);
		}
		if(projectMember.getEmployee().getUser().getEmail() != null){
			 writeRow.createCell(2).setCellValue(projectMember.getEmployee().getUser().getEmail());
			 createExcelsheet.autoSizeColumn(2);
		}
		if(null != projectMember.getEmployee().getUser().getMobilePhone()){
			 writeRow.createCell(3).setCellValue(projectMember.getEmployee().getUser().getMobilePhone());
			 createExcelsheet.autoSizeColumn(3);
		}
		if(null != projectMember.getEmployee().getUserEmploymentType()){
			 writeRow.createCell(4).setCellValue(projectMember.getEmployee().getUserEmploymentType());
			 createExcelsheet.autoSizeColumn(4);
		}
		if(null != projectMember.getEmployee().getRate()){
			StringBuilder rateString = new StringBuilder();
			 writeRow.createCell(5).setCellValue(rateString.append(projectMember.getEmployee().getRate()).append("/").append(projectMember.getEmployee().getRatePer()).toString());
			 createExcelsheet.autoSizeColumn(5);
		}
		
		if(null != projectMember.getProjects().getProjectName()){
			 writeRow.createCell(6).setCellValue(projectMember.getProjects().getProjectName()); 
			 createExcelsheet.autoSizeColumn(6);
		}
		if(null != projectMember.getProjects().getOrgLocationsId() && null != projectMember.getProjects().getOrgRelationId()){
			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getProjects().getOrgLocationsId().getId(), projectMember.getProjects().getOrgRelationId().getId());
			 writeRow.createCell(7).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName()); 
			 createExcelsheet.autoSizeColumn(7);
		}
		if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
			writeRow.createCell(8).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName()); 
			 createExcelsheet.autoSizeColumn(8);
		}
		if(null != projectMember.getProjects().getInvoiceFrequency()){
			 writeRow.createCell(9).setCellValue(projectMember.getProjects().getInvoiceFrequency()); 
			 createExcelsheet.autoSizeColumn(9);
		}
		int count = 10;
		 SubmissionApproverHierarchy sah= null;
		 float monthTotalHours = 0f;
		 float  monthBillableTotalHours = 0f;
		 float monthNonBillableTotalHours = 0f;
		 Submission_ApprovalStatus combinedApprovalStatus = null;
    	sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),projectMember.getId(),startDate,endDate);
		if(displayWeeksList != null && !displayWeeksList.isEmpty()){
    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
    	     float weekTotalHours = 0f;
   	    	 float  weekBillableHours = 0f;
   	    	 float weekNonBillableHours = 0f;
   	    	    HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
		        Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
		    	if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
		    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
		    	}
		    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
    	    		approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
    	    	}
	    	    if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
	    	    	 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
	    	    }
    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
    	    	}
    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
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
    	    	
    	    	 StringBuilder listSb = new StringBuilder();
    	    	 writeRow.createCell(count).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekTotalHours)).toString());
    	    	 createExcelsheet.autoSizeColumn(count);
    	    	 writeRow.createCell(count+1).setCellValue( listSb.append(CommonUtil.getHoursDisplayFormat(weekBillableHours)).toString());
    	    	 createExcelsheet.autoSizeColumn(count+1);
    	    	 writeRow.createCell(count+2).setCellValue(listSb.append(CommonUtil.getHoursDisplayFormat(weekNonBillableHours)).toString());
    	    	 createExcelsheet.autoSizeColumn(count+2);
    	         count++;
    	         
    	     }
     }
		
		
		writeRow.createCell(count).setCellValue( CommonUtil.getHoursDisplayFormat(monthTotalHours));
		createExcelsheet.autoSizeColumn(count);
		writeRow.createCell(count+1).setCellValue( CommonUtil.getHoursDisplayFormat(monthBillableTotalHours));
		 createExcelsheet.autoSizeColumn(count+1);
		writeRow.createCell(count+2).setCellValue( CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours));
		 createExcelsheet.autoSizeColumn(count+2);
		writeRow.createCell(count+3).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours()));
		createExcelsheet.autoSizeColumn(count+3);
		writeRow.createCell(count+4).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours()));
		createExcelsheet.autoSizeColumn(count+4);
		writeRow.createCell(count+5).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours()));
		createExcelsheet.autoSizeColumn(count+5);
		writeRow.createCell(count+6).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours()));
		createExcelsheet.autoSizeColumn(count+6);
		writeRow.createCell(count+7).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours()));
		createExcelsheet.autoSizeColumn(count+7);
		writeRow.createCell(count+8).setCellValue( CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours()));
		createExcelsheet.autoSizeColumn(count+8);
		writeRow.createCell(count+9).setCellValue(combinedApprovalStatus.toString());
		createExcelsheet.autoSizeColumn(count+9);
		return createExcelsheet;
	}
	
	public void timesheetReportsExcelBorder(Workbook createExcelworkbook){
	XSSFCellStyle style = (XSSFCellStyle) createExcelworkbook.createCellStyle();
	style.setBorderTop(BorderStyle.MEDIUM);
	style.setBorderBottom(BorderStyle.MEDIUM);
	style.setBorderLeft(BorderStyle.MEDIUM);
	style.setBorderRight(BorderStyle.MEDIUM);
	}
}