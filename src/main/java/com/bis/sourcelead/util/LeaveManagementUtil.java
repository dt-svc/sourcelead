package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveService;

@Component
public class LeaveManagementUtil {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	LeaveService leaveService;
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementUtil.class);
	
	public static List<Date> getDaysBetweenDates(Date startDate, Date endDate,String orgFormat) throws ParseException {
		List<Date> dates = new ArrayList<Date>();
		List<String> dateStr = new ArrayList<>();
		DateFormat formatter;
		formatter = new SimpleDateFormat(orgFormat);
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dates.add(getOrgLocationCurrentDate(orgFormat, new Date(curTime)));
			curTime += interval;
		}
		return dates;
	}
	
	private static Date getOrgLocationCurrentDate(String orgDateFormat, Date date) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat(orgDateFormat);
			return dateFormat.parse(dateFormat.format(date));
	}

	public int getBalanceCountForLeaveType(LeaveType type, Employees emp, OrgLocations orgLocations, User loginUser) throws ParseException
	{
		int totalLeaves = 0;
		if(type.getPeriod() != null){
			if("Annually".equalsIgnoreCase(type.getPeriod())){
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays(); 
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays(); 
						}else{
							totalLeaves = type.getNewJoinee(); 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays(); 
					}
				}
			}
			else if("HalfYearly".equalsIgnoreCase(type.getPeriod())){
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays() * 2; 
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays() * 2; 
						}else{
							totalLeaves = type.getNewJoinee() * 2; 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays() * 2; 
					}
				}
			}
			else if("Quarterly".equalsIgnoreCase(type.getPeriod())){
				
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays() * 4; 
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays() * 4; 
						}else{
							totalLeaves = type.getNewJoinee() * 4; 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays() * 4; 
					}
				}
			}
			else if("Monthly".equalsIgnoreCase(type.getPeriod())){
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays() * 12; 
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays() * 12; 
						}else{
							totalLeaves = type.getNewJoinee() * 12; 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays() * 12; 
					}
				}
			}
			else if("SemiMonthly".equalsIgnoreCase(type.getPeriod())){
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays() * 24;
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays() * 24; 
						}else{
							totalLeaves = type.getNewJoinee() * 24; 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays() * 24; 
					}
				}
			}
			else if("BiWeekly".equalsIgnoreCase(type.getPeriod())){
				if("All Employees".equalsIgnoreCase(type.getLeaveEffective())){
					totalLeaves = type.getNoOfDays() * 26;
				}else{
					if(emp.getHireDate() != null){
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if("Year".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 365 ;
						}else if("Month".equalsIgnoreCase(type.getExperiencedType())){
							expDays = type.getExperiencedDuration() * 30 ;
						}else {
							expDays = type.getExperiencedDuration();
						}
						if(diffDays > expDays){
							totalLeaves = type.getExperiencedApplicableDays() * 26; 
						}else{
							totalLeaves = type.getNewJoinee() * 26; 
						}
					}else{
						totalLeaves = type.getExperiencedApplicableDays() * 26; 
					}
				}
			}
		}
		
		return totalLeaves;
	}

	public int getNumberOfExtraDaysByPeriod(LeaveType leaveType, LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception
	{
		int noOfExtraDays = 0;
		Date fromDate = commonUtil.getDateFormat(leaveDTO.getFromDate());
		Date[] dates = getMonthStartDateAndEndDate(fromDate);
		if("Monthly".equalsIgnoreCase(leaveType.getPeriod())){
			int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(commonUtil.getDateFormat(leaveDTO.getFromDate()), commonUtil.getDateFormat(leaveDTO.getToDate()));
			List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndDuration(leaveType.getId(), orgLocations.getId(),user.getId(),dates[0],dates[1]);
			if(leaveList.size() > 0){
				noOfExtraDays = numberOfdays;
			}else{
				noOfExtraDays = numberOfdays - leaveType.getNoOfDays();
			}
		}
		return noOfExtraDays;
	}
	
	public Date[] getMonthStartDateAndEndDate(Date fromDate){
		Date[] dates = new Date[2];
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(fromDate);
	    calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//	    Date nextMonthFirstDay = calendar.getTime();
	    dates[0] = calendar.getTime();
//	    System.out.println(nextMonthFirstDay);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	    dates[1] = calendar.getTime();
//	    System.out.println(nextMonthLastDay);
		return dates;
	}
	
	public Map<String,Integer> getMonthlyAppliedLeaves(Leave leave, LeaveDTO leaveDTO, User user) throws Exception{
		Map<String,Integer> monthlyLeaves = new HashMap<>();
		String months[] = Constants.MONTHS;
		Date fromDate = commonUtil.getDateFormat(leaveDTO.getFromDate());
		Date toDate = commonUtil.getDateFormat(leaveDTO.getToDate());
		
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		int fromMonth = fromCal.get(Calendar.MONTH);
		toCal.setTime(toDate);
		int toMonth = toCal.get(Calendar.MONTH);
		int noOfDays = 0;
		if(fromMonth == toMonth){
			noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(fromDate,toDate);
			monthlyLeaves.put(months[fromMonth], noOfDays);
		}else{
			for(int i=1; i <= toMonth;i++){
				if(fromMonth+i == toMonth){
					Date[] dates = new Date[2];
					fromCal.set(Calendar.DATE, fromCal.getActualMaximum(Calendar.DAY_OF_MONTH));
					dates[0] = fromCal.getTime();
					noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(fromDate,dates[0]);
					monthlyLeaves.put(months[fromMonth], noOfDays);
					
					toCal.set(Calendar.DATE, toCal.getActualMinimum(Calendar.DAY_OF_MONTH));
					dates[1] = toCal.getTime();
					noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(dates[1],toDate);
					monthlyLeaves.put(months[toMonth], noOfDays);
				}else{
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.MONTH,fromMonth+i);
					noOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					monthlyLeaves.put(months[toMonth], noOfDays);
				}
			}
			
		}
		return monthlyLeaves;
	}
		
}