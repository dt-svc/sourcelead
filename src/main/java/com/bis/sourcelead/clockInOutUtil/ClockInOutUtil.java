package com.bis.sourcelead.clockInOutUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.clockInOutDTO.PreviousNextDTO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class ClockInOutUtil {
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	public Date[] getClockFilterDate(String dateSelection,String timeZoneId) throws Exception {
		Date[] dates = new Date[2];
		Date[] searchDates = null;
		Date startDate = null;
		Date endDate = null;
		if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
			searchDates = today(timeZoneId);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
			searchDates = currentWeek();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
			searchDates = currentMonthDates();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
			searchDates = currentYearDates();
		}
		
		
		startDate = new java.sql.Date(searchDates[0].getTime());
		endDate = new java.sql.Date(searchDates[1].getTime());
		dates[0] = startDate;
		dates[1] = endDate;
		return dates;
	}
	
	public Date[] today(String timeZoneId) throws Exception {
		
		DateTime zoned = new DateTime(DateTimeZone.forID(timeZoneId));
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
	public Date[] currentWeek() throws Exception {
		
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
	public Date[] currentMonthDates() throws Exception {
		
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
	

public Date[] getClockFilterPreviousDates(String currentDate, Date empJoiningDate, String dateFormat, String dayWisetype) {
	
	Date[] searchDates = null;
	String[] str = currentDate.split(" To ");
	
	try {
		
		if(empJoiningDate != null) {
			  if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
		  			searchDates = previousDays(currentDate,dateFormat,empJoiningDate);
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
		  			searchDates = previousWeeks(str[0],dateFormat);
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
		  			searchDates = previousMonthDates(str[0],dateFormat);
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
		  			searchDates = previousYearDates(str[0],dateFormat);
		  		}
		}else {
			searchDates = new Date[2];
			  if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
				  searchDates[0] = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
				  searchDates[1] = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
			  }else {
					searchDates[0] = commonUtil.orgLocationDateFormat(dateFormat, str[0]);
					searchDates[1] = commonUtil.orgLocationDateFormat(dateFormat, str[1]);

			  }
		}
	    	  
	      
	} catch (Exception e) {
		e.printStackTrace();
	}
	return searchDates;
}

private Date[] previousYearDates(String startDate, String dateFormat) {
	Date current = commonUtil.orgLocationDateFormat(dateFormat, startDate);

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(current);
    calendar.add(Calendar.YEAR, -1);
    int min = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
    calendar.set(Calendar.DAY_OF_YEAR, min);
    
    Date st = calendar.getTime();

    int max = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    calendar.set(Calendar.DAY_OF_YEAR, max);
    
    Date end = calendar.getTime();
    
    Date[] dates = new Date[2];
    dates[0] = st;
    dates[1] = end;
	return dates;
}

private Date[] previousMonthDates(String startDate, String dateFormat) {
	
	Date current = commonUtil.orgLocationDateFormat(dateFormat, startDate);

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(current);
    calendar.add(Calendar.MONTH, -1);
    int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
    calendar.set(Calendar.DAY_OF_MONTH, min);
    
    Date st = calendar.getTime();

    int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    calendar.set(Calendar.DAY_OF_MONTH, max);
    
    Date end = calendar.getTime();
    
    Date[] dates = new Date[2];
    dates[0] = st;
    dates[1] = end;
	return dates;
	
}

private Date[] previousWeeks(String startDate, String dateFormat) {
	Date[] dates = null;
	
	Date current = commonUtil.orgLocationDateFormat(dateFormat, startDate);
    Calendar c = Calendar.getInstance();
    c.setTime(current);
    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
    c.add(Calendar.DATE, -i - 6);
    Date start = c.getTime();
    c.add(Calendar.DATE, 6);
    Date end = c.getTime();
    
    dates = new Date[2];
    dates[0] = start;
    dates[1] = end;
	return dates;
}

private Date[] previousDays(String currentDate, String dateFormat, Date empJoiningDate) {
	
	  Date[] dates = null;
	  Date current = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
	  if(empJoiningDate != null) {
		  
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(current);
		  calendar.add(Calendar.DAY_OF_YEAR, -1);
		 Date prevoiusDate = calendar.getTime();
		  if(prevoiusDate.after(empJoiningDate) || prevoiusDate.equals(empJoiningDate)) {
	  	    	dates = new Date[2];
	  	   		dates[0] = prevoiusDate;
	  	   		dates[1] = prevoiusDate;
	  	       }
	  }else {
		  dates = new Date[2];
  	  	   dates[0] = current;
  	  	   dates[1] = current;
	  }
	return dates;
}

public Date[] getClockFilterNextDates(String currentDate, Date empJoiningDate, String dateFormat, String dayWiseType) {
	
	Date[] searchDates = null;
	String[] str = currentDate.split(" To ");
	
	try {
		
		if(empJoiningDate != null) {
			  if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
		  			searchDates = nextDays(currentDate,dateFormat,empJoiningDate);
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
		  			searchDates = nextWeeks(currentDate,dateFormat);
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
		  			searchDates = nextMonthDates(str[1],dateFormat);
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
		  			searchDates = nextYearDates(str[1],dateFormat);
		  		}
		}else {
			searchDates = new Date[2];
			  if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
				  searchDates[0] = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
				  searchDates[1] = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
			  }else {
					searchDates[0] = commonUtil.orgLocationDateFormat(dateFormat, str[0]);
					searchDates[1] = commonUtil.orgLocationDateFormat(dateFormat, str[1]);

			  }
		}
	    	  
	      
	} catch (Exception e) {
		e.printStackTrace();
	}
	return searchDates;
	
	
}

private Date[] nextYearDates(String startDate, String dateFormat) {
	Date[] dates = null;
	Date current = commonUtil.orgLocationDateFormat(dateFormat, startDate);

	Calendar calendar = Calendar.getInstance();
    Date curDate = calendar.getTime();

	calendar.setTime(current);
    calendar.add(Calendar.YEAR, 1);
    int min = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
    calendar.set(Calendar.DAY_OF_YEAR, min);
    
    Date st = calendar.getTime();

    int max = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    calendar.set(Calendar.DAY_OF_YEAR, max);
    
    Date end = calendar.getTime();
    if(st.before(curDate) || end.before(curDate)) {
    	dates = new Date[2];
        dates[0] = st;
        dates[1] = end;
    }
    
	return dates;
}

private Date[] nextMonthDates(String startDate, String dateFormat) {
	Date[] dates = null;
	Date current = commonUtil.orgLocationDateFormat(dateFormat, startDate);

	Calendar calendar = Calendar.getInstance();
    Date curDate = calendar.getTime();

	
	calendar.setTime(current);
    calendar.add(Calendar.MONTH, 1);
    int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
    calendar.set(Calendar.DAY_OF_MONTH, min);
    
    Date st = calendar.getTime();

    int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    calendar.set(Calendar.DAY_OF_MONTH, max);
    
    Date end = calendar.getTime();
    
    if(st.before(curDate) || end.before(curDate)) {
        dates = new Date[2];
        dates[0] = st;
        dates[1] = end;
    }
	return dates;
	
}

private Date[] nextWeeks(String currentDate, String dateFormat) {
	Date[] dates = null;
	
	String[] str = currentDate.split(" To ");
	
	Date current = commonUtil.orgLocationDateFormat(dateFormat, str[1]);
    Calendar c = Calendar.getInstance();
    Date curDate = c.getTime();
    c.setTime(current);
//    int i = c.get(Calendar.DAY_OF_WEEK) + c.getFirstDayOfWeek();
    c.add(Calendar.DATE,1);
    Date start = c.getTime();
    c.add(Calendar.DATE, 6);
    Date end = c.getTime();
    
    Date startD = commonUtil.orgLocationDateFormat(dateFormat, str[0]);
    Date endD = commonUtil.orgLocationDateFormat(dateFormat, str[1]);
    
    if(startD.before(curDate) && endD.before(curDate)) {
  	  dates = new Date[2];
	    dates[0] = start;
	    dates[1] = end;
    }
    
	return dates;
}

private Date[] nextDays(String currentDate, String dateFormat, Date empJoiningDate) {
	 Date[] dates = null;
	 try {
		 Calendar calendar = Calendar.getInstance();
		 Date curDate = calendar.getTime();
		  Date current = commonUtil.orgLocationDateFormat(dateFormat, currentDate);
		  if(empJoiningDate != null) {
			  
			  
			  calendar.setTime(current);
			  calendar.add(Calendar.DAY_OF_YEAR, 1);
			 Date nextDate = calendar.getTime();
			  if(nextDate.before(curDate)) {
		  	    	dates = new Date[2];
		  	   		dates[0] = nextDate;
		  	   		dates[1] = nextDate;
		  	       }
		  }else {
			  if(current.before(curDate)) {
				  dates = new Date[2];
		  	  	   dates[0] = current;
		  	  	   dates[1] = current;
			  }
		  }
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return dates;
}

public List<String> getAllDatesBetweenTwoDates(OrgLocations orgLocation, Date[] dateArray) throws Exception {
	
	List<String> dates = new ArrayList<>();
	dates.add("Employee Name");
	Calendar calendar = new GregorianCalendar();
    calendar.setTime(dateArray[0]);

    while (calendar.getTime().before(dateArray[1]) || calendar.getTime().equals(dateArray[1]))
    {
        Date result = calendar.getTime();
        dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), result));
        calendar.add(Calendar.DATE, 1);
    }
    return dates;
}

public List<String> getAllMonthsOfCurrentYear(OrgLocations orgLocation,Date[] dateArray) {
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	
	List<String> dates = new ArrayList<>();
	dates.add("Employee Name");
	 Calendar calendar = new GregorianCalendar();
	    calendar.setTime(dateArray[0]);
	     
	    Calendar endCalendar = new GregorianCalendar();
	    endCalendar.setTime(dateArray[1]);
	 
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        String convertedDate = sdf.format(result);
	        dates.add(convertedDate);
	        calendar.add(Calendar.MONTH, 1);
	    }
	return dates;
}

public Date[] getClockFilterPreviousDates(String currentDate, Date empJoiningDate, OrgLocations location, String dayWisetype) {
	
	Date[] searchDates = null;
	String[] str = currentDate.split(" To ");
	
	try {
		
		if(empJoiningDate != null) {
			  if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
		  			searchDates = previousDays(currentDate,location.getDateFormat(),empJoiningDate);
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
		  			searchDates = previousWeeks(str[0],location.getDateFormat());
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
		  			searchDates = previousMonthDates(str[0],location.getDateFormat());
		  		}else if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
		  			searchDates = previousYearDates(str[0],location.getDateFormat());
		  		}
		}else {
			searchDates = new Date[2];
			  if(dayWisetype.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
				  searchDates[0] = commonUtil.orgLocationDateFormat(location.getDateFormat(), currentDate);
				  searchDates[1] = commonUtil.orgLocationDateFormat(location.getDateFormat(), currentDate);
			  }else {
					searchDates[0] = commonUtil.orgLocationDateFormat(location.getDateFormat(), str[0]);
					searchDates[1] = commonUtil.orgLocationDateFormat(location.getDateFormat(), str[1]);

			  }
		}
	    	  
	      
	} catch (Exception e) {
		e.printStackTrace();
	}
	return searchDates;
}

public Date[] getClockFilterNextDates(String currentDate, Date empJoiningDate, OrgLocations location, String dayWiseType) {
	
	Date[] searchDates = null;
	String[] str = currentDate.split(" To ");
	
	try {
		
		if(empJoiningDate != null) {
			  if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
		  			searchDates = nextDays(currentDate,location.getDateFormat(),empJoiningDate);
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
		  			searchDates = nextWeeks(currentDate,location.getDateFormat());
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
		  			searchDates = nextMonthDates(str[1],location.getDateFormat());
		  		}else if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
		  			searchDates = nextYearDates(str[1],location.getDateFormat());
		  		}
		}else {
			searchDates = new Date[2];
			  if(dayWiseType.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
				  searchDates[0] = commonUtil.orgLocationDateFormat(location.getDateFormat(), currentDate);
				  searchDates[1] = commonUtil.orgLocationDateFormat(location.getDateFormat(), currentDate);
			  }else {
					searchDates[0] = commonUtil.orgLocationDateFormat(location.getDateFormat(), str[0]);
					searchDates[1] = commonUtil.orgLocationDateFormat(location.getDateFormat(), str[1]);

			  }
		}
	    	  
	      
	} catch (Exception e) {
		e.printStackTrace();
	}
	return searchDates;
	
	
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
	
}
