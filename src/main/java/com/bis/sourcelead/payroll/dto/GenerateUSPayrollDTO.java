package com.bis.sourcelead.payroll.dto;

public class GenerateUSPayrollDTO {
	
	private String payrollStartDate;
	
	private String payrollEndDate;
	
	private String payrollPayDate;
	
	public String getPayrollStartDate()
	{
		return payrollStartDate;
	}
	public void setPayrollStartDate(String payrollStartDate)
	{
		this.payrollStartDate = payrollStartDate;
	}
	public String getPayrollEndDate()
	{
		return payrollEndDate;
	}
	public void setPayrollEndDate(String payrollEndDate)
	{
		this.payrollEndDate = payrollEndDate;
	}
	public String getPayrollPayDate()
	{
		return payrollPayDate;
	}
	public void setPayrollPayDate(String payrollPayDate)
	{
		this.payrollPayDate = payrollPayDate;
	}
	
	
}
