package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.DepartmentDTO;

public class PerformanceAppraisalTemplateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long appraisalTemplateId;
	
	@Required(desc="name")
	private String name;
	
	@Required(desc="appraisalStartDate")
	private String appraisalStartDate;
	
	@Required(desc="appraisalEndDate")
	private String appraisalEndDate;
	
	private String appraisalDepartment ;
	
	private String appraisalRoles ;
	
	private String appraisalEmpType ;
	
	private String selfAppraisalStartDate;
	
	private String selfAppraisalEndDate;
	
	private String appraisalApprovingStartDate;
	
	private String appraisalApprovingEndDate;
	
	private String appraisalProcessStartDate;
	
	private String appraisalProcessEndDate;
	
	private String description ;
	
	private String descriptionDisplay;
	
	private String feedbackCategory;
	
	private String appraisalCategories;
	
	private String comptencyCategories;
	
	private String jsonCategoriesString ;
	
	private String appraisalGoals;
	
	private BigInteger templateId;
	
	private BigInteger assignedId;
	
	@Required(desc="goalsWeightage", dependsOn = "appraisalGoals", parentValue = "Y")
	private String goalsWeightage;
	
	private String appraisalProjectTasks;
	
	@Required(desc="projectTasksWeightage", dependsOn = "appraisalProjectTasks", parentValue = "Y")
	private String projectTasksWeightage;
	
	private List<DepartmentDTO> departmentList;
	
	private String isSelfAppraisalRequired;
	
	private String isReportingManagerAppraisalRequired;
	
	private String isResourceManagerAppraisalRequired;
	
	private String isPeerReviewRequired;
	
	private String peerReviewStartDate;
	
	private String peerReviewEndDate;
	
	private String resourceManagerReviewStartDate;
	
	private String resourceManagerReviewEndDate;
	
	private String templateUUId;
	
	private BigInteger templateAssignedUserCount;
	
	private String appraisalAssignedUser;
	
	private String appraisalAssignedUserImgUrl;
	
	private int start;
	
	private int length;
	
	private String searchValue;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private List<Map<String, String>> order;
	
	private BigInteger daysDifference;
	
	private String dayDiff;
	
	private char daysDiff;
	
	private String submissionStage;
	
	public Long getAppraisalTemplateId() {
		return appraisalTemplateId;
	}

	public void setAppraisalTemplateId(Long appraisalTemplateId) {
		this.appraisalTemplateId = appraisalTemplateId;
	}

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

	public String getAppraisalDepartment() {
		return appraisalDepartment;
	}

	public void setAppraisalDepartment(String appraisalDepartment) {
		this.appraisalDepartment = appraisalDepartment;
	}

	public String getAppraisalRoles() {
		return appraisalRoles;
	}

	public void setAppraisalRoles(String appraisalRoles) {
		this.appraisalRoles = appraisalRoles;
	}

	public String getAppraisalEmpType() {
		return appraisalEmpType;
	}

	public void setAppraisalEmpType(String appraisalEmpType) {
		this.appraisalEmpType = appraisalEmpType;
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

	public String getAppraisalCategories() {
		return appraisalCategories;
	}

	public void setAppraisalCategories(String appraisalCategories) {
		this.appraisalCategories = appraisalCategories;
	}

	public String getJsonCategoriesString() {
		return jsonCategoriesString;
	}

	public void setJsonCategoriesString(String jsonCategoriesString) {
		this.jsonCategoriesString = jsonCategoriesString;
	}

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
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

	public String getFeedbackCategory() {
		return feedbackCategory;
	}

	public void setFeedbackCategory(String feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}

	public String getAppraisalGoals() {
		return appraisalGoals;
	}

	public void setAppraisalGoals(String appraisalGoals) {
		this.appraisalGoals = appraisalGoals;
	}

	public String getGoalsWeightage() {
		return goalsWeightage;
	}

	public void setGoalsWeightage(String goalsWeightage) {
		this.goalsWeightage = goalsWeightage;
	}

	public String getAppraisalProjectTasks() {
		return appraisalProjectTasks;
	}

	public void setAppraisalProjectTasks(String appraisalProjectTasks) {
		this.appraisalProjectTasks = appraisalProjectTasks;
	}

	public String getProjectTasksWeightage() {
		return projectTasksWeightage;
	}

	public void setProjectTasksWeightage(String projectTasksWeightage) {
		this.projectTasksWeightage = projectTasksWeightage;
	}

	public String getComptencyCategories() {
		return comptencyCategories;
	}

	public void setComptencyCategories(String comptencyCategories) {
		this.comptencyCategories = comptencyCategories;
	}

	public String getIsSelfAppraisalRequired() {
		return isSelfAppraisalRequired;
	}

	public void setIsSelfAppraisalRequired(String isSelfAppraisalRequired) {
		this.isSelfAppraisalRequired = isSelfAppraisalRequired;
	}

	public String getIsReportingManagerAppraisalRequired() {
		return isReportingManagerAppraisalRequired;
	}

	public void setIsReportingManagerAppraisalRequired(String isReportingManagerAppraisalRequired) {
		this.isReportingManagerAppraisalRequired = isReportingManagerAppraisalRequired;
	}

	public String getIsResourceManagerAppraisalRequired() {
		return isResourceManagerAppraisalRequired;
	}

	public void setIsResourceManagerAppraisalRequired(String isResourceManagerAppraisalRequired) {
		this.isResourceManagerAppraisalRequired = isResourceManagerAppraisalRequired;
	}

	public String getIsPeerReviewRequired() {
		return isPeerReviewRequired;
	}

	public void setIsPeerReviewRequired(String isPeerReviewRequired) {
		this.isPeerReviewRequired = isPeerReviewRequired;
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

	public BigInteger getTemplateId() {
		return templateId;
	}

	public void setTemplateId(BigInteger templateId) {
		this.templateId = templateId;
	}

	public String getTemplateUUId() {
		return templateUUId;
	}

	public void setTemplateUUId(String templateUUId) {
		this.templateUUId = templateUUId;
	}

	public BigInteger getTemplateAssignedUserCount() {
		return templateAssignedUserCount;
	}

	public void setTemplateAssignedUserCount(BigInteger templateAssignedUserCount) {
		this.templateAssignedUserCount = templateAssignedUserCount;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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

	public BigInteger getDaysDifference() {
		return daysDifference;
	}

	public void setDaysDifference(BigInteger daysDifference) {
		this.daysDifference = daysDifference;
	}

	public String getDayDiff() {
		return dayDiff;
	}

	public void setDayDiff(String dayDiff) {
		this.dayDiff = dayDiff;
	}

	public char getDaysDiff() {
		return daysDiff;
	}

	public void setDaysDiff(char daysDiff) {
		this.daysDiff = daysDiff;
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

	public BigInteger getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(BigInteger assignedId) {
		this.assignedId = assignedId;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
	}

	
}
