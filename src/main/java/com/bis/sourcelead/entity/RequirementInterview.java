package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEW", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirmentInterview")
	public class RequirementInterview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	@Column(name = "INTERVIEW_TYPE")
	private String interviewType;
	
	@Column(name = "DATE_OF_INTERVIEW")
	private Date dateOfInterview;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;
	
	@Column(name = "LOCATION", columnDefinition = "TEXT default NULL")
	private String location;
	
	@Column(name="NOTIFICATION_TO_APPLICANT")
	private String notificationToApplicant;
	
	@Column(name="NOTIFICATION_TO_SPOC")
	private String notificationToSpoc;
	
	@Column(name="NOTIFICATION_TO_OTHERS")
	private String notificationToOthers;
	
	@Column(name="NOTIFICATION_TO_OTHERS_EMAILS", columnDefinition = "TEXT default NULL")
	private String notificationToOthersEmails;

	@Column(name="REMAINED_BEFORE")
	private String remainedBefore;
	
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
	private String interviewersList;
	
	@Transient
	private String clientName;
	
	@OneToMany(mappedBy = "requirementInterview",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementInterviewer> requirementInterviewers = new HashSet<RequirementInterviewer>(0);

	@Column(name = "INTERVIEW_STATUS", columnDefinition = "TEXT default NULL")
	private String interviewStatus;
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public Date getDateOfInterview() {
		return dateOfInterview;
	}

	public void setDateOfInterview(Date dateOfInterview) {
		this.dateOfInterview = dateOfInterview;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getNotificationToOthers() {
		return notificationToOthers;
	}

	public void setNotificationToOthers(String notificationToOthers) {
		this.notificationToOthers = notificationToOthers;
	}

	public String getNotificationToOthersEmails() {
		return notificationToOthersEmails;
	}

	public void setNotificationToOthersEmails(String notificationToOthersEmails) {
		this.notificationToOthersEmails = notificationToOthersEmails;
	}

	public String getRemainedBefore() {
		return remainedBefore;
	}

	public void setRemainedBefore(String remainedBefore) {
		this.remainedBefore = remainedBefore;
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

	public String getInterviewersList() {
		return interviewersList;
	}

	public void setInterviewersList(String interviewersList) {
		this.interviewersList = interviewersList;
	}

	public Set<RequirementInterviewer> getRequirementInterviewers() {
		return requirementInterviewers;
	}

	public void setRequirementInterviewers(Set<RequirementInterviewer> requirementInterviewers) {
		this.requirementInterviewers = requirementInterviewers;
	}
}
