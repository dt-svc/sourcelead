package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.ProjectAssignTask;

@SuppressWarnings("serial")
public class ProjectTasksDTO extends SearchDTO implements Serializable{
	private Long id;
	
	@Required(desc="taskName")
	private String taskName;
	
	@Required(desc="taskBillableType")
	private String taskBillableType;

	private String epic;
	
	@Required(desc="taskstype")
	private String taskstype;
	
	private String userStory;
	
	private String subTask;
	
	private Long priority;
	
	private Long severity;
	
	private String duDate;
	
	private String estimatedDate;
	
	private String estimatedTime;
	
	@Required(desc="taskStatus")
	private Long taskStatus;
	
	private String taskDescription;
	
	private String currencyType;
	
	private String businessValue;
	
	private Long taskOwner;
	
	private String assignTaskId;
	
	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private String documentName;
	
	private String projectId;
	
	private String projectTaskStatus;
	
	private String projectTaskType;
	
	private String projectTaskPriority;
	
	private String assignedTo;
	
	private String assignedMembers;
	
	private String teams;
	
	private String projectClientUUID;

	private List<CustomFormFieldDTO> customFormData;

	private String exportType;
	
	private String projectUUID;
	
	private String loginUserId;
	
	private String tags;
	
	private String priorityFilter;
	
	private String severityFilter;
	
	private String createdByFilter;
	
	private String tasksFilter;
	
	private String userCode;
	
	private Long candidateId;
	
	private Long orgLocationId;
	
	
	//Overdue task report
	private String projectName;
	
	private String taskTitle;
	
	private String assignTo;
	
	private String dueDate;
	
	private Integer completion;
	
	private String taskType;
	
	private String taskPriority;
	
	private String actionTimeStr;
	
	private String projectFilter;
	
	private String taskTypeFilter;
	
	private String fromDate;
	
	private String toDate;
	
	private List<ProjectAssignTask> assignedTaskUsers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskBillableType() {
		return taskBillableType;
	}
	public void setTaskBillableType(String taskBillableType) {
		this.taskBillableType = taskBillableType;
	}
	public String getEpic() {
		return epic;
	}
	public void setEpic(String epic) {
		this.epic = epic;
	}
	
	public String getTaskstype() {
		return taskstype;
	}
	public void setTaskstype(String taskstype) {
		this.taskstype = taskstype;
	}
	public String getUserStory() {
		return userStory;
	}
	public void setUserStory(String userStory) {
		this.userStory = userStory;
	}
	public String getSubTask() {
		return subTask;
	}
	public void setSubTask(String subTask) {
		this.subTask = subTask;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getSeverity() {
		return severity;
	}
	public void setSeverity(Long severity) {
		this.severity = severity;
	}
	public String getDuDate() {
		return duDate;
	}
	public void setDuDate(String duDate) {
		this.duDate = duDate;
	}
	public String getEstimatedDate() {
		return estimatedDate;
	}
	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public Long getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Long taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getBusinessValue() {
		return businessValue;
	}
	public void setBusinessValue(String businessValue) {
		this.businessValue = businessValue;
	}
	public Long getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(Long taskOwner) {
		this.taskOwner = taskOwner;
	}
	public String getAssignTaskId() {
		return assignTaskId;
	}
	public void setAssignTaskId(String assignTaskId) {
		this.assignTaskId = assignTaskId;
	}
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectTaskStatus() {
		return projectTaskStatus;
	}
	public String getProjectTaskType() {
		return projectTaskType;
	}
	public void setProjectTaskStatus(String projectTaskStatus) {
		this.projectTaskStatus = projectTaskStatus;
	}
	public void setProjectTaskType(String projectTaskType) {
		this.projectTaskType = projectTaskType;
	}
	public String getProjectTaskPriority() {
		return projectTaskPriority;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setProjectTaskPriority(String projectTaskPriority) {
		this.projectTaskPriority = projectTaskPriority;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssignedMembers() {
		return assignedMembers;
	}
	public void setAssignedMembers(String assignedMembers) {
		this.assignedMembers = assignedMembers;
	}
	public String getTeams() {
		return teams;
	}
	public void setTeams(String teams) {
		this.teams = teams;
	}
	public String getProjectClientUUID()
	{
		return projectClientUUID;
	}
	public void setProjectClientUUID(String projectClientUUID)
	{
		this.projectClientUUID = projectClientUUID;
	}

	public List<CustomFormFieldDTO> getCustomFormData() {
		return customFormData;
	}
	public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
		this.customFormData = customFormData;
	}
	
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
	public String getProjectUUID() {
		return projectUUID;
	}
	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPriorityFilter() {
		return priorityFilter;
	}
	public String getSeverityFilter() {
		return severityFilter;
	}
	public String getCreatedByFilter() {
		return createdByFilter;
	}
	public String getTasksFilter() {
		return tasksFilter;
	}
	public void setPriorityFilter(String priorityFilter) {
		this.priorityFilter = priorityFilter;
	}
	public void setSeverityFilter(String severityFilter) {
		this.severityFilter = severityFilter;
	}
	public void setCreatedByFilter(String createdByFilter) {
		this.createdByFilter = createdByFilter;
	}
	public void setTasksFilter(String tasksFilter) {
		this.tasksFilter = tasksFilter;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public String getDueDate() {
		return dueDate;
	}
	public Integer getCompletion() {
		return completion;
	}
	public String getTaskType() {
		return taskType;
	}
	public String getTaskPriority() {
		return taskPriority;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public void setCompletion(Integer completion) {
		this.completion = completion;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}
	public String getActionTimeStr() {
		return actionTimeStr;
	}
	public void setActionTimeStr(String actionTimeStr) {
		this.actionTimeStr = actionTimeStr;
	}
	public List<ProjectAssignTask> getAssignedTaskUsers() {
		return assignedTaskUsers;
	}
	public void setAssignedTaskUsers(List<ProjectAssignTask> assignedTaskUsers) {
		this.assignedTaskUsers = assignedTaskUsers;
	}
	public String getProjectFilter() {
		return projectFilter;
	}
	public String getTaskTypeFilter() {
		return taskTypeFilter;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setProjectFilter(String projectFilter) {
		this.projectFilter = projectFilter;
	}
	public void setTaskTypeFilter(String taskTypeFilter) {
		this.taskTypeFilter = taskTypeFilter;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}

