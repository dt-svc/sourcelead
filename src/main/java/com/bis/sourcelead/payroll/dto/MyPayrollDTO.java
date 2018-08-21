package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class MyPayrollDTO {
	
	private String payPeriod;
	
	private String payDate;
	
	private String accountType;
	
	private BigDecimal grossPay;
	
	private BigDecimal taxes;
	
	private BigDecimal deductions;
	
	private BigDecimal contributio;
	
	private BigDecimal netPay;

	public String getPayPeriod()
	{
		return payPeriod;
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

	public BigDecimal getContributio()
	{
		return contributio;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
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

	public void setContributio(BigDecimal contributio)
	{
		this.contributio = contributio;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}
	
}
