package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OffboardTasksDTO implements Serializable {
	
	  @Required(desc = "taskName")
	  private String taskName;
	  
	  private String templateId;
	  
	  @Required(desc="assignedToType")
	  private String assignedToType;
	  
	  @Required(desc = "onboardAssignedTo", dependsOn = "assignedToType", parentValue = "Associate")
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
	  
	  private String offboardTaskId;
	  
	  private String offboardAssignedTo;
	  
	  private String id;
	  
	  private String priority;
	  
	  private String dueAfterString;
	  
	  private List<String> usersList;
	  
	  private String offboardTaskType;
	  
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


	public List<String> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<String> usersList) {
		this.usersList = usersList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOffboardTaskId() {
		return offboardTaskId;
	}

	public void setOffboardTaskId(String offboardTaskId) {
		this.offboardTaskId = offboardTaskId;
	}

	public String getOffboardAssignedTo() {
		return offboardAssignedTo;
	}

	public void setOffboardAssignedTo(String offboardAssignedTo) {
		this.offboardAssignedTo = offboardAssignedTo;
	}

	public String getOffboardTaskType() {
		return offboardTaskType;
	}

	public void setOffboardTaskType(String offboardTaskType) {
		this.offboardTaskType = offboardTaskType;
	}

	public String getAssignedToType() {
		return assignedToType;
	}

	public void setAssignedToType(String assignedToType) {
		this.assignedToType = assignedToType;
	}
	
	
}