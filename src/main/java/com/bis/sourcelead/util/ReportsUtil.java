package com.bis.sourcelead.util;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsUtil.class);
	
	/**
	 * Get from and to date of last 7 days
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last7Days() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, -7);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 15 days
	 * @return Date[]  Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last15DaysDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, -15);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 1 Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last1MonthDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 3 Months
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last3MonthDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -3);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}

	/**
	 * Get from and to date of last 1 Year
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] lastYearDates() {
		Date[] dates = new Date[2];
		Calendar calendarStart = Calendar.getInstance();
		int previousYear = calendarStart.get(Calendar.YEAR) - 1;
		calendarStart.set(Calendar.YEAR, previousYear);
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = calendarStart.getTime();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, previousYear);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		dates[1] = calendarEnd.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;

	}
	
	/**
	 * Get from and to date of current  Date
	 * 
	 * @return Date[] Date[0] = Start Date ,Date[1] = End date
	 */
	public Date[] yearToDate() {
		Date[] dates = new Date[2];
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dates[1] = cal.getTime();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;

	}

}
