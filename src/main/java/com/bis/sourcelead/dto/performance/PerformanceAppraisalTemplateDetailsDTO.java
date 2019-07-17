package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;

public class PerformanceAppraisalTemplateDetailsDTO implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String appraisalStartDate;
	
	private String appraisalEndDate;
	
	private String selfAppraisalStartDate;
	
	private String selfAppraisalEndDate;
	
	private String appraisalApprovingStartDate;
	
	private String appraisalApprovingEndDate;
	
	private String appraisalProcessStartDate;
	
	private String appraisalProcessEndDate;
	
	private String resourceManagerReviewStartDate;
	
	private String resourceManagerReviewEndDate;
	
	private String reportingManagerReviewStartDate;
	
	private String reportingManagerReviewEndDate;
	
	private String peerReviewStartDate;
	
	private String peerReviewEndDate;
	
	private String description ;
	
	private String descriptionDisplay;
	
	private String reportingManager;
	
	private String resourceManager;
	
	private String peerIds;
	
	private String resourceManagerImgUrl;
	
	private String reportingManagerImgUrl;
	
	private BigInteger orgLocationId;
	
	private BigInteger appraisalAssignedUserId;
	
	private String assignedUserCode;
	
	private String categoriesJsonString;
	
	private BigInteger appraisalAssignedId;
	
	private String appraisalAssignedUser;
	
	private String appraisalAssignedUserImgUrl;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppraisalStartDate() {
		return appraisalStartDate;
	}

	public void setAppraisalStartDate(String appraisalStartDate) {
		this.appraisalStartDate = appraisalStartDate;
	}

	public String getAppraisalEndDate() {
		return appraisalEndDate;
	}

	public void setAppraisalEndDate(String appraisalEndDate) {
		this.appraisalEndDate = appraisalEndDate;
	}

	public String getSelfAppraisalStartDate() {
		return selfAppraisalStartDate;
	}

	public void setSelfAppraisalStartDate(String selfAppraisalStartDate) {
		this.selfAppraisalStartDate = selfAppraisalStartDate;
	}

	public String getSelfAppraisalEndDate() {
		return selfAppraisalEndDate;
	}

	public void setSelfAppraisalEndDate(String selfAppraisalEndDate) {
		this.selfAppraisalEndDate = selfAppraisalEndDate;
	}

	public String getAppraisalApprovingStartDate() {
		return appraisalApprovingStartDate;
	}

	public void setAppraisalApprovingStartDate(String appraisalApprovingStartDate) {
		this.appraisalApprovingStartDate = appraisalApprovingStartDate;
	}

	public String getAppraisalApprovingEndDate() {
		return appraisalApprovingEndDate;
	}

	public void setAppraisalApprovingEndDate(String appraisalApprovingEndDate) {
		this.appraisalApprovingEndDate = appraisalApprovingEndDate;
	}

	public String getAppraisalProcessStartDate() {
		return appraisalProcessStartDate;
	}

	public void setAppraisalProcessStartDate(String appraisalProcessStartDate) {
		this.appraisalProcessStartDate = appraisalProcessStartDate;
	}

	public String getAppraisalProcessEndDate() {
		return appraisalProcessEndDate;
	}

	public void setAppraisalProcessEndDate(String appraisalProcessEndDate) {
		this.appraisalProcessEndDate = appraisalProcessEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionDisplay() {
		return descriptionDisplay;
	}

	public void setDescriptionDisplay(String descriptionDisplay) {
		this.descriptionDisplay = descriptionDisplay;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(String resourceManager) {
		this.resourceManager = resourceManager;
	}

	public String getPeerIds() {
		return peerIds;
	}

	public void setPeerIds(String peerIds) {
		this.peerIds = peerIds;
	}


	public String getResourceManagerImgUrl() {
		return resourceManagerImgUrl;
	}

	public void setResourceManagerImgUrl(String resourceManagerImgUrl) {
		this.resourceManagerImgUrl = resourceManagerImgUrl;
	}

	public String getReportingManagerImgUrl() {
		return reportingManagerImgUrl;
	}

	public void setReportingManagerImgUrl(String reportingManagerImgUrl) {
		this.reportingManagerImgUrl = reportingManagerImgUrl;
	}

	public String getResourceManagerReviewStartDate() {
		return resourceManagerReviewStartDate;
	}

	public void setResourceManagerReviewStartDate(String resourceManagerReviewStartDate) {
		this.resourceManagerReviewStartDate = resourceManagerReviewStartDate;
	}

	public String getResourceManagerReviewEndDate() {
		return resourceManagerReviewEndDate;
	}

	public void setResourceManagerReviewEndDate(String resourceManagerReviewEndDate) {
		this.resourceManagerReviewEndDate = resourceManagerReviewEndDate;
	}

	public String getReportingManagerReviewStartDate() {
		return reportingManagerReviewStartDate;
	}

	public void setReportingManagerReviewStartDate(String reportingManagerReviewStartDate) {
		this.reportingManagerReviewStartDate = reportingManagerReviewStartDate;
	}

	public String getReportingManagerReviewEndDate() {
		return reportingManagerReviewEndDate;
	}

	public void setReportingManagerReviewEndDate(String reportingManagerReviewEndDate) {
		this.reportingManagerReviewEndDate = reportingManagerReviewEndDate;
	}

	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public BigInteger getAppraisalAssignedUserId() {
		return appraisalAssignedUserId;
	}

	public void setAppraisalAssignedUserId(BigInteger appraisalAssignedUserId) {
		this.appraisalAssignedUserId = appraisalAssignedUserId;
	}

	public String getAssignedUserCode() {
		return assignedUserCode;
	}

	public void setAssignedUserCode(String assignedUserCode) {
		this.assignedUserCode = assignedUserCode;
	}

	public String getCategoriesJsonString() {
		return categoriesJsonString;
	}

	public void setCategoriesJsonString(String categoriesJsonString) {
		this.categoriesJsonString = categoriesJsonString;
	}

	public BigInteger getAppraisalAssignedId() {
		return appraisalAssignedId;
	}

	public void setAppraisalAssignedId(BigInteger appraisalAssignedId) {
		this.appraisalAssignedId = appraisalAssignedId;
	}

	public String getPeerReviewStartDate() {
		return peerReviewStartDate;
	}

	public void setPeerReviewStartDate(String peerReviewStartDate) {
		this.peerReviewStartDate = peerReviewStartDate;
	}

	public String getPeerReviewEndDate() {
		return peerReviewEndDate;
	}

	public void setPeerReviewEndDate(String peerReviewEndDate) {
		this.peerReviewEndDate = peerReviewEndDate;
	}

	public String getAppraisalAssignedUser() {
		return appraisalAssignedUser;
	}

	public void setAppraisalAssignedUser(String appraisalAssignedUser) {
		this.appraisalAssignedUser = appraisalAssignedUser;
	}

	public String getAppraisalAssignedUserImgUrl() {
		return appraisalAssignedUserImgUrl;
	}

	public void setAppraisalAssignedUserImgUrl(String appraisalAssignedUserImgUrl) {
		this.appraisalAssignedUserImgUrl = appraisalAssignedUserImgUrl;
	}

	
	
	
}
