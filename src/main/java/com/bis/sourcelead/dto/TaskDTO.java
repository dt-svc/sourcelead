package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;

public class TaskDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
    private Long id;
	
   	private OrgLocations ownerOrglocationId;
	
   	private OrgLocations assignedOrglocationId;
	
   	private Long taskOwnerId;
	
   	private Long assignedUserId;
	
   	private Long onboardingId;
	
   	private Long offboardingId;
	
   	private Long vendorOnboardingId;
	
	private String priority;
	
	private Date taskDate;
	
    private String taskTime;
	 
	private String subject;
    
  	private String taskRepeat;
    
    private String alertType;
    
    private String taskStatus;
    
    private String description;
    
    private String taskDateStr;
    
    private String assignedName;
    
    private String taskOwnerName;
    
    private String vendorTaskDateStr;
    
    //Email fields
    private Long taskStatusId;
    
    private String taskId;
    
    private Long userId;
    
    private Long editOwnerId;
    
    private String assignValue;
    
    private Long taskpriorityId;
    
    private Long taskSeverityId;
    
    private String editDueDate;
    
    private String projectClientUUID;
    
    private Long candidateId;
    
    private String estimatedTime;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOwnerOrglocationId() {
		return ownerOrglocationId;
	}

	public void setOwnerOrglocationId(OrgLocations ownerOrglocationId) {
		this.ownerOrglocationId = ownerOrglocationId;
	}

	public OrgLocations getAssignedOrglocationId() {
		return assignedOrglocationId;
	}

	public void setAssignedOrglocationId(OrgLocations assignedOrglocationId) {
		this.assignedOrglocationId = assignedOrglocationId;
	}

	public Long getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(Long taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public Long getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(Long assignedUserId) {
		this.assignedUserId = assignedUserId;
	}

	public Long getOnboardingId() {
		return onboardingId;
	}

	public void setOnboardingId(Long onboardingId) {
		this.onboardingId = onboardingId;
	}

	public Long getOffboardingId() {
		return offboardingId;
	}

	public void setOffboardingId(Long offboardingId) {
		this.offboardingId = offboardingId;
	}

	public Long getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(Long vendorOnboardingId) {
		this.vendorOnboardingId = vendorOnboardingId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTaskRepeat() {
		return taskRepeat;
	}

	public void setTaskRepeat(String taskRepeat) {
		this.taskRepeat = taskRepeat;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskDateStr() {
		return taskDateStr;
	}

	public void setTaskDateStr(String taskDateStr) {
		this.taskDateStr = taskDateStr;
	}

	public String getAssignedName() {
		return assignedName;
	}

	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

	public String getVendorTaskDateStr() {
		return vendorTaskDateStr;
	}

	public void setVendorTaskDateStr(String vendorTaskDateStr) {
		this.vendorTaskDateStr = vendorTaskDateStr;
	}

	public Long getTaskStatusId() {
		return taskStatusId;
	}

	public String getTaskId() {
		return taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getEditOwnerId() {
		return editOwnerId;
	}

	public String getAssignValue() {
		return assignValue;
	}

	public Long getTaskpriorityId() {
		return taskpriorityId;
	}

	public Long getTaskSeverityId() {
		return taskSeverityId;
	}

	public String getEditDueDate() {
		return editDueDate;
	}

	public void setTaskStatusId(Long taskStatusId) {
		this.taskStatusId = taskStatusId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setEditOwnerId(Long editOwnerId) {
		this.editOwnerId = editOwnerId;
	}

	public void setAssignValue(String assignValue) {
		this.assignValue = assignValue;
	}

	public void setTaskpriorityId(Long taskpriorityId) {
		this.taskpriorityId = taskpriorityId;
	}

	public void setTaskSeverityId(Long taskSeverityId) {
		this.taskSeverityId = taskSeverityId;
	}

	public void setEditDueDate(String editDueDate) {
		this.editDueDate = editDueDate;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
}
