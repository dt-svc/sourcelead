package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class OrgLocationIndiaBankingDetailsDTO {

	@Required(desc="accountName")
	private String accountName;
	
	@Required(desc="accountNumber")
	private String accountNumber;
	
	@Required(desc="bankName")
	private String bankName;
	
	@Required(desc="bankAddress")
	private String bankAddress;
	
	@Required(desc="ifscCode")
	private String ifscCode;
	
	private String bsrNumber;
	
	private String micrCode;
	
	private String uuid;
	
	

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBsrNumber() {
		return bsrNumber;
	}

	public void setBsrNumber(String bsrNumber) {
		this.bsrNumber = bsrNumber;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
