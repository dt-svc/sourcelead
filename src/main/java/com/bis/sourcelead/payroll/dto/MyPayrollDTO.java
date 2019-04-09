package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class MyPayrollDTO {
	
	private String payPeriodStartDate;
	
	private String payPeriodEndDate;
	
	private String payDate;
	
	private String accountType;
	
	private BigDecimal grossPay;
	
	private BigDecimal taxes;
	
	private BigDecimal deductions;
	
	private BigDecimal contribution;
	
	private BigDecimal netPay;
	
	private String payrollUUID;

	private String paySlipFileURL;
	
	private boolean enablePayslip;
	
	

	public boolean getEnablePayslip()
	{
		return enablePayslip;
	}

	public void setEnablePayslip(boolean enablePayslip)
	{
		this.enablePayslip = enablePayslip;
	}

	public String getPaySlipFileURL()
	{
		return paySlipFileURL;
	}

	public void setPaySlipFileURL(String paySlipFileURL)
	{
		this.paySlipFileURL = paySlipFileURL;
	}

	public String getPayDate()
	{
		return payDate;
	}

	public String getAccountType()
	{
		return accountType;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public BigDecimal getDeductions()
	{
		return deductions;
	}


	public BigDecimal getNetPay()
	{
		return netPay;
	}


	public void setPayDate(String payDate)
	{
		this.payDate = payDate;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public void setDeductions(BigDecimal deductions)
	{
		this.deductions = deductions;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public BigDecimal getContribution()
	{
		return contribution;
	}

	public void setContribution(BigDecimal contribution)
	{
		this.contribution = contribution;
	}

	public String getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public String getPayPeriodEndDate()
	{
		return payPeriodEndDate;
	}

	public void setPayPeriodStartDate(String payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayPeriodEndDate(String payPeriodEndDate)
	{
		this.payPeriodEndDate = payPeriodEndDate;
	}

	public String getPayrollUUID()
	{
		return payrollUUID;
	}

	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}
	
	
	
}
