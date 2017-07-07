package com.bis.sourcelead.enums;

public enum CurrencyCodes {
	INR("INR"),
	AUD("AUD"),
	USD("USD"),
	GBP("GBP"),
	SGD("SGD"),
	EUR("EUR");
    private String value;
	private CurrencyCodes(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static CurrencyCodes getNameByValue(String scenarioId){
	   	  for(CurrencyCodes currencyCodes : CurrencyCodes.values()){
	   	    if(scenarioId.equalsIgnoreCase(currencyCodes.value)) 
	   	    	return currencyCodes;
	   	  }
	   	  return null;
	  }
 
}
