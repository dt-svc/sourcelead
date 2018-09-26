package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


public class CheckRegisterReportDTO {
	
	private BigInteger empPayrollId;
	
	private BigInteger empId;
	
	private String employeeId;
	
	private String departmentId;
	
	private String firstName;
	
	private String lastName;
	
	private String payType;
	
	private BigDecimal netPay;
	
	private BigDecimal taxes;
	
	private BigDecimal grossPay;
	
	private Date freqStartDate;
	
	private Date freqEndDate;
	
	private Date payCheckDate;
	
	public String getType()
	{
		return type;
	}

	public Date getRunDate()
	{
		return runDate;
	}

	public String getPayGroupName()
	{
		return payGroupName;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setRunDate(Date runDate)
	{
		this.runDate = runDate;
	}

	public void setPayGroupName(String payGroupName)
	{
		this.payGroupName = payGroupName;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	private int totalChecks;
	
	private String type;
	
	private Date runDate;
	
	private String payGroupName;
	
	private String payFrequency;
	

	public String getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public BigInteger getEmpPayrollId()
	{
		return empPayrollId;
	}

	public BigInteger getEmpId()
	{
		return empId;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPayType()
	{
		return payType;
	}

	public BigDecimal getNetPay()
	{
		return netPay;
	}

	public BigDecimal getTaxes()
	{
		return taxes;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}

	public Date getFreqStartDate()
	{
		return freqStartDate;
	}

	public Date getFreqEndDate()
	{
		return freqEndDate;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public void setEmpPayrollId(BigInteger empPayrollId)
	{
		this.empPayrollId = empPayrollId;
	}

	public void setEmpId(BigInteger empId)
	{
		this.empId = empId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public void setNetPay(BigDecimal netPay)
	{
		this.netPay = netPay;
	}

	public void setTaxes(BigDecimal taxes)
	{
		this.taxes = taxes;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	public void setFreqStartDate(Date freqStartDate)
	{
		this.freqStartDate = freqStartDate;
	}

	public void setFreqEndDate(Date freqEndDate)
	{
		this.freqEndDate = freqEndDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public int getTotalChecks()
	{
		return totalChecks;
	}

	public void setTotalChecks(int totalChecks)
	{
		this.totalChecks = totalChecks;
	}
	
	
}
