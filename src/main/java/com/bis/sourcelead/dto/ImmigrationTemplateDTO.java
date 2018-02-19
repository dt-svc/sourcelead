package com.bis.sourcelead.dto;

import java.util.List;

/**
 * @author 
 *
 */
public class ImmigrationTemplateDTO {

	private Long id;
	private String templateName;
	private String templateDescription;
	private List<ImmigrationTemplateDocDTO> immigrationDocList;
	// add form form list property here
	private String taskName;  //  Immigration task related properties
	private String assignToUsers;
	private String assignToCandidate;
	private String priority;
	private Integer dueAfterMonths;
	private Integer dueAfterDays;
	private String taskRepeat;
	private String taskEmail;
	private String taskNotification;
	private String taskDescription;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public List<ImmigrationTemplateDocDTO> getImmigrationDocList() {
		return immigrationDocList;
	}
	public void setImmigrationDocList(List<ImmigrationTemplateDocDTO> immigrationDocList) {
		this.immigrationDocList = immigrationDocList;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAssignToUsers() {
		return assignToUsers;
	}
	public void setAssignToUsers(String assignToUsers) {
		this.assignToUsers = assignToUsers;
	}
	public String getAssignToCandidate() {
		return assignToCandidate;
	}
	public void setAssignToCandidate(String assignToCandidate) {
		this.assignToCandidate = assignToCandidate;
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
	
}
