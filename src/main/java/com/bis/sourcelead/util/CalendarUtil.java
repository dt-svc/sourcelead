package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Weeks;

import com.bis.sourcelead.constants.Constants;


public  class CalendarUtil {	
	
	
	public static double getCombinedHours(double hours1,double hours2){
		double cthHours = hours1;
		double cthHoursValue = (int) cthHours;
		double cthMinutesValue = cthHours - cthHoursValue;
		double totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

		double wthHours = hours2;
		double wthHoursValue = (int) wthHours;
		double wthMinutesValue = wthHours - wthHoursValue;
		double totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

		double totalMinutes = totalCthMinutes+totalWthMinutes;

		double actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
		double actualMinutes = (totalMinutes % 60)/100;

		String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
		
		
		return Double.parseDouble(actualTimeString);
	}


	/*public static void main(String[] args){
		Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
		currentCal.setLenient(false);
		currentCal.setTime(new Date());
		System.out.println(currentCal.getTime());
		System.out.println(new Date());
	}*/
	/*public static void main(String[] args){
		Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
		currentCal.setLenient(false);
		currentCal.setTime(new Date());
		System.out.println("<<<<======Current Week Dates===========>>>>>>>>>>");
		Calendar currentMonday = getCurrentWeekDayByCalendar(currentCal, "MON");
		System.out.println("Current-Monday>>>>>>>>>>>>"+currentMonday.getTime());
		Calendar currentTuesday = getCurrentWeekDayByCalendar(currentCal, "TUE");
		System.out.println("Current-Tuesday>>>>>>>>>>>>"+currentTuesday.getTime());
		Calendar currentWedday = getCurrentWeekDayByCalendar(currentCal, "WED");
		System.out.println("Current-Wedday>>>>>>>>>>>>"+currentWedday.getTime());
		Calendar currentTursday = getCurrentWeekDayByCalendar(currentCal, "THU");
		System.out.println("Current-Thursday>>>>>>>>>>>>"+currentTursday.getTime());
		Calendar currentFriday = getCurrentWeekDayByCalendar(currentCal, "FRI");
		System.out.println("Current-Friday>>>>>>>>>>>>"+currentFriday.getTime());
		Calendar currentSatday = getCurrentWeekDayByCalendar(currentCal, "SAT");
		System.out.println("Current-Saturday>>>>>>>>>>>>"+currentSatday.getTime());
		Calendar currentSunday = getCurrentWeekDayByCalendar(currentCal, "SUN");
		System.out.println("Current-Sunday>>>>>>>>>>>>"+currentSunday.getTime());
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");


		System.out.println("<<<<======Next Week Dates===========>>>>>>>>>>");
		Calendar nextMonday =  getNextWeekDayByCalendar(currentCal, "MON");
		Calendar nextTuesday = getNextWeekDayByCalendar(currentCal, "TUE");
		Calendar nextWedday =  getNextWeekDayByCalendar(currentCal, "WED");
		Calendar nextTursday = getNextWeekDayByCalendar(currentCal, "THU");
		Calendar nextFriday =  getNextWeekDayByCalendar(currentCal, "FRI");
		Calendar nextSatday =  getNextWeekDayByCalendar(currentCal, "SAT");
		Calendar nextSunday =  getNextWeekDayByCalendar(currentCal, "SUN");

		System.out.println("Next-Monday>>>>>>>>>>>>"+   nextMonday.getTime());
		System.out.println("Next-Tuesday>>>>>>>>>>>>"+  nextTuesday.getTime());
		System.out.println("Next-Wedday>>>>>>>>>>>>"+   nextWedday.getTime());
		System.out.println("Next-Thursday>>>>>>>>>>>>"+ nextTursday.getTime());
		System.out.println("Next-Friday>>>>>>>>>>>>"+   nextFriday.getTime());
		System.out.println("Next-Saturday>>>>>>>>>>>>"+ nextSatday.getTime());
		System.out.println("Next-Sunday>>>>>>>>>>>>"+   nextSunday.getTime());
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");

		System.out.println("<<<<======Previous Week Dates===========>>>>>>>>>>");     
		Calendar previousMonday =  getPreviousWeekDayByCalendar(currentCal, "MON");       
		Calendar previousTuesday = getPreviousWeekDayByCalendar(currentCal, "TUE");       
		Calendar previousWedday =  getPreviousWeekDayByCalendar(currentCal, "WED");       
		Calendar previousTursday = getPreviousWeekDayByCalendar(currentCal, "THU");       
		Calendar previousFriday =  getPreviousWeekDayByCalendar(currentCal, "FRI");       
		Calendar previousSatday =  getPreviousWeekDayByCalendar(currentCal, "SAT");       
		Calendar previousSunday =  getPreviousWeekDayByCalendar(currentCal, "SUN");      
		System.out.println("Previous-Monday>>>>>>>>>>>>"+   previousMonday.getTime()); 
		System.out.println("Previous-Tuesday>>>>>>>>>>>>"+  previousTuesday.getTime());
		System.out.println("Previous-Wedday>>>>>>>>>>>>"+   previousWedday.getTime()); 
		System.out.println("Previous-Thursday>>>>>>>>>>>>"+ previousTursday.getTime());
		System.out.println("Previous-Friday>>>>>>>>>>>>"+   previousFriday.getTime()); 
		System.out.println("Previous-Saturday>>>>>>>>>>>>"+ previousSatday.getTime()); 
	    System.out.println("Previous-Sunday>>>>>>>>>>>>"+   previousSunday.getTime()); 
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");  


		System.out.println("<<<<======Current W/O Week Dates===========>>>>>>>>>>");
		Date currentWithMonday = getCurrentWeekDay( "MON");
		Date currentWithTuesday = getCurrentWeekDay( "TUE");
		Date currentWithWedday = getCurrentWeekDay( "WED");
		Date currentWithTursday = getCurrentWeekDay( "THU");
		Date currentWithFriday = getCurrentWeekDay( "FRI");
		Date currentWithSatday = getCurrentWeekDay( "SAT");
		Date currentWithSunday = getCurrentWeekDay( "SUN");
		System.out.println("Current-Without-Calendar-Monday>>>>>>>>>>>>"+   currentWithMonday);
		System.out.println("Current-Without-Calendar-Tuesday>>>>>>>>>>>>"+  currentWithTuesday);
		System.out.println("Current-Without-Calendar-Wedday>>>>>>>>>>>>"+   currentWithWedday);
		System.out.println("Current-Without-Calendar-Thursday>>>>>>>>>>>>"+ currentWithTursday);
		System.out.println("Current-Without-Calendar-Friday>>>>>>>>>>>>"+   currentWithFriday);
		System.out.println("Current-Without-Calendar-Saturday>>>>>>>>>>>>"+ currentWithSatday);
		System.out.println("Current-Without-Calendar-Sunday>>>>>>>>>>>>"+   currentWithSunday);
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");      


		System.out.println("<<<<======Next W/O Week Dates===========>>>>>>>>>>");
		Date nextWithMonday = getNextWeekDay( "MON");
		Date nextWithTuesday = getNextWeekDay( "TUE");
		Date nextWithWedday = getNextWeekDay( "WED");
		Date nextWithTursday = getNextWeekDay( "THU");
		Date nextWithFriday = getNextWeekDay( "FRI");
		Date nextWithSatday = getNextWeekDay( "SAT");
	    Date nextWithSunday = getNextWeekDay( "SUN");
		System.out.println("Next-Without-Calendar-Monday>>>>>>>>>>>>"+   nextWithMonday);
		System.out.println("Next-Without-Calendar-Tuesday>>>>>>>>>>>>"+  nextWithTuesday);
		System.out.println("Next-Without-Calendar-Wedday>>>>>>>>>>>>"+   nextWithWedday);
		System.out.println("Next-Without-Calendar-Thursday>>>>>>>>>>>>"+ nextWithTursday);
		System.out.println("Next-Without-Calendar-Friday>>>>>>>>>>>>"+   nextWithFriday);
		System.out.println("Next-Without-Calendar-Saturday>>>>>>>>>>>>"+ nextWithSatday);
		System.out.println("Next-Without-Calendar-Sunday>>>>>>>>>>>>"+   nextWithSunday);
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");      

		System.out.println("<<<<======Previous W/O Week Dates===========>>>>>>>>>>");
		Date previousWithMonday =  getPreviousWeekDay( "MON");
		Date previousWithTuesday = getPreviousWeekDay( "TUE");
		Date previousWithWedday =  getPreviousWeekDay( "WED");
		Date previousWithTursday = getPreviousWeekDay( "THU");
		Date previousWithFriday =  getPreviousWeekDay( "FRI");
		Date previousWithSatday =  getPreviousWeekDay( "SAT");
		Date previousWithSunday =getPreviousWeekDay( "SUN");
		System.out.println("Previous-Without-Calendar-Monday>>>>>>>>>>>>"+   previousWithMonday);
		System.out.println("Previous-Without-Calendar-Tuesday>>>>>>>>>>>>"+  previousWithTuesday);
		System.out.println("Previous-Without-Calendar-Wedday>>>>>>>>>>>>"+   previousWithWedday);
		System.out.println("Previous-Without-Calendar-Thursday>>>>>>>>>>>>"+ previousWithTursday);
		System.out.println("Previous-Without-Calendar-Friday>>>>>>>>>>>>"+   previousWithFriday);
		System.out.println("Previous-Without-Calendar-Saturday>>>>>>>>>>>>"+ previousWithSatday);
	    System.out.println("Previous-Without-Calendar-Sunday>>>>>>>>>>>>"+   previousWithSunday);
		System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");


		try {
			System.out.println("Monday After 4weeks>>>>>>>>>>>>"+CalendarUtil.getDateIncrementByincrement(currentMonday,28).getTime());

			System.out.println("<<<<======Week Days After 4Weeks===========>>>>>>>>>>");
			Date date4WithMonday = CalendarUtil.getDateIncrementByincrement(currentMonday,28).getTime();
			Date date4WithTuesday =CalendarUtil.getDateIncrementByincrement(currentTuesday,28).getTime();
			Date date4WithWedday = CalendarUtil.getDateIncrementByincrement(currentWedday,28).getTime();
			Date date4WithTursday = CalendarUtil.getDateIncrementByincrement(currentTursday,28).getTime();
			Date date4WithFriday = CalendarUtil.getDateIncrementByincrement(currentFriday,28).getTime();
			Date date4WithSatday = CalendarUtil.getDateIncrementByincrement(currentSatday,28).getTime();
		    Date date4WithSunday = CalendarUtil.getDateIncrementByincrement(currentSunday,28).getTime();
			System.out.println("4th-Week-Monday>>>>>>>>>>>>"+   date4WithMonday);
			System.out.println("4th-Week-Tuesday>>>>>>>>>>>>"+  date4WithTuesday);
			System.out.println("4th-Week-Wedday>>>>>>>>>>>>"+   date4WithWedday);
			System.out.println("4th-Week-Thursday>>>>>>>>>>>>"+ date4WithTursday);
			System.out.println("4th-Week-Friday>>>>>>>>>>>>"+   date4WithFriday);
			System.out.println("4th-Week-Saturday>>>>>>>>>>>>"+ date4WithSatday);
			System.out.println("4th-Week-Sunday>>>>>>>>>>>>"+   date4WithSunday);
			System.out.println("<<<<<<<<<<<<<<<<<<<<=================>>>>>>>>>>>>");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}*/


	/*public static Map<String, String> getStartAndEndDateForGivenFrequency(Date frequencyStartDate,Date frequencyEndDate) {
	 	 int monthDiff =monthsBetween(frequencyStartDate, frequencyEndDate);
	      Map<String, String> startEndDate = new LinkedHashMap<>();
	      Calendar Q1StartDate=Calendar.getInstance(TimeZone.getDefault());
		    Calendar Q1EndDate =Calendar.getInstance(TimeZone.getDefault());
		    for(int i =0;i<=monthDiff;i++){
		    if(i==0){
		    	Q1StartDate.setTime(frequencyStartDate);
		    	Q1EndDate.setTime(frequencyStartDate);
		    }else{
			    Q1StartDate.add(Calendar.MONTH,1);
			    Q1EndDate.add(Calendar.MONTH,1);
		    }
		    Q1StartDate.set(Calendar.DATE,1);
		    Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		    String StartDate = Constants.DMY.format(Q1StartDate.getTime());
		    String endDate = Constants.DMY.format(Q1EndDate.getTime());
		    startEndDate.put(StartDate,endDate);
	     }
		return startEndDate;
   }*/
	
	
	/***
	 *  getUTC formatted Date String For a given Date and Dateformat
	 * @param dateWithTime
	 * @param dateFormat
	 * @return
	 */
	public static String getUTCDateTimeStrForGivenDate(Date dateWithTime,String dateFormat) {
	    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String dateStr = utcFormat.format(dateWithTime);
        
        
        DateFormat currentTFormat = new SimpleDateFormat(dateFormat+" HH:mm:ss");
        currentTFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String converted_date = null;
		try {
			converted_date = currentTFormat.format(utcFormat.parse(dateStr));
		} catch (ParseException e) {}
	
	    return converted_date;
   }
	
	/***
	 *  getUTC formatted Date  For a given Date and Dateformat
	 * @param dateWithTime
	 * @param dateFormat
	 * @return
	 */
	public static Date getUTCDateTimeForGivenDate(Date dateWithTime,String dateFormat) {
		String converted_dateStr = getUTCDateTimeStrForGivenDate(dateWithTime, dateFormat);
		DateFormat df = new SimpleDateFormat(dateFormat+" HH:mm:ss");
		Date converted_date = null;
		try {
			converted_date = df.parse(converted_dateStr);
		}catch (Exception e) {}
	    return converted_date;
    }
	
	
	public static String getDateStrForGivenTimeZone(String timeZoneId,Date dateWithTime,String dateFormat) {
		    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateStr = utcFormat.format(dateWithTime);
	        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	        
	        DateFormat currentTFormat = new SimpleDateFormat(dateFormat);
	        currentTFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
	        String converted_date = null;
			try {
				converted_date = currentTFormat.format(utcFormat.parse(dateStr));
			} catch (ParseException e) {}
		
		    return converted_date;
	}
	public static Date getDateForGivenTimeZone(String timeZoneId,Date dateWithTime,String dateFormat) {
		String converted_dateStr = getDateStrForGivenTimeZone(timeZoneId, dateWithTime, dateFormat);
		DateFormat df = new SimpleDateFormat(dateFormat);
		Date converted_date = null;
		try {
			converted_date = df.parse(converted_dateStr);
		}catch (Exception e) {}
	    return converted_date;
	}
	
	
	public static String getDateTimeStrForGivenTimeZone(String timeZoneId,Date dateWithTime,String dateFormat) {
	    DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateStr = utcFormat.format(dateWithTime);
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        DateFormat currentTFormat = new SimpleDateFormat(dateFormat+" HH:mm:ss");
        currentTFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        String converted_date = null;
		try {
			converted_date = currentTFormat.format(utcFormat.parse(dateStr));
		} catch (ParseException e) {}
	
	    return converted_date;
   }
	
	public static Date getDateTimeForGivenTimeZone(String timeZoneId,Date dateWithTime,String dateFormat) {
		String converted_dateStr = getDateTimeStrForGivenTimeZone(timeZoneId, dateWithTime, dateFormat);
		DateFormat df = new SimpleDateFormat(dateFormat+" HH:mm:ss");
		Date converted_date = null;
		try {
			converted_date = df.parse(converted_dateStr);
		}catch (Exception e) {}
	    return converted_date;
	}
	
	public static String getTimesheetHoursDisplayFormat(Float hours2,Float totalMinutes){
		if(totalMinutes == null) {
			float wthHours = hours2;
			float wthHoursValue = (int) wthHours;
			float wthMinutesValue = wthHours - wthHoursValue;
		    totalMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);
		}
		float actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
		float actualMinutes = (totalMinutes % 60)/100;
		
		final DecimalFormat decimalFormat = new DecimalFormat("00.00");                
		String actualTimeString = null;
		if(actualMinutes != 0){
			actualTimeString = decimalFormat.format((actualHours+actualMinutes));
		}
		else{
			actualTimeString = decimalFormat.format(actualHours+actualMinutes);
		}
		return actualTimeString;
	}

	
	public static String getHalfTimesheetHoursDisplayFormat(Float hours2){
		float wthHours = hours2;
		float wthHoursValue = (int) wthHours;
		float wthMinutesValue = wthHours - wthHoursValue;
		float totalMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);


		float halfDayMinutes = totalMinutes/2;
		
		float actualHours = (int) (halfDayMinutes / 60); //since both are ints, you get an int
		float actualMinutes = (halfDayMinutes % 60)/100;
		
		final DecimalFormat decimalFormat = new DecimalFormat("00.00");                
		String actualTimeString = null;
		if(actualMinutes != 0){
			actualTimeString = decimalFormat.format((actualHours+actualMinutes));
		}
		else{
			actualTimeString = decimalFormat.format(actualHours+actualMinutes);
		}
		
		return actualTimeString;
	}
	
	
	public static LinkedHashSet<String> getMonthStringsFromStartDateAndEndDate(Date frequencyStartDate,Date frequencyEndDate) {
	 	 int monthDiff =monthsBetween(frequencyStartDate, frequencyEndDate);
	      LinkedHashSet<String> monthString = new LinkedHashSet<>();
	      Calendar Q1StartDate=Calendar.getInstance(TimeZone.getDefault());
		    for(int i =0;i<=monthDiff;i++){
		    if(i==0){
		    	Q1StartDate.setTime(frequencyStartDate);
		    }else{
			    Q1StartDate.add(Calendar.MONTH,1);
		    }
		    Q1StartDate.set(Calendar.DATE,1);
		    String StartDate = Constants.M.format(Q1StartDate.getTime());
		    monthString.add(StartDate);
	     }
		return monthString;
  }
	
	
	private static int getDayOffset(String day_Of_Week){
		int day_offset = 0;
		switch (day_Of_Week) {
		case "MON":  day_offset = Calendar.MONDAY;
		break;
		case "TUE":  day_offset = Calendar.TUESDAY;
		break;
		case "WED":  day_offset = Calendar.WEDNESDAY;
		break;
		case "THU":  day_offset = Calendar.THURSDAY;
		break;
		case "FRI":  day_offset = Calendar.FRIDAY;
		break;
		case "SAT":  day_offset = Calendar.SATURDAY;
		break;
		case "SUN":  day_offset = Calendar.SATURDAY+1;
		break;
		default:   day_offset = Calendar.SUNDAY;
		break;
		}
		return day_offset;
	}
	
	public static int getDayOffsetByWeekName(String day_Of_Week){
		int day_offset = 0;
		switch (day_Of_Week) {
		case "MON":  day_offset = Calendar.MONDAY;
		break;
		case "TUE":  day_offset = Calendar.TUESDAY;
		break;
		case "WED":  day_offset = Calendar.WEDNESDAY;
		break;
		case "THU":  day_offset = Calendar.THURSDAY;
		break;
		case "FRI":  day_offset = Calendar.FRIDAY;
		break;
		case "SAT":  day_offset = Calendar.SATURDAY;
		break;
		case "SUN":  day_offset = Calendar.SUNDAY;
		break;
		default:   day_offset = Calendar.SUNDAY;
		break;
		}
		return day_offset;
	}

	public static int monthsBetween(Date a, Date b) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		if (a.before(b)) {
			cal.setTime(a);
		} else {
			cal.setTime(b);
			b = a;
		}
		int c = 0;
		while (cal.getTime().before(b)) {
			cal.add(Calendar.MONTH, 1);
			c++;
		}
		return c - 1;
	}
	
	
	public static boolean isDateBetweenTwoDates(Date min,Date max,Date d){
		return (d.after(min) || d.equals(min))&& (d.before(max)|| d.equals(max));
	}

	public static Date getNextWeekDay(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			if(day_offset == 8){
				nowCal.add(Calendar.DATE, 14);
				nowCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				nowCal.add(Calendar.DATE, 7);
				nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			}
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	public static Date getNextWeekDayForTimesheet(String day_Of_Week,String dateFormart) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			DateFormat  sdf = new SimpleDateFormat(dateFormart);
			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			if(day_offset == 8){
				nowCal.add(Calendar.DATE, 14);
				nowCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			}
			else{
				nowCal.add(Calendar.DATE, 7);
				nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			}

			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date getCurrentWeekDay(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(nowCal.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			if(day_offset == 8){
				nowCal.add(Calendar.DATE, 7);
				nowCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			}
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getCurrentWeekDayForTimesheets(String day_Of_Week,String dateFormart) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			DateFormat  sdf = new SimpleDateFormat(dateFormart);
			String nowStr = sdf.format(nowCal.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			if(day_offset == 8){
				nowCal.add(Calendar.DATE, 7);
				nowCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			}
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Calendar getCurrentWeekDayByCalendarForTimesheets(Calendar calendar,String day_Of_Week,String dateFormart) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day_offset = getDayOffset(day_Of_Week);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat  sdf = new SimpleDateFormat(dateFormart);
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			SimpleDateFormat sf = new SimpleDateFormat("E");
			
			cal.setTime(nowDate);
			if(StringUtils.isNotEmpty(sf.format(nowDate)) && sf.format(nowDate).equalsIgnoreCase("SUN") && day_offset == 2) {
				cal.add(Calendar.DATE, -7);
				cal.set(Calendar.DAY_OF_WEEK, day_offset);
			}
			else {
				if(day_offset == 8){
					cal.add(Calendar.DATE, 7);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				}
				else{
					cal.set(Calendar.DAY_OF_WEEK, day_offset);
				}
			}
			return cal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getPreviousWeekDay(String day_Of_Week) {

		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);

			if(day_offset == 8){
				nowCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				nowCal.add(Calendar.DATE, -7);
				nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			}
			nowCal.getTime();
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	public static Calendar getPreviousWeekDayByCalendar(Calendar calendar,String day_Of_Week,String dateFormart) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day_offset = getDayOffset(day_Of_Week);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			cal.setTime(nowDate);
			if(day_offset == 8){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				cal.add(Calendar.DATE, -7);
				cal.set(Calendar.DAY_OF_WEEK, day_offset);
			}

			return cal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Calendar getCurrentWeekDayByCalendar(Calendar calendar,String day_Of_Week,String dateFormart) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day_offset = getDayOffset(day_Of_Week);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat  sdf = new SimpleDateFormat(dateFormart);
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			cal.setTime(nowDate);
			if(day_offset == 8){
				cal.add(Calendar.DATE, 7);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				cal.set(Calendar.DAY_OF_WEEK, day_offset);
			}

			return cal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Calendar getNextWeekDayByCalendar(Calendar calendar,String day_Of_Week,String dateFormart) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat  sdf = new SimpleDateFormat(dateFormart);
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			cal.setTime(nowDate);
			if(day_offset == 8){
				cal.add(Calendar.DATE, 14);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else{
				cal.add(Calendar.DATE, 7);
				cal.set(Calendar.DAY_OF_WEEK, day_offset);
			}

			return cal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Calendar getDateIncrementByincrement(Calendar calendar, int increment) {
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		nowCal.setTime(calendar.getTime());
		nowCal.add(Calendar.DAY_OF_MONTH, increment);
		return nowCal;
	}
	public static Date addDays(Date date, int days)
	{
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(nowCal.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.add(Calendar.DATE, days);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Date getDateFromDateAndTime(Date date,String timeInTwelveHoursFormat) {

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mma");
		Date time = null;
		Date requiredDate = null;
		try {
			time = parseFormat.parse(timeInTwelveHoursFormat);
			String[] hourAndMinutes = displayFormat.format(time).split(":");
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hourAndMinutes[0]));
			cal.set(Calendar.MINUTE,Integer.parseInt(hourAndMinutes[1]));
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
			requiredDate = cal.getTime();
		} catch (ParseException e) {
		}
		return requiredDate;
	}


	public static String get12FormatTimeFrom24FormatTime(String twentyHourFormatTime) {

		SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
		String twelveHourFormatTime = null;
		Date date;
		try {
			date = parseFormat.parse(twentyHourFormatTime);
			twelveHourFormatTime =displayFormat.format(date);
		} catch (ParseException e) {
		}
		return twelveHourFormatTime;
	}

	public static HashMap<String,Date> getMonthStartDateAndEndDateByMothPreference(Date startDate,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		dateList.put("STARTDATE", calendar.getTime());
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateList.put("ENDDATE", calendar.getTime());
		return dateList;
	}

	public static String getMonthAndYearPatternDateByMonthPreference(Date date,Integer monthPreference){
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthPreference);
		DateFormat format = Constants.MY;
		String dateStr = format.format(calendar.getTime());
		return dateStr;
	}

	public static String getYearPatternByYearPreference(Date date,Integer yearPreference){
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, yearPreference);
		DateFormat format = Constants.Y;
		String dateStr = format.format(calendar.getTime());
		return dateStr;
	}
	
	
	public static String getQuarterByDate(Date date){
		int month = date.getMonth()+1;
		String quarter = "";
		float fraction = month/3f; 
		if((0<fraction) && (fraction<=1)){
			quarter ="Q1";
		}
		else if((1<fraction) && (fraction<=2)){
			quarter ="Q2";
		}
		else if((2<fraction) && (fraction<=3)){
			quarter ="Q3";
		}
		else{
			quarter ="Q4";
		}
		return quarter;
	}


	public static LinkedHashMap<String,Date> getMonthStartDateAndEndDateFromGivenMonthString(String month){
		LinkedHashMap<String,Date> dateList = new LinkedHashMap<String,Date>();
		final DateFormat format = Constants.MY;
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		Date date;
		try {
			date = format.parse(month);
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			dateList.put("STARTDATE", calendar.getTime());

			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateList.put("ENDDATE", calendar.getTime());
		} catch (ParseException e) {}
		return dateList;
	}
	
	public static LinkedHashMap<String,Date> getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(String month,String year){
		LinkedHashMap<String,Date> dateList = new LinkedHashMap<String,Date>();
		final DateFormat format = Constants.M;
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		Date date;
		try {
			date = format.parse(month);
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, Integer.parseInt(year));
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			dateList.put("STARTDATE", calendar.getTime());

			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			dateList.put("ENDDATE", calendar.getTime());
		} catch (ParseException e) {}
		return dateList;
	}
	
	public static LinkedHashMap<String,Date> getPreviousMonthStartDateAndEndDateFromGivenStartDateForTsReports(Date startDate){
		LinkedHashMap<String,Date> dateList = new LinkedHashMap<String,Date>();
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(startDate);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.MONTH, -1);
		dateList.put("STARTDATE", calendar.getTime());

		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateList.put("ENDDATE", calendar.getTime());
		return dateList;
	}

	public static boolean isCurrentDateBetweenDateRange(String weekStartDate,String weekEndDate,String dateFormat) {
		Boolean flag = false;
		if(StringUtils.isNotEmpty(weekStartDate) && StringUtils.isNotEmpty(weekEndDate)){
			DateFormat format = new SimpleDateFormat(dateFormat);
			Date startDate = null;
			Date endDate = null;
			Date currentDate = null;
			try {
				currentDate = format.parse(format.format(new Date()));
				if(dateFormat.contains("-")){
					startDate = format.parse(weekStartDate);
					endDate = format.parse(weekEndDate);
				}
				else{
					startDate = format.parse(weekStartDate.replaceAll("-", "/"));
					endDate = format.parse(weekEndDate.replaceAll("-", "/"));
				}
				
				flag =   ((currentDate.after(startDate) && currentDate.before(endDate)) || (currentDate.equals(startDate)) || ((currentDate.equals(endDate))));
			} catch (ParseException e) {}
			
		}
		return flag;
	}

	
	public static boolean isGivenDateBetweenDateRange(String weekStartDate,String weekEndDate,String date,String dateFormat) {
		Boolean flag = false;
		if(StringUtils.isNotEmpty(weekStartDate) && StringUtils.isNotEmpty(weekEndDate)){
			DateFormat format = new SimpleDateFormat(dateFormat.replaceAll("-", "/"));
			Date startDate = null;
			Date endDate = null;
			Date currentDate = null;
			try {
				currentDate = format.parse(date.replaceAll("-", "/"));
				if(dateFormat.contains("-")){
					startDate = format.parse(weekStartDate.replaceAll("-", "/"));
					endDate = format.parse(weekEndDate.replaceAll("-", "/"));
				}
				else{
					startDate = format.parse(weekStartDate.replaceAll("-", "/"));
					endDate = format.parse(weekEndDate.replaceAll("-", "/"));
				}
				
				flag =   ((currentDate.after(startDate) && currentDate.before(endDate)) || (currentDate.equals(startDate)) || ((currentDate.equals(endDate))));
			} catch (ParseException e) {}
			
			
		}
		return flag;
	}
	
	public static boolean isGivenDateBetweenDateRange(Date startDate,Date endDate,Date currentDate) {
		return	 ((currentDate.after(startDate) && currentDate.before(endDate)) || (currentDate.equals(startDate)) || ((currentDate.equals(endDate))));
	}
	public static HashMap<String,Date> getMonthStartDateByGivenDate(Date date,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		dateList.put("STARTDATE", calendar.getTime());
		return dateList;
	}


	public static HashMap<String,Date> getMonthEndDateByGivenDate(Date date,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateList.put("ENDDATE", calendar.getTime());
		return dateList;
	}

	public static HashMap<String,Date> getMonthStartDateAnd15thDateByGivenDateAndMothPreference(Date startDate,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		dateList.put("STARTDATE", calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 15); 
		dateList.put("ENDDATE", calendar.getTime());
		return dateList;
	}

	public static HashMap<String,Date> getMonth16thDateAndEndDateByGivenDateAndMothPreference(Date sixteenthDate,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(sixteenthDate);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DAY_OF_MONTH, 16); 
		dateList.put("STARTDATE", calendar.getTime());
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateList.put("ENDDATE", calendar.getTime());
		return dateList;
	}



	public static HashMap<String,Date> getMonth15thDateByGivenDate(Date date,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DAY_OF_MONTH, 15); 
		dateList.put("STARTDATE", calendar.getTime());
		return dateList;
	}


	public static HashMap<String,Date> getMonth16thDateByGivenDate(Date date,Integer monthPreference){
		HashMap<String,Date> dateList = new HashMap<String,Date>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());   
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthPreference);
		calendar.set(Calendar.DAY_OF_MONTH, 16); 
		dateList.put("STARTDATE", calendar.getTime());
		return dateList;
	}

	//    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
	//	Date date = calendar.getTime();
	//	// 3 letter name form of the day
	//	System.out.println(new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()).toUpperCase());

	public static String currentDay()
	{
		try{
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			Date date = calendar.getTime();
			return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()).toUpperCase();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public static int getNumberOfWeeks(Date date1, Date date2){
		DateTime dateTime1 = new DateTime(date1);
		DateTime dateTime2 = new DateTime(date2);

		int weeks = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
		return weeks;
	}

	public static long getNumberOfDays(Date date1, Date date2){
		long diff = date2.getTime() - date1.getTime();
		long  numberofDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return numberofDays;

	}
	public static int getDayOfTheDate(Date date){
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	public static String getNextDate(String  curDate,String dateFormat) throws ParseException {
		final DateFormat format = new SimpleDateFormat(dateFormat);   //Constants.DF_DMY;(old logic)
		final Date date = format.parse(curDate);
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime()); 
	}
	
	public static Date getNextDate(Date  curDate) throws ParseException {
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(curDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime(); 
	}

	/*public static String getShortDateFromLongDate(String  curDate) throws ParseException {
		final DateFormat format =Constants.DF_DMY;
		final Date date = format.parse(curDate);
		final DateFormat Short_Format =Constants.SHORT_DF_STD_DM;
		return Short_Format.format(date); 
	}*/

	public static String getPreviousDateByDecrementor(String  curDate,int decrementor,String dateFormat) throws ParseException {
		final DateFormat format = new SimpleDateFormat(dateFormat);  //Constants.DF_DMY;(old format)
		final Date date = format.parse(curDate.replaceAll("/", "-"));
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -decrementor);
		return format.format(calendar.getTime()); 
	}


	public static LinkedList<String> getAllMonths()
	{
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] arrayOfMonthsNames = dfs.getMonths();
		LinkedList<String> monthsList = new LinkedList<>(Arrays.asList(arrayOfMonthsNames));
		monthsList.remove(12);
		return monthsList;
	}
	
	public static Map<String, Date> getStartAndEndDateFromMonthAndYear(int i,String years) {
	      Map<String, Date> dateList = new LinkedHashMap<>();
		    
	      Calendar Q1StartDate=Calendar.getInstance(TimeZone.getDefault());
		  Q1StartDate.set(Calendar.YEAR,Integer.parseInt(years));
	      Q1StartDate.set(Calendar.MONTH,i);
	      Q1StartDate.set(Calendar.DATE,1);
		   
		   
		  Calendar Q1EndDate =Calendar.getInstance(TimeZone.getDefault());
		  Q1EndDate.set(Calendar.YEAR,Integer.parseInt(years));
		  Q1EndDate.set(Calendar.MONTH, i);
		  Q1EndDate.set(Calendar.DATE, Q1EndDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		  dateList.put("STARTDATE",Q1StartDate.getTime());
		  dateList.put("ENDDATE", Q1EndDate.getTime());
	 	 return dateList;
	
    }
	public static LinkedHashSet<String> getYearsListFromCurrentDate(int previousYears,int priorYears){
		LinkedHashSet<String> YearsList = new LinkedHashSet<String>();

		for(int i =previousYears;i>0;i--){
			String date = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),-i);
			YearsList.add(date);
		}
		String currentDate = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),0);
		YearsList.add(currentDate);
		for(int i =1;i<=priorYears;i++){
			String date = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),i);
			YearsList.add(date);
		}
		return YearsList;
	}
	
	public static LinkedHashSet<String> getYearsListFromCurrentDate(int previousYears){
		LinkedHashSet<String> YearsList = new LinkedHashSet<String>();

		for(int i =previousYears;i>0;i--){
			String date = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),-i);
			YearsList.add(date);
		}
		String currentDate = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),0);
		YearsList.add(currentDate);
	
		Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
		if(Constants.DF_MM.format(currentCal.getTime()).equalsIgnoreCase("11") || Constants.DF_MM.format(currentCal.getTime()).equalsIgnoreCase("12")){
			String date = CalendarUtil.getYearPatternByYearPreference(CommonUtil.currentDate(),1);
			YearsList.add(date);
		}
		
		return YearsList;
	}

	public static String getCurrentYear()
	{

		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		DateFormat format =Constants.Y;
		return format.format(cal.getTime());
		//		return Year.now().getValue();
	}
	
	public static String getCurrentMonth()
	{
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		DateFormat format =Constants.M;
		return format.format(cal.getTime());
	}
	
	public static long getNumberOfDaysForReports(Date date1, Date date2){
		long diff = date2.getTime() - date1.getTime();
		long  numberofDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return numberofDays+1;
		
		/* long diff = date2.getTime() - date1.getTime();
		long  numberofDays = 0L;
		if(DateUtils.isSameDay(date1, date2)) {
			return numberofDays+1;
		}else {
			numberofDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if(numberofDays == 0) {
				return numberofDays+2;
			}else {
				return numberofDays+1;
			}
		
		}*/

	}
	
	public static int  getNumberOfBillableDates(Date assignDate, Date terminatedDate) {
	    int count = 0;
	    Calendar start = Calendar.getInstance(TimeZone.getDefault());
	    start.setTime(assignDate);
	    Calendar end = Calendar.getInstance(TimeZone.getDefault());
	    end.setTime(terminatedDate);
	    end.add(Calendar.DAY_OF_YEAR, 1); //Add 1 day to endDate to make sure endDate is included into the final list
	    while (start.before(end)) {
	    	 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
	    	 String day_Of_Date = simpleDateformat.format(start.getTime());
	    	 if(!day_Of_Date.equals("Sat") && !day_Of_Date.equals("Sun")) {
	    		 count++;
	    	 }
	        start.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return count;
	}

 public static List<Date> getListOfDaysBetweenTwoDates(Date assignDate, Date terminatedDate) {
    List<Date> result = new ArrayList<Date>();
    Calendar start = Calendar.getInstance(TimeZone.getDefault());
    start.setTime(assignDate);
    Calendar end = Calendar.getInstance(TimeZone.getDefault());
    end.setTime(terminatedDate);
    end.add(Calendar.DAY_OF_YEAR, 1); //Add 1 day to endDate to make sure endDate is included into the final list
    while (start.before(end)) {
        result.add(start.getTime());
        start.add(Calendar.DAY_OF_YEAR, 1);
    }
    return result;
}
 
 public static long getNumberOfDaysCount(Date date1, Date date2){
		long diff = date2.getTime() - date1.getTime();
		long  numberofDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
		return numberofDays;

	}
 public static String getMonthFromGivenDate(Date date){
	    SimpleDateFormat   simpleDateFormat = new SimpleDateFormat("MMMM");
		return simpleDateFormat.format(date);
	}
 public static String getYearFromGivenDate(Date date){
	    SimpleDateFormat   simpleDateFormat = new SimpleDateFormat("YYYY");
		return simpleDateFormat.format(date);
	}
 
 public static Date removeTime(Date date) {    
     Calendar cal = Calendar.getInstance(TimeZone.getDefault());  
     cal.setTime(date);  
     cal.set(Calendar.HOUR_OF_DAY, 0);  
     cal.set(Calendar.MINUTE, 0);  
     cal.set(Calendar.SECOND, 0);  
     cal.set(Calendar.MILLISECOND, 0);  
     return cal.getTime(); 
 }
  
 
 public static int numberOfDayOccurancesBetweenTwoDates(Date d1,Date d2,String day_Of_Week) {
	 Calendar c1 = Calendar.getInstance(TimeZone.getDefault());
	 c1.setTime(d1);
	  
	 Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
	 c2.setTime(d2);
	 int count = 0;
	  
	 while(c1.before(c2)|| c1.equals(c2)) {
	     if(day_Of_Week.equalsIgnoreCase("Sun") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Mon") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Tue") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Wed") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Thu") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Fri") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)
	    	    count++;
	     else if(day_Of_Week.equalsIgnoreCase("Sat") && c1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
	    	    count++;
	     c1.add(Calendar.DATE,1);
	     
	 }
	 return count;
 }
	public static Date addDaysForGenerateInvoice(Date date, int days)
	{
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getMonthStartDateAndEndDateWithPrefixByMonth(String month, int year, boolean getStartDate) {
		try {
			int calendarMonth = 0;
			switch (month.toUpperCase()) {
			case "JANUARY":
				calendarMonth = 0;
				break;
			case "FEBUARY":
				calendarMonth = 1;
				break;
			case "MARCH":
				calendarMonth = 2;
				break;
			case "APRIL":
				calendarMonth = 3;
				break;
			case "MAY":
				calendarMonth = 4;
				break;
			case "JUNE":
				calendarMonth = 5;
				break;
			case "JULY":
				calendarMonth = 6;
				break;
			case "AUGUST":
				calendarMonth = 7;
				break;
			case "SEPTEMBER":
				calendarMonth = 8;
				break;
			case "OCTOBER":
				calendarMonth = 9;
				break;
			case "NOVEMBER":
				calendarMonth = 10;
				break;
			case "DECEMBER":
				calendarMonth = 11;
				break;
			default:
				break;
			}

			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONDAY, calendarMonth);
			int startDate = c.getActualMinimum(Calendar.DAY_OF_MONTH);
			int endDate = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(getStartDate) {
				return startDate + getDatePrefix(startDate);
			} else {
				return endDate + getDatePrefix(endDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getDatePrefix(int date) {
		try {
			if (date == 1 || date == 21 || date == 31) {
				return "st";
			} else if (date == 2 || date == 22) {
				return "nd";
			} else if (date == 3 || date == 23) {
				return "rd";
			}
			return "th";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(getMonthStartDateAndEndDateWithPrefixByMonth("May", 2019, true));
	}	
 
}

