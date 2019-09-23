package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.Date;

public class SalaryDTO {
	
	
	private BigInteger candidateId;
	
	private String firstName;
	
	private String lastName;
	
	private String isTerminated;
	
	private Date terminatedDate;
	
	private Date joiningDate;
	
	private String payFrequency;
	
	
	private int noOfDays;

	public BigInteger getCandidateId()
	{
		return candidateId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getIsTerminated()
	{
		return isTerminated;
	}

	public Date getTerminatedDate()
	{
		return terminatedDate;
	}

	public void setCandidateId(BigInteger candidateId)
	{
		this.candidateId = candidateId;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setIsTerminated(String isTerminated)
	{
		this.isTerminated = isTerminated;
	}

	public void setTerminatedDate(Date terminatedDate)
	{
		this.terminatedDate = terminatedDate;
	}

	public int getNoOfDays()
	{
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays)
	{
		this.noOfDays = noOfDays;
	}

	public Date getJoiningDate()
	{
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate)
	{
		this.joiningDate = joiningDate;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}
	
	
}
