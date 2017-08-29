package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class ProjectTasksDTO implements Serializable {
	private Long id;
	private String taskName;
	private String taskBillableType;
	private Long epic;
	private Long userStory;
	private Long subTask;
	private Long priority;
	private Long severity;
	private String duDate;
	private String estimatedDate;
	private Long taskStatus;
	private String taskDescription;
	private String currencyType;
	private String businessValue;
	private Long taskOwner;
	private List<Long> assignTaskId;
	private  MultipartFile[] project_files;
	
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
	public Long getEpic() {
		return epic;
	}
	public void setEpic(Long epic) {
		this.epic = epic;
	}
	public Long getUserStory() {
		return userStory;
	}
	public void setUserStory(Long userStory) {
		this.userStory = userStory;
	}
	public Long getSubTask() {
		return subTask;
	}
	public void setSubTask(Long subTask) {
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
	public List<Long> getAssignTaskId() {
		return assignTaskId;
	}
	public void setAssignTaskId(List<Long> assignTaskId) {
		this.assignTaskId = assignTaskId;
	}
	public MultipartFile[] getProject_files() {
		return project_files;
	}
	public void setProject_files(MultipartFile[] project_files) {
		this.project_files = project_files;
	}
}
