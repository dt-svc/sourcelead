package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestParam;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollReportDTO extends SearchDTO{
	
	private String empName;
	
	private BigDecimal empPayRate;
	
	private String regularHours;
	
	private String overTimeHours;
	
	private BigDecimal preTaxDeduction;
	
	private BigDecimal postTaxDeduction;
	
	private BigDecimal taxes;
	
	private BigDecimal netPay;
	
	private BigDecimal grossPay;
	
	private BigDecimal lastPay;
	
	private BigDecimal ytdNet;
	
	private String action;
	
	private String groupId;
	
	private String payCheckDate;
	

	public String getEmpName()
	{
		return empName;
	}

	
	public String getRegularHours()
	{
		return regularHours;
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


	public String getAction()
	{
		return action;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}


	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
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

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}


	public BigDecimal getEmpPayRate()
	{
		return empPayRate;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}


	public void setEmpPayRate(BigDecimal empPayRate)
	{
		this.empPayRate = empPayRate;
	}


	public BigDecimal getGrossPay()
	{
		return grossPay;
	}


	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}


	public BigDecimal getYtdNet()
	{
		return ytdNet;
	}


	public void setYtdNet(BigDecimal ytdNet)
	{
		this.ytdNet = ytdNet;
	}
	
	
	
}
