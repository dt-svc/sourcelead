package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public class WagesTaxSummaryDTO extends SearchDTO implements Serializable{

	private String id;
	private String orgLocationId;
	private String employeeId;
	private String employeeName;
	private String checkDate;
	private String checkNo;
	private String gross;
	private String fwt;
	private String ssec;
	private String med;
	private String swt;
	private String sdiSui;
	private String local;
	private String deds;
	private String net;
	private String fromDate;
	private String 	toDate;
	private String batchNumber;
	private String status;
	private String ssnNumber;
	private String userId;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String fromDateStr;
	private String toDateStr;
	private String checkDateStr;
	private String sortColumn;
	private String sortOrder;
	private String empId;
	
	
	public String getEmpId()
	{
		return empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	public String getCheckDate()
	{
		return checkDate;
	}
	public void setCheckDate(String checkDate)
	{
		this.checkDate = checkDate;
	}
	public String getCheckNo()
	{
		return checkNo;
	}
	public void setCheckNo(String checkNo)
	{
		this.checkNo = checkNo;
	}
	public String getGross()
	{
		return gross;
	}
	public void setGross(String gross)
	{
		this.gross = gross;
	}
	public String getFwt()
	{
		return fwt;
	}
	public void setFwt(String fwt)
	{
		this.fwt = fwt;
	}
	public String getSsec()
	{
		return ssec;
	}
	public void setSsec(String ssec)
	{
		this.ssec = ssec;
	}
	public String getMed()
	{
		return med;
	}
	public void setMed(String med)
	{
		this.med = med;
	}
	public String getSwt()
	{
		return swt;
	}
	public void setSwt(String swt)
	{
		this.swt = swt;
	}
	public String getSdiSui()
	{
		return sdiSui;
	}
	public void setSdiSui(String sdiSui)
	{
		this.sdiSui = sdiSui;
	}
	public String getLocal()
	{
		return local;
	}
	public void setLocal(String local)
	{
		this.local = local;
	}
	public String getDeds()
	{
		return deds;
	}
	public void setDeds(String deds)
	{
		this.deds = deds;
	}
	public String getNet()
	{
		return net;
	}
	public void setNet(String net)
	{
		this.net = net;
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
	public String getSsnNumber()
	{
		return ssnNumber;
	}
	public void setSsnNumber(String ssnNumber)
	{
		this.ssnNumber = ssnNumber;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
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
	public String getCheckDateStr()
	{
		return checkDateStr;
	}
	public void setCheckDateStr(String checkDateStr)
	{
		this.checkDateStr = checkDateStr;
	}
	public String getSortColumn()
	{
		return sortColumn;
	}
	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}
	public String getSortOrder()
	{
		return sortOrder;
	}
	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}
	
	
}
