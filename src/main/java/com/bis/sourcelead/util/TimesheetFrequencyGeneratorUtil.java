package com.bis.sourcelead.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectsService;

@Component
public  class TimesheetFrequencyGeneratorUtil {
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	CommonUtil commonUtil;
	public Map<String,String> getTimesheetFrequencyListFromProjectInvoiceFrequency(Projects projects,OrgLocations orgLocations) throws Exception{
		String timesheetFrequency = projects.getInvoiceFrequency();
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		if(StringUtils.isNotEmpty(timesheetFrequency)){
				Date empStartDate = projects.getStartDate();
				Date empEndDate = CommonUtil.currentDate();
				//DateFormat format = Constants.DF_STD_DMY;
				//OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
				//SimpleDateFormat format=projects.getOrgLocationsId().getLocFullFormDateFormatter();
				 
				 try {
					    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Monthly")){
					   
					    	 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							 cal.setLenient(false);
							 cal.setTime(commonUtil.currentDate());
							 Date previousMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),-(cal.get(Calendar.MONTH)+1)).get("STARTDATE");
							 Date nextMonthEndDate =  CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),12-(cal.get(Calendar.MONTH)+1)).get("ENDDATE");
							 /**
							  *calculating previous month startDate and next month endDate based on empStartDate and empEndDate
							  *
						      *
						     */
							  Date empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
							  Date empMonthEndDate = null;
							  if(empEndDate != null)
								 empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
							
							  /**
							   * checking whether previousmoth start date is greaterthan employee month startdate or not
						       */
							   if(empMonthStartDate.before(previousMonthStartDate)){
								   empMonthStartDate = previousMonthStartDate;
							   }
							   
							  /**
							   * checking whether nextmonth end date is lesserthan employee enddate or not
						       */
							   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate))){
								   empMonthEndDate = nextMonthEndDate;
							   }
							   
							   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
							   int count = 1;
							   for(int i=0;i<=monthdiff;i++){
								   Date startDate = empMonthStartDate;
								   Date endDate = CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
								   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
								   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
							   }
;
				         }
					    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){
					    	
					    	 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							 cal.setLenient(false);
							 cal.setTime(commonUtil.currentDate());
							 Date previousMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),-(cal.get(Calendar.MONTH)+1)).get("STARTDATE");
							 Date nextMonthEndDate =  CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),12-(cal.get(Calendar.MONTH)+1)).get("ENDDATE");
							  /**
							  *calculating previous month startDate,15thDate
							  * 
							  * Cheking whether employee startdate is after 15th(Mid Of the Month) date or not
						      *
						     */
							  Date empMonthStartDate = null;
							  Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
							  if(empStartDate.after(empMonth15thDate)){
								  empMonthStartDate = CalendarUtil.getMonth16thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
							  }
							  else{
								  empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
							  }
							  
							  /**
							  *calculating Next month endDate 
							  *
							  * Cheking whether employee enddate is before 15th(Mid Of the Month) date or not
						      *
							  */ 
							  Date empMonthEndDate = null;
							  if(empEndDate != null){
								  if(empEndDate.before(empMonth15thDate)){
									  empMonthEndDate = empMonth15thDate;
								  }
								  else{
									  empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
								  }
								 
							  }
								
							  /**
							   * checking whether previousmoth start date is greaterthan employee month startdate or not
						       */
							   if(empMonthStartDate.before(previousMonthStartDate)){
								   empMonthStartDate = previousMonthStartDate;
							   }
							   
							  /**
							   * checking whether nextmonth end date is lesserthan employee enddate or not
						       */
							   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate))){
								   empMonthEndDate = nextMonthEndDate;
							   }
							  
							   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
							   for(int i=0;i<=monthdiff;i++){
								   Date startDate = empMonthStartDate;
								   Date endDate = CalendarUtil.getMonth15thDateByGivenDate(startDate, 0).get("STARTDATE");
								   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
								   if(empMonthEndDate.after(endDate)){
									   startDate =  CalendarUtil.getMonth16thDateByGivenDate(startDate, 0).get("STARTDATE");
									   endDate   =  CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
									   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
								   }
								   else{
									   break;
								   }
							   }
					    
					    }
					    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
					    	/**
							  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
							  *
						      *
						    */
					    	Date biweeklyCurrentStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projects,commonUtil.currentDate());
					    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setLenient(false);
							cal.setTime(biweeklyCurrentStartMonDayDate);
							Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
							Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
							
							
							
							Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-(3*14));
							Date previousMondayDate = previousMonday.getTime();
							
							Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,(3*14));
							
							Date nextSundayDate = nextSunday.getTime();
														
							
							Date empBiweeklyStartDate = getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(projects, empStartDate);
							
							Date employeeBiweeklyEndDate = null;
							if(empEndDate != null)
							employeeBiweeklyEndDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(projects, empEndDate);
							
							
							
						    /**
						     * checking whether prior 6th week monday date is greaterthan employee startdate or not
					         */
						     if(empBiweeklyStartDate.before(previousMondayDate)){
						    	 empBiweeklyStartDate = previousMondayDate;
						     }
							   
							 /**
							   * checking whether post 6th week sunday date is lesserthan employee enddate or not
						      */
							  if(employeeBiweeklyEndDate == null || (employeeBiweeklyEndDate != null && employeeBiweeklyEndDate.after(nextSundayDate))){
								  employeeBiweeklyEndDate = nextSundayDate;
							  }
							
					    
							    Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
								 startCal.setLenient(false);
								 startCal.setTime(empBiweeklyStartDate);
								 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
								 
								 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
								 endCal.setLenient(false);
								 endCal.setTime(employeeBiweeklyEndDate);
								 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
								  int totalWeeks = 0; 
								  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
								  tsCal.setLenient(false);
								  
								  Date tsStartDate = null;
								  Date tsEndDate = null;
									
								  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
								  for (int week = 0; week <(totalWeeks/2); week++) {
									  if(tsStartDate == null){
											tsCal.setTime(empStartMonday.getTime());
											tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
											tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
										}
										else{
											tsCal.setTime(tsStartDate);
										}
									    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
									    
									    tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 14).getTime();
									    Calendar tsEndCal = Calendar.getInstance(TimeZone.getDefault());
									    tsEndCal.setLenient(false);
									    tsEndCal.setTime(tsStartDate);
										tsEndDate = CalendarUtil.getDateIncrementByincrement(tsEndCal, 13).getTime();
									    
								  }
								  if(totalWeeks == 0){
									  tsCal.setTime(empStartMonday.getTime());
									  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
									  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
									  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
								  }
					    
					    
					    
					    }
					    /**
					     * Default Invoice Frequency is weekly
					     */
					    else{
					    	
					    	/**
							  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
							  *
						      *
						    */
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setLenient(false);
							cal.setTime(commonUtil.currentDate());
							Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
							Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
							Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
							Date previousMondayDate = previousMonday.getTime();
							
							Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
							Date nextSundayDate = nextSunday.getTime();
						    /**
						     * checking whether prior 6th week monday date is greaterthan employee startdate or not
					         */
						     if(empStartDate.before(previousMondayDate)){
						    	 empStartDate = previousMondayDate;
						     }
							   
							 /**
							   * checking whether post 6th week sunday date is lesserthan employee enddate or not
						      */
							  if(empEndDate == null || (empEndDate != null && empEndDate.after(nextSundayDate))){
								  empEndDate = nextSundayDate;
							  }
							
							 Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
							 startCal.setLenient(false);
							 startCal.setTime(empStartDate);
							 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
							 
							 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
							 endCal.setLenient(false);
							 endCal.setTime(empEndDate);
							 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
								
							  int totalWeeks = 0; 
							  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
							  tsCal.setLenient(false);
							  
							  Date tsStartDate = null;
							  Date tsEndDate = null;
							  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
							  for (int week = 0; week < totalWeeks; week++) {
								  
								    if(tsStartDate == null){
										tsCal.setTime(empStartMonday.getTime());
										tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
									}
									else{
										tsCal.setTime(tsStartDate);
									}
								    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
								    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
									tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
							  }
							  
							  if(totalWeeks == 0){
								  tsCal.setTime(empStartMonday.getTime());
								  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
								  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
								  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter( orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter( orgLocations.getDateFormat(),tsEndDate));
							  }
					    }
					   
				     }
				   catch (Exception e) {
						e.printStackTrace();
					} 
			
			}
		return timesheetFrequencyList;
	}
	
	
	
	public Map<String,String> getEmployeeTimesheetFrequencyListFromInvoiceFrequency(String timesheetFrequency,OrgLocations orgLocations) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		if(StringUtils.isNotEmpty(timesheetFrequency)){
				 try {
					    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Monthly")){
					    	 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							 cal.setLenient(false);
							 cal.setTime(CommonUtil.currentDate());
							 Date empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),-1).get("STARTDATE");
							 Date empMonthEndDate =  CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),1).get("ENDDATE");
						     int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
						     int count = 1;
						     for(int i=0;i<=monthdiff;i++){
							   Date startDate = empMonthStartDate;
							   Date endDate = CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
							   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
							   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
							   }
				         }
					      else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){
					    	 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							 cal.setLenient(false);
							 cal.setTime(CommonUtil.currentDate());
							 Date empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),-1).get("STARTDATE");
							 Date empMonthEndDate =  CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),1).get("ENDDATE");
							  
						    int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
						    for(int i=0;i<=monthdiff;i++){
							   Date startDate = empMonthStartDate;
							   Date endDate = CalendarUtil.getMonth15thDateByGivenDate(startDate, 0).get("STARTDATE");
							   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
							   if(empMonthEndDate.after(endDate)){
								   startDate =  CalendarUtil.getMonth16thDateByGivenDate(startDate, 0).get("STARTDATE");
								   endDate   =  CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
								   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
								   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
							   }
							   else{
								   break;
							   }
						   }
					    
					    }
					    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
					    	
					    	 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							 cal.setLenient(false);
							 cal.setTime(CommonUtil.currentDate());
							 Date startDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),-1).get("STARTDATE");
							 Date endDate =  CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),1).get("ENDDATE");
							 
							 int dayOfTheDate = CalendarUtil.getDayOfTheDate(startDate);
			    			 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    			 startCal.setLenient(false);
			    			 startCal.setTime(startDate);
			    			 Calendar startMonday =null;
			    			 if(dayOfTheDate == 1){
			    				 startMonday = CalendarUtil.getPreviousWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
			    			  }
			    			  else{
			    				  startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
			    			  }
			    			 Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    			 endCal.setLenient(false);
			    			 endCal.setTime(endDate);
			    			 Calendar endDay = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
			    			 
			    			 Date tsStartDate = null;
		    				  Date tsEndDate = null;
		    				  Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		    				  tsCal.setLenient(false);
		    				  
		    				  int totalWeeks = (int)((endDay.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
		    				  for (int week = 0; week <(totalWeeks); week++) {
		    					  if(tsStartDate == null){
		    							tsCal.setTime(startMonday.getTime());
		    							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
		    							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
		    						}
		    						else{
		    							tsCal.setTime(tsStartDate);
		    						}
		    					    if(tsStartDate == null){
		    							tsCal.setTime(startMonday.getTime());
		    							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
		    							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
		    						}
		    						else{
		    							tsCal.setTime(tsStartDate);
		    							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
		    						}
		    					    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
		    					    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
		    				  }
					    }
					    else{
					    	
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setLenient(false);
							cal.setTime(CommonUtil.currentDate());
							Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
							Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
							Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
							Date empStartDate = previousMonday.getTime();
							
							Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
							Date empEndDate = nextSunday.getTime();
						    
							
							 Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
							 startCal.setLenient(false);
							 startCal.setTime(empStartDate);
							 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
							 
							 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
							 endCal.setLenient(false);
							 endCal.setTime(empEndDate);
							 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
								
							  int totalWeeks = 0; 
							  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
							  tsCal.setLenient(false);
							  
							  Date tsStartDate = null;
							  Date tsEndDate = null;
							  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
							  for (int week = 0; week < totalWeeks; week++) {
								  
								    if(tsStartDate == null){
										tsCal.setTime(empStartMonday.getTime());
										tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
									}
									else{
										tsCal.setTime(tsStartDate);
									}
								    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
								    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
									tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
							  }
							  
							  if(totalWeeks == 0){
								  tsCal.setTime(empStartMonday.getTime());
								  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
								  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
								  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
							  }
					    }
					   
				     }
				   catch (Exception e) {
						e.printStackTrace();
					} 
			
			}
		return timesheetFrequencyList;
	}
	
	public Map<String,String> getTimesheetFrequencyListFromProjectInvoiceFrequency(ProjectMembers projectMember,OrgLocations orgLocations,String frequencyFor) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		if(projectMember != null && projectMember.getProjects() != null){
			String timesheetFrequency = projectMember.getProjects().getInvoiceFrequency();
			if(StringUtils.isNotEmpty(timesheetFrequency)){
				HashMap<String,Date> dateList = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(projectMember.getId(),frequencyFor);
				if(dateList != null  && !dateList.isEmpty()){
					Date empStartDate = dateList.get("STARTDATE");
					Date empEndDate = dateList.get("ENDDATE");
					//DateFormat format = Constants.DF_STD_DMY;
					//DateFormat  format = LoginSessionController.DATEFORMAT;
					 try {
						    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Monthly")){
						   
						        /**
								  *calculating previous month startDate and next month endDate based on currentDate
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								 cal.setLenient(false);
								 cal.setTime(commonUtil.currentDate());
								 Date previousMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("STARTDATE");
								 
								 Date nextMonthEndDate = null;
								 if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("employeeScheduler")){
									 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.CURRENT_MONTH).get("ENDDATE");
								 }
								 else if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("approverScheduler")){
									 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("ENDDATE");
							   	 }
								 else{
									 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
								 }
								
								 /**
								  *calculating previous month startDate and next month endDate based on empStartDate and empEndDate
								  *
							      *
							     */
								  Date empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  Date empMonthEndDate = null;
								  if(empEndDate != null)
									 empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
								
								  /**
								   * checking whether previousmoth start date is greaterthan employee month startdate or not
							       */
								   if(empMonthStartDate.before(previousMonthStartDate)){
									   empMonthStartDate = previousMonthStartDate;
								   }
								   
								  /**
								   * checking whether nextmonth end date is lesserthan employee enddate or not
							       */
								   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate))){
									   empMonthEndDate = nextMonthEndDate;
								   }
								   
								   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
								   int count = 1;
								   for(int i=0;i<=monthdiff;i++){
									   Date startDate = empMonthStartDate;
									   Date endDate = CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
									   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
								   };
					         }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){

						        /**
								  *calculating previous month startDate and next month endDate based on currentDate
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								 cal.setLenient(false);
								 cal.setTime(CommonUtil.currentDate());
								 Date previousMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("STARTDATE");
								 Date nextMonthEndDate = null;
								 if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("employeeScheduler")){
									 
									 Date currentMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
									 if(currentMonth15thDate.after(CommonUtil.currentDate())){
										 nextMonthEndDate = currentMonth15thDate;
									 }
									 else{
										 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.CURRENT_MONTH).get("ENDDATE");
									 }
								 }
								 else if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("approverScheduler")){
									 Date currentMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
									 if(currentMonth15thDate.after(CommonUtil.currentDate())){
										 nextMonthEndDate = currentMonth15thDate;
									 }
									 else{
										 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("ENDDATE");
									 }
							   	 }
								 else{
									 nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
								 }
								 
								  /**
								  *calculating previous month startDate,15thDate
								  * 
								  * Cheking whether employee startdate is after 15th(Mid Of the Month) date or not
							      *
							     */
								  Date empMonthStartDate = null;
								  Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  if(empStartDate.after(empMonth15thDate)){
									  empMonthStartDate = CalendarUtil.getMonth16thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  }
								  else{
									  empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  }
								  
								  /**
								  *calculating Next month endDate 
								  *
								  * Cheking whether employee enddate is before 15th(Mid Of the Month) date or not
							      *
								  */ 
								  Date empMonthEndDate = null;
								  if(empEndDate != null){
									  if(empEndDate.before(empMonth15thDate)){
										  empMonthEndDate = empMonth15thDate;
									  }
									  else{
										  empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
									  }
									 
								  }
									
								  /**
								   * checking whether previousmoth start date is greaterthan employee month startdate or not
							       */
								   if(empMonthStartDate.after(previousMonthStartDate)){
									   empMonthStartDate = previousMonthStartDate;
								   }
								   
								  /**
								   * checking whether nextmonth end date is lesserthan employee enddate or not
							       */
								   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate))){
									   empMonthEndDate = nextMonthEndDate;
								   }
								  
								   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
								   for(int i=0;i<=monthdiff;i++){
									   Date startDate = empMonthStartDate;
									   Date endDate = CalendarUtil.getMonth15thDateByGivenDate(startDate, 0).get("STARTDATE");
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
									   if(empMonthEndDate.after(endDate)){
										   startDate =  CalendarUtil.getMonth16thDateByGivenDate(startDate, 0).get("STARTDATE");
										   endDate   =  CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
										   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate));
										   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
									   }
									   else{
										   break;
									   }
								   }
						    
						    }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
						    	/**
								  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
								  *
							      *
							    */
						    	Date biweeklyCurrentStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projectMember.getProjects(),commonUtil.currentDate());
						    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(biweeklyCurrentStartMonDayDate);
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
								
								
								
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-(3*14));
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = null;
								if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("employeeScheduler")){
									 nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,0);
								}
								else if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("approverScheduler")){
									nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,-7);
								}
								else{
								       nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,(3*14));
								}
						
								Date nextSundayDate = nextSunday.getTime();
															
								
								Date empBiweeklyStartDate = getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(projectMember.getProjects(), empStartDate);
								
								Date employeeBiweeklyEndDate = null;
								if(empEndDate != null)
								employeeBiweeklyEndDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(projectMember.getProjects(), empEndDate);
								
								
								
							    /**
							     * checking whether prior 6th week monday date is greaterthan employee startdate or not
						         */
							     if(empBiweeklyStartDate.after(previousMondayDate)){
							    	 empBiweeklyStartDate = previousMondayDate;
							     }
								   
								 /**
								   * checking whether post 6th week sunday date is lesserthan employee enddate or not
							      */
								  if(employeeBiweeklyEndDate == null || (employeeBiweeklyEndDate != null && employeeBiweeklyEndDate.after(nextSundayDate))){
									  employeeBiweeklyEndDate = nextSundayDate;
								  }
								
						    
								    Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
									 startCal.setLenient(false);
									 startCal.setTime(empBiweeklyStartDate);
									 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
									 
									 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
									 endCal.setLenient(false);
									 endCal.setTime(employeeBiweeklyEndDate);
									 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
									  int totalWeeks = 0; 
									  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
									  tsCal.setLenient(false);
									  
									  Date tsStartDate = null;
									  Date tsEndDate = null;
										
									  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
									  for (int week = 0; week <(totalWeeks/2); week++) {
										  if(tsStartDate == null){
												tsCal.setTime(empStartMonday.getTime());
												tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
												tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
											}
											else{
												tsCal.setTime(tsStartDate);
											}
										    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
										    
										    tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 14).getTime();
										    Calendar tsEndCal = Calendar.getInstance(TimeZone.getDefault());
										    tsEndCal.setLenient(false);
										    tsEndCal.setTime(tsStartDate);
											tsEndDate = CalendarUtil.getDateIncrementByincrement(tsEndCal, 13).getTime();
										    
									  }
									  if(totalWeeks == 0){
										  tsCal.setTime(empStartMonday.getTime());
										  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
										  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
									  }
						    
						    
						    
						    }
						    /**
						     * Default Invoice Frequency is weekly
						     */
						    else{
						    	
						    	/**
								  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
								  *
							      *
							    */
								Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(commonUtil.currentDate());
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = null;
								if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("employeeScheduler")){
									nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,0);
								}
								else if(StringUtils.isNotEmpty(frequencyFor) && frequencyFor.equalsIgnoreCase("approverScheduler")){
									nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,-7);
								}
								else{
									nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
								}
								Date nextSundayDate = nextSunday.getTime();
								
								
								/*Calendar startcal = Calendar.getInstance(TimeZone.getDefault());
								startcal.setLenient(false);
								startcal.setTime(empStartDate);
								@SuppressWarnings("unused")
								Date employeeStartDate = CalendarUtil.getCurrentWeekDayByCalendar(startcal, "MON").getTime();
								Date employeeEndDate = null;
								if(empEndDate != null){
									Calendar endcal = Calendar.getInstance(TimeZone.getDefault());
									endcal.setLenient(false);
									endcal.setTime(empEndDate);
									employeeEndDate = CalendarUtil.getCurrentWeekDayByCalendar(endcal, "SUN").getTime();
								}*/
								
								
							    /**
							     * checking whether prior 6th week monday date is greaterthan employee startdate or not
						         */
							     if(empStartDate.before(previousMondayDate) && (empEndDate == null || (empEndDate != null && empEndDate.after(previousMondayDate)))){
							    	 empStartDate = previousMondayDate;
							     }
								  
							     if(nextSundayDate.before(empStartDate)) {
							    	 Calendar startcal = Calendar.getInstance(TimeZone.getDefault());
										startcal.setLenient(false);
										startcal.setTime(empStartDate);
										nextSundayDate = CalendarUtil.getDateIncrementByincrement(startcal,42).getTime();
							     }
								 /**
								   * checking whether post 6th week sunday date is lesserthan employee enddate or not
							      */
								  if(empEndDate == null || (empEndDate != null && empEndDate.after(nextSundayDate))){
									  empEndDate = nextSundayDate;
								  }
								
								 Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
								 startCal.setLenient(false);
								 startCal.setTime(empStartDate);
								 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
								 
								 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
								 endCal.setLenient(false);
								 endCal.setTime(empEndDate);
								 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
									
								  int totalWeeks = 0; 
								  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
								  tsCal.setLenient(false);
								  
								  Date tsStartDate = null;
								  Date tsEndDate = null;
								  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
								  for (int week = 0; week < totalWeeks; week++) {
									  
									    if(tsStartDate == null){
											tsCal.setTime(empStartMonday.getTime());
											tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
											tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
										}
										else{
											tsCal.setTime(tsStartDate);
										}
									    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
									    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
								  }
								  
								  if(totalWeeks == 0){
									  tsCal.setTime(empStartMonday.getTime());
									  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
									  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
									  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
								  }
						    }
						   
					     }
					   catch (Exception e) {
							e.printStackTrace();
						} 
				}
				
			}
		}
		return timesheetFrequencyList;
		
	}
	
	public Map<String,String> getTabFrequencyListFromProjectInvoiceFrequency(String startDate,String endDate,String dateFormat) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
		 	    //DateFormat format = Constants.DF_DMY;
			//DateFormat  format = LoginSessionController.DATEFORMAT;
		        try {
		            Date stDate = commonUtil.orgLocationDateFormat(dateFormat,startDate);
		            Date enDate = commonUtil.orgLocationDateFormat(dateFormat,endDate);
		            
		             Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
					 startCal.setLenient(false);
					 startCal.setTime(stDate);
					 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",dateFormat);
								
					  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
					  tsCal.setLenient(false);
					  tsCal.setTime(stDate);
					  
					  Date tsStartDate = null;
					  Date tsEndDate = null;
					  int dayOfWeek = CalendarUtil.getDayOfTheDate(stDate);
					  if(dayOfWeek > 1){
						  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(dateFormat,stDate), commonUtil.dateStrFromOrgDateFormatter(dateFormat,CalendarUtil.getDateIncrementByincrement(tsCal, 8-dayOfWeek).getTime())); 
						  tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, (8-dayOfWeek)).getTime();
						  tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, (8-dayOfWeek)+1).getTime();
					  }
					  else if(dayOfWeek == 1){
						  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(dateFormat,stDate), commonUtil.dateStrFromOrgDateFormatter(dateFormat,stDate)); 
						  tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 1).getTime();
					  }
					  int totalWeeks = CalendarUtil.getNumberOfWeeks(tsStartDate, enDate);
					  for (int week = 0; week < totalWeeks; week++) {
						  
						    if(tsStartDate == null){
								tsCal.setTime(empStartMonday.getTime());
								tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",dateFormat).getTime();
								tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",dateFormat).getTime();
							}
							else{
								tsCal.setTime(tsStartDate);
								tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",dateFormat).getTime();
							}
						    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(dateFormat,tsStartDate), commonUtil.dateStrFromOrgDateFormatter(dateFormat,tsEndDate));
						    if(week != totalWeeks-1){
						    	 tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",dateFormat).getTime();
								 tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",dateFormat).getTime();
						    }
						   
					  }
					  
					  if(tsEndDate != null){
						  Long numberOfDays = CalendarUtil.getNumberOfDays(tsEndDate,enDate);
						  if(numberOfDays>1){
							  timesheetFrequencyList.put(CalendarUtil.getPreviousDateByDecrementor(endDate, numberOfDays.intValue()-1,dateFormat), commonUtil.dateStrFromOrgDateFormatter(dateFormat,enDate)); 
						  }
						  else if(numberOfDays == 1){
							  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(dateFormat,enDate), commonUtil.dateStrFromOrgDateFormatter(dateFormat,enDate)); 
						  }
					  }

		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		}
		return timesheetFrequencyList;
		
	}
	public Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(Projects projects,Date date) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(date);
		Calendar currentMonday = null;
		SimpleDateFormat sf = new SimpleDateFormat("E"); 
		if(sf.format(date).equalsIgnoreCase("Mon")) {
			currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		}else {
			currentMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		}
		 
		if(projectStartMonday.before(currentMonday)){
			while(projectStartMonday.before(currentMonday)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyStartMondayDate = projectStartMonday.getTime();
		}
		else{
			biWeeklyStartMondayDate = projectStartMonday.getTime();
		}
		
		return biWeeklyStartMondayDate;
	}
	
	public Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDateForTsImports(Projects projects,Date date) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(date);
		Calendar currentMonday =null;
		SimpleDateFormat sf = new SimpleDateFormat("E"); 
		if(sf.format(date).equalsIgnoreCase("SUN")) {
			 currentMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		}else {
			 currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		}
		if(projectStartMonday.before(currentMonday) || projectStartMonday.equals(currentMonday)){
			while(projectStartMonday.before(currentMonday) || projectStartMonday.equals(currentMonday)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyStartMondayDate = CalendarUtil.getDateIncrementByincrement(projectStartMonday,-14).getTime();
		}
		else{
			biWeeklyStartMondayDate = projectStartMonday.getTime();
		}
		
		return biWeeklyStartMondayDate;
	}
	

	private Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(Projects projects,Date employeeStartDate) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(employeeStartDate);
		Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		if(empStartMonday.before(projectStartMonday) || empStartMonday.equals(projectStartMonday)){
			while(projectStartMonday.before(empStartMonday)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyStartMondayDate = projectStartMonday.getTime();
		}
		
		return biWeeklyStartMondayDate;
	}
	

	
	private Date getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(Projects projects,Date employeeEndDate) throws Exception{
		Date biWeeklyEndSundayDate = null;
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(employeeEndDate);
		Calendar empEndSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
		if(projectStartMonday.before(empEndSunday)){
			while(projectStartMonday.before(empEndSunday)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyEndSundayDate = CalendarUtil.getDateIncrementByincrement(projectStartMonday, 13).getTime();
		}
		
		return biWeeklyEndSundayDate;
	}
	 
	
	
	public Map<String,String> getTimesheetFrequencyListFromProjectInvoiceFrequencyWithStartAndEndDate(ProjectMembers projectMember,OrgLocations orgLocations,Date startDate,Date endDate) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		if(projectMember != null && projectMember.getProjects() != null){
			String timesheetFrequency = projectMember.getProjects().getInvoiceFrequency();
			if(StringUtils.isNotEmpty(timesheetFrequency)){
				HashMap<String,Date> dateList = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(projectMember.getId(),"");
				if(dateList != null  && !dateList.isEmpty()){
					Date empStartDate = dateList.get("STARTDATE");
					Date empEndDate = dateList.get("ENDDATE");
					 try {
						    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Monthly")){
						   
						        /**
								  *calculating  month startDate from Given Date
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								 cal.setLenient(false);
								 cal.setTime(startDate);
								 Date frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.CURRENT_MONTH).get("STARTDATE");
								 
								 
								 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
								 endCal.setLenient(false);
								 endCal.setTime(endDate);
								 Date frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(endCal.getTime(),Constants.CURRENT_MONTH).get("ENDDATE");
								 
								
								 /**
								  *calculating previous month startDate and next month endDate based on empStartDate and empEndDate
								  *
							      *
							     */
								  Date empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  Date empMonthEndDate = null;
								  if(empEndDate != null)
									 empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
								
								  /**
								   * checking whether frequency start date is greaterthan employee month startdate or not
							       */
								   if(empMonthStartDate.before(frequencyStartDate)){
									   empMonthStartDate = frequencyStartDate;
								   }
								   
								  /**
								   * checking whether frequency end date is lesserthan employee enddate or not
							       */
								   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(frequencyEndDate))){
									   empMonthEndDate = frequencyEndDate;
								   }
								   
								   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
								   int count = 1;
								   for(int i=0;i<=monthdiff;i++){
									   Date stDate = empMonthStartDate;
									   Date enDate = CalendarUtil.getMonthEndDateByGivenDate(stDate, 0).get("ENDDATE");
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),stDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),enDate));
									   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(stDate, 1).get("STARTDATE");
								   }
;
					         }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){

						    	  /**
								  *calculating  month startDate from Given Date
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								 cal.setLenient(false);
								 cal.setTime(startDate);
								 Date frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.CURRENT_MONTH).get("STARTDATE");
								 
								 Calendar endcal = Calendar.getInstance(TimeZone.getDefault());
								 endcal.setLenient(false);
								 endcal.setTime(endDate);
								 Date frequencyEnd15thDate = CalendarUtil.getMonth15thDateByGivenDate(endcal.getTime(),Constants.CURRENT_MONTH).get("STARTDATE");
								 Date frequencyEndDate = null;
								 if(frequencyEnd15thDate.after(endDate)){
									 frequencyEndDate = frequencyEnd15thDate;
								 }else {
									 frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(endcal.getTime(),Constants.CURRENT_MONTH).get("ENDDATE");
								 }
								
								  /**
								  *calculating previous month startDate,15thDate
								  * 
								  * Cheking whether employee startdate is after 15th(Mid Of the Month) date or not
							      *
							     */
								  Date empMonthStartDate = null;
								  Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  if(empStartDate.after(empMonth15thDate)){
									  empMonthStartDate = CalendarUtil.getMonth16thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  }
								  else{
									  empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  }
								  
								  /**
								  *calculating Next month endDate 
								  *
								  * Cheking whether employee enddate is before 15th(Mid Of the Month) date or not
							      *
								  */ 
								  Date empMonthEndDate = null;
								  if(empEndDate != null){
									  if(empEndDate.before(empMonth15thDate)){
										  empMonthEndDate = empMonth15thDate;
									  }
									  else{
										  empMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
									  }
									 
								  }
									
								  /**
								   * checking whether frequency start date is greaterthan employee month startdate or not
							       */
								   if(empMonthStartDate.before(frequencyStartDate)){
									   empMonthStartDate = frequencyStartDate;
								   }
								   
								  /**
								   * checking whether frequency end date is lesserthan employee enddate or not
							       */
								   if(empMonthEndDate == null || (empMonthEndDate != null && empMonthEndDate.after(frequencyEndDate))){
									   empMonthEndDate = frequencyEndDate;
								   }
								  
									   int monthdiff = CalendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
									   for(int i=0;i<=monthdiff;i++){
										   Date stDate = empMonthStartDate;
										   Date enDate = CalendarUtil.getMonth15thDateByGivenDate(stDate, 0).get("STARTDATE");
										   if(CalendarUtil.isDateBetweenTwoDates(cal.getTime(), endcal.getTime(), enDate)) {
											   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),stDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),enDate)); 
										   }
										   if(empMonthEndDate.after(enDate)){
											   stDate =  CalendarUtil.getMonth16thDateByGivenDate(stDate, 0).get("STARTDATE");
											   enDate   =  CalendarUtil.getMonthEndDateByGivenDate(stDate, 0).get("ENDDATE");
											   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),stDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),enDate));
											   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(stDate, 1).get("STARTDATE");
										   }
										   else{
											   break;
										   }
									   }
								  
						    
						    }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
						    	/**
								  * caluclating Bi weekly Start MondayDate for given date
								  *
							      *
							    */
						    	Date biweeklyStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projectMember.getProjects(),startDate);
						    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(biweeklyStartMonDayDate);
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Date frequencyStartDate = startMonday.getTime();
								
								Date biweeklyCurrentEndSunDayDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(projectMember.getProjects(), endDate);
								Calendar endSunday = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(biweeklyCurrentEndSunDayDate);
								Date frequencyEndDate = endSunday.getTime();
								 
								
								Date empBiweeklyStartDate = getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(projectMember.getProjects(), empStartDate);
								Date employeeBiweeklyEndDate = null;
								if(empEndDate != null)
								employeeBiweeklyEndDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(projectMember.getProjects(), empEndDate);
								
								
							    /**
							     * checking frequency start date is greaterthan employee startdate or not
						         */
							     if(empBiweeklyStartDate.before(frequencyStartDate)){
							    	 empBiweeklyStartDate = frequencyStartDate;
							     }
								   
								 /**
								   * checking whether frequency end date is lesserthan employee enddate or not
							      */
								  if(employeeBiweeklyEndDate == null || (employeeBiweeklyEndDate != null && employeeBiweeklyEndDate.after(frequencyEndDate))){
									  employeeBiweeklyEndDate = frequencyEndDate;
								  }
								
						    
								    Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
									 startCal.setLenient(false);
									 startCal.setTime(empBiweeklyStartDate);
									 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
									 
									 Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
									 endCal.setLenient(false);
									 endCal.setTime(employeeBiweeklyEndDate);
									 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
									  int totalWeeks = 0; 
									  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
									  tsCal.setLenient(false);
									  
									  Date tsStartDate = null;
									  Date tsEndDate = null;
										
									  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
									  for (int week = 0; week <(totalWeeks/2); week++) {
										  if(tsStartDate == null){
												tsCal.setTime(empStartMonday.getTime());
												tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
												tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
											}
											else{
												tsCal.setTime(tsStartDate);
											}
										    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
										    
										    tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 14).getTime();
										    Calendar tsEndCal = Calendar.getInstance(TimeZone.getDefault());
										    tsEndCal.setLenient(false);
										    tsEndCal.setTime(tsStartDate);
											tsEndDate = CalendarUtil.getDateIncrementByincrement(tsEndCal, 13).getTime();
										    
									  }
									  if(totalWeeks == 0){
										  tsCal.setTime(empStartMonday.getTime());
										  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
										  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
									  }
						    
						    
						    
						    }
						    else{
						    	
						    	/**
								  *calculating frequencyStarDate and frequencyEndDate for employee
								  *
							      *
							    */
								Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(startDate);
								Calendar frequencyStartCal = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Date frequencyStartDate = frequencyStartCal.getTime();
								
								
								Calendar endDateCal = Calendar.getInstance(TimeZone.getDefault());
								endDateCal.setLenient(false);
								endDateCal.setTime(endDate);
								Calendar frequencyEndCal = CalendarUtil.getCurrentWeekDayByCalendar(endDateCal, "SUN",orgLocations.getDateFormat());
								Date frequencyEndDate = frequencyEndCal.getTime();
								
								
								
								
							    /**
							     * checking whether frequency start date is greaterthan employee startdate or not
						         */
							     if(empStartDate.before(frequencyStartDate)){
							    	 empStartDate = frequencyStartDate;
							     }
								   
								 /**
								   * checking whether frequency end  date is lesserthan employee enddate or not
							      */
								  if(empEndDate == null || (empEndDate != null && empEndDate.after(frequencyEndDate))){
									  empEndDate = frequencyEndDate;
								  }
								  SimpleDateFormat sf = new SimpleDateFormat("E"); 
								  Calendar empStartMonday = null;
								  if(!sf.format(empStartDate).equalsIgnoreCase("MON")) {
									  Calendar startCal = Calendar.getInstance(TimeZone.getDefault());
									  startCal.setLenient(false);
									  startCal.setTime(empStartDate);
									  empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
								  }else {
									  empStartMonday = frequencyStartCal;
								  }
								 
								  Calendar empEndMonday = null;
								  if(!sf.format(empEndDate).equalsIgnoreCase("SUN")) {
									      Calendar endCal = Calendar.getInstance(TimeZone.getDefault());
										 endCal.setLenient(false);
										 endCal.setTime(empEndDate);
										 empEndMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
								  }else {
									  empEndMonday = frequencyEndCal;
								  }
									
								  int totalWeeks = 0; 
								  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
								  tsCal.setLenient(false);
								  
								  Date tsStartDate = null;
								  Date tsEndDate = null;
								  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
								  for (int week = 0; week < totalWeeks; week++) {
									  
									    if(tsStartDate == null){
											tsCal.setTime(empStartMonday.getTime());
											tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
											tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
										}
										else{
											tsCal.setTime(tsStartDate);
										}
									    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
									    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
								  }
								  
								  if(totalWeeks == 0){
									  tsCal.setTime(empStartMonday.getTime());
									  tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
									  tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
									  timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
								  }
						    }
						   
					     }
					   catch (Exception e) {
							e.printStackTrace();
						} 
				}
				
			}
		}
		return timesheetFrequencyList;
		
	}
	/*public Map<String,Date> getBiWeeklyStartDateAndEndDateByGivenDate(Projects projects,Date date) throws Exception{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		Date biweeklyCurrentStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projects);
    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(biweeklyCurrentStartMonDayDate);
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		return null;
	}*/
	
}