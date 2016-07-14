package com.bis.sourcelead.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public  class CalendarUtil {
	
	public static void main(String[] args) throws Exception{
		String testDate = "07-03-2016";
		SimpleDateFormat formatter =  new SimpleDateFormat("MM-dd-yyyy");
		Date date = null;
		try {
			date = formatter.parse(testDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
         Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		  startCal.setLenient(false);
		  startCal.setTime(date);
		  
		  System.out.println(CalendarUtil.getCurrentWeekDay("MON"));
		
		 Calendar startSunDay =  CalendarUtil.getCurrentWeekDayByCalendar(startCal, "SUN");
          
         System.out.println(startSunDay.getTime());
         System.out.println("===================================");
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,1).getTime()+"</n>"); 
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,2).getTime()+"</n>");
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,3).getTime()+"</n>"); 
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,4).getTime()+"</n>"); 
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,5).getTime()+"</n>"); 
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,6).getTime()+"</n>"); 
         System.out.println(CalendarUtil.getDateIncrementByincrement(startSunDay,7).getTime()+"</n>"); 
         
		
	}
	
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
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			nowCal.add(Calendar.DATE, dayOfWeek+day_offset-1);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
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
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			nowCal.add(Calendar.DATE, dayOfWeek+day_offset-1);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
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
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			// If dayOfWeek is 
			nowCal.add(Calendar.DATE, -dayOfWeek+day_offset);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
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
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			// If dayOfWeek is 
			nowCal.add(Calendar.DATE, -dayOfWeek+day_offset);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			return nowCal.getTime();
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
			int currentDayOFWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			if(currentDayOFWeek != Calendar.DAY_OF_WEEK ){
				int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
				nowCal.add(Calendar.DATE, -dayOfWeek);
			}
			
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			nowCal.add(Calendar.DATE, -dayOfWeek+day_offset);
			
			
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			nowCal.getTime();
			return nowCal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
     public static Calendar getPreviousWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
		  
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			int currentDayOFWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			if(currentDayOFWeek != Calendar.DAY_OF_WEEK ){
				int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
				nowCal.add(Calendar.DATE, -dayOfWeek);
			}
			
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			nowCal.add(Calendar.DATE, -dayOfWeek+day_offset);
			
			
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			return nowCal;
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
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			// If dayOfWeek is 
			calendar.add(Calendar.DATE, dayOfWeek+day_offset);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			return calendar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static Calendar getNextWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"), Locale.US);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String nowStr = sdf.format(calendar.getTime());
			Date nowDate = sdf.parse(nowStr);
			nowCal.setTime(nowDate);
			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);
			nowCal.add(Calendar.DATE, dayOfWeek+day_offset-1);
			//nowCal.setTimeZone(Constants.TIMEZONE_CENTRAL);
			return nowCal;
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
