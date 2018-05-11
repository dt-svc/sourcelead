package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "VENDOR_ON_BOARD_TASKS")
public class VendorOnboardTasks implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "TASK_NAME")
   	private String taskName;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VENDOR_ON_BOARDING_TEMPLATE_ID", nullable = false)
	private VendorOnboardingTemplate vendorOnboardingTemplateId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VENDOR_ON_BOARDING_ID", nullable = false)
	private VendorOnboarding vendorOnboardingId;
    
    @Column(name = "ASSIGN_TO_USERS", columnDefinition = "TEXT default NULL")
   	private String assignToUsers;
	
	@Column(name = "ASSIGN_TO_VENDOR")
   	private String assignToVendor;
	
	@Column(name = "PRIORITY")
   	private String priority;
	
	@Column(name = "DUE_AFTER_MONTHS")
   	private Integer dueAfterMonths;
	
	@Column(name = "DUE_AFTER_DAYS")
   	private Integer dueAfterDays;
	
	@Column(name = "DUE_AFTER_HOURS")
   	private Integer dueAfterHours;
	
	@Column(name = "DUE_AFTER_MINUTES")
   	private Integer dueAfterMinutes;
	
	@Column(name = "TASK_REPEAT")
   	private String taskRepeat;
	
	@Column(name = "TASK_EMAIL")
   	private String taskEmail;
	
	@Column(name = "TASK_NOTIFICATION")
   	private String taskNotification;
	
	@Column(name = "TASK_TYPE")
   	private String taskType;
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
    
    @Column(name = "STATUS")
   	private Integer status;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
	
	@Transient
	@JsonProperty
	private String dueAfterString;
	
	
   	@Transient
   	@JsonProperty
   	private List<String> usersList;
	
	

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

	public VendorOnboardingTemplate getVendorOnboardingTemplateId() {
		return vendorOnboardingTemplateId;
	}

	public void setVendorOnboardingTemplateId(VendorOnboardingTemplate vendorOnboardingTemplateId) {
		this.vendorOnboardingTemplateId = vendorOnboardingTemplateId;
	}

	public VendorOnboarding getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(VendorOnboarding vendorOnboardingId) {
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
		
		 StringBuilder dueAfter = new StringBuilder();
		 
		 if(dueAfterMonths != null){
			 dueAfter = dueAfter.append(dueAfterMonths).append(" Month(s) ");
		 }
		 
		 if(dueAfterDays != null){
			 dueAfter = dueAfter.append(dueAfterDays).append(" Day(s) ");
		 }
		 
		 if(dueAfterHours != null){
			 dueAfter = dueAfter.append(dueAfterHours).append(" Hours ");
		 }
		 
		 if(dueAfterMinutes != null){
			 dueAfter = dueAfter.append(dueAfterMinutes).append(" Minutes");
		 }
		 
		 dueAfterString = dueAfter.toString();
		
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

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
   	
}
