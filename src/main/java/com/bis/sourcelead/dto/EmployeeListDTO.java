package com.bis.sourcelead.dto;

import java.io.Serializable;

public class EmployeeListDTO extends SearchDTO implements Serializable{

	private String company;
	private String orgLocationId;
	private String branch;
	private String empId;
	private String candidateName;
	private String rate;
	private String fixedRate;
	private String payRate;
	private String fixedPay;
	private String insurance;
	private String ded;
	private String effectiveRate;
	private String payrollDifference;
	private String immigration;
	private String comments;
	private String advanceHcp;
	private String balance;
	
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getBranch()
	{
		return branch;
	}
	public void setBranch(String branch)
	{
		this.branch = branch;
	}
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getCandidateName()
	{
		return candidateName;
	}
	public void setCandidateName(String candidateName)
	{
		this.candidateName = candidateName;
	}
	public String getRate()
	{
		return rate;
	}
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	public String getFixedRate()
	{
		return fixedRate;
	}
	public void setFixedRate(String fixedRate)
	{
		this.fixedRate = fixedRate;
	}
	public String getPayRate()
	{
		return payRate;
	}
	public void setPayRate(String payRate)
	{
		this.payRate = payRate;
	}
	public String getFixedPay()
	{
		return fixedPay;
	}
	public void setFixedPay(String fixedPay)
	{
		this.fixedPay = fixedPay;
	}
	public String getInsurance()
	{
		return insurance;
	}
	public void setInsurance(String insurance)
	{
		this.insurance = insurance;
	}
	public String getDed()
	{
		return ded;
	}
	public void setDed(String ded)
	{
		this.ded = ded;
	}
	public String getEffectiveRate()
	{
		return effectiveRate;
	}
	public void setEffectiveRate(String effectiveRate)
	{
		this.effectiveRate = effectiveRate;
	}
	public String getPayrollDifference()
	{
		return payrollDifference;
	}
	public void setPayrollDifference(String payrollDifference)
	{
		this.payrollDifference = payrollDifference;
	}
	public String getImmigration()
	{
		return immigration;
	}
	public void setImmigration(String immigration)
	{
		this.immigration = immigration;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public String getAdvanceHcp()
	{
		return advanceHcp;
	}
	public void setAdvanceHcp(String advanceHcp)
	{
		this.advanceHcp = advanceHcp;
	}
	public String getBalance()
	{
		return balance;
	}
	public void setBalance(String balance)
	{
		this.balance = balance;
	}
	
	
}
