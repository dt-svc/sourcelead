package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnboardTasksDTO implements Serializable {
	
	@Required(desc = "taskName")  
	private String taskName;
	  
	  private String templateId;
	  
	  @Required(desc="assignedToType")
	  private String assignedToType;
	  
	  @Required(desc = "taskName", dependsOn = "assignedToType", parentValue = "Associate")
	  private String onboardAssignedTo;
	  
	  private String taskPriority;
	  
	  private String taskRepeat;
	  
	  private String taskMonths;
	  
	  private String taskDays;
	  
	  private String taskHours;
	  
	  private String taskMinutes;
	  
	  private String taskNotification;
	  
	  private String taskEmail;
	  
	  private String taskdescription;
	  
	  private String onboardTaskId;
	  
	  private String vendorOnboardId;
	  
	  private List<VendorOnboardTaskDTO> vendorOnboardTasksInfo;
	  
	  private String isMailSent;
	  
	  private Long id;
	  
	  private String priority;
	  
	  private String dueAfterString;
	  
	  private List<String> usersList;
	  
	  private String onboardTaskType;
	  
	  
	public List<String> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<String> usersList) {
		this.usersList = usersList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDueAfterString() {
		return dueAfterString;
	}

	public void setDueAfterString(String dueAfterString) {
		this.dueAfterString = dueAfterString;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getOnboardAssignedTo() {
		return onboardAssignedTo;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public String getTaskRepeat() {
		return taskRepeat;
	}

	public String getTaskMonths() {
		return taskMonths;
	}

	public String getTaskDays() {
		return taskDays;
	}

	public String getTaskHours() {
		return taskHours;
	}

	public String getTaskMinutes() {
		return taskMinutes;
	}

	public String getTaskNotification() {
		return taskNotification;
	}

	public String getTaskEmail() {
		return taskEmail;
	}

	public String getTaskdescription() {
		return taskdescription;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setOnboardAssignedTo(String onboardAssignedTo) {
		this.onboardAssignedTo = onboardAssignedTo;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public void setTaskRepeat(String taskRepeat) {
		this.taskRepeat = taskRepeat;
	}

	public void setTaskMonths(String taskMonths) {
		this.taskMonths = taskMonths;
	}

	public void setTaskDays(String taskDays) {
		this.taskDays = taskDays;
	}

	public void setTaskHours(String taskHours) {
		this.taskHours = taskHours;
	}

	public void setTaskMinutes(String taskMinutes) {
		this.taskMinutes = taskMinutes;
	}

	public void setTaskNotification(String taskNotification) {
		this.taskNotification = taskNotification;
	}

	public void setTaskEmail(String taskEmail) {
		this.taskEmail = taskEmail;
	}

	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}

	public String getOnboardTaskId() {
		return onboardTaskId;
	}

	public void setOnboardTaskId(String onboardTaskId) {
		this.onboardTaskId = onboardTaskId;
	}

	public String getVendorOnboardId() {
		return vendorOnboardId;
	}

	public void setVendorOnboardId(String vendorOnboardId) {
		this.vendorOnboardId = vendorOnboardId;
	}

	public List<VendorOnboardTaskDTO> getVendorOnboardTasksInfo() {
		return vendorOnboardTasksInfo;
	}

	public void setVendorOnboardTasksInfo(List<VendorOnboardTaskDTO> vendorOnboardTasksInfo) {
		this.vendorOnboardTasksInfo = vendorOnboardTasksInfo;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getOnboardTaskType() {
		return onboardTaskType;
	}

	public void setOnboardTaskType(String onboardTaskType) {
		this.onboardTaskType = onboardTaskType;
	}

	public String getAssignedToType() {
		return assignedToType;
	}

	public void setAssignedToType(String assignedToType) {
		this.assignedToType = assignedToType;
	}

}