package com.bis.sourcelead.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.service.impl.InvoiceServiceImpl;
import com.google.gson.Gson;
/**
 * 
 * @author Rajashekar
 *
 */
@Component
public class ConversionUtil {
	
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
}
