package com.bis.sourcelead.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectMembersHistoryService;

@Component
public  class TimesheetFrequencyGeneratorUtil {
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
    CalendarUtil calendarUtil;
	
	public HashMap<String,String> getTimesheetFrequencyListFromProjectInvoiceFrequency(ProjectMembers projectMember) throws Exception{
		HashMap<String,String> timesheetFrequencyList = new HashMap<String,String>();
		if(projectMember != null && projectMember.getProjects() != null){
			String timesheetFrequency = projectMember.getProjects().getTimesheetFrequency();
			if(StringUtils.isNotEmpty(timesheetFrequency)){
				HashMap<String,Date> dateList = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(projectMember.getId());
				if(dateList != null  && !dateList.isEmpty()){
					Date empStartDate = dateList.get("STARTDATE");
					Date empEndDate = dateList.get("ENDDATE");
					 String pattern = "dd-MM-yyyy";
			         SimpleDateFormat format = new SimpleDateFormat(pattern);
					 try {
						    if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Monthly")){
						   
						        /**
								  *calculating previous month startDate and next month endDate based on currentDate
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								 cal.setLenient(false);
								 cal.setTime(commonUtil.currentDate());
								 Date previousMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("STARTDATE");
								 Date nextMonthEndDate = calendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
								
								
								 /**
								  *calculating previous month startDate and next month endDate based on empStartDate and empEndDate
								  *
							      *
							     */
								  Date empMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  Date empMonthEndDate = null;
								  if(empEndDate != null)
									 empMonthEndDate = calendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
								
								  /**
								   * checking whether previousmoth start date is greaterthan employee month startdate or not
							       */
								   if(empMonthStartDate.before(previousMonthStartDate)){
									   empMonthStartDate = previousMonthStartDate;
								   }
								   
								  /**
								   * checking whether nextmonth end date is lesserthan employee enddate or not
							       */
								   if(empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate)){
									   empMonthEndDate = nextMonthEndDate;
								   }
								   
								   int monthdiff = calendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
								   for(int i=0;i<=monthdiff;i++){
									   Date startDate = empMonthStartDate;
									   Date endDate = calendarUtil.getMonthEndDateByGivenDate(startDate, i).get("ENDDATE");
									   timesheetFrequencyList.put(format.format(startDate), format.format(endDate));
									   empMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(startDate, i+1).get("ENDDATE");
								   }
					         }
						    else if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Semi-Monthly")){

						        /**
								  *calculating previous month startDate and next month endDate based on currentDate
								  *
							      *
							    */
								 Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								 cal.setLenient(false);
								 cal.setTime(commonUtil.currentDate());
								 Date previousMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(cal.getTime(),Constants.PREVIOUS_MONTH).get("STARTDATE");
								 Date nextMonthEndDate = calendarUtil.getMonthEndDateByGivenDate(cal.getTime(),Constants.NEXT_MONTH).get("ENDDATE");
						   					    
								 
								  /**
								  *calculating previous month startDate,15thDate
								  * 
								  * Cheking whether employee startdate is after 15th(Mid Of the Month) date or not
							      *
							     */
								  Date empMonthStartDate = null;
								  Date empMonth15thDate = calendarUtil.getMonth15thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  if(empStartDate.after(empMonth15thDate)){
									  empMonthStartDate = calendarUtil.getMonth16thDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
								  }
								  else{
									  empMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(empStartDate,Constants.CURRENT_MONTH).get("STARTDATE");
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
										  empMonthEndDate = calendarUtil.getMonthEndDateByGivenDate(empEndDate,Constants.CURRENT_MONTH).get("ENDDATE");
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
								   if(empMonthEndDate != null && empMonthEndDate.after(nextMonthEndDate)){
									   empMonthEndDate = nextMonthEndDate;
								   }
								  
								   int monthdiff = calendarUtil.monthsBetween(empMonthStartDate, empMonthEndDate);
								   for(int i=0;i<=monthdiff;i++){
									   Date startDate = empMonthStartDate;
									   Date endDate = calendarUtil.getMonth15thDateByGivenDate(startDate, i).get("STARTDATE");
									   timesheetFrequencyList.put(format.format(startDate), format.format(endDate));
									   if(empMonthEndDate.after(endDate)){
										   startDate =  calendarUtil.getMonth16thDateByGivenDate(startDate, i).get("STARTDATE");
										   endDate   =  calendarUtil.getMonthEndDateByGivenDate(startDate, i).get("ENDDATE");
										   timesheetFrequencyList.put(format.format(startDate), format.format(endDate));
										   empMonthStartDate = calendarUtil.getMonthStartDateByGivenDate(startDate, i+1).get("ENDDATE");
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
						    	Date biweeklyCurrentStartMonDayDate =  getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projectMember.getProjects());
						    	Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								cal.setLenient(false);
								cal.setTime(biweeklyCurrentStartMonDayDate);
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
								Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
								
								
								
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-(3*14));
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,(3*14));
								Date nextSundayDate = nextSunday.getTime();
															
								
								Date empBiweeklyStartDate = getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(projectMember.getProjects(), empStartDate);
								
								Date employeeBiweeklyEndDate = getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(projectMember.getProjects(), empEndDate);
								
								
								
							    /**
							     * checking whether prior 6th week monday date is greaterthan employee startdate or not
						         */
							     if(empBiweeklyStartDate.before(previousMondayDate)){
							    	 empBiweeklyStartDate = previousMondayDate;
							     }
								   
								 /**
								   * checking whether post 6th week sunday date is lesserthan employee enddate or not
							      */
								  if(employeeBiweeklyEndDate != null && employeeBiweeklyEndDate.after(nextSundayDate)){
									  employeeBiweeklyEndDate = nextSundayDate;
								  }
								
						    
								    Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
									 startCal.setLenient(false);
									 startCal.setTime(empBiweeklyStartDate);
									 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
									 
									 Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
									 endCal.setLenient(false);
									 endCal.setTime(employeeBiweeklyEndDate);
									 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(startCal, "MON");
										
									  int totalWeeks = 0; 
									  Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
									  tsCal.setLenient(false);
									  
									  Date tsStartDate = null;
									  Date tsEndDate = null;
										
									  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
									  for (int week = 0; week < totalWeeks; week++) {
										    if(tsStartDate == null){
												tsCal.setTime(startMonday.getTime());
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
								Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								cal.setLenient(false);
								cal.setTime(commonUtil.currentDate());
								Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
								Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
								Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
								Date previousMondayDate = previousMonday.getTime();
								
								Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
								Date nextSundayDate = nextSunday.getTime();
								
								
								Calendar startcal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								startcal.setLenient(false);
								startcal.setTime(empStartDate);
								@SuppressWarnings("unused")
								Date employeeStartDate = CalendarUtil.getCurrentWeekDayByCalendar(startcal, "MON").getTime();
								
								
								Calendar endcal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								endcal.setLenient(false);
								endcal.setTime(empEndDate);
								@SuppressWarnings("unused")
								Date employeeEndDate = CalendarUtil.getCurrentWeekDayByCalendar(endcal, "SUN").getTime();
								
							    /**
							     * checking whether prior 6th week monday date is greaterthan employee startdate or not
						         */
							     if(employeeStartDate.before(previousMondayDate)){
							    	 employeeStartDate = previousMondayDate;
							     }
								   
								 /**
								   * checking whether post 6th week sunday date is lesserthan employee enddate or not
							      */
								  if(employeeEndDate != null && employeeEndDate.after(nextSundayDate)){
									  employeeEndDate = nextSundayDate;
								  }
								
								 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								 startCal.setLenient(false);
								 startCal.setTime(empStartDate);
								 Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
								 
								 Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								 endCal.setLenient(false);
								 endCal.setTime(empEndDate);
								 Calendar empEndMonday = CalendarUtil.getNextWeekDayByCalendar(startCal, "MON");
									
								  int totalWeeks = 0; 
								  Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
								  tsCal.setLenient(false);
								  
								  Date tsStartDate = null;
								  Date tsEndDate = null;
									
								  totalWeeks = (int)((empEndMonday.getTimeInMillis()-empStartMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
								  for (int week = 0; week < totalWeeks; week++) {
									    if(week % 2 == 0)
									    	continue;
									    if(tsStartDate == null){
											tsCal.setTime(startMonday.getTime());
											tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
											tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
										}
										else{
											tsCal.setTime(tsStartDate);
										}
									    timesheetFrequencyList.put(format.format(tsStartDate), format.format(tsEndDate));
									    tsStartDate = CalendarUtil.getDateIncrementByincrement(tsCal, 14).getTime();
										tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 26).getTime();
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
	
	private Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(Projects projects) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON");
		
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(commonUtil.currentDate());
		Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
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
	

	private Date getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(Projects projects,Date employeeStartDate) throws Exception{
		Date biWeeklyStartMondayDate = null;
		Calendar projCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON");
		
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(employeeStartDate);
		Calendar empStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		if(projectStartMonday.before(empStartMonday)){
			while(projectStartMonday.before(empStartMonday)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyStartMondayDate = projectStartMonday.getTime();
		}
		
		return biWeeklyStartMondayDate;
	}
	

	
	private Date getBiweeklyEndSunDayDateByUsingProjectStartDateAndEmployeeEndDate(Projects projects,Date employeeEndDate) throws Exception{
		Date biWeeklyEndSundayDate = null;
		Calendar projCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		projCal.setLenient(false);
		projCal.setTime(projects.getStartDate());
		Calendar projectStartMonday = CalendarUtil.getCurrentWeekDayByCalendar(projCal, "MON");
		
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(employeeEndDate);
		Calendar empEndSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
		if(projectStartMonday.before(empEndSunday)){
			while(projectStartMonday.before(employeeEndDate)){
				projectStartMonday = CalendarUtil.getDateIncrementByincrement(projectStartMonday,14);
			}
			biWeeklyEndSundayDate = CalendarUtil.getDateIncrementByincrement(projectStartMonday, 13).getTime();
		}
		
		return biWeeklyEndSundayDate;
	}
	 

}