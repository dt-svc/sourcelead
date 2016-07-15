package com.bis.sourcelead.entity;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TIMESHEET_SUBMISSIONS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class TimesheetSubmissions {
	
	 
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PROJECT_MEMBER_ID", nullable=false)
	private ProjectMembers projectMembers;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "START_DATE")
	private Date startDate;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "ESTIMATED_HOURS")
	private String estimatedHours;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status submissionStatus;
	
	@Column(name = "SUBMISSION_APPROVALSTATUS")
	@Enumerated(EnumType.STRING)
	private Submission_ApprovalStatus submissionApprovalStatus;
	
  
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
		
	@Column(name = "LEVEL_OF_APPROVER")
	private Integer levelOfApprover;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "APPROVER_USER_ID", nullable=false)
	private User approverUserId;
	
	@Column(name = "STATUS")
	private String status;
	 
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = " CREATED_DATE")
	private Date createdDate; 
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	  
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
	private Date updatedDate; 
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private Collection<ProjectTasks> projectTasksList;
	
	@Transient
	private String  endDateStr;
	
	@Transient
	private String  startDateStr;
	
	@Transient
	private String  fullName;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
	
	@JsonIgnore
	public ProjectMembers getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(ProjectMembers projectMembers) {
		this.projectMembers = projectMembers;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Submission_Status getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(Submission_Status submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public Submission_ApprovalStatus getSubmissionApprovalStatus() {
		return submissionApprovalStatus;
	}

	public void setSubmissionApprovalStatus(Submission_ApprovalStatus submissionApprovalStatus) {
		this.submissionApprovalStatus = submissionApprovalStatus;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Integer getLevelOfApprover() {
		return levelOfApprover;
	}

	public void setLevelOfApprover(Integer levelOfApprover) {
		this.levelOfApprover = levelOfApprover;
	}

	public User getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(User approverUserId) {
		this.approverUserId = approverUserId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public Collection<ProjectTasks> getProjectTasksList() {
		return projectTasksList;
	}

	public void setProjectTasksList(Collection<ProjectTasks> projectTasksList) {
		this.projectTasksList = projectTasksList;
	}

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	 
}
