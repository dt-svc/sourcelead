package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.entity.OrgLocations;

public class EmployerPayrollCostDTO extends SearchDTO implements Serializable {

	private String employeeId;
	private String orgLocationId;
	private String employeeName;
	private String ssn;
	private String totalHrs;
	private String grossWages;
	private String fica;
	private String futa;
	private String sui;
	private String erLocals;
	private String wcCost;
	private String adminFee;
	private String totalErCost;
	private String fromDate;
	private String toDate;
	private String batchNumber;
	private String status;
	private String fromDateStr;
	private String toDateStr;
	public String getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	public String getSsn()
	{
		return ssn;
	}
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	public String getTotalHrs()
	{
		return totalHrs;
	}
	public void setTotalHrs(String totalHrs)
	{
		this.totalHrs = totalHrs;
	}
	public String getGrossWages()
	{
		return grossWages;
	}
	public void setGrossWages(String grossWages)
	{
		this.grossWages = grossWages;
	}
	public String getFica()
	{
		return fica;
	}
	public void setFica(String fica)
	{
		this.fica = fica;
	}
	public String getFuta()
	{
		return futa;
	}
	public void setFuta(String futa)
	{
		this.futa = futa;
	}
	public String getSui()
	{
		return sui;
	}
	public void setSui(String sui)
	{
		this.sui = sui;
	}
	public String getErLocals()
	{
		return erLocals;
	}
	public void setErLocals(String erLocals)
	{
		this.erLocals = erLocals;
	}
	public String getWcCost()
	{
		return wcCost;
	}
	public void setWcCost(String wcCost)
	{
		this.wcCost = wcCost;
	}
	public String getAdminFee()
	{
		return adminFee;
	}
	public void setAdminFee(String adminFee)
	{
		this.adminFee = adminFee;
	}
	public String getTotalErCost()
	{
		return totalErCost;
	}
	public void setTotalErCost(String totalErCost)
	{
		this.totalErCost = totalErCost;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getBatchNumber()
	{
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getFromDateStr()
	{
		return fromDateStr;
	}
	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}
	public String getToDateStr()
	{
		return toDateStr;
	}
	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}
	
	
	
}
