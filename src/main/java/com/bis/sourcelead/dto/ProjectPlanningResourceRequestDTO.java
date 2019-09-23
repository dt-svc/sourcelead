package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectPlanningResourceRequestDTO implements Serializable{

	 private static final long serialVersionUID = 1L;
	
	 private String projectPlanningRequestId;
	
	 private String candidateName;
	 
	 private String projectName;
	 
	 private String approverName;
	 
	 private String requestedUser;
	
	 private Double payRate;
	
	 private String payRateCurrency;
	 
	 private String resourceStatus;
	
	 private String payRateType;
	
	 private Double billRate;
	
	 private String billRateCurrency;
	 
	 private String billRateType;
	
	 private String sortColumn;
		
	 private String sortOrder;
	 
	 private List<Map<String, String>> order;
	 
	 private String searchValue;
	 
	 private int start;
	 
	 private int length;
	
	 private String consultingType;
	 
	 private String requestUUID;
	 
	 private Integer approvedCount;
		
	 private Integer rejectedCount;
		
	 private Integer waitingCount;
		
	 private Integer onholdCount;
	 
	 private String rejectedReason;
	 
	 private String projectPlanningRequestUUID;
	 
	 private String projectClientUUID;
	 
	 private String projectUUID;
	 
	 private String candidateUUID;
	
	 private String userCode;
	 
	 private String approvedUser;
	 
	 private Long requestedOrgLocationId;
	 
	 private BigDecimal percentageOfWork;
	 

	public BigDecimal getPercentageOfWork() {
		return percentageOfWork;
	}

	public void setPercentageOfWork(BigDecimal percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}

	public String getProjectPlanningRequestId() {
		return projectPlanningRequestId;
	}

	public void setProjectPlanningRequestId(String projectPlanningRequestId) {
		this.projectPlanningRequestId = projectPlanningRequestId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public String getPayRateCurrency() {
		return payRateCurrency;
	}

	public void setPayRateCurrency(String payRateCurrency) {
		this.payRateCurrency = payRateCurrency;
	}

	public String getPayRateType() {
		return payRateType;
	}

	public void setPayRateType(String payRateType) {
		this.payRateType = payRateType;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public String getBillRateType() {
		return billRateType;
	}

	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}

	public String getConsultingType() {
		return consultingType;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}

	public Integer getApprovedCount() {
		return approvedCount;
	}

	public void setApprovedCount(Integer approvedCount) {
		this.approvedCount = approvedCount;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public Integer getWaitingCount() {
		return waitingCount;
	}

	public void setWaitingCount(Integer waitingCount) {
		this.waitingCount = waitingCount;
	}

	public Integer getOnholdCount() {
		return onholdCount;
	}

	public void setOnholdCount(Integer onholdCount) {
		this.onholdCount = onholdCount;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getProjectPlanningRequestUUID() {
		return projectPlanningRequestUUID;
	}

	public void setProjectPlanningRequestUUID(String projectPlanningRequestUUID) {
		this.projectPlanningRequestUUID = projectPlanningRequestUUID;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Long getRequestedOrgLocationId() {
		return requestedOrgLocationId;
	}

	public void setRequestedOrgLocationId(Long requestedOrgLocationId) {
		this.requestedOrgLocationId = requestedOrgLocationId;
	}
}
