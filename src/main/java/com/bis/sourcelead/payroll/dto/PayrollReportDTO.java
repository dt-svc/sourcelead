package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class PayrollReportDTO {
	
	private String empName;
	
	private BigDecimal payRate;
	
	private String regularHours;
	
	private String overtimeHours;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal taxes;
	
	private BigDecimal netPay;
	
	private BigDecimal lastPay;
	
	private BigDecimal YTD;
	
	private String action;

	public String getEmpName()
	{
		return empName;
	}

	public BigDecimal getPayRate()
	{
		return payRate;
	}

	public String getRegularHours()
	{
		return regularHours;
	}

	public String getOvertimeHours()
	{
		return overtimeHours;
	}

	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}

	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public BigDecimal getLastPay()
	{
		return lastPay;
	}

	public BigDecimal getYTD()
	{
		return YTD;
	}

	public String getAction()
	{
		return action;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public void setPayRate(BigDecimal payRate)
	{
		this.payRate = payRate;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setOvertimeHours(String overtimeHours)
	{
		this.overtimeHours = overtimeHours;
	}

	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}

	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public void setLastPay(BigDecimal lastPay)
	{
		this.lastPay = lastPay;
	}

	public void setYTD(BigDecimal yTD)
	{
		YTD = yTD;
	}

	public void setAction(String action)
	{
		this.action = action;
	}
	
	
}
