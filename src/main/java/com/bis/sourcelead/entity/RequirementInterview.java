package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEW")
	public class RequirementInterview implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TIME_ZONE", nullable=false )
	private Timezone timezone;
	
	@Column(name = "LOCATION", columnDefinition = "TEXT default NULL")
	private String location;
	
	@Column(name="NOTIFICATION_TO_OTHERS")
	private String notificationToOthers;
	
	@Column(name="NOTIFICATION_TO_OTHERS_EMAILS", columnDefinition = "TEXT default NULL")
	private String notificationToOthersEmails;

	@Column(name="REMAINED_BEFORE")
	private String remainedBefore;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "PROPOSE_TIME")
	private String proposeTime;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INTERVIEW_TEMPLATE_ID")
    private SubmissionTemplate interviewTemplateId;
	
	@Column(name = "INTERVIEW_RESCHEDULE_COMMENT", columnDefinition = "TEXT default NULL")
	private String rescheduleComment;
	
	@Column(name = "CUSTOM_INTERVIEW_TYPE")
	private String customInterviewType;
	
	@Column(name = "INTERVIEW_SCORE")
	private Integer interviewScore;
	
	@Column(name = "INTERVIEW_PHASE")
	private String interviewPhase;
	
	@Column(name = "INTERVIEW_COMMENT", columnDefinition = "TEXT default NULL")
	private String interviewComment;
	
	@Column(name = "UUID")
	private String uuid;
	
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
	@JsonProperty
	private List<InterviewFeedbackTemplate> interviewFeedbackTemplateList;
	
	@Transient
	@JsonProperty
	private String candidateName;
	
	@Transient
	@JsonProperty
	private String requirementTitle;
	
	@Transient
   	private String requirementId;
	
	@Transient
	@JsonProperty
	private String interviewStartDateTimeStr;
	
	@Transient
	@JsonProperty
	private String interviewEndtDateTimeStr;
	
	@Transient
	private String clientName;
	
	@Transient
	private String scheduleType;
	
	
	@OneToMany(mappedBy = "requirementInterview",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<RequirementInterviewer> requirementInterviewers = new HashSet<RequirementInterviewer>(0);

	@Column(name = "INTERVIEW_STATUS", columnDefinition = "TEXT default NULL")
	private String interviewStatus;
	
	
	public String getCustomInterviewType() {
		return customInterviewType;
	}

	public void setCustomInterviewType(String customInterviewType) {
		this.customInterviewType = customInterviewType;
	}

	public Integer getInterviewScore() {
		return interviewScore;
	}

	public void setInterviewScore(Integer interviewScore) {
		this.interviewScore = interviewScore;
	}

	public String getInterviewPhase() {
		return interviewPhase;
	}

	public void setInterviewPhase(String interviewPhase) {
		this.interviewPhase = interviewPhase;
	}

	public String getInterviewComment() {
		return interviewComment;
	}

	public void setInterviewComment(String interviewComment) {
		this.interviewComment = interviewComment;
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

	public Timezone getTimezone() {
		return timezone;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getProposeTime() {
		return proposeTime;
	}

	public void setProposeTime(String proposeTime) {
		this.proposeTime = proposeTime;
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

	public List<InterviewFeedbackTemplate> getInterviewFeedbackTemplateList() {
		return interviewFeedbackTemplateList;
	}

	public void setInterviewFeedbackTemplateList(List<InterviewFeedbackTemplate> interviewFeedbackTemplateList) {
		this.interviewFeedbackTemplateList = interviewFeedbackTemplateList;
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

	public String getInterviewEndtDateTimeStr() {
		return interviewEndtDateTimeStr;
	}

	public void setInterviewEndtDateTimeStr(String interviewEndtDateTimeStr) {
		this.interviewEndtDateTimeStr = interviewEndtDateTimeStr;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Set<RequirementInterviewer> getRequirementInterviewers() {
		return requirementInterviewers;
	}

	public void setRequirementInterviewers(Set<RequirementInterviewer> requirementInterviewers) {
		this.requirementInterviewers = requirementInterviewers;
	}

	public SubmissionTemplate getInterviewTemplateId() {
		return interviewTemplateId;
	}

	public void setInterviewTemplateId(SubmissionTemplate interviewTemplateId) {
		this.interviewTemplateId = interviewTemplateId;
	}

	public String getRescheduleComment() {
		return rescheduleComment;
	}

	public void setRescheduleComment(String rescheduleComment) {
		this.rescheduleComment = rescheduleComment;
	}

	public String getScheduleType()
	{
		return scheduleType;
	}

	public void setScheduleType(String scheduleType)
	{
		this.scheduleType = scheduleType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
