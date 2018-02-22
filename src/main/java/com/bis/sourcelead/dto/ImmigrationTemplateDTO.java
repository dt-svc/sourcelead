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

	private String immigrationDocDeletedListIds;
	
	private String immigrationFormDeletedListIds;
	
	private List<ImmigrationTemplateDocDTO> immigrationDocList;
	
	private List<ImmigrationTemplateFormDTO> immigrationFormList;
	
	// add form form list property here
	private String taskName;  //  Immigration task related properties
	
	private String priority;
	
	private Integer dueAfterMonths;
	
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
	
	private String petitionAssignedTo;
	
	private String taskReminder;
	
	

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

	public String getPetitionAssignedTo() {
		return petitionAssignedTo;
	}

	public void setPetitionAssignedTo(String petitionAssignedTo) {
		this.petitionAssignedTo = petitionAssignedTo;
	}

	public String getTaskReminder() {
		return taskReminder;
	}

	public void setTaskReminder(String taskReminder) {
		this.taskReminder = taskReminder;
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

	public List<ImmigrationTemplateFormDTO> getImmigrationFormList() {
		return immigrationFormList;
	}

	public void setImmigrationFormList(List<ImmigrationTemplateFormDTO> immigrationFormList) {
		this.immigrationFormList = immigrationFormList;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getImmigrationDocDeletedListIds() {
		return immigrationDocDeletedListIds;
	}

	public void setImmigrationDocDeletedListIds(String immigrationDocDeletedListIds) {
		this.immigrationDocDeletedListIds = immigrationDocDeletedListIds;
	}

	public String getImmigrationFormDeletedListIds() {
		return immigrationFormDeletedListIds;
	}

	public void setImmigrationFormDeletedListIds(String immigrationFormDeletedListIds) {
		this.immigrationFormDeletedListIds = immigrationFormDeletedListIds;
	}

}
