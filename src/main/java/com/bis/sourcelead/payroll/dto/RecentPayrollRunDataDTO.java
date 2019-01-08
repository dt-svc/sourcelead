package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RecentPayrollRunDataDTO {
	
	private String payFrequency;
	
	private BigDecimal netPay;
	
	private String payGroupName;
	
	private String payrollCheckDate;
	
	private String payrollRunDate;
	
	private int noOfEmployees;
	
	private int noOfDirectDeposit;
	
	private int noOfCheques;
	
	private Date startDate;
	
	private Date endDate;
	
	private BigDecimal grossPay;
	
	private String paysheetFreqUUID;
	
	private String payPeriodNum;
	

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public String getPayGroupName()
	{
		return payGroupName;
	}

	public String getPayrollCheckDate()
	{
		return payrollCheckDate;
	}

	public String getPayrollRunDate()
	{
		return payrollRunDate;
	}

	public int getNoOfEmployees()
	{
		return noOfEmployees;
	}

	public int getNoOfDirectDeposit()
	{
		return noOfDirectDeposit;
	}

	public void setPayGroupName(String payGroupName)
	{
		this.payGroupName = payGroupName;
	}

	public void setPayrollCheckDate(String payrollCheckDate)
	{
		this.payrollCheckDate = payrollCheckDate;
	}

	public void setPayrollRunDate(String payrollRunDate)
	{
		this.payrollRunDate = payrollRunDate;
	}

	public void setNoOfEmployees(int noOfEmployees)
	{
		this.noOfEmployees = noOfEmployees;
	}

	public void setNoOfDirectDeposit(int noOfDirectDeposit)
	{
		this.noOfDirectDeposit = noOfDirectDeposit;
	}

	public int getNoOfCheques()
	{
		return noOfCheques;
	}

	public void setNoOfCheques(int noOfCheques)
	{
		this.noOfCheques = noOfCheques;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(BigDecimal grossPay) {
		this.grossPay = grossPay;
	}

	public String getPaysheetFreqUUID() {
		return paysheetFreqUUID;
	}

	public void setPaysheetFreqUUID(String paysheetFreqUUID) {
		this.paysheetFreqUUID = paysheetFreqUUID;
	}

	public String getPayPeriodNum() {
		return payPeriodNum;
	}

	public void setPayPeriodNum(String payPeriodNum) {
		this.payPeriodNum = payPeriodNum;
	}
	

}
