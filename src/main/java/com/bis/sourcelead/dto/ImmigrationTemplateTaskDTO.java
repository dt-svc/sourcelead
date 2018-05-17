package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

/**
 * @author
 *
 */
public class ImmigrationTemplateTaskDTO extends SearchDTO implements Serializable {

	private Long id;

	private Long templateId;
	
	// add form form list property here
	@Required(desc = "Task Name")
	private String taskName; // Immigration task related properties

	private String priority;

	@Required(desc = "Due After Months")
	private Integer dueAfterMonths;

	@Required(desc = "Due After Days")
	private Integer dueAfterDays;

	private String taskRepeat;

	private String taskEmail;

	private String taskNotification;

	private String taskDescription;

	private String formFileName;

	private String formFileUrl;

	private String formFileContentType;

	private String formName;

	private String formDescription;

	private String staticPath;

	//@Required(desc = "Petition AssignedTo")
	private String petitionAssignedTo;
	
	private String petitionAssignedToName;

	private String taskReminder;

	private String assignedTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getDueAfterMonths() {
		return dueAfterMonths;
	}

	public void setDueAfterMonths(Integer dueAfterMonths) {
		this.dueAfterMonths = dueAfterMonths;
	}

	public Integer getDueAfterDays() {
		return dueAfterDays;
	}

	public void setDueAfterDays(Integer dueAfterDays) {
		this.dueAfterDays = dueAfterDays;
	}

	public String getTaskRepeat() {
		return taskRepeat;
	}

	public void setTaskRepeat(String taskRepeat) {
		this.taskRepeat = taskRepeat;
	}

	public String getTaskEmail() {
		return taskEmail;
	}

	public void setTaskEmail(String taskEmail) {
		this.taskEmail = taskEmail;
	}

	public String getTaskNotification() {
		return taskNotification;
	}

	public void setTaskNotification(String taskNotification) {
		this.taskNotification = taskNotification;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getFormFileName() {
		return formFileName;
	}

	public void setFormFileName(String formFileName) {
		this.formFileName = formFileName;
	}

	public String getFormFileUrl() {
		return formFileUrl;
	}

	public void setFormFileUrl(String formFileUrl) {
		this.formFileUrl = formFileUrl;
	}

	public String getFormFileContentType() {
		return formFileContentType;
	}

	public void setFormFileContentType(String formFileContentType) {
		this.formFileContentType = formFileContentType;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormDescription() {
		return formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getPetitionAssignedTo() {
		return petitionAssignedTo;
	}

	public void setPetitionAssignedTo(String petitionAssignedTo) {
		this.petitionAssignedTo = petitionAssignedTo;
	}

	public String getPetitionAssignedToName() {
		return petitionAssignedToName;
	}

	public void setPetitionAssignedToName(String petitionAssignedToName) {
		this.petitionAssignedToName = petitionAssignedToName;
	}

	public String getTaskReminder() {
		return taskReminder;
	}

	public void setTaskReminder(String taskReminder) {
		this.taskReminder = taskReminder;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	

	

}
