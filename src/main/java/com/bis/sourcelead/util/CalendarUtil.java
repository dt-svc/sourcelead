package com.bis.sourcelead.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public  class CalendarUtil {
	
	
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
	
	
	
	protected static int getDayOffset(String day_Of_Week){
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
	
	
	public static Date getNextWeekDayForTimesheet(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
	
	public static Date getCurrentWeekDayForTimesheets(String day_Of_Week) {
		int day_offset = getDayOffset(day_Of_Week);
		Calendar nowCal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
	
	public static Calendar getCurrentWeekDayByCalendarForTimesheets(Calendar calendar,String day_Of_Week) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day_offset = getDayOffset(day_Of_Week);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
	
	
     public static Calendar getPreviousWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
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
	
	public static Calendar getCurrentWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day_offset = getDayOffset(day_Of_Week);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
	

	public static Calendar getNextWeekDayByCalendar(Calendar calendar,String day_Of_Week) {
        int day_offset = getDayOffset(day_Of_Week);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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
	
	public static Calendar getDateIncrementByincrement(Calendar calendar, int increment) throws Exception {
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
	 
//    Calendar calendar = Calendar.getInstance();
//	Date date = calendar.getTime();
//	// 3 letter name form of the day
//	System.out.println(new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()).toUpperCase());
	
    public static String currentDay()
    {
       try{
    	    Calendar calendar = Calendar.getInstance();
    		Date date = calendar.getTime();
    	return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()).toUpperCase();
       }catch(Exception ex){
    	   ex.printStackTrace();
       }
	return null;
    }
	 

}