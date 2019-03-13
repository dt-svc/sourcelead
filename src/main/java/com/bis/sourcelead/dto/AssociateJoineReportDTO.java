package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class AssociateJoineReportDTO extends SearchDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long empId;
	private String employeeId;
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
	private String graphFrequency;
	private Long terminatedCount;
	private Long hiredCount;
	private String monthStr;
	private String month;
	private Long empTypeFullTimeCount;
	private Long empTypePartTimeCount;
	private Long ftMaleCnt;
	private Long ftFemaleCnt;
	private Long ptMaleCnt;
	private Long ptFemaleCnt;
	
	private long empFullTimeCountTotal;
	private long empPartTimeCountTotal;
	private long empFtMaleCountTotal;
	private long empFtFeMaleCountTotal;
	private long empPtMaleCountTotal;
	private long empPtFeMaleCountTotal;
	
	private long totalJoinedCount;
	private long totalTerminatedCount;
	private String probationStatus;
	
	
	
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
	public String getGraphFrequency()
	{
		return graphFrequency;
	}
	public void setGraphFrequency(String graphFrequency)
	{
		this.graphFrequency = graphFrequency;
	}
	public Long getTerminatedCount()
	{
		return terminatedCount;
	}
	public void setTerminatedCount(Long terminatedCount)
	{
		this.terminatedCount = terminatedCount;
	}
	public Long getHiredCount()
	{
		return hiredCount;
	}
	public void setHiredCount(Long hiredCount)
	{
		this.hiredCount = hiredCount;
	}
	public String getMonthStr()
	{
		return monthStr;
	}
	public void setMonthStr(String monthStr)
	{
		this.monthStr = monthStr;
	}
	public String getMonth()
	{
		return month;
	}
	public void setMonth(String month)
	{
		this.month = month;
	}
	public Long getEmpTypeFullTimeCount()
	{
		return empTypeFullTimeCount;
	}
	public void setEmpTypeFullTimeCount(Long empTypeFullTimeCount)
	{
		this.empTypeFullTimeCount = empTypeFullTimeCount;
	}
	public Long getEmpTypePartTimeCount()
	{
		return empTypePartTimeCount;
	}
	public void setEmpTypePartTimeCount(Long empTypePartTimeCount)
	{
		this.empTypePartTimeCount = empTypePartTimeCount;
	}
	public Long getFtMaleCnt()
	{
		return ftMaleCnt;
	}
	public void setFtMaleCnt(Long ftMaleCnt)
	{
		this.ftMaleCnt = ftMaleCnt;
	}
	public Long getFtFemaleCnt()
	{
		return ftFemaleCnt;
	}
	public void setFtFemaleCnt(Long ftFemaleCnt)
	{
		this.ftFemaleCnt = ftFemaleCnt;
	}
	public Long getPtMaleCnt()
	{
		return ptMaleCnt;
	}
	public void setPtMaleCnt(Long ptMaleCnt)
	{
		this.ptMaleCnt = ptMaleCnt;
	}
	public Long getPtFemaleCnt()
	{
		return ptFemaleCnt;
	}
	public void setPtFemaleCnt(Long ptFemaleCnt)
	{
		this.ptFemaleCnt = ptFemaleCnt;
	}
	public long getEmpFullTimeCountTotal()
	{
		return empFullTimeCountTotal;
	}
	public void setEmpFullTimeCountTotal(long empFullTimeCountTotal)
	{
		this.empFullTimeCountTotal = empFullTimeCountTotal;
	}
	public long getEmpPartTimeCountTotal()
	{
		return empPartTimeCountTotal;
	}
	public void setEmpPartTimeCountTotal(long empPartTimeCountTotal)
	{
		this.empPartTimeCountTotal = empPartTimeCountTotal;
	}
	public long getEmpFtMaleCountTotal()
	{
		return empFtMaleCountTotal;
	}
	public void setEmpFtMaleCountTotal(long empFtMaleCountTotal)
	{
		this.empFtMaleCountTotal = empFtMaleCountTotal;
	}
	public long getEmpFtFeMaleCountTotal()
	{
		return empFtFeMaleCountTotal;
	}
	public void setEmpFtFeMaleCountTotal(long empFtFeMaleCountTotal)
	{
		this.empFtFeMaleCountTotal = empFtFeMaleCountTotal;
	}
	public long getEmpPtMaleCountTotal()
	{
		return empPtMaleCountTotal;
	}
	public void setEmpPtMaleCountTotal(long empPtMaleCountTotal)
	{
		this.empPtMaleCountTotal = empPtMaleCountTotal;
	}
	public long getEmpPtFeMaleCountTotal()
	{
		return empPtFeMaleCountTotal;
	}
	public void setEmpPtFeMaleCountTotal(long empPtFeMaleCountTotal)
	{
		this.empPtFeMaleCountTotal = empPtFeMaleCountTotal;
	}
	public long getTotalJoinedCount()
	{
		return totalJoinedCount;
	}
	public void setTotalJoinedCount(long totalJoinedCount)
	{
		this.totalJoinedCount = totalJoinedCount;
	}
	public long getTotalTerminatedCount()
	{
		return totalTerminatedCount;
	}
	public void setTotalTerminatedCount(long totalTerminatedCount)
	{
		this.totalTerminatedCount = totalTerminatedCount;
	}
	public String getProbationStatus()
	{
		return probationStatus;
	}
	public void setProbationStatus(String probationStatus)
	{
		this.probationStatus = probationStatus;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}