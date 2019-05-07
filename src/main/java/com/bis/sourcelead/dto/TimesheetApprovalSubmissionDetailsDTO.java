package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;


public class TimesheetApprovalSubmissionDetailsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger projectMemberId;
	private BigInteger projectId;
	private BigInteger masterCandidateId;
	
	private String employeeName;
	private String profileImageFileUrl;
	private String employeeEmail;
	private String employeePhoneNo;
	private String designation;
	private String project;
	private String clientName;
	private BigInteger clientId;
	private String vendorOrgName;
	private List<String> weeekTotal;
	private List<String> submissionTotal;
	private String totalHours;
	private String estimatedHours;
	private String submissionStatus;
	private String actionPerformedBy;
	private String rejectedReason;
	private String actualStartDate;
	private String actualEndDate;
	private String currentTimeFrequency;
	private Integer tdSpanLength;
	
	private String orgRelationUUID;
	
	private String dashboardInvoiceFrequency;
	private String dashboardDateList;
	private String tableFor;
	
	private BigInteger consultantUserId;
	private BigInteger workLocationId;
	
	private String total;
	private String stage;
	public BigInteger getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(BigInteger projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	public BigInteger getMasterCandidateId() {
		return masterCandidateId;
	}
	public void setMasterCandidateId(BigInteger masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getProfileImageFileUrl() {
		return profileImageFileUrl;
	}
	public void setProfileImageFileUrl(String profileImageFileUrl) {
		this.profileImageFileUrl = profileImageFileUrl;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeePhoneNo() {
		return employeePhoneNo;
	}
	public void setEmployeePhoneNo(String employeePhoneNo) {
		this.employeePhoneNo = employeePhoneNo;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public BigInteger getClientId() {
		return clientId;
	}
	public void setClientId(BigInteger clientId) {
		this.clientId = clientId;
	}
	public String getVendorOrgName() {
		return vendorOrgName;
	}
	public void setVendorOrgName(String vendorOrgName) {
		this.vendorOrgName = vendorOrgName;
	}
	public List<String> getWeeekTotal() {
		return weeekTotal;
	}
	public void setWeeekTotal(List<String> weeekTotal) {
		this.weeekTotal = weeekTotal;
	}
	public List<String> getSubmissionTotal() {
		return submissionTotal;
	}
	public void setSubmissionTotal(List<String> submissionTotal) {
		this.submissionTotal = submissionTotal;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
	public String getEstimatedHours() {
		return estimatedHours;
	}
	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	public String getSubmissionStatus() {
		return submissionStatus;
	}
	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}
	public String getActionPerformedBy() {
		return actionPerformedBy;
	}
	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	public String getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	public String getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getCurrentTimeFrequency() {
		return currentTimeFrequency;
	}
	public void setCurrentTimeFrequency(String currentTimeFrequency) {
		this.currentTimeFrequency = currentTimeFrequency;
	}
	public Integer getTdSpanLength() {
		return tdSpanLength;
	}
	public void setTdSpanLength(Integer tdSpanLength) {
		this.tdSpanLength = tdSpanLength;
	}
	public String getOrgRelationUUID() {
		return orgRelationUUID;
	}
	public void setOrgRelationUUID(String orgRelationUUID) {
		this.orgRelationUUID = orgRelationUUID;
	}
	public String getDashboardInvoiceFrequency() {
		return dashboardInvoiceFrequency;
	}
	public void setDashboardInvoiceFrequency(String dashboardInvoiceFrequency) {
		this.dashboardInvoiceFrequency = dashboardInvoiceFrequency;
	}
	public String getDashboardDateList() {
		return dashboardDateList;
	}
	public void setDashboardDateList(String dashboardDateList) {
		this.dashboardDateList = dashboardDateList;
	}
	public String getTableFor() {
		return tableFor;
	}
	public void setTableFor(String tableFor) {
		this.tableFor = tableFor;
	}
	public BigInteger getConsultantUserId() {
		return consultantUserId;
	}
	public void setConsultantUserId(BigInteger consultantUserId) {
		this.consultantUserId = consultantUserId;
	}
	public BigInteger getWorkLocationId() {
		return workLocationId;
	}
	public void setWorkLocationId(BigInteger workLocationId) {
		this.workLocationId = workLocationId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	
	
	
}
