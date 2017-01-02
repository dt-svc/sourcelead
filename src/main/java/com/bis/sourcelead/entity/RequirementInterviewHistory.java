package com.bis.sourcelead.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEW_HISTORY", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirmentInterviewHistory")
	public class RequirementInterviewHistory implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="INTERVIEW_ID", nullable=false )        
    private RequirementInterview interviewId;
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_LOCATION_ID")
   	private OrgLocations orgLocationId;
	
	@Column(name = "INTERVIEW_SOURCE_TYPE")
	private String interviewSourceType;
	
	@Column(name="NOTIFICATION_TO_APPLICANT")
	private String notificationToApplicant;
	
	@Column(name="NOTIFICATION_TO_SPOC")
	private String notificationToSpoc;
	
	@Column(name="STATUS")
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
	private String remainderTime;
	
	@Transient
	private String dateOfInterviewStr;
	
	@Transient
	private String startTimeStr;
	
	@Transient
	private String endTimeStr;
	
	@Transient
	private String interviewersList;
	
	@Transient
	private String clientName;
	
	@Transient
	private Long interviewTemplateid;
	
	@Transient
	private String feedbackEndTime;
	
	@Transient
	private String feedbackStartTime;

	@Transient
    private String isNotifyToVendorRequired;
	
	@Transient
	private String candidateName;
	
	@Transient
	private String requirementTitle;
	
	@Transient
   	private String requirementId;
	
	@Transient
	private String interviewStartDateTimeStr;
	
	@Transient
	private String  interviewHistoryCreatedDate;
	
	@Transient
	private String  interviewType;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementInterview getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(RequirementInterview interviewId) {
		this.interviewId = interviewId;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}
	
	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public String getInterviewSourceType() {
		return interviewSourceType;
	}

	public void setInterviewSourceType(String interviewSourceType) {
		this.interviewSourceType = interviewSourceType;
	}

	public String getNotificationToApplicant() {
		return notificationToApplicant;
	}

	public void setNotificationToApplicant(String notificationToApplicant) {
		this.notificationToApplicant = notificationToApplicant;
	}

	public String getNotificationToSpoc() {
		return notificationToSpoc;
	}

	public void setNotificationToSpoc(String notificationToSpoc) {
		this.notificationToSpoc = notificationToSpoc;
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

	public String getRemainderTime() {
		return remainderTime;
	}

	public void setRemainderTime(String remainderTime) {
		this.remainderTime = remainderTime;
	}

	public String getDateOfInterviewStr() {
		return dateOfInterviewStr;
	}

	public void setDateOfInterviewStr(String dateOfInterviewStr) {
		this.dateOfInterviewStr = dateOfInterviewStr;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getInterviewersList() {
		return interviewersList;
	}

	public void setInterviewersList(String interviewersList) {
		this.interviewersList = interviewersList;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getInterviewTemplateid() {
		return interviewTemplateid;
	}

	public void setInterviewTemplateid(Long interviewTemplateid) {
		this.interviewTemplateid = interviewTemplateid;
	}

	public String getFeedbackEndTime() {
		return feedbackEndTime;
	}

	public void setFeedbackEndTime(String feedbackEndTime) {
		this.feedbackEndTime = feedbackEndTime;
	}
	

	public String getIsNotifyToVendorRequired() {
		return isNotifyToVendorRequired;
	}

	public void setIsNotifyToVendorRequired(String isNotifyToVendorRequired) {
		this.isNotifyToVendorRequired = isNotifyToVendorRequired;
	}


	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public String getInterviewStartDateTimeStr() {
		return interviewStartDateTimeStr;
	}

	public void setInterviewStartDateTimeStr(String interviewStartDateTimeStr) {
		this.interviewStartDateTimeStr = interviewStartDateTimeStr;
	}

	public String getFeedbackStartTime() {
		return feedbackStartTime;
	}

	public void setFeedbackStartTime(String feedbackStartTime) {
		this.feedbackStartTime = feedbackStartTime;
	}

	public String getInterviewHistoryCreatedDate() {
		return interviewHistoryCreatedDate;
	}

	public void setInterviewHistoryCreatedDate(String interviewHistoryCreatedDate) {
		this.interviewHistoryCreatedDate = interviewHistoryCreatedDate;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
}
