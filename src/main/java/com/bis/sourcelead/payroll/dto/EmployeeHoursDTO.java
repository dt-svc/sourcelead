package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class EmployeeHoursDTO {
	
	private double regularHours;
	
	private double otHours;
	
	private BigDecimal regularTimeAmount;
	
	private BigDecimal overTimeAmount;
	
	private BigDecimal totalHours;

	public double getRegularHours()
	{
		return regularHours;
	}

	public double getOtHours()
	{
		return otHours;
	}

	public BigDecimal getRegularTimeAmount()
	{
		return regularTimeAmount;
	}

	public BigDecimal getOverTimeAmount()
	{
		return overTimeAmount;
	}

	public void setRegularHours(double regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setOtHours(double otHours)
	{
		this.otHours = otHours;
	}

	public void setRegularTimeAmount(BigDecimal regularTimeAmount)
	{
		this.regularTimeAmount = regularTimeAmount;
	}

	public void setOverTimeAmount(BigDecimal overTimeAmount)
	{
		this.overTimeAmount = overTimeAmount;
	}

	public BigDecimal getTotalHours()
	{
		return totalHours;
	}

	public void setTotalHours(BigDecimal totalHours)
	{
		this.totalHours = totalHours;
	}
	
	
}
