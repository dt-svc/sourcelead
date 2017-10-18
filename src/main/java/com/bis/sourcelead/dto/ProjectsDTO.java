package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectsDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String projectSequeneNumber;
	
	private String expenseId;
	
	private String startDate;
	
	private String endDate;
	
	private String status;
	
	private String timeSheetFrequency;
	
	private String isAllowFreelanceDevelopers;
	
	private String isAllowRemoteWork;
	
	private String isAllowOverTime;
	
	private String flatPercentageId;
	
	private String projectName;
	
	private String level1Manager;
	
	private String level2Manager;
	
	private String invoiceFrequency;
	
	private String isTimesheetAttachmentRequired;
	
	private String isAllowOffshoreResource;
	
	private String isTimeEntryAfterDeadline;
	
	private String isAllowBidding;
	
	private String projectDescription;
	
	private String projectId;
	
	private String estimatedHours;
	
	private String estimatedBudget;
	
	private Double flatPercentage;
	
	private String staticPath;

    private List<Map<String, String>> fileInfo;

	private List<FileInfo> uploadedFileInfo;
	
	private String projectManagers;
	
	private String projectDate;
	
	private String client;
	
	private String projectStatus;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String estimatedBudgetCurrency;
	
	private String budgetApproverUser;
	
	private String notifyBudgetAt;
	
	private String budgetStatus;
	
	private String projectShortCode;
	
	List<ExpensesCategoryListDTO> expenseCategoryList;
	
	List<ProjectMembersDTO> projectMembersList;
	
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getProjectSequeneNumber() {
		return projectSequeneNumber;
	}

	public void setProjectSequeneNumber(String projectSequeneNumber) {
		this.projectSequeneNumber = projectSequeneNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeSheetFrequency() {
		return timeSheetFrequency;
	}

	public void setTimeSheetFrequency(String timeSheetFrequency) {
		this.timeSheetFrequency = timeSheetFrequency;
	}

	public String getIsAllowFreelanceDevelopers() {
		return isAllowFreelanceDevelopers;
	}

	public void setIsAllowFreelanceDevelopers(String isAllowFreelanceDevelopers) {
		this.isAllowFreelanceDevelopers = isAllowFreelanceDevelopers;
	}

	public String getIsAllowRemoteWork() {
		return isAllowRemoteWork;
	}

	public void setIsAllowRemoteWork(String isAllowRemoteWork) {
		this.isAllowRemoteWork = isAllowRemoteWork;
	}

	public String getIsAllowOverTime() {
		return isAllowOverTime;
	}

	public void setIsAllowOverTime(String isAllowOverTime) {
		this.isAllowOverTime = isAllowOverTime;
	}

	public String getFlatPercentageId() {
		return flatPercentageId;
	}

	public void setFlatPercentageId(String flatPercentageId) {
		this.flatPercentageId = flatPercentageId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLevel1Manager() {
		return level1Manager;
	}

	public void setLevel1Manager(String level1Manager) {
		this.level1Manager = level1Manager;
	}

	public String getLevel2Manager() {
		return level2Manager;
	}

	public void setLevel2Manager(String level2Manager) {
		this.level2Manager = level2Manager;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getIsTimesheetAttachmentRequired() {
		return isTimesheetAttachmentRequired;
	}

	public void setIsTimesheetAttachmentRequired(String isTimesheetAttachmentRequired) {
		this.isTimesheetAttachmentRequired = isTimesheetAttachmentRequired;
	}

	public String getIsAllowOffshoreResource() {
		return isAllowOffshoreResource;
	}

	public void setIsAllowOffshoreResource(String isAllowOffshoreResource) {
		this.isAllowOffshoreResource = isAllowOffshoreResource;
	}

	public String getIsTimeEntryAfterDeadline() {
		return isTimeEntryAfterDeadline;
	}

	public void setIsTimeEntryAfterDeadline(String isTimeEntryAfterDeadline) {
		this.isTimeEntryAfterDeadline = isTimeEntryAfterDeadline;
	}

	public String getIsAllowBidding() {
		return isAllowBidding;
	}

	public void setIsAllowBidding(String isAllowBidding) {
		this.isAllowBidding = isAllowBidding;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public String getEstimatedBudget() {
		return estimatedBudget;
	}

	public void setEstimatedBudget(String estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public Double getFlatPercentage() {
		return flatPercentage;
	}

	public void setFlatPercentage(Double flatPercentage) {
		this.flatPercentage = flatPercentage;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(String projectManagers) {
		this.projectManagers = projectManagers;
	}

	public String getProjectDate() {
		return projectDate;
	}

	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}

	public String getBudgetApproverUser() {
		return budgetApproverUser;
	}

	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}

	public void setBudgetApproverUser(String budgetApproverUser) {
		this.budgetApproverUser = budgetApproverUser;
	}

	public String getNotifyBudgetAt() {
		return notifyBudgetAt;
	}

	public void setNotifyBudgetAt(String notifyBudgetAt) {
		this.notifyBudgetAt = notifyBudgetAt;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public String getProjectShortCode() {
		return projectShortCode;
	}

	public void setProjectShortCode(String projectShortCode) {
		this.projectShortCode = projectShortCode;
	}

	public List<ExpensesCategoryListDTO> getExpenseCategoryList() {
		return expenseCategoryList;
	}

	public void setExpenseCategoryList(List<ExpensesCategoryListDTO> expenseCategoryList) {
		this.expenseCategoryList = expenseCategoryList;
	}

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}
	
	
}
