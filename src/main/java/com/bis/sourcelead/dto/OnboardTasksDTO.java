package com.bis.sourcelead.dto;

import java.io.Serializable;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnboardTasksDTO implements Serializable {
	
	  private String taskName;
	  
	  private String templateId;
	  
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
	
}