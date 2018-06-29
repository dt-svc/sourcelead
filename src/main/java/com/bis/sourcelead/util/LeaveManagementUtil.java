package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.joda.time.LocalDate;
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
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;

@Component
public class LeaveManagementUtil {
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	LeaveService leaveService;
	@Autowired
	EmployeesService employeesService;
	@Autowired
	ConversionUtil conversionUtil;
	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	@Autowired
	HolidayService holidayService;
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementUtil.class);
	
	public static List<Date> getDaysBetweenDates(Date startDate, Date endDate, String orgFormat) throws ParseException
	{
		List<Date> dates = new ArrayList<Date>();
		List<String> dateStr = new ArrayList<>();
		DateFormat formatter;
		formatter = new SimpleDateFormat(orgFormat);
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly
											// using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dates.add(getOrgLocationCurrentDate(orgFormat, new Date(curTime)));
			curTime += interval;
		}
		return dates;
	}
	
	private static Date getOrgLocationCurrentDate(String orgDateFormat, Date date) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat(orgDateFormat);
		return dateFormat.parse(dateFormat.format(date));
	}
	
	public int getBalanceCountForLeaveType(LeaveType type, Employees employee, OrgLocations orgLocations, User loginUser) throws ParseException
	{
		Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(loginUser.getId(), orgLocations.getId());
		int totalLeaves = 0;
		if (type.getPeriod() != null) {
			if ("Annually".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays();
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays();
						}
						else {
							totalLeaves = type.getNewJoinee();
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays();
					}
				}
			}
			else if ("HalfYearly".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays() * 2;
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays() * 2;
						}
						else {
							totalLeaves = type.getNewJoinee() * 2;
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays() * 2;
					}
				}
			}
			else if ("Quarterly".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays() * 4;
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays() * 4;
						}
						else {
							totalLeaves = type.getNewJoinee() * 4;
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays() * 4;
					}
				}
			}
			else if ("Monthly".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays() * 12;
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays() * 12;
						}
						else {
							totalLeaves = type.getNewJoinee() * 12;
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays() * 12;
					}
				}
			}
			else if ("SemiMonthly".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays() * 24;
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays() * 24;
						}
						else {
							totalLeaves = type.getNewJoinee() * 24;
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays() * 24;
					}
				}
			}
			else if ("BiWeekly".equalsIgnoreCase(type.getPeriod())) {
				if ("All Employees".equalsIgnoreCase(type.getLeaveEffective())) {
					totalLeaves = type.getNoOfDays() * 26;
				}
				else {
					if (emp.getHireDate() != null) {
						Date joinDate = emp.getHireDate();
						Date currentDate = new Date();
						int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
						int expDays = 0;
						if ("Year".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 365;
						}
						else if ("Month".equalsIgnoreCase(type.getExperiencedType())) {
							expDays = type.getExperiencedDuration() * 30;
						}
						else {
							expDays = type.getExperiencedDuration();
						}
						if (diffDays > expDays) {
							totalLeaves = type.getExperiencedApplicableDays() * 26;
						}
						else {
							totalLeaves = type.getNewJoinee() * 26;
						}
					}
					else {
						totalLeaves = type.getExperiencedApplicableDays() * 26;
					}
				}
			}
		}
		return totalLeaves;
	}
	
	public int getNumberOfExtraDaysByPeriod(LeaveType leaveType, Leave leave, OrgLocations orgLocations, User user) throws Exception
	{
		int noOfExtraDays = 0;
		Date[] dates = getMonthStartDateAndEndDate(leave.getFromDate());
		if ("Monthly".equalsIgnoreCase(leaveType.getPeriod())) {
			int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(leave.getFromDate(), leave.getToDate());
			List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndDuration(leaveType.getId(), orgLocations.getId(), user.getId(), dates[0], dates[1]);
			if (leaveList.size() > 0) {
				noOfExtraDays = numberOfdays;
			}
			else {
				noOfExtraDays = numberOfdays - leaveType.getNoOfDays();
			}
		}
		return noOfExtraDays;
	}
	
	public Date[] getMonthStartDateAndEndDate(Date fromDate)
	{
		Date[] dates = new Date[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		// Date nextMonthFirstDay = calendar.getTime();
		dates[0] = calendar.getTime();
		// System.out.println(nextMonthFirstDay);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dates[1] = calendar.getTime();
		// System.out.println(nextMonthLastDay);
		return dates;
	}
	
	public Map<String, Float> getMonthlyAppliedLeaves(Leave leave, LeaveDTO leaveDTO, User user) throws Exception
	{
		Map<String, Float> monthlyLeaves = new HashMap<>();
		String months[] = Constants.MONTHS;
		Date fromDate = commonUtil.orgLocationDateFormat(leave.getOrgLocations().getDateFormat(), leaveDTO.getFromDate());
		Date toDate = commonUtil.orgLocationDateFormat(leave.getOrgLocations().getDateFormat(), leaveDTO.getToDate());
		String fromHalfday = leave.getFromHalfDay();
		String toHalfday = leave.getToHalfDay();
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		int fromMonth = fromCal.get(Calendar.MONTH);
		toCal.setTime(toDate);
		int toMonth = toCal.get(Calendar.MONTH);
		int noOfDays = 0;
		float actualNoOfDays = 0;
		if (fromMonth == toMonth) {
			noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(fromDate, toDate);
			actualNoOfDays = getTotalAppliedLeaves(noOfDays, fromHalfday, toHalfday);
			monthlyLeaves.put(months[fromMonth], actualNoOfDays);
		}
		else {
			for (int i = 1; i < toMonth; i++) {
				if (fromMonth + i == toMonth) {
					Date[] dates = new Date[2];
					fromCal.set(Calendar.DATE, fromCal.getActualMaximum(Calendar.DAY_OF_MONTH));
					dates[0] = fromCal.getTime();
					noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(fromDate, dates[0]);
					actualNoOfDays = getTotalAppliedLeaves(noOfDays, fromHalfday, null);
					monthlyLeaves.put(months[fromMonth], actualNoOfDays);
					toCal.set(Calendar.DATE, toCal.getActualMinimum(Calendar.DAY_OF_MONTH));
					dates[1] = toCal.getTime();
					noOfDays = commonUtil.getNumberOfDaysBetweenTwoDates(dates[1], toDate);
					actualNoOfDays = getTotalAppliedLeaves(noOfDays, null, toHalfday);
					monthlyLeaves.put(months[toMonth], actualNoOfDays);
				}
				else if (fromMonth + i < toMonth) {
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.MONTH, fromMonth + i);
					noOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					monthlyLeaves.put(months[toMonth - i], (float) noOfDays);
				}
			}
		}
		return monthlyLeaves;
	}
	
	private float getTotalAppliedLeaves(int days, String fromHalfday, String toHalfday)
	{
		float halfday = 0;
		float actualApplied = 0;
		if ("YES".equalsIgnoreCase(fromHalfday)) {
			halfday += 0.5f;
		}
		if ("YES".equalsIgnoreCase(toHalfday)) {
			halfday += 0.5f;
		}
		if ("YES".equalsIgnoreCase(fromHalfday) && "YES".equalsIgnoreCase(toHalfday)) {
			if (days > 1) {
				actualApplied = days - halfday;
			}
			else {
				actualApplied = halfday;
			}
		}
		else {
			actualApplied = days - halfday;
		}
		return actualApplied;
	}
	
	public float calculateAppliedLeavesCountWithHalfDays(Leave leave)
	{
		float halfday = 0;
		float actualApplied = 0;
		if ("YES".equalsIgnoreCase(leave.getFromHalfDay())) {
			halfday += 0.5f;
		}
		if ("YES".equalsIgnoreCase(leave.getToHalfDay())) {
			halfday += 0.5f;
		}
		int days = leave.getNumberOfDays();
		if ("YES".equalsIgnoreCase(leave.getFromHalfDay())
				&& "YES".equalsIgnoreCase(leave.getToHalfDay())) {
			if (days > 1) {
				actualApplied = days - halfday;
			}
			else {
				actualApplied = halfday;
			}
		}
		else {
			actualApplied = days - halfday;
		}
		return actualApplied;
	}
	
	public float getTotalBalanceLeavesForNonCarryforwardLeaveType(LeaveType leaveType, float totalbalanceLeaveCounts, Employees emp) throws Exception
	{
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		// totalbalanceLeaveCounts = totalbalanceLeaveCounts - ((currentMonth) *
		// leave.getLeaveType().getNoOfDays());
		float totalLeaveCount = 0;
		if ("Monthly".equalsIgnoreCase(leaveType.getPeriod())
				&& Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
			/*
			 * if("Experienced".equalsIgnoreCase(leaveType.getLeaveEffective()))
			 * { for(int i=0; i<currentMonth;i++){ if(emp.getHireDate() !=
			 * null){ Date joinDate = emp.getHireDate(); Date currentDate = new
			 * Date(); int diffDays =
			 * commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
			 * int expDays = 0;
			 * if("Year".equalsIgnoreCase(leaveType.getExperiencedType())){
			 * expDays = leaveType.getExperiencedDuration() * 365 ; }else
			 * if("Month".equalsIgnoreCase(leaveType.getExperiencedType())){
			 * expDays = leaveType.getExperiencedDuration() * 30 ; }else {
			 * expDays = leaveType.getExperiencedDuration(); } if(diffDays >
			 * expDays){ totalLeaveCount =
			 * leaveType.getExperiencedApplicableDays(); }else{ totalLeaveCount
			 * = leaveType.getNewJoinee(); } }else{ totalLeaveCount =
			 * leaveType.getExperiencedApplicableDays(); } }
			 * 
			 * totalbalanceLeaveCounts = totalbalanceLeaveCounts -
			 * totalLeaveCount; }else{
			 */
			for (int i = 0; i < currentMonth; i++) {
				totalLeaveCount += leaveType.getNoOfDaysPerPeriod();
			}
			totalbalanceLeaveCounts = totalbalanceLeaveCounts - totalLeaveCount;
			// totalbalanceLeaveCounts = totalbalanceLeaveCounts -
			// totalLeaveCount;
			// }
		}
		return totalbalanceLeaveCounts;
	}
	
	public float calculateExtraLeaves(Long userId, Long leaveTypeId, Float appliedLeavesPerPeriod)
	{
		float extraLeaves = 0f;
		Map<String, Float> nuberOfCurrentMonthLeavestaken = leaveService.getNumberOfCurrentMonthLeavesTakenByUser(userId, leaveTypeId);
		float monthlyLeaves = appliedLeavesPerPeriod;
		float appLeaves = nuberOfCurrentMonthLeavestaken.get("numberOfLeavsTaken");
		if (appLeaves < monthlyLeaves) {
			extraLeaves = 0f;
		}
		else if (appLeaves > monthlyLeaves) {
			float extra = Math.abs(appLeaves - monthlyLeaves);
			extraLeaves = extra - nuberOfCurrentMonthLeavestaken.get("numberOfExtraLeaves");
		}
		else if (appLeaves == monthlyLeaves) {
			extraLeaves = 0f;
		}
		return extraLeaves;
	}
	
	public Date getCurrentMonthStartDate()
	{
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = 1;
		c.set(year, month, day);
		return c.getTime();
	}
	
	public float getAppliedLeaveCount(OrgLocations orgLocations, LeaveType l, Leave leave, float totalLeaves)
	{
		float appliedLeaveCount = 0;
		LocalDate todayDate = LocalDate.now();
		List<AppliedLeaveDateView> appliedLeaves = null;
		AssociateBalanceLeaveView al = new AssociateBalanceLeaveView();
		if (l.getIncludeHolidays() != null) {
			al.setIncludeHolidays(l.getIncludeHolidays());
		}
		
		if (l.getIncludeWeekoff() != null) {
			al.setIncludeWeekoff(l.getIncludeWeekoff());
		}
		if (Constants.MONTHLY.equalsIgnoreCase(l.getPeriod())
				&& Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
			Date[] monthDates = conversionUtil.getMonthStartDateEndDate();
			appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), leave.getFromDate(), leave.getToDate(), leave.getUser().getId());
			appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, al, monthDates[0], monthDates[1]);
		}
		else if (Constants.QUARTERLY.equalsIgnoreCase(l.getPeriod())
				&& Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
			LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
			appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), leave.getUser().getId());
			appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, al, currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate());
		}
		else if (Constants.HALFYEARLY.equalsIgnoreCase(l.getPeriod())
				&& Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
			LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
			appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), leave.getUser().getId());
			appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, al, currentHalfYear[0].toDate(), currentHalfYear[1].toDate());
		}
		else if (Constants.ANNUALLY.equalsIgnoreCase(l.getPeriod())
				&& Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
			LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
			appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), leave.getUser().getId());
			appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, al, thisYearDates[0].toDate(), thisYearDates[1].toDate());
		}
		if (appliedLeaves != null && appliedLeaves.size() > 0) {
			appliedLeaveCount =appliedLeaves.size();
		}
		/*else {
			appliedLeaveCount = totalLeaves;
		}*/
		return appliedLeaveCount;
	}
	
	/**
	 * Exclude Week Offs and Holidays from leaves
	 * 
	 * @param holidayList
	 * @param appliedLeaves
	 * @param orgLocations
	 * @param l
	 * @param endDaet
	 * @param startDate
	 * @return List<AppliedLeaveDateView>
	 */
	public List<AppliedLeaveDateView> getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet)
	{
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the
																		// day
																		// of
																		// the
																		// week
																		// abbreviated
		if (l.getIncludeHolidays() != null
				&& !Constants.YES.equalsIgnoreCase(l.getIncludeHolidays())) {
			List<HolidayAndRestrictday> holidayList = holidayService.getHolidayByFromToDate(startDate, endDaet, orgLocations);
			for (HolidayAndRestrictday holiday : holidayList) {
				Date holidayDate = commonUtil.orgLocationCommonDate(holiday.getDay(), orgLocations);
				boolean s = appliedLeaves.removeIf(x -> commonUtil.orgLocationCommonDate(x.getApplyLeaveDate(), orgLocations).equals(holidayDate));
			}
		}
		if (l.getIncludeWeekoff() != null
				&& !Constants.YES.equalsIgnoreCase(l.getIncludeWeekoff())) {
			if (orgLocations.getWeekOffDays() != null) {
				String[] weekOoffs = orgLocations.getWeekOffDays().split(",");
				for (String weekEndDay : weekOoffs) {
					appliedLeaves.removeIf(x -> simpleDateformat.format(x.getApplyLeaveDate()).toUpperCase().equals(weekEndDay.trim().toUpperCase()));
				}
			}
		}
		return appliedLeaves;
	}
}