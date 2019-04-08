package com.bis.sourcelead.clockInOutUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;

@Component
public class ClockInOutUtil {
	
	
	public Date[] getClockFilterDate(String dateSelection,OrgLocations orgLocations) throws Exception {
		Date[] dates = new Date[2];
		Date[] searchDates = null;
		Date startDate = null;
		Date endDate = null;
		if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
			searchDates = today(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
			searchDates = currentWeek(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
			searchDates = currentMonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
			searchDates = currentYearDates();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_YESTERDAY)) {
			searchDates = yesterdayDates(orgLocations);
		}
		
		
		startDate = new java.sql.Date(searchDates[0].getTime());
		endDate = new java.sql.Date(searchDates[1].getTime());
		dates[0] = startDate;
		dates[1] = endDate;
		return dates;
	}
	
	public Date[] today(OrgLocations orgLocations) throws Exception {
		
		DateTime zoned = new DateTime(DateTimeZone.forID(orgLocations.getTimeZoneName()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime toDayZoned = zoned.minusDays(0);
		String todayDate = outputFormatter.print(toDayZoned);
		Date todayDateDateUTC = TZ_DF_TS.parse(todayDate);
		
		Date[] dates = new Date[2];
		dates[0] = todayDateDateUTC;
		dates[1] = startDateUTC;
		return dates;
	}
	
	/**
	 * Get from and to date of current week
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] currentWeek(OrgLocations orgLocations) throws Exception {
		
		Calendar c = Calendar.getInstance(); // this takes current date
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Date oneWeekDateUTC = c.getTime();
		c.add(Calendar.DATE, 6);
		Date startDateUTC = c.getTime();
		Date[] dates = new Date[2];
		dates[0] = oneWeekDateUTC;
		dates[1] = startDateUTC;
		return dates;
		
		
	}

	
	/**
	 * Get from and to date of current Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] currentMonthDates(OrgLocations orgLocations) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		Date startDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date toDate = calendar.getTime();
		
		Date[] dates = new Date[2];
		dates[0] = startDate;
		dates[1] = toDate;
		
		return dates;
	}
	
	/**
	 * Get from and to date of current Year
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] currentYearDates() {
		Date[] dates = new Date[2];
		Calendar calendarStart = Calendar.getInstance();
		int previousYear = calendarStart.get(Calendar.YEAR);
		calendarStart.set(Calendar.YEAR, previousYear);
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = calendarStart.getTime();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, previousYear);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		dates[1] = calendarEnd.getTime();
		return dates;

	}
	
public Date[] yesterdayDates(OrgLocations orgLocations) throws Exception {
		
	    DateTime zoned = new DateTime(DateTimeZone.forID(orgLocations.getTimeZoneName()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime oneDayZoned = zoned.minusDays(1);
		String oneDaysDate = outputFormatter.print(oneDayZoned);
		Date oneDateUTC = TZ_DF_TS.parse(oneDaysDate);
		
		Date[] dates = new Date[2];
		dates[0] = oneDateUTC;
		dates[1] = oneDateUTC;
		return dates;
	}
	
}
