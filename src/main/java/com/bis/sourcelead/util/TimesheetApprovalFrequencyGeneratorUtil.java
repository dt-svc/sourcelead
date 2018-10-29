package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.web.LoginSessionController;

@Component
public  class TimesheetApprovalFrequencyGeneratorUtil {
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	public LinkedHashSet<String> getMonthsListFromCurrentDate(int previousMonths,int priorMonths) throws Exception{
		LinkedHashSet<String> MonthsList = new LinkedHashSet<String>();
		
		for(int i =previousMonths;i>0;i--){
			String date = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),-i);
			MonthsList.add(date);
		}
		String currentDate = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),0);
		MonthsList.add(currentDate);
		for(int i =1;i<=priorMonths;i++){
			String date = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),i);
			MonthsList.add(date);
		}
		return MonthsList;
	}
	
	
	public Map<String,String> getTimesheetFrequencyListFromProjectInvoiceFrequency(Projects project) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		
		if(project != null){
			OrgLocations orgLocations = orgLocationService.getOrgLocations(project.getOrgLocationsId().getId());
			String timesheetFrequency = project.getInvoiceFrequency();
			if(StringUtils.isNotEmpty(timesheetFrequency)){
				/*HashMap<String,Date> dateList = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(projectMember.getId());*/
				/*if(dateList != null  && !dateList.isEmpty()){*/
					/*Date empStartDate = dateList.get("STARTDATE");
					Date empEndDate = dateList.get("ENDDATE");*/
				
					Date empStartDate = project.getStartDate();
					Date empEndDate = project.getEndDate();
					//DateFormat format = Constants.DF_STD_DMY;
					//java.text.SimpleDateFormat format = project.getOrgLocationsId().getLocFullFormDateFormatter();
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
								 Date nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
								
								
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
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),endDate));
									   empMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(startDate, 1).get("STARTDATE");
								   }
;
					         }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){

						        /**
								  *calculating previous month startDate and next month endDate based on currentDate
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								 cal.setLenient(false);
								 cal.setTime(commonUtil.currentDate());
								 Date previousMonthStartDate = CalendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("STARTDATE");
								 Date nextMonthEndDate = CalendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
						   					    
								
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
									   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),endDate));
									   if(empMonthEndDate.after(endDate)){
										   startDate =  CalendarUtil.getMonth16thDateByGivenDate(startDate, 0).get("STARTDATE");
										   endDate   =  CalendarUtil.getMonthEndDateByGivenDate(startDate, 0).get("ENDDATE");
										   timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),endDate));
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
						    	Date biweeklyCurrentStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(project,CommonUtil.currentDate());
						    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								cal.setLenient(false);
								cal.setTime(biweeklyCurrentStartMonDayDate);
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
								
								
								
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-(3*14));
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,(3*14));
								Date nextSundayDate = nextSunday.getTime();
															
								
								Date empBiweeklyStartDate = getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(project, empStartDate);
								
								Date employeeBiweeklyEndDate = null;
								if(empEndDate != null)
								employeeBiweeklyEndDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(project, empEndDate);
								
								
								
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
										    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter( project.getOrgLocationsId().getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter( project.getOrgLocationsId().getDateFormat(),tsEndDate));
										    
										    tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 14).getTime();
										    Calendar tsEndCal = Calendar.getInstance(TimeZone.getDefault());
										    tsEndCal.setLenient(false);
										    tsEndCal.setTime(tsStartDate);
											tsEndDate = CalendarUtil.getDateIncrementByincrement(tsEndCal, 13).getTime();
										    
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
								cal.setTime(CommonUtil.currentDate());
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
								Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
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
									    timesheetFrequencyList.put(commonUtil.dateStrFromOrgDateFormatter( project.getOrgLocationsId().getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter( project.getOrgLocationsId().getDateFormat(),tsEndDate));
									    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
										tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN",orgLocations.getDateFormat()).getTime();
								  }
						    	
						    	
						    }
						   
					     }
					   catch (Exception e) {
							e.printStackTrace();
						} 
				/*}*/
				
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
	
	
	/*public Map<String,String> getTabFrequencyListForTsReports(Date startDate,Date endDate) throws Exception{
		Map<String,String> timesheetFrequencyList = new LinkedHashMap<String,String>();
		 	    //DateFormat format = Constants.DF_DMY;
		              DateFormat  format = LoginSessionController.DATEFORMAT;
					  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
					  tsCal.setTime(startDate);
					  
					  int totalWeeks = CalendarUtil.getNumberOfWeeks(startDate, endDate);
					  Date tsStartDate = null;
					  Date tsEndDate = null;
					  for (int week = 0; week < totalWeeks; week++) {
						  
						    if(tsStartDate == null){
								tsCal.setTime(startDate);
								tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
								tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN").getTime();
							}
							else{
								tsCal.setTime(tsStartDate);
							}
						    timesheetFrequencyList.put(format.format(tsStartDate), format.format(tsEndDate));
						    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN").getTime();
					  }
					 

		return timesheetFrequencyList;
		
	}*/
	public Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(Projects projects,Date date) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(TimeZone.getDefault());
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(projCal, "MON",orgLocations.getDateFormat());
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(date);
		Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "MON",orgLocations.getDateFormat());
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
	
	
	

	public Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(Projects projects,Date employeeStartDate) throws Exception{
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
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON",orgLocations.getDateFormat());
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setLenient(false);
		cal.setTime(employeeEndDate);
		Calendar empEndSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",orgLocations.getDateFormat());
		if(projectStartMonday.before(empEndSunday)){
			while(projectStartMonday.before(employeeEndDate)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyEndSundayDate = CalendarUtil.getDateIncrementByincrement(projectStartMonday, 13).getTime();
		}
		
		return biWeeklyEndSundayDate;
	}
	 
	
}