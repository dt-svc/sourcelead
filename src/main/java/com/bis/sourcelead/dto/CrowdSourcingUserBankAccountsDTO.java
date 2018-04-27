package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class CrowdSourcingUserBankAccountsDTO {

	@Required(desc="accountHolderName")
	 private String accountHolderName;
	 
	 private String country;
	 
	 private String currency;
	 
	 private String bankName;
	 
	 @Required(desc="accountNumber")
	 private String accountNumber;
	 
	 private String bankCode;

	public String getAccountHolderName()
	{
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName)
	{
		this.accountHolderName = accountHolderName;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getBankCode()
	{
		return bankCode;
	}

	public void setBankCode(String bankCode)
	{
		this.bankCode = bankCode;
	}
	 
}
