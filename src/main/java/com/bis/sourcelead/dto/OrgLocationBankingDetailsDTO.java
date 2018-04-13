package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class OrgLocationBankingDetailsDTO {

	@Required(desc="accountType")
	private String accountType;
	
	@Required(desc="accountName")
	private String accountName;
	
	@Required(desc="routingNumber")
	private String routingNumber;
	
	@Required(desc="accountNumber")
	private String accountNumber;
	
	private String uuid;
	
	

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
