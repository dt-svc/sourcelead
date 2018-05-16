package com.bis.sourcelead.entity;

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

import org.hibernate.annotations.Type;

import com.bis.sourcelead.dto.FileInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "TIMESHEET_ACTIVITIES")
public class TimesheetActivities {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Projects projectId;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "MASTER_CANDIDATE_ID", nullable = false)
	private MasterCandidate masterCandidate;
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "HIERARCHY_ID", nullable = false)
	private ProjectApproverHierarchy hierarchyId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "SUBMISSION_ID", nullable = false)
	private TimesheetSubmissions submissionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBMISSION_APPROVER_HIERARCHY_ID", nullable = false)
	private SubmissionApproverHierarchy submissionApproverHierarchyId;
	
	
	
	@Column(name = "SUBMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status submissionStatus;
	
	@Column(name = "SUBMISSION_APPROVALSTATUS")
	@Enumerated(EnumType.STRING)
	private Submission_ApprovalStatus submissionApprovalStatus;*/
	
	 
	/*@Column(name = "INVOICE_STATUS")
    private String invoiceStatus;
	
	@Column(name = "BILLING_STATUS")
    private String billingStatus;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "INVOICE_ID", nullable = false)
	private Invoice invoiceId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "BILL_ID", nullable = false)
	private Invoice billId;*/
	
	@Column(name = "ACTIVITY_STATUS")
	private String activityStatus;
	
	
	@Column(name = "FREQUENCY_START_DATE")
    private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE")
    private Date frequencyEndDate;
	
	/*@Column(name = "MECHANISM_TYPE")
	@Enumerated(EnumType.STRING)
	private Approval_Mechanism_Type mechanismType;*/
	
	@Column(name = "REJECTED_REASON")
	@Type(type="text")
	private String rejectedReason;
	
	@Column(name = "ACTIONPERFORMEDBY")
	@Type(type="text")
	private String actionPerformedBy;
	
	@Column(name = "ACTION_PERFORMEDBY_USERID")
	private Long actionPerformedbyUserId;
	
	@Column(name = "ACTION_BY_ORGLOC_ID")
	private Long actionPerformedByOrgLocationId;
	
	@Column(name = "STATUS")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "REOPENED_USER_ID", nullable = false)
	private User reopenedUser;
	
	@Column(name = "REOPEN_REASON")
	@Type(type="text")
	private String reopenReason;
	
	@Column(name = "REOPEN_REJECTED_REASON")
	@Type(type="text")
	private String reopenRejectedReason;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name="WEEK_COMMENTS",columnDefinition = "TEXT default NULL")
	private String weekComments;
	
	@Transient
	@JsonProperty
	@JsonBackReference
	private String displayDate;
	
	@Transient
	@JsonProperty
	@JsonBackReference
	private String displayTime;
	
	@Transient
	@JsonProperty
	@JsonBackReference
	private String userFullName;
	
	@Transient
	@JsonProperty
	@JsonBackReference
	private List<FileInfo> timesheetFileUrl;
	
	@Column(name = "WEEK_TOTAL")
	private String weekTotal;
	
	@Column(name = "WEEK_TOTAL_BILLABLE")
	private String weekTotalBillable;
	
	@Column(name = "WEEK_TOTAL_NON_BILLABLE")
	private String weekTotalNonBillable;
	
	/*@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFrequencyStartDate() {
		return frequencyStartDate;
	}

	public void setFrequencyStartDate(Date frequencyStartDate) {
		this.frequencyStartDate = frequencyStartDate;
	}

	public Date getFrequencyEndDate() {
		return frequencyEndDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate) {
		this.frequencyEndDate = frequencyEndDate;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getActionPerformedBy() {
		return actionPerformedBy;
	}

	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}

	public Long getActionPerformedByOrgLocationId() {
		return actionPerformedByOrgLocationId;
	}

	public void setActionPerformedByOrgLocationId(Long actionPerformedByOrgLocationId) {
		this.actionPerformedByOrgLocationId = actionPerformedByOrgLocationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getReopenedUser() {
		return reopenedUser;
	}

	public void setReopenedUser(User reopenedUser) {
		this.reopenedUser = reopenedUser;
	}

	public String getReopenReason() {
		return reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}

	public String getReopenRejectedReason() {
		return reopenRejectedReason;
	}

	public void setReopenRejectedReason(String reopenRejectedReason) {
		this.reopenRejectedReason = reopenRejectedReason;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
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

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public MasterCandidate getMasterCandidate() {
		return masterCandidate;
	}

	public void setMasterCandidate(MasterCandidate masterCandidate) {
		this.masterCandidate = masterCandidate;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	public Long getActionPerformedbyUserId() {
		return actionPerformedbyUserId;
	}

	public void setActionPerformedbyUserId(Long actionPerformedbyUserId) {
		this.actionPerformedbyUserId = actionPerformedbyUserId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public List<FileInfo> getTimesheetFileUrl() {
		return timesheetFileUrl;
	}

	public void setTimesheetFileUrl(List<FileInfo> timesheetFileUrl) {
		this.timesheetFileUrl = timesheetFileUrl;
	}

	public String getWeekComments() {
		return weekComments;
	}

	public void setWeekComments(String weekComments) {
		this.weekComments = weekComments;
	}

	public String getWeekTotal() {
		return weekTotal;
	}

	public void setWeekTotal(String weekTotal) {
		this.weekTotal = weekTotal;
	}

	public String getWeekTotalBillable() {
		return weekTotalBillable;
	}

	public void setWeekTotalBillable(String weekTotalBillable) {
		this.weekTotalBillable = weekTotalBillable;
	}

	public String getWeekTotalNonBillable() {
		return weekTotalNonBillable;
	}

	public void setWeekTotalNonBillable(String weekTotalNonBillable) {
		this.weekTotalNonBillable = weekTotalNonBillable;
	}
	
}
