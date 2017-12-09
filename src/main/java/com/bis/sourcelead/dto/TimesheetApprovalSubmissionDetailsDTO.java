package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;


public class TimesheetApprovalSubmissionDetailsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long projectMemberId;
	private Long projectId;
	private Long masterCandidateId;
	private String employeeName;
	private String profileImageFileUrl;
	private String employeeEmail;
	private String employeePhoneNo;
	private String designation;
	private String project;
	private String clientName;
	private String total;
	private String stage;
	private String vendorOrgName;
	private List<String> weeekTotal;
	private List<String> submissionTotal;
	private String totalHours;
	private String estimatedHours;
	private String submissionStatus;
	private String submissionApprovalStatus;
	private String actionPerformedBy;
	private String rejectedReason;
	private String actualStartDate;
	private String actualEndDate;
	private int tdSpanLength;
	
	public Long getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
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
	public String getSubmissionApprovalStatus() {
		return submissionApprovalStatus;
	}
	public void setSubmissionApprovalStatus(String submissionApprovalStatus) {
		this.submissionApprovalStatus = submissionApprovalStatus;
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
	public int getTdSpanLength() {
		return tdSpanLength;
	}
	public void setTdSpanLength(int tdSpanLength) {
		this.tdSpanLength = tdSpanLength;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getMasterCandidateId() {
		return masterCandidateId;
	}
	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}
	
	
}
