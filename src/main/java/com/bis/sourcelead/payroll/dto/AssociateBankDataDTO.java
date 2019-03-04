package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.annotations.Required;

public class AssociateBankDataDTO {
	
	private Long id;
	
	@Required(desc = "Account Type")
	private String accountType;
	
	private String bankName;
	
	private String accountNumber;
	
	private String routingNumber;
	
	private String associateBankDataUUID;
	
	private String userId;
	
	//new fields added for associate banking details
	
	private String assoicateId;
	private String associateEmail;
	private String paymentMode;
	private String bsrNumber;
	private String MicrCode;
	private String ifscCode;
	private String firstName;
	private String lastName;
	
	
	public String getAssoicateId() {
		return assoicateId;
	}

	public void setAssoicateId(String assoicateId) {
		this.assoicateId = assoicateId;
	}

	public String getAssociateEmail() {
		return associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBsrNumber() {
		return bsrNumber;
	}

	public void setBsrNumber(String bsrNumber) {
		this.bsrNumber = bsrNumber;
	}

	public String getMicrCode() {
		return MicrCode;
	}

	public void setMicrCode(String micrCode) {
		MicrCode = micrCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	//-----------------end ----------
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getAccountType()
	{
		return accountType;
	}
	
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
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
	
	public String getRoutingNumber()
	{
		return routingNumber;
	}
	
	public void setRoutingNumber(String routingNumber)
	{
		this.routingNumber = routingNumber;
	}
	
	public String getAssociateBankDataUUID()
	{
		return associateBankDataUUID;
	}
	
	public void setAssociateBankDataUUID(String associateBankDataUUID)
	{
		this.associateBankDataUUID = associateBankDataUUID;
	}
}
