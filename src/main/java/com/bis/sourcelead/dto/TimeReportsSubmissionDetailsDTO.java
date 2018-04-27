package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.HashMap;

public class TimeReportsSubmissionDetailsDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long projectMemberId;
	private String fileUrl;
	private String fullName;
	private String email;
	private String designationType;
	private String employeeId;
	private String projectName;
	private String clientName;
	private String vendorName;
	private String frequrncy;
	private String rate;
	private String rateType;
	private HashMap<String, ReportsTimeDTO> reportsTimeMap;
	
	private String monthTotalHours;
	private String monthBillabeHours;
	private String monthNonBillableHours;
	
	private String projectToDateTotal;
	private String projectToDateBillableTotal;
	private String projectToDateNonBillableTotal;
	private String lastMonthTotal;
	private String lastMonthBillableTotal;
	private String lastMonthNonBillableTotal;
	private String approvalStatus;
	private String combinedApprovalClass;
	
	public Long getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignationType() {
		return designationType;
	}
	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getFrequrncy() {
		return frequrncy;
	}
	public void setFrequrncy(String frequrncy) {
		this.frequrncy = frequrncy;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public HashMap<String, ReportsTimeDTO> getReportsTimeMap() {
		return reportsTimeMap;
	}
	public void setReportsTimeMap(HashMap<String, ReportsTimeDTO> reportsTimeMap) {
		this.reportsTimeMap = reportsTimeMap;
	}
	public String getProjectToDateTotal() {
		return projectToDateTotal;
	}
	public void setProjectToDateTotal(String projectToDateTotal) {
		this.projectToDateTotal = projectToDateTotal;
	}
	public String getProjectToDateBillableTotal() {
		return projectToDateBillableTotal;
	}
	public void setProjectToDateBillableTotal(String projectToDateBillableTotal) {
		this.projectToDateBillableTotal = projectToDateBillableTotal;
	}
	public String getProjectToDateNonBillableTotal() {
		return projectToDateNonBillableTotal;
	}
	public void setProjectToDateNonBillableTotal(String projectToDateNonBillableTotal) {
		this.projectToDateNonBillableTotal = projectToDateNonBillableTotal;
	}
	public String getLastMonthTotal() {
		return lastMonthTotal;
	}
	public void setLastMonthTotal(String lastMonthTotal) {
		this.lastMonthTotal = lastMonthTotal;
	}
	public String getLastMonthBillableTotal() {
		return lastMonthBillableTotal;
	}
	public void setLastMonthBillableTotal(String lastMonthBillableTotal) {
		this.lastMonthBillableTotal = lastMonthBillableTotal;
	}
	public String getLastMonthNonBillableTotal() {
		return lastMonthNonBillableTotal;
	}
	public void setLastMonthNonBillableTotal(String lastMonthNonBillableTotal) {
		this.lastMonthNonBillableTotal = lastMonthNonBillableTotal;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getMonthTotalHours() {
		return monthTotalHours;
	}
	public void setMonthTotalHours(String monthTotalHours) {
		this.monthTotalHours = monthTotalHours;
	}
	public String getMonthBillabeHours() {
		return monthBillabeHours;
	}
	public void setMonthBillabeHours(String monthBillabeHours) {
		this.monthBillabeHours = monthBillabeHours;
	}
	public String getMonthNonBillableHours() {
		return monthNonBillableHours;
	}
	public void setMonthNonBillableHours(String monthNonBillableHours) {
		this.monthNonBillableHours = monthNonBillableHours;
	}
	public String getCombinedApprovalClass() {
		return combinedApprovalClass;
	}
	public void setCombinedApprovalClass(String combinedApprovalClass) {
		this.combinedApprovalClass = combinedApprovalClass;
	}
	
	
	
}
