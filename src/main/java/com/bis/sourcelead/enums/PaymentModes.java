package com.bis.sourcelead.enums;

public enum PaymentModes {
	CREDITCARD("Credit Card"),
	DEBITCARD("Debit Card"),
	CASH("Cash"),
	CHEQUE("Cheque"),
	EWALLET("e-Wallet");
	
    private String value;
	private PaymentModes(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static PaymentModes getNameByValue(String scenarioId){
	   	  for(PaymentModes paymentModes : PaymentModes.values()){
	   	    if(scenarioId.equalsIgnoreCase(paymentModes.value)) 
	   	    	return paymentModes;
	   	  }
	   	  return null;
	    }
}
