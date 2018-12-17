package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaveDTO extends SearchDTO implements Serializable{

	private long id;
	private String orgLocationId;
	private String userId;
	private String user;
	private String fromDate;
	private String fromHalfDay;
	private String toDate;
	private String toHalfDay;
	private String leaveType;
	private long leaveTypeId;
	private String reason;
	private List<String> approverUser;
	private String approvedBy;
	private String rejectedBy;
	private String cancelledBy;
	private String status;
	private String deleteReason;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private List<FileInfo> fileInfo;
	private String staticPath;
	private String empName;
	private String duration;
	private int noOfDays;
	private String balanceleaves;
	private String fileUrl;
	private String fileName;
	private String fileContentType;
	private String empid;
	private String empType;
	private String empEmail;
	private String fromDateDay;
	private String toDateDay;
	private String leavesTaken;
	private String approverUsers;
	private String thisMonthLeaves;

	private String action;
	private String leaveStatus;

	private String reportYear;
	private String reportMonth;
	private String reportFromDate;
	private String reportEndDate;
	private String department;
	private String leavesearchDate;
	private String barChartYear;
	private String barCharMonth;
	private String barChartFromDate;
	private String barChartToDate;
	private String totalLeaves;
	private String colorCode;
	private String rejectedReason;

	private String cancelReason;
	private String approvedByUser;
	private String isWithdrawEnable;

	private String employeeRequestedCounts;
	private String employeeApprovedCounts;
	private String employeeRejectedCounts;
	private String employeeCancelledCounts;
	
	private String selfPendingCounts;
	private String selfApprovedCounts;
	private String selfRejectedCounts;
	private String selfCancelledCounts;
	private String isBillable;
	private int selfBalancedCounts;
	private String topLeaveTakersreportYear;
	private float extraLeavesTaken;
	private String leaveUUID;
	private float appliedNoOfDays;
	private float actualLeavesTaken;
	private float thisMonthLeavesTaken;
	private float totalBalLeaves;
	
	private String exportType;
	private float leaveNoDays;
	private String leaveName;
	private boolean isSuperUser;
	
	private String withdrawReason;
	private Long leaveId;
	private String currentStage;
	
	private String fromDateFromHalfTime;
	private String fromDateToHalfTime;
	private String toDateFromHalfTime;
	private String toDateToHalfTime;
	private String durationHrs;
	
	private List<String> otherApproverUser;
	private String notifyToOthers;
	private String loginUserRole;
	private String type;
	
	public String getLeavesTaken() {
		return leavesTaken;
	}
	public void setLeavesTaken(String leavesTaken) {
		this.leavesTaken = leavesTaken;
	}
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
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
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getFromHalfDay()
	{
		return fromHalfDay;
	}
	public void setFromHalfDay(String fromHalfDay)
	{
		this.fromHalfDay = fromHalfDay;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getToHalfDay()
	{
		return toHalfDay;
	}
	public void setToHalfDay(String toHalfDay)
	{
		this.toHalfDay = toHalfDay;
	}
	public String getLeaveType()
	{
		return leaveType;
	}
	public void setLeaveType(String leaveType)
	{
		this.leaveType = leaveType;
	}
	public long getLeaveTypeId()
	{
		return leaveTypeId;
	}
	public void setLeaveTypeId(long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public List<String> getApproverUser()
	{
		return approverUser;
	}
	public void setApproverUser(List<String> approverUser)
	{
		this.approverUser = approverUser;
	}
	public String getApprovedBy()
	{
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy)
	{
		this.approvedBy = approvedBy;
	}
	public String getRejectedBy()
	{
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy)
	{
		this.rejectedBy = rejectedBy;
	}
	public String getCancelledBy()
	{
		return cancelledBy;
	}
	public void setCancelledBy(String cancelledBy)
	{
		this.cancelledBy = cancelledBy;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getDeleteReason()
	{
		return deleteReason;
	}
	public void setDeleteReason(String deleteReason)
	{
		this.deleteReason = deleteReason;
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
	public List<FileInfo> getFileInfo()
	{
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo)
	{
		this.fileInfo = fileInfo;
	}
	public String getStaticPath()
	{
		return staticPath;
	}
	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getDuration()
	{
		return duration;
	}
	public void setDuration(String duration)
	{
		this.duration = duration;
	}
	public int getNoOfDays()
	{
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays)
	{
		this.noOfDays = noOfDays;
	}
	public String getBalanceleaves()
	{
		return balanceleaves;
	}
	public void setBalanceleaves(String balanceleaves)
	{
		this.balanceleaves = balanceleaves;
	}
	public String getFileUrl()
	{
		return fileUrl;
	}
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getEmpid()
	{
		return empid;
	}
	public void setEmpid(String empid)
	{
		this.empid = empid;
	}
	public String getEmpType()
	{
		return empType;
	}
	public void setEmpType(String empType)
	{
		this.empType = empType;
	}
	public String getEmpEmail()
	{
		return empEmail;
	}
	public void setEmpEmail(String empEmail)
	{
		this.empEmail = empEmail;
	}
	
	public String getFromDateDay()
	{
		return fromDateDay;
	}
	public void setFromDateDay(String fromDateDay)
	{
		this.fromDateDay = fromDateDay;
	}
	public String getToDateDay()
	{
		return toDateDay;
	}
	public void setToDateDay(String toDateDay)
	{
		this.toDateDay = toDateDay;
	}
	public String getApproverUsers() {
		return approverUsers;
	}
	public void setApproverUsers(String approverUsers)
	{
		this.approverUsers = approverUsers;
	}
	public String getThisMonthLeaves()
	{
		return thisMonthLeaves;
	}
	public void setThisMonthLeaves(String thisMonthLeaves)
	{
		this.thisMonthLeaves = thisMonthLeaves;
	}

	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}
	public String getLeaveStatus()
	{
		return leaveStatus;
	}
	public void setLeaveStatus(String leaveStatus)
	{
		this.leaveStatus = leaveStatus;
	}
	

	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	public String getReportMonth() {
		return reportMonth;
	}
	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}
	public String getReportFromDate() {
		return reportFromDate;
	}
	public void setReportFromDate(String reportFromDate) {
		this.reportFromDate = reportFromDate;
	}
	public String getReportEndDate() {
		return reportEndDate;
	}
	public void setReportEndDate(String reportEndDate) {
		this.reportEndDate = reportEndDate;
	}

	public String getEmployeeRequestedCounts() {
		return employeeRequestedCounts;
	}
	public void setEmployeeRequestedCounts(String employeeRequestedCounts) {
		this.employeeRequestedCounts = employeeRequestedCounts;
	}
	public String getEmployeeApprovedCounts() {
		return employeeApprovedCounts;
	}
	public void setEmployeeApprovedCounts(String employeeApprovedCounts) {
		this.employeeApprovedCounts = employeeApprovedCounts;
	}
	public String getEmployeeRejectedCounts() {
		return employeeRejectedCounts;
	}
	public void setEmployeeRejectedCounts(String employeeRejectedCounts) {
		this.employeeRejectedCounts = employeeRejectedCounts;
	}
	public String getEmployeeCancelledCounts() {
		return employeeCancelledCounts;
	}
	public void setEmployeeCancelledCounts(String employeeCancelledCounts) {
		this.employeeCancelledCounts = employeeCancelledCounts;
	}
	public String getSelfPendingCounts() {
		return selfPendingCounts;
	}
	public void setSelfPendingCounts(String selfPendingCounts) {
		this.selfPendingCounts = selfPendingCounts;
	}
	public String getSelfApprovedCounts() {
		return selfApprovedCounts;
	}
	public void setSelfApprovedCounts(String selfApprovedCounts) {
		this.selfApprovedCounts = selfApprovedCounts;
	}
	public String getSelfRejectedCounts() {
		return selfRejectedCounts;
	}
	public void setSelfRejectedCounts(String selfRejectedCounts) {
		this.selfRejectedCounts = selfRejectedCounts;
	}
	public String getSelfCancelledCounts() {
		return selfCancelledCounts;
	}
	public void setSelfCancelledCounts(String selfCancelledCounts) {
		this.selfCancelledCounts = selfCancelledCounts;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public String getLeavesearchDate()
	{
		return leavesearchDate;
	}
	public void setLeavesearchDate(String leavesearchDate)
	{
		this.leavesearchDate = leavesearchDate;
	}
	public String getBarChartYear() {
		return barChartYear;
	}
	public void setBarChartYear(String barChartYear) {
		this.barChartYear = barChartYear;
	}
	public String getBarCharMonth() {
		return barCharMonth;
	}
	public void setBarCharMonth(String barCharMonth) {
		this.barCharMonth = barCharMonth;
	}
	public String getBarChartFromDate() {
		return barChartFromDate;
	}
	public void setBarChartFromDate(String barChartFromDate) {
		this.barChartFromDate = barChartFromDate;
	}
	public String getBarChartToDate() {
		return barChartToDate;
	}
	public void setBarChartToDate(String barChartToDate) {
		this.barChartToDate = barChartToDate;
	}
	public String getTotalLeaves() {
		return totalLeaves;
	}
	public void setTotalLeaves(String totalLeaves) {
		this.totalLeaves = totalLeaves;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getApprovedByUser() {
		return approvedByUser;
	}
	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}
	public String getIsWithdrawEnable() {
		return isWithdrawEnable;
	}
	public void setIsWithdrawEnable(String isWithdrawEnable) {
		this.isWithdrawEnable = isWithdrawEnable;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getIsBillable() {
		return isBillable;
	}
	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}
	public int getSelfBalancedCounts() {
		return selfBalancedCounts;
	}
	public void setSelfBalancedCounts(int selfBalancedCounts) {
		this.selfBalancedCounts = selfBalancedCounts;
	}
	public String getTopLeaveTakersreportYear() {
		return topLeaveTakersreportYear;
	}
	public void setTopLeaveTakersreportYear(String topLeaveTakersreportYear) {
		this.topLeaveTakersreportYear = topLeaveTakersreportYear;
	}
	public float getExtraLeavesTaken() {
		return extraLeavesTaken;
	}
	public void setExtraLeavesTaken(float extraLeavesTaken) {
		this.extraLeavesTaken = extraLeavesTaken;
	}
	public String getLeaveUUID()
	{
		return leaveUUID;
	}
	public void setLeaveUUID(String leaveUUID)
	{
		this.leaveUUID = leaveUUID;
	}
	public float getAppliedNoOfDays()
	{
		return appliedNoOfDays;
	}
	public void setAppliedNoOfDays(float appliedNoOfDays)
	{
		this.appliedNoOfDays = appliedNoOfDays;
	}
	public float getActualLeavesTaken()
	{
		return actualLeavesTaken;
	}
	public void setActualLeavesTaken(float actualLeavesTaken)
	{
		this.actualLeavesTaken = actualLeavesTaken;
	}
	public float getThisMonthLeavesTaken()
	{
		return thisMonthLeavesTaken;
	}
	public void setThisMonthLeavesTaken(float thisMonthLeavesTaken)
	{
		this.thisMonthLeavesTaken = thisMonthLeavesTaken;
	}
	public float getTotalBalLeaves()
	{
		return totalBalLeaves;
	}
	public void setTotalBalLeaves(float totalBalLeaves)
	{
		this.totalBalLeaves = totalBalLeaves;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public float getLeaveNoDays() {
		return leaveNoDays;
	}
	public void setLeaveNoDays(float leaveNoDays) {
		this.leaveNoDays = leaveNoDays;
	}
	public String getLeaveName() {
		return leaveName;
	}
	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}
	public boolean getIsSuperUser()
	{
		return isSuperUser;
	}
	public void setIsSuperUser(boolean isSuperUser)
	{
		this.isSuperUser = isSuperUser;
	}
	public String getWithdrawReason() {
		return withdrawReason;
	}
	public void setWithdrawReason(String withdrawReason) {
		this.withdrawReason = withdrawReason;
	}
	public Long getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}
	public String getCurrentStage() {
		return currentStage;
	}
	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}
	public List<String> getOtherApproverUser() {
		return otherApproverUser;
	}
	public void setOtherApproverUser(List<String> otherApproverUser) {
		this.otherApproverUser = otherApproverUser;
	}
	public String getNotifyToOthers() {
		return notifyToOthers;
	}
	public void setNotifyToOthers(String notifyToOthers) {
		this.notifyToOthers = notifyToOthers;
	}
	public String getFromDateFromHalfTime() {
		return fromDateFromHalfTime;
	}
	public void setFromDateFromHalfTime(String fromDateFromHalfTime) {
		this.fromDateFromHalfTime = fromDateFromHalfTime;
	}
	public String getFromDateToHalfTime() {
		return fromDateToHalfTime;
	}
	public void setFromDateToHalfTime(String fromDateToHalfTime) {
		this.fromDateToHalfTime = fromDateToHalfTime;
	}
	public String getToDateFromHalfTime() {
		return toDateFromHalfTime;
	}
	public void setToDateFromHalfTime(String toDateFromHalfTime) {
		this.toDateFromHalfTime = toDateFromHalfTime;
	}
	public String getToDateToHalfTime() {
		return toDateToHalfTime;
	}
	public void setToDateToHalfTime(String toDateToHalfTime) {
		this.toDateToHalfTime = toDateToHalfTime;
	}
	public String getDurationHrs()
	{
		return durationHrs;
	}
	public void setDurationHrs(String durationHrs)
	{
		this.durationHrs = durationHrs;
	}
	public void setSuperUser(boolean isSuperUser)
	{
		this.isSuperUser = isSuperUser;
	}
	public String getLoginUserRole()
	{
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole)
	{
		this.loginUserRole = loginUserRole;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
}
