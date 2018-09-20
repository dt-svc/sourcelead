package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "TASKS")
public class Tasks implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ORGLOCATION_ID")
   	private OrgLocations ownerOrglocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGNED_ORGLOCATION_ID")
   	private OrgLocations assignedOrglocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_OWNER_ID")
   	private User taskOwnerId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGNED_USER_ID")
   	private User assignedUserId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMMIGRATION_ID")
   	private Immigration immigrationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ON_BOARDING_ID")
   	private CandidateOnBoarding onboardingId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_OFF_BOARDING_ID")
   	private CandidateOffBoarding offboardingId;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VENDOR_ON_BOARDING_ID")
   	private VendorOnboarding vendorOnboardingId;
	
	
	@Column(name = "PRIORITY")
	private String priority;
	
	 @Column(name = "TASK_DATE")
	 private Date taskDate;
	
    @Column(name = "TASK_TIME")
    private String taskTime;
	 
    @Column(name="SUBJECT")
	private String subject;
    
    @Column(name="TASK_REPEAT")
  	private String taskRepeat;
    
    @Column(name = "ALERT_TYPE")
    private String alertType;
    
    @Column(name = "TASK_STATUS")
    private String taskStatus;
    
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
    private String description;
    
    @Column(name = "STATUS")
    private Integer status;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Transient
    private String taskDateStr;
    
    @Transient
    @JsonProperty
    private String assignedName;
    
    @Transient
    private String taskOwnerName;
    
    
    @Transient
    @JsonProperty
    private String vendorTaskDateStr;
    

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

	public User getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(User taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public User getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(User assignedUserId) {
		this.assignedUserId = assignedUserId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getTaskRepeat() {
		return taskRepeat;
	}

	public void setTaskRepeat(String taskRepeat) {
		this.taskRepeat = taskRepeat;
	}

	public String getTaskDateStr() {
		return taskDateStr;
	}

	public void setTaskDateStr(String taskDateStr) {
		this.taskDateStr = taskDateStr;
	}

	public String getAssignedName() {
		if(assignedUserId != null) {
			assignedName = assignedUserId.getFullName();
		}
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

	public CandidateOnBoarding getOnboardingId() {
		return onboardingId;
	}

	public void setOnboardingId(CandidateOnBoarding onboardingId) {
		this.onboardingId = onboardingId;
	}

	public CandidateOffBoarding getOffboardingId() {
		return offboardingId;
	}

	public void setOffboardingId(CandidateOffBoarding offboardingId) {
		this.offboardingId = offboardingId;
	}

	public VendorOnboarding getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(VendorOnboarding vendorOnboardingId) {
		this.vendorOnboardingId = vendorOnboardingId;
	}

	public String getVendorTaskDateStr() {
		if(taskDate != null){
			SimpleDateFormat dateFormat = ownerOrglocationId.getLocFullFormDateFormatter();
			vendorTaskDateStr = dateFormat.format(taskDate);	
		}else{
			vendorTaskDateStr = "--";
		}
		return vendorTaskDateStr;
	}

	public void setVendorTaskDateStr(String vendorTaskDateStr) {
		this.vendorTaskDateStr = vendorTaskDateStr;
	}

	public Immigration getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(Immigration immigrationId) {
		this.immigrationId = immigrationId;
	}
	
}

