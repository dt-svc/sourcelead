package com.bis.sourcelead.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.impl.InvoiceServiceImpl;
import com.google.gson.Gson;
/**
 * 
 * @author Rajashekar
 *
 */
@Component
public class ConversionUtil {
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConversionUtil.class);
	
    private static final String API_PROVDIER = "http://api.fixer.io/";
	
	/**
	 * converting rate into per hour
	 * @param rate
	 * @param ratePer
	 * @return float
	 */
	public float getHourlyRate(String rate, String ratePer)
	{
		switch (ratePer) {
			case Constants.RATE_TYPE_HOURLY:
				return Float.parseFloat(rate);
			case Constants.RATE_TYPE_WEEKLY:
				return Float.parseFloat(rate) / 40;
			case Constants.RATE_TYPE_BI_WEEKLY:
				return Float.parseFloat(rate) / 80;
			case Constants.RATE_TYPE_SEMI_MONTHLY:
				return Float.parseFloat(rate) / 87;
			case Constants.RATE_TYPE_MONTHLY: 
				return Float.parseFloat(rate) / 184;
			case Constants.RATE_TYPE_BI_MONTHLY:
				return Float.parseFloat(rate) / 368;
			default:
				return Float.parseFloat(rate) / 2088;
		}
	}
	
	/**
	 * convert currency from one type to another
	 * @param fromCurrencyCode
	 * @param toCurrencyCode
	 * @param amount
	 * @return float
	 */
	public  float convert(String fromCurrencyCode, String toCurrencyCode, float amount)
	{
		
		if ((fromCurrencyCode != null && !fromCurrencyCode.isEmpty())
				&& (toCurrencyCode != null && !toCurrencyCode.isEmpty())) {
			
			CurrencyConversionResponse response = getResponse(API_PROVDIER + "/latest?base="
					+ fromCurrencyCode);
			if (response != null) {
				
				String rate = response.getRates().get(toCurrencyCode);
				float conversionRate = Float.valueOf((rate != null) ? rate : "0.0");
				return conversionRate * amount;
			}
		}
		return 0;
	}
	
	
	// Method to get the response from API
	private static CurrencyConversionResponse getResponse(String strUrl)
	{
		
		CurrencyConversionResponse response = null;
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer();
		if (strUrl == null || strUrl.isEmpty()) {
			logger.info("Application Error");
			return null;
		}
		URL url;
		try {
			url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream stream = connection.getInputStream();
			int data = stream.read();
			while (data != -1) {
				
				sb.append((char) data);
				data = stream.read();
			}
			stream.close();
			response = gson.fromJson(sb.toString(), CurrencyConversionResponse.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String getVendorOrgCurrencyCode(String country)
	{
	  String countryCurrencyCode = null;
      if (Constants.COUNTRY_INDIA.equals(country)) {
    	  countryCurrencyCode =  Constants.CURRENCYCODE_INR;
		}
		else if (Constants.COUNTRY_AUSTRALIA.equals(country)) {
			 countryCurrencyCode =  Constants.CURRENCYCODE_AUD;
		}
		else if (Constants.COUNTRY_UNITEDSTATES.equals(country)) {
			 countryCurrencyCode =  Constants.CURRENCYCODE_USD;
		}
		else if (Constants.COUNTRY_SINGAPORE.equals(country)) {
			countryCurrencyCode =  Constants.CURRENCYCODE_SGD;
		}
		else if (Constants.COUNTRY_UNITEDKINGDOM.equals(country)) {
			countryCurrencyCode =  Constants.CURRENCYCODE_GBP;
		}
		else if (Constants.COUNTRY_FRANCE.equals(country)) {
			countryCurrencyCode =  Constants.CURRENCYCODE_EUR;
		}
      return countryCurrencyCode;
	}
	
	/**
	 * converts string date java.util Date
	 * @param dateStr
	 * @return Date
	 * @throws ParseException
	 */
	public Date getDateFromString(String dateStr) throws ParseException{
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateStr);
	}

	/**
	 * converts string date java.util Date
	 * @param dateStr
	 * @return Date
	 * @throws ParseException
	 */
	public Date getDateFromMMDDYYY(String dateStr) throws ParseException{
		String pattern = "MM/dd/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateStr);
	}
	/** 
	 * Get Date as UTC Format
	 * @param orgLocation
	 * @param monthStartDate
	 * @param outputFormatter
	 * @return
	 * @throws Exception
	 */
	public Date getUtcDateFormat(OrgLocations orgLocation, LocalDate monthStartDate,DateTimeFormatter outputFormatter) throws Exception{
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateUTC = null;
		String endDate = outputFormatter.print(monthStartDate);
		String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
		dateUTC = TZ_DF_TS.parse(endDateString);
		return dateUTC;
		
	}
	
	/**
	 * Get the quarter months Start date and End date
	 * 
	 * @param localDate
	 * @return
	 */
	public LocalDate[] getThisMonthQuarterDates(LocalDate localDate)
	{
		LocalDate[] dates = new LocalDate[2];
		// Get the month of the current date
		int monthDate = localDate.getMonthOfYear();
		// 1st - 3rd months represent quarter 1
		// 4th - 6th months represent quarter 2
		// 7th - 9th months represent quarter 3
		// 10th - 12th months represent quarter 4
		int startDate = 0;
		int endDate = 0;
		if (monthDate >= 1 && monthDate <= 3) {
			startDate = monthDate - 1;
			endDate = monthDate - 3;
		}
		else if (monthDate >= 4 && monthDate <= 6) {
			startDate = monthDate - 4;
			endDate = monthDate - 6;
		}
		else if (monthDate >= 7 && monthDate <= 9) {
			startDate = monthDate - 7;
			endDate = monthDate - 9;
		}
		else if (monthDate >= 10 && monthDate <= 12) {
			startDate = monthDate - 10;
			endDate = monthDate - 12;
		}
		startDate = Math.abs(startDate);
		endDate = Math.abs(endDate);
		dates[0] = localDate.minusMonths(startDate).dayOfMonth().withMinimumValue();
		dates[1] = localDate.plusMonths(endDate).dayOfMonth().withMaximumValue();
		return dates;
	}
	
	/**
	 * converts java.util Date string date 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static String getDateAsString(Date date){
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		 return dateFormat.format(date);

	}
   /**
    * Get current Year start Date and End date of current year
    * @return
    */
	public LocalDate[] getThisYearStartDateEndDate()
	{
		LocalDate[] dates = new LocalDate[2];
		Calendar calDate = Calendar.getInstance();
		calDate.set(Calendar.DAY_OF_YEAR, 1);
		Date sDate = calDate.getTime();
		dates[0] = new LocalDate(sDate);
		calDate.set(Calendar.DAY_OF_YEAR, calDate.getActualMaximum(Calendar.DAY_OF_YEAR));
		Date eDate = calDate.getTime();
		dates[1] = new LocalDate(eDate);
		return dates;
	}
     /**
      * Get startDateUtc and endDateUtc by passing previous days (- numberof days)
      * @param numberOfDays
      * @param orgLocation
      * @param startDateOutputFormatter
      * @param endDateOutputFormatter
      * @return  Date[]
      * @throws Exception
      */
	public Date[] getPreviousDates(int numberOfDays, OrgLocations orgLocation, DateTimeFormatter startDateOutputFormatter, DateTimeFormatter endDateOutputFormatter) throws Exception
	{
		Date[] dates = new Date[2];
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// start date
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = startDateOutputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		dates[0] = TZ_DF_TS.parse(startDateString);
		// End Date
		String endDate = endDateOutputFormatter.print(zoned);
		String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
		dates[1] = TZ_DF_TS.parse(endDateString);
		return dates;
	}
    /**
     * Get financial start Month date
     * @return LocalDate
     */
	public LocalDate getFinMonthFromDate()
	{
		Date date = new Date();
		int year = getYearFromDate(date);
		logger.info("Financial Year : " + year + "-" + (year + 1));
		logger.info("current month is " + date.getMonth());
		// getMonthFromDate(year,date.getMonth());
		int month = date.getMonth();
		if (month >= 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, 3); // 11 = december
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			LocalDate localDate = LocalDate.fromDateFields(cal.getTime());
			return localDate;
		}
		return null;
	}
	/**
	 * Get year from current date
	 * @param date
	 * @return int
	 */
	  public static int getYearFromDate(Date date) {
	        int result = -1;
	        if (date != null) {
	        	    Calendar prevYear = Calendar.getInstance();
	        	    prevYear.add(Calendar.YEAR, -1);
	        	    return prevYear.get(Calendar.YEAR);
	        }
	        return result;
	    }
    /**
     * Get financial year end date
     * @return LocalDate
     */
	public LocalDate getFinMonthEndDate()
	{
		Date date = new Date();
		int year = getYearFromDate(date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year + 1);
		cal.set(Calendar.MONTH, 2); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		LocalDate localDate = LocalDate.fromDateFields(cal.getTime());
		return localDate;
	}
	/**
	 * Other country financial years
	 * @return LocalDate[]
	 */
	public LocalDate[] getOtherCountryFinYeraDates()
	{
		LocalDate[] dates = new LocalDate[2];
		int year = getYearFromDate(new Date());
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.YEAR, year);
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		// returning the first date
		dates[0] = LocalDate.fromDateFields(calendarStart.getTime());
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, year);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		// returning the last date
		dates[1] = LocalDate.fromDateFields(calendarEnd.getTime());
		return dates;
	}

	/**
	 * Return all dates between two given dates
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public List<LocalDate> getAllDatesBetweenGivenDates(LocalDate date1, LocalDate date2) {
		List<LocalDate> datesList = null;
		if (date1 != null && date2 != null) {
			datesList = new ArrayList<>();
			// LocalDate date1 = new LocalDate(startDateUTC);
			// LocalDate date2 = new LocalDate(endDateUTC);
			while (date1.isBefore(date2)) {
				datesList.add(date1);
				// System.out.println(date1.toString("MMM/yyyy"));
				date1 = date1.plus(Period.months(1));
			}
		}
		return datesList;
	}

	/**
	 * return Date in dd-MMM-yyyy(12-Jul-2017) as String format
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateAsddMMMYYYY(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		return formatter.format(date);
	}
	
	public static String getDateAsddMMMYYYYHHMMSS(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
		return formatter.format(date);
	}

	public Date getDateFromMMDDYYY_HHMMSS(String dateStr) {
        Date date = null;
		try {
        	String pattern = "MM/dd/yyyy hh:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date =  format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
