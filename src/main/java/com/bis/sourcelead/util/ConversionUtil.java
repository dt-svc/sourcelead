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
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
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
				return Float.parseFloat(rate) / 87;
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
	
	public Date getDateFromString(String dateStr) throws ParseException{
		String pattern = "dd-MM-yyyy";
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
}
