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

@Entity
@Table(name = "OFFBOARD_TASKS")
public class OffboardTasks implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "TASK_NAME")
   	private String taskName;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CANDIDATE_OFF_BOARDING_ID", nullable = false)
   	private CandidateOffBoarding candidateOffBoardingId;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFF_BOARDING_TEMPLATE_ID", nullable = false)
	private OffBoardingTemplate offboardingTemplateId;
    
	@Column(name = "ASSIGN_TO_USERS")
   	private String assignToUsers;
	
	@Column(name = "ASSIGN_TO_CANDIDATE")
   	private String assignToCandidate;
	
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
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
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
   	
   	@Column(name = "IS_TASK_SUBMITTED")
   	private String isTaskSumbitted;
	
   	
   	@Transient
   	private List<String> usersList;
   	
   	@Transient
   	private String dueAfterString;
   	
	@Transient
   	private String offboardTaskType;
   	
   	
   	

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

	public CandidateOffBoarding getCandidateOffBoardingId() {
		return candidateOffBoardingId;
	}

	public void setCandidateOffBoardingId(CandidateOffBoarding candidateOffBoardingId) {
		this.candidateOffBoardingId = candidateOffBoardingId;
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

	public OffBoardingTemplate getOffboardingTemplateId() {
		return offboardingTemplateId;
	}

	public void setOffboardingTemplateId(OffBoardingTemplate offboardingTemplateId) {
		this.offboardingTemplateId = offboardingTemplateId;
	}

	public List<String> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<String> usersList) {
		this.usersList = usersList;
	}

	public String getDueAfterString() {
		return dueAfterString;
	}

	public void setDueAfterString(String dueAfterString) {
		this.dueAfterString = dueAfterString;
	}

	public String getOffboardTaskType() {
		return offboardTaskType;
	}

	public void setOffboardTaskType(String offboardTaskType) {
		this.offboardTaskType = offboardTaskType;
	}

	public String getIsTaskSumbitted() {
		return isTaskSumbitted;
	}

	public void setIsTaskSumbitted(String isTaskSumbitted) {
		this.isTaskSumbitted = isTaskSumbitted;
	}

   	

}
