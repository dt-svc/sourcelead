package com.bis.sourcelead.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public  class CalendarUtil {
	
	
	
	protected static int getDayOffset(String day_Of_Week){
		int day_offset = 0;
        switch (day_Of_Week) {
           case "MON":  day_offset = 1;
                    break;
           case "TUE":  day_offset = 2;
                    break;
           case "WED":  day_offset = 3;
                break;
           case "THU":  day_offset = 4;
               break;
           case "FRI":  day_offset = 5;
               break;
           case "SAT":  day_offset = 6;
                 break;
           case "SUN":  day_offset = 7;
                break;
           default:   day_offset = 0;
                    break;
       }
        return day_offset;
	}

	
	
	public static Date getNextWeekDay(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.add(Calendar.DATE, 7);
			nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static Date getNextWeekDayForTimesheet(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.add(Calendar.DATE, 7);
			nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date getCurrentWeekDay(String day_Of_Week) {
		  
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(nowCal.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getCurrentWeekDayForTimesheets(String day_Of_Week) {
		  
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nowStr = sdf.format(nowCal.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Calendar getCurrentWeekDayByCalendarForTimesheets(Calendar calendar,String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			calendar.setTime(nowDate);
			calendar.set(Calendar.DAY_OF_WEEK, day_offset);
			return calendar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getPreviousWeekDay(String day_Of_Week) {
		  
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			String nowStr = sdf.format(new Date().getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			nowCal.add(Calendar.DATE, -7);
			nowCal.set(Calendar.DAY_OF_WEEK, day_offset);
			nowCal.getTime();
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
     public static Calendar getPreviousWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
		  
    	 int day_offset = getDayOffset(day_Of_Week);
 		try {
 			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
 			String nowStr = sdf.format(calendar.getTime());
 			Date nowDate = sdf.parse(nowStr);
 			calendar.setTime(nowDate);
 			calendar.add(Calendar.DATE, -7);
 			calendar.set(Calendar.DAY_OF_WEEK, day_offset);
 			return calendar;
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		return null;
	}
	
	public static Calendar getCurrentWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			calendar.setTime(nowDate);
			calendar.set(Calendar.DAY_OF_WEEK, day_offset);
			return calendar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static Calendar getNextWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
        int day_offset = getDayOffset(day_Of_Week);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			calendar.setTime(nowDate);
			calendar.add(Calendar.DATE, 7);
			calendar.set(Calendar.DAY_OF_WEEK, day_offset);
			return calendar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static Calendar getDateIncrementByincrement(Calendar calendar, int increment) throws Exception {
		Calendar nowCal = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
		nowCal.setTime(calendar.getTime());
		nowCal.add(Calendar.DAY_OF_MONTH, increment);
		return nowCal;
	}
    public static Date addDays(Date date, int days)
    {
        Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
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
	 
	
	
	

}
