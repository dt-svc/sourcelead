package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class AssociateJoineReportDTO extends SearchDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long empId;
	private Long userId;
	private Long orgLocationsId;
	private Date hireDate;
	private String userRole;
	private String empName;
	private String frequency;
	private String fromDate;
	private String toDate;
	private String empType;
	private String deptName;
	private String reportingManager;
	private String gender;
	private String currency;
	private String rate;
	private String ratePer;
	private String shiftName;
	private String type;
	private Date terminateDate;
	private String terminateStringDate;
	private String terminatedReason;
	
	
	
	public Long getEmpId()
	{
		return empId;
	}
	public void setEmpId(Long empId)
	{
		this.empId = empId;
	}
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	public Long getOrgLocationsId()
	{
		return orgLocationsId;
	}
	public void setOrgLocationsId(Long orgLocationsId)
	{
		this.orgLocationsId = orgLocationsId;
	}
	public Date getHireDate()
	{
		return hireDate;
	}
	public void setHireDate(Date hireDate)
	{
		this.hireDate = hireDate;
	}
	public String getUserRole()
	{
		return userRole;
	}
	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getFrequency()
	{
		return frequency;
	}
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
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
	public String getEmpType()
	{
		return empType;
	}
	public void setEmpType(String empType)
	{
		this.empType = empType;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public String getReportingManager()
	{
		return reportingManager;
	}
	public void setReportingManager(String reportingManager)
	{
		this.reportingManager = reportingManager;
	}
	public String getGender()
	{
		return gender;
	}
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	public String getCurrency()
	{
		return currency;
	}
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
	public String getRate()
	{
		return rate;
	}
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	public String getRatePer()
	{
		return ratePer;
	}
	public void setRatePer(String ratePer)
	{
		this.ratePer = ratePer;
	}
	public String getShiftName()
	{
		return shiftName;
	}
	public void setShiftName(String shiftName)
	{
		this.shiftName = shiftName;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Date getTerminateDate()
	{
		return terminateDate;
	}
	public void setTerminateDate(Date terminateDate)
	{
		this.terminateDate = terminateDate;
	}
	public String getTerminateStringDate()
	{
		return terminateStringDate;
	}
	public void setTerminateStringDate(String terminateStringDate)
	{
		this.terminateStringDate = terminateStringDate;
	}
	public String getTerminatedReason()
	{
		return terminatedReason;
	}
	public void setTerminatedReason(String terminatedReason)
	{
		this.terminatedReason = terminatedReason;
	}
	
}