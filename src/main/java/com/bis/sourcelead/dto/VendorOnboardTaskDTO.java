package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.entity.User;

public class VendorOnboardTaskDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String vendorOnboardTaskId;
	
    private Long id;
	
   	private String taskName;
	
	private Long vendorOnboardingTemplateId;
    
	private Long vendorOnboardingId;
    
   	private String assignToUsers;
	
   	private String assignToVendor;
	
   	private String priority;
	
	@Column(name = "DUE_AFTER_MONTHS")
   	private Integer dueAfterMonths;
	
   	private Integer dueAfterDays;
	
   	private Integer dueAfterHours;
	
   	private Integer dueAfterMinutes;
	
   	private String taskRepeat;
	
   	private String taskEmail;
	
   	private String taskNotification;
	
   	private String taskType;
	
   	@CleanHtml(escape=false)
	private String description;
    
   	private Integer status;
   		
   	private Date createdDate;
   	
   	private Date updatedDate;
   	
  	private User createdBy;
	
  	private User updatedBy;
	
	private String dueAfterString;
	
   	private List<String> usersList;
	
	

	public String getVendorOnboardTaskId() {
		return vendorOnboardTaskId;
	}

	public void setVendorOnboardTaskId(String vendorOnboardTaskId) {
		this.vendorOnboardTaskId = vendorOnboardTaskId;
	}

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

	public Long getVendorOnboardingTemplateId() {
		return vendorOnboardingTemplateId;
	}

	public void setVendorOnboardingTemplateId(Long vendorOnboardingTemplateId) {
		this.vendorOnboardingTemplateId = vendorOnboardingTemplateId;
	}

	public Long getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(Long vendorOnboardingId) {
		this.vendorOnboardingId = vendorOnboardingId;
	}

	public String getAssignToUsers() {
		return assignToUsers;
	}

	public void setAssignToUsers(String assignToUsers) {
		this.assignToUsers = assignToUsers;
	}

	public String getAssignToVendor() {
		return assignToVendor;
	}

	public void setAssignToVendor(String assignToVendor) {
		this.assignToVendor = assignToVendor;
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

	public Integer getDueAfterHours() {
		return dueAfterHours;
	}

	public void setDueAfterHours(Integer dueAfterHours) {
		this.dueAfterHours = dueAfterHours;
	}

	public Integer getDueAfterMinutes() {
		return dueAfterMinutes;
	}

	public void setDueAfterMinutes(Integer dueAfterMinutes) {
		this.dueAfterMinutes = dueAfterMinutes;
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

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
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
}
