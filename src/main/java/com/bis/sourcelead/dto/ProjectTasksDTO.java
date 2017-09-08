package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class ProjectTasksDTO implements Serializable {
	private Long id;
	
	private String taskName;
	
	private String taskBillableType;

	private String epic;
	
	private String taskstype;
	
	private String userStory;
	
	private String subTask;
	
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
	
	private String staticPath;
	
//    private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> fileInfo;
	
	private String documentName;
	
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
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
//	public List<FileInfo> getUploadedFileInfo() {
//		return uploadedFileInfo;
//	}
//	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
//		this.uploadedFileInfo = uploadedFileInfo;
//	}
//	public List<Map<String, String>> getFileInfo() {
//		return fileInfo;
//	}
//	public void setFileInfo(List<Map<String, String>> fileInfo) {
//		this.fileInfo = fileInfo;
//	}
	
	
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
}
