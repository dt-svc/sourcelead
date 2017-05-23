package com.bis.sourcelead.beans;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Rajashekar 
 *
 */
public class CurrencyConversionResponse {
	
	private Map<String, String> rates = new TreeMap<String, String>();
	
	public Map<String, String> getRates()
	{
		return rates;
	}
	
	public void setRates(Map<String, String> rates)
	{
		this.rates = rates;
	}
}