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
	
	private String emailId;
	
	private String reason;
	
	private String associateId;
	
	private String candidateId;
	
	
	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

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
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getAssociateId()
	{
		return associateId;
	}

	public void setAssociateId(String associateId)
	{
		this.associateId = associateId;
	}
	
	
}
