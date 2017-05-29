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
	private float exchangeRate; 
	private float exchangeCurrency;
	
	public Map<String, String> getRates()
	{
		return rates;
	}
	
	public void setRates(Map<String, String> rates)
	{
		this.rates = rates;
	}

	public float getExchangeRate()
	{
		return exchangeRate;
	}

	public void setExchangeRate(float exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}

	public float getExchangeCurrency()
	{
		return exchangeCurrency;
	}

	public void setExchangeCurrency(float exchangeCurrency)
	{
		this.exchangeCurrency = exchangeCurrency;
	}
	
	
}