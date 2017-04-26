package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class TimesheetReportServiceImpl implements TimesheetReportService {


	@Autowired
	ProjectMembersService projectMembersService;
	
	@Override
	public String prepareTimeSheetDiv(String frequencyYear, String frequencyMonth,User loginUser,OrgLocations orgLocations) {
		StringBuilder prepareTimeSheetDiv = new StringBuilder("");
		StringBuilder selectedYear = new StringBuilder("");
		StringBuilder selectedMonth = new StringBuilder("");
		
		prepareTimeSheetDiv.append("<div class='content-area content-new clearfix'>");
		prepareTimeSheetDiv.append("<div class='timesheet_head timesheetRep'>");
		          prepareTimeSheetDiv.append("<div class='timeReFi'>");
			  	          LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(2, 2);
				          prepareTimeSheetDiv.append("		                <select id='frequencyYear' name='frequencyYear' class='form-control chosen-select' onchange=''  >");
							for (String yrStr : tsrYearsList) {
								    StringBuilder isSelected = new StringBuilder("");
								    String currentYear = CalendarUtil.getCurrentYear();
								    if(StringUtils.isEmpty(frequencyYear) && currentYear.equalsIgnoreCase(yrStr)){
								    	isSelected.append("selected='selected'");
								    	selectedYear.append(yrStr);
								    }
								    else if(StringUtils.isNotEmpty(frequencyYear) && currentYear.equalsIgnoreCase(yrStr)){
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
			          prepareTimeSheetDiv.append("		                <select id='frequencyMonth' name='frequencyMonth' class='form-control chosen-select' onchange=''  >");
						for (String monthStr : tsrMonthsList) {
							    StringBuilder isSelected = new StringBuilder("");
							    String currentMonth = CalendarUtil.getCurrentMonth();
							    if(StringUtils.isEmpty(frequencyMonth) && currentMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    }
							    else if(StringUtils.isNotEmpty(frequencyMonth) && currentMonth.equalsIgnoreCase(monthStr)){
							    	isSelected.append("selected='selected'");
							    	selectedMonth.append(monthStr);
							    }  
							    prepareTimeSheetDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+monthStr+"'>");
							    prepareTimeSheetDiv.append(monthStr);
							    prepareTimeSheetDiv.append("		                  </option>");
						}
						prepareTimeSheetDiv.append("		                </select>");
				prepareTimeSheetDiv.append("	            </div>");
					
				 StringBuilder statusDiv = timesheetReportsTableDiv(selectedYear.toString(),selectedMonth.toString(),orgLocations,loginUser);
				 prepareTimeSheetDiv.append(statusDiv);		
		prepareTimeSheetDiv.append("</div>");
		prepareTimeSheetDiv.append("</div>");
		return prepareTimeSheetDiv.toString(); 
		
	}
	
	
	 public StringBuilder timesheetReportsTableDiv(String year,String month,OrgLocations orgLocations,User loginUser){
	       
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(month,year);
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("STARTDATE");
		  
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
        	
        }
        else{
        	timesheetApprovalDiv.append("   <div class='dataTables_empty'>"); 
			timesheetApprovalDiv.append("  </div> "); 
        }

     return timesheetApprovalDiv;
  } 


}
