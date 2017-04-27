package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
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

@Service
public class TimesheetReportServiceImpl implements TimesheetReportService {


	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Value("${sourceleadUrl}")
    String sourceleadUrl;
	
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
        	timesheetApprovalDiv.append("			<div class='timesheetRepTab'>");
			timesheetApprovalDiv.append("	    	<table id='example' style='width:100%'>");
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


}
