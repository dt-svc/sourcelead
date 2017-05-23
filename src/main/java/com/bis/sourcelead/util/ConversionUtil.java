package com.bis.sourcelead.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.beans.CurrencyConversionResponse;
import com.bis.sourcelead.constants.Constants;
import com.google.gson.Gson;
/**
 * 
 * @author Rajashekar
 *
 */
@Component
public class ConversionUtil {
	
    private static final String API_PROVDIER = "http://api.fixer.io/";
	
	/**
	 * converting rate into per hour
	 * @param rate
	 * @param ratePer
	 * @return double
	 */
	public double getHourlyRate(String rate, String ratePer)
	{
		switch (ratePer) {
			case Constants.RATE_TYPE_HOURLY:
				return Double.parseDouble(rate);
			case Constants.RATE_TYPE_WEEKLY:
				return Double.parseDouble(rate) / 40;
			case Constants.RATE_TYPE_BI_WEEKLY:
				return Double.parseDouble(rate) / 80;
			case Constants.RATE_TYPE_SEMI_MONTHLY:
				return Double.parseDouble(rate) / 87;
			case Constants.RATE_TYPE_MONTHLY:
				return Double.parseDouble(rate) / 184;
			case Constants.RATE_TYPE_BI_MONTHLY:
				return Double.parseDouble(rate) / 87;
			default:
				return Double.parseDouble(rate) / 2088;
		}
	}
	
	/**
	 * convert currency from one type to another
	 * @param fromCurrencyCode
	 * @param toCurrencyCode
	 * @param amount
	 * @return double
	 */
	public  double convert(String fromCurrencyCode, String toCurrencyCode, double amount)
	{
		
		if ((fromCurrencyCode != null && !fromCurrencyCode.isEmpty())
				&& (toCurrencyCode != null && !toCurrencyCode.isEmpty())) {
			
			CurrencyConversionResponse response = getResponse(API_PROVDIER + "/latest?base="
					+ fromCurrencyCode);
			if (response != null) {
				
				String rate = response.getRates().get(toCurrencyCode);
				double conversionRate = Double.valueOf((rate != null) ? rate : "0.0");
				return conversionRate * amount;
			}
		}
		return 0.0;
	}
	
	// Method to get the response from API
	private static CurrencyConversionResponse getResponse(String strUrl)
	{
		
		CurrencyConversionResponse response = null;
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer();
		if (strUrl == null || strUrl.isEmpty()) {
			
			System.out.println("Application Error");
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
			
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
}
