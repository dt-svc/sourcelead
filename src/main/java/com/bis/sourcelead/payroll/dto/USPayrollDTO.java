package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class USPayrollDTO {
	
	private String empId;
	
	private String empName;
	
	private float regularHours;	
	
	private String overtimeHours;
	
	private BigDecimal grossPay;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal taxes;	
	
	private BigDecimal benefits;
	
	private String netPay; // 
	
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public float getRegularHours()
	{
		return regularHours;
	}
	public void setRegularHours(float regularHours)
	{
		this.regularHours = regularHours;
	}
	public String getOvertimeHours()
	{
		return overtimeHours;
	}
	public void setOvertimeHours(String overtimeHours)
	{
		this.overtimeHours = overtimeHours;
	}
	public BigDecimal getGrossPay()
	{
		return grossPay;
	}
	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}
	public BigDecimal getPreTaxDeduction()
	{
		return preTaxDeduction;
	}
	public void setPreTaxDeduction(BigDecimal preTaxDeduction)
	{
		this.preTaxDeduction = preTaxDeduction;
	}
	public BigDecimal getPostTaxDeduction()
	{
		return postTaxDeduction;
	}
	public void setPostTaxDeduction(BigDecimal postTaxDeduction)
	{
		this.postTaxDeduction = postTaxDeduction;
	}
	public BigDecimal getTaxes()
	{
		return taxes;
	}
	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}
	public BigDecimal getBenefits()
	{
		return benefits;
	}
	public void setBenefits(BigDecimal benefits)
	{
		this.benefits = benefits;
	}
	public String getNetPay()
	{
		return netPay;
	}
	public void setNetPay(String netPay)
	{
		this.netPay = netPay;
	}
	
	
}
