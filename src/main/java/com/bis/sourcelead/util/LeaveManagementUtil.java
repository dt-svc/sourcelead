package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.ibm.icu.text.DecimalFormat;

@Component
public class LeaveManagementUtil {
	
	public DecimalFormat dec = new DecimalFormat("#0.00");
	
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
	
	@Autowired
	AssociateBalanceLeaveDao associateBalanceLeaveDao;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementUtil.class);
	
	public static List<Date> getDaysBetweenDates(Date startDate, Date endDate, String orgFormat) throws ParseException
	{
		List<Date> dates = new ArrayList<Date>();
		List<String> dagetExperienceTyper = new ArrayList<>();
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
	/**
	 * Get number of leaves taken by employee by the duration
	 * @param orgLocations
	 * @param l
	 * @param leave
	 * @param leaveStatus 
	 * @param totalLeaves
	 * @return float
	 */
	public float getAppliedLeaveCount(OrgLocations orgLocations, LeaveType l, Leave leave, String leaveStatus)
	{
		List<AppliedLeaveDateView> appliedLeaves = null;
		AssociateBalanceLeaveView al = new AssociateBalanceLeaveView();
		if (l.getIncludeHolidays() != null) {
			al.setIncludeHolidays(l.getIncludeHolidays());
		}
		if (l.getIncludeWeekoff() != null) {
			al.setIncludeWeekoff(l.getIncludeWeekoff());
		}
		al.setPeriod(leaveStatus);
		if(StringUtils.isNotBlank(leave.getFromHalfDay())) {
			al.setFromHalfDay(leave.getFromHalfDay());
		}
		if(StringUtils.isNotBlank(leave.getToHalfDay())) {
			al.setToHalfDay(leave.getToHalfDay());
		}
		if(StringUtils.isNotBlank(leave.getFromDateFromHalfTime())) {
			al.setFromHalfFromTime(leave.getFromDateFromHalfTime());
		}
		if(StringUtils.isNotBlank(leave.getFromDateToHalfTime())) {
			al.setFromHalfToTime(leave.getFromDateToHalfTime());
		}
		if(StringUtils.isNotBlank(leave.getToDateFromHalfTime())) {
			al.setToHalfFromTime(leave.getToDateFromHalfTime());
		}
		if(StringUtils.isNotBlank(leave.getToDateToHalfTime())) {
			al.setToHalfToTime(leave.getToDateToHalfTime());
		}
		appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(orgLocations.getId(), l.getId(), leave.getFromDate(), leave.getToDate(), leave.getUser().getId(),leaveStatus,orgLocations.getOrg().getId());
		//List<AppliedLeaveDateView> unique = appliedLeaves.stream().filter(distinctByKey(p -> p.getApplyLeaveDate())).collect(Collectors.toList());
		float appliedLeavesCount = (float) getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, al, leave.getFromDate(), leave.getToDate(),l);
		return appliedLeavesCount;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
	public float getTotalAppliedLeaveCount(OrgLocations orgLocations, LeaveType l,Long userId)
	{
		float appliedLeaveCount = 0;
		AssociateBalanceLeaveView al = new AssociateBalanceLeaveView();
		if (l.getIncludeHolidays() != null) {
			al.setIncludeHolidays(l.getIncludeHolidays());
		}
		if (l.getIncludeWeekoff() != null) {
			al.setIncludeWeekoff(l.getIncludeWeekoff());
		}
		
		if(orgLocations.getOrgId() == null) {
			orgLocations.setOrgId(orgLocations.getOrg().getId());
		}
		
		LocalDate todayDate = LocalDate.now();
		List<AppliedLeaveDateView> appliedLeaves = null;
		if (Constants.CUSTOM.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
			Date[] monthDates = new Date[2];
			monthDates[0] = commonUtil.getDateFormat(l.getFromDate());
			monthDates[1] = commonUtil.getDateFormat(l.getToDate());
			appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), monthDates[0], monthDates[1], userId,orgLocations.getOrgId());
			appliedLeaveCount = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,al,monthDates[0],monthDates[1],l);
		}else {
			if (Constants.MONTHLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
				Date[] monthDates = conversionUtil.getMonthStartDateEndDate();
				appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), monthDates[0], monthDates[1], userId,orgLocations.getOrgId());
				appliedLeaveCount = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,al,monthDates[0],monthDates[1],l);
			}
			else if (Constants.QUARTERLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
				LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
				appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), userId,orgLocations.getOrgId());
				appliedLeaveCount = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,al,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate(),l);
			}
			else if (Constants.HALFYEARLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
				LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
				appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), userId,orgLocations.getOrgId());
				appliedLeaveCount = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,al,currentHalfYear[0].toDate(),currentHalfYear[1].toDate(),l);
			}
			else if (Constants.ANNUALLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
				LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
				appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), userId,orgLocations.getOrgId());
				appliedLeaveCount = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,al,thisYearDates[0].toDate(),thisYearDates[1].toDate(),l);
			}
		}
		
		/*if (appliedLeaves != null && appliedLeaves.size() > 0) {
			appliedLeaveCount = appliedLeaves.size();
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
	 * @param endDate
	 * @param startDate
	 * @param leaveType 
	 * @return List<AppliedLeaveDateView>
	 */
	public double getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDate, LeaveType leaveType)
	{
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); 
		
		double fromHours = 0f;
		double toHours = 0f;
		int appLeaves = 0;
		double actualLeaves = 0;
		
		
		if("Duration".equalsIgnoreCase(l.getPeriod())){
			appliedLeaves = new ArrayList<>();
			List<Date> dates = getDaysBetweenLeaveDates(startDate, endDate);
			for(Date d : dates){
				AppliedLeaveDateView view = new AppliedLeaveDateView();
				view.setApplyLeaveDate(d);
				appliedLeaves.add(view);
			}
		}
		
		if(appliedLeaves != null && !appliedLeaves.isEmpty()) {
		
			if (leaveType.getIncludeHolidays() != null
					&& !Constants.YES.equalsIgnoreCase(leaveType.getIncludeHolidays())) {
				List<HolidayAndRestrictday> holidayList = holidayService.getHolidayByFromToDate(startDate, endDate, orgLocations);
				for (HolidayAndRestrictday holiday : holidayList) {
					Date holidayDate = commonUtil.orgLocationCommonDate(holiday.getDay(), orgLocations);
					boolean s = appliedLeaves.removeIf(x -> commonUtil.orgLocationCommonDate(x.getApplyLeaveDate(), orgLocations).equals(holidayDate));
				}
			}
			if (leaveType.getIncludeWeekoff() != null && !Constants.YES.equalsIgnoreCase(leaveType.getIncludeWeekoff())) {
				if (orgLocations.getWeekOffDays() != null) {
					String[] weekOoffs = orgLocations.getWeekOffDays().split(",");
					for (String weekEndDay : weekOoffs) {
						appliedLeaves.removeIf(x -> simpleDateformat.format(x.getApplyLeaveDate()).toUpperCase().equals(weekEndDay.trim().toUpperCase()));
					}
				}
			}
			
		
			if ("Duration".equalsIgnoreCase(l.getPeriod())) {
				if (appliedLeaves.stream().anyMatch(al -> al.getApplyLeaveDate().equals(startDate))) {
					if (Constants.STATUS_YES.equalsIgnoreCase(l.getFromHalfDay())) {
						if(StringUtils.isNotBlank(l.getFromHalfFromTime()) && StringUtils.isNotBlank(l.getFromHalfToTime())){
							appLeaves += 1;
							fromHours = calculateActualHoursForHalfDay(l.getFromHalfFromTime(), l.getFromHalfToTime(), orgLocations.getAverageWorkingHours(), leaveType);
						}
					}
				}
				if (appliedLeaves.stream().anyMatch(al -> al.getApplyLeaveDate().equals(endDate))) {
					if (Constants.STATUS_YES.equalsIgnoreCase(l.getToHalfDay())) {
						if(StringUtils.isNotBlank(l.getToHalfFromTime()) && StringUtils.isNotBlank(l.getToHalfToTime())){
							appLeaves += 1;
							toHours = calculateActualHoursForHalfDay(l.getToHalfFromTime(), l.getToHalfToTime(), orgLocations.getAverageWorkingHours(), leaveType);
						}
					}
				}
				int orgHours = 0;
				if("Half Day".equalsIgnoreCase(leaveType.getDuration())){
					orgHours = orgLocations.getAverageWorkingHours() / 2;
				}else{
					orgHours = orgLocations.getAverageWorkingHours();
				}
				float hourDays = (float) ((fromHours + toHours) / orgHours);
				actualLeaves = (appliedLeaves.size() - appLeaves) + hourDays;
			}else{
				for(AppliedLeaveDateView ald : appliedLeaves){
					if (Constants.STATUS_YES.equalsIgnoreCase(ald.getFromHalfDay()) && ald.getFromDate().equals(ald.getApplyLeaveDate())) {
						appLeaves += 1;
						fromHours += calculateActualHoursForHalfDay(ald.getFromDateFromHalfTime(), ald.getFromDateToHalfTime(), orgLocations.getAverageWorkingHours(), leaveType);
					}
					if (Constants.STATUS_YES.equalsIgnoreCase(ald.getToHalfDay()) && ald.getToDate().equals(ald.getApplyLeaveDate())) {
						appLeaves += 1;
						toHours += calculateActualHoursForHalfDay(ald.getToDateFromHalfTime(), ald.getToDateToHalfTime(), orgLocations.getAverageWorkingHours(), leaveType);
					}
				}
				int orgHours = 0;
				if("Half Day".equalsIgnoreCase(leaveType.getDuration())){
					orgHours = orgLocations.getAverageWorkingHours() / 2;
				}else{
					orgHours = orgLocations.getAverageWorkingHours();
				}
				float hourDays = (float) ((fromHours + toHours) / orgHours);
				actualLeaves = (appliedLeaves.size() - appLeaves) + hourDays;
			}
		}
		return actualLeaves;
	}

	public List<Date> getDaysBetweenLeaveDates(Date fromDate, Date toDate) {
		List<Date> dates = new ArrayList<Date>();
		List<String> dagetExperienceTyper = new ArrayList<>();
//		DateFormat formatter;
//		formatter = new SimpleDateFormat(orgFormat);
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = toDate.getTime(); // create your endtime here, possibly
											// using Calendar or Date
		long curTime = fromDate.getTime();
		while (curTime <= endTime) {
			dates.add(new Date(curTime));
			curTime += interval;
		}
		return dates;
	}
	
	public boolean checkGenderAndDepartmentAndRoleAndEmpType(LeaveType leaveType, Employees emp, UserRole userRole)
	{
		boolean flag = false;
		boolean isDeptExist = false;
		Set<Long> roleIds = new HashSet<>();
	
	
	
		
		if(leaveType.getLeaveTypeRolesSet() != null){
			for (LeaveRole leaveRole : leaveType.getLeaveTypeRolesSet()) {
				roleIds.add(leaveRole.getRoleId().getId());
			}
		}
		if(leaveType.getDepartmentIds() != null) {
			String[] arr = leaveType.getDepartmentIds().split(",");
			List<String> strList = Arrays.asList(arr);
	 		 isDeptExist = strList.stream().anyMatch(d->emp.getDepartmentId() != null && d.equals(emp.getDepartmentId().getId().toString())); 
		}
		
		if ((leaveType.getEffectiveGender() != null
				&& StringUtils.isNotBlank(userRole.getUser().getGender())
				&& userRole.getUser().getGender().equalsIgnoreCase(leaveType.getEffectiveGender()))
				|| "All".equalsIgnoreCase(leaveType.getEffectiveGender())) {
			// if(userRole.getUser().getGender().equalsIgnoreCase(leaveType.getEffectiveGender())
			// || "All".equalsIgnoreCase(leaveType.getEffectiveGender())){
			if ("N".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
				if (roleIds.size() > 0 && StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if (emp.getDepartmentId() != null) {
						if (roleIds.contains(userRole.getRoles().getId())
								&& isDeptExist == true	&& leaveType.getLeaveEmpType().contains(emp.getUserEmploymentType())) {
							flag = true;
						}
					}
				}
				else if (roleIds.size() > 0 && StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& !StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if (emp.getDepartmentId() != null) {
						if (roleIds.contains(userRole.getRoles().getId()) && isDeptExist == true) {
							flag = true;
						}
					}
				}
				else if (roleIds.size() == 0 && StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if (emp.getDepartmentId() != null && emp.getUserEmploymentType() != null) {
						if (isDeptExist == true	&& leaveType.getLeaveEmpType().contains(emp.getUserEmploymentType())) {
							flag = true;
						}
					}
				}
				else if (roleIds.size() > 0 && !StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if(emp.getUserEmploymentType() != null) {
						if (roleIds.contains(userRole.getRoles().getId()) && leaveType.getLeaveEmpType().contains(emp.getUserEmploymentType())) {
							flag = true;
						}	
					}
					
				}
				else if (roleIds.size() == 0 && !StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& StringUtils.isNotBlank(leaveType.getLeaveEmpType()) && emp.getUserEmploymentType() != null) {
					if (emp.getUserEmploymentType() != null && leaveType.getLeaveEmpType().contains(emp.getUserEmploymentType())) {
						flag = true;
					}
				}
				else if (roleIds.size() > 0 && !StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& !StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if (roleIds.contains(userRole.getRoles().getId())) {
						flag = true;
					}
				}
				else if (roleIds.size() == 0 && StringUtils.isNotBlank(leaveType.getDepartmentIds())
						&& !StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
					if (emp.getDepartmentId() != null) {
						if (isDeptExist) {
							flag = true;
						}
					}
				}
			}
			else {
				flag = true;
			}
		}
		return flag;
	}


	public float calculateAppliedLeavesCountWithHalfDaysAndWorkingHours(Leave leave, Float noOfDaysPerPeriod, Integer averageWorkingHours)
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
	
	public double calculateActualHoursForHalfDay(String fromTime, String toTime, Integer workingHours, LeaveType leaveType){
		double actualAppliedLeave = 0f;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		DecimalFormat decimal = new DecimalFormat("#0.0000");
        try {
        	if(StringUtils.isNotBlank(fromTime) && StringUtils.isNotBlank(toTime)){
        		Date d1 = sdf.parse(fromTime);
                Date d2 = sdf.parse(toTime);
                long fromElapsed = d2.getTime() - d1.getTime(); 
                int fromHours = (int) Math.floor((fromElapsed / 3600000)/60);
                int fromMinutes = (int) Math.floor((fromElapsed - fromHours * 3600000) / 60000);
                
                if("Half Day".equalsIgnoreCase(leaveType.getDuration())){
                	workingHours = workingHours / 2;
                }
                
//                if(Constants.MONTHLY.equalsIgnoreCase(leaveType.getPeriod())){
                	double oneHr = workingHours;
                	double oneHrMin = oneHr / 60;
                	actualAppliedLeave = fromMinutes / 60;
//                	actualAppliedLeave = oneHrMin * fromMinutes;
//                }else if(Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod())){
//                	double oneHr =  workingHours;
//                	double oneHrMin = oneHr / 60;
//                	actualAppliedLeave = oneHrMin * fromMinutes;
//                }else if(Constants.QUARTERLY.equalsIgnoreCase(leaveType.getPeriod())){
//                	
//                }else if(Constants.HALFYEARLY.equalsIgnoreCase(leaveType.getPeriod())){
//                	
//                }
        	}
            
         } catch (ParseException e) {
              e.printStackTrace();
         }
		
		
		return Float.valueOf(decimal.format(actualAppliedLeave));
	}


	public float calculateAppliedLeaveMinutes(String fromTime, String toTime, Integer workingHours, LeaveType leaveType){
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		DecimalFormat decimal = new DecimalFormat("#0.0000");
		float fromMinutes = 0;
        try {
        	if(StringUtils.isNotBlank(fromTime) && StringUtils.isNotBlank(toTime)){
        		Date d1 = sdf.parse(fromTime);
                Date d2 = sdf.parse(toTime);
                long fromElapsed = d2.getTime() - d1.getTime(); 
                int fromHours = (int) Math.floor((fromElapsed / 3600000)/60);
                fromMinutes = (float) Math.floor((fromElapsed - fromHours * 3600000) / 60000);
        	}
            
         } catch (ParseException e) {
              e.printStackTrace();
         }
		
		return fromMinutes;
	}
	public float getNumberofDaysPerPeriodForLeaveType(LeaveType type, Employees emp, User loginUser) throws ParseException
	{
		float totalLeaves = 0;
		if (type.getPeriod() != null) {
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
							
							if(Constants.ANNUALLY.equalsIgnoreCase(type.getPeriod()) || Constants.NO.equalsIgnoreCase(type.getAccuralPolicy())) {
								totalLeaves = type.getExperiencedApplicableDays();
							}else {
								totalLeaves = type.getExperiencedPerPeriod();
							}
							
						}
						else {
							if(Constants.ANNUALLY.equalsIgnoreCase(type.getPeriod()) || Constants.NO.equalsIgnoreCase(type.getAccuralPolicy())) {
								totalLeaves = type.getNewJoinee();
							}else {
								totalLeaves = type.getNewJoinPerPeriod();
							}
							
						}
					}
					else {
						if(Constants.ANNUALLY.equalsIgnoreCase(type.getPeriod()) || Constants.NO.equalsIgnoreCase(type.getAccuralPolicy())) {
							totalLeaves = type.getExperiencedApplicableDays();
						}else {
							if(type.getExperiencedPerPeriod() != null){
								totalLeaves = type.getExperiencedPerPeriod();
							}else{
								if(Constants.MONTHLY.equalsIgnoreCase(type.getPeriod())){
									totalLeaves = type.getExperiencedApplicableDays() / 12;
								}else if(Constants.QUARTERLY.equalsIgnoreCase(type.getPeriod())){
									totalLeaves = type.getExperiencedApplicableDays() / 4;
								}else if(Constants.HALFYEARLY.equalsIgnoreCase(type.getPeriod())){
									totalLeaves = type.getExperiencedApplicableDays() / 6;
								}
							}
							
						}
						
					}
		}
		return totalLeaves;
	}
	
	public String getExperienceType(LeaveType type, Employees emp, User loginUser) throws ParseException
	{
		String userType = "";
		try {
			if (type.getPeriod() != null) {
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
						userType = "EXP";
					}
					else {
						userType = "FRESHER";
					}
				}
				else {
					userType = "EXP";
				}
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return userType;
	}
	
	public Set<PayrollLopHis> getToalAppliedLeavesByCandidaet(List<LeaveType> leavTypeList, OrgLocations orgLocation, Date startDate, Date endDate, Long userId, EmployeePayroll payroll)
	{
		Set<PayrollLopHis> lopHis = new HashSet<>();
		try {
			for (LeaveType leaveType : leavTypeList) {
				if(leaveType.getIncludeInPayroll() != null && "Y".equalsIgnoreCase(leaveType.getIncludeInPayroll()) && "Billable".equalsIgnoreCase(leaveType.getLeaveType())) {
					AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
					ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
					ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
					List<AppliedLeaveDateView> appliedLeaves  = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocation.getId(), leaveType.getId(), startDate, endDate, userId, orgLocation.getOrg().getId());
					float appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves, orgLocation, ablv, startDate, endDate, leaveType);
					if (appliedLeaves != null && appliedLeaves.size() > 0) {
						float associateBalanceLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leaveType.getId(), userId, orgLocation.getId(), orgLocation.getOrg().getId());
						float balLeave = associateBalanceLeaves - appliedNoofLeaves;
						if (balLeave < 0) {
							PayrollLopHis payrollLopHis = new PayrollLopHis(); 
							payrollLopHis.setPayroll(payroll);
							payrollLopHis.setTotalBalanceLeaves(associateBalanceLeaves);
							payrollLopHis.setTotalAppliedLeaves(appliedNoofLeaves);
							payrollLopHis.setPayrollStartDate(startDate);
							payrollLopHis.setPayrollEndDate(endDate);
							payrollLopHis.setLeaveTypeName(leaveType.getName());
							payrollLopHis.setLeaveTypeId(leaveType);
							payrollLopHis.setLopLeaves(-balLeave);
							lopHis.add(payrollLopHis);
//							minusLeave = minusLeave + balLeave;
						}
					}
				}	
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lopHis;
	}
	
	
	public Set<PayrollLopHis> getToalAppliedLeavesByCandidaetInd(List<LeaveType> leavTypeList, OrgLocations orgLocation, Date startDate, Date endDate, Long userId, IndEmployeePayroll payroll)
	{
		Set<PayrollLopHis> lopHis = new HashSet<>();
		try {
			for (LeaveType leaveType : leavTypeList) {
				if(leaveType.getIncludeInPayroll() != null && "Y".equalsIgnoreCase(leaveType.getIncludeInPayroll()) && "Billable".equalsIgnoreCase(leaveType.getLeaveType())) {
					AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
					ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
					ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
					List<AppliedLeaveDateView> appliedLeaves  = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocation.getId(), leaveType.getId(), startDate, endDate, userId, orgLocation.getOrg().getId());
					float appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves, orgLocation, ablv, startDate, endDate, leaveType);
					if (appliedLeaves != null && appliedLeaves.size() > 0) {
						float associateBalanceLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leaveType.getId(), userId, orgLocation.getId(), orgLocation.getOrg().getId());
						float balLeave = associateBalanceLeaves - appliedNoofLeaves;
						if (balLeave < 0) {
							PayrollLopHis payrollLopHis = new PayrollLopHis(); 
							payrollLopHis.setIndPayroll(payroll);
							payrollLopHis.setTotalBalanceLeaves(associateBalanceLeaves);
							payrollLopHis.setTotalAppliedLeaves(appliedNoofLeaves);
							payrollLopHis.setPayrollStartDate(startDate);
							payrollLopHis.setPayrollEndDate(endDate);
							payrollLopHis.setLeaveTypeName(leaveType.getName());
							payrollLopHis.setLeaveTypeId(leaveType);
							payrollLopHis.setLopLeaves(-balLeave);
							lopHis.add(payrollLopHis);
						}
					}
				}	
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lopHis;
	}
	
	
}