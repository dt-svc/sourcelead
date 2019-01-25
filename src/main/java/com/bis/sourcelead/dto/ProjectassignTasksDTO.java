package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.bis.sourcelead.annotations.Required;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ProjectassignTasksDTO extends SearchDTO implements Serializable{

	private BigInteger assignId;
	
	private BigInteger candidateId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private BigInteger memberId;
	
	private BigInteger taskId;
	
	private String projectName;
	
	private BigInteger lookupId;
	
	private String taskStatus;
	
	private String taskSeverity;
	
	private String estimatedTime;
	
	private String taskOwnerName;
	
	private String fileUrl;
	
	private Long candidateid;
	
	private Long orgLocationId;
	
	private String projctName;
	
	private String taskName;

	private String priority;
	
	private String colorPic;
	
	private Integer percentage;
	
	private String dueDate;
	
	private String exportType;
	
	private Map<String,String> assignMembersMap;
	
	private String priorityFilter;
	
	private String taskStatusFilter;
	
	private String taskTypeFilter;
	
	private String projectFilter;
	
	private String severityFilter;
	
	private String fromDate;
	
	private String toDate;
	
	private String taskType;

	public BigInteger getAssignId() {
		return assignId;
	}

	public BigInteger getCandidateId() {
		return candidateId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public BigInteger getMemberId() {
		return memberId;
	}

	public BigInteger getTaskId() {
		return taskId;
	}

	public String getProjectName() {
		return projectName;
	}

	public BigInteger getLookupId() {
		return lookupId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setAssignId(BigInteger assignId) {
		this.assignId = assignId;
	}

	public void setCandidateId(BigInteger candidateId) {
		this.candidateId = candidateId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setMemberId(BigInteger memberId) {
		this.memberId = memberId;
	}

	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setLookupId(BigInteger lookupId) {
		this.lookupId = lookupId;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Map<String, String> getAssignMembersMap() {
		return assignMembersMap;
	}

	public void setAssignMembersMap(Map<String, String> assignMembersMap) {
		this.assignMembersMap = assignMembersMap;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getCandidateid() {
		return candidateid;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public String getProjctName() {
		return projctName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getPriority() {
		return priority;
	}

	public String getColorPic() {
		return colorPic;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setProjctName(String projctName) {
		this.projctName = projctName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setColorPic(String colorPic) {
		this.colorPic = colorPic;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getPriorityFilter() {
		return priorityFilter;
	}

	public String getTaskStatusFilter() {
		return taskStatusFilter;
	}

	public String getTaskTypeFilter() {
		return taskTypeFilter;
	}

	public String getProjectFilter() {
		return projectFilter;
	}

	public void setPriorityFilter(String priorityFilter) {
		this.priorityFilter = priorityFilter;
	}

	public void setTaskStatusFilter(String taskStatusFilter) {
		this.taskStatusFilter = taskStatusFilter;
	}

	public void setTaskTypeFilter(String taskTypeFilter) {
		this.taskTypeFilter = taskTypeFilter;
	}

	public void setProjectFilter(String projectFilter) {
		this.projectFilter = projectFilter;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTaskSeverity() {
		return taskSeverity;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskSeverity(String taskSeverity) {
		this.taskSeverity = taskSeverity;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getSeverityFilter() {
		return severityFilter;
	}

	public void setSeverityFilter(String severityFilter) {
		this.severityFilter = severityFilter;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}

