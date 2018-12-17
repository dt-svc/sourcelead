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

import com.fasterxml.jackson.annotation.JsonProperty;

	@Entity
	@Table(name = "REQUIREMENT_INTERVIEW_HISTORY")
	public class RequirementInterviewHistory implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INTERVIEW_ID", nullable=false )        
    private RequirementInterview interviewId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SUBMISSION_ID", nullable=false )        
    private RequirementSubmission submissionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	@JsonProperty
	private String startTimeStr;
	
	@Transient
	@JsonProperty
	private String endTimeStr;
	
	@Transient
	@JsonProperty
	private String interviewersList;
	
	@Transient
	@JsonProperty
	private String clientName;
	
	@Transient
	@JsonProperty
	private Long interviewTemplateid;
	
	@Transient
	@JsonProperty
	private String feedbackEndTime;
	
	@Transient
	@JsonProperty
	private String feedbackStartTime;

	@Transient
    private String isNotifyToVendorRequired;
	
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
	private String  interviewHistoryCreatedDate;
	
	@Transient
	@JsonProperty
	private String  interviewType;
	
	@Transient
	@JsonProperty
	private String  location;

	@Transient
	@JsonProperty
	private String  companyName;

	@Transient
	@JsonProperty
	private String  requirementUUID;
	
	@Transient
	@JsonProperty
	private String interviewTimeZoneStartTimeStr;
	
	@Transient
	@JsonProperty
	private String interviewTimeZoneEndTimeStr;
	
	@Transient
	@JsonProperty
	private String interviewTimezoneDateStr;
	
	@Transient
	@JsonProperty
	private Long interviewerId;
	
	@Transient
	@JsonProperty
	private List<RequirementInterviewHistory> interviewerList;
	
	@Transient
	private List<ProposeTime> proposeTimeList;
	
	@Transient
	@JsonProperty
	private List<Tasks> tasksList;
	
	@Transient
	@JsonProperty
	private String dayOfweek;

	@Transient
	@JsonProperty
	private String selectedDate;
	
	@Transient
	@JsonProperty
	private String candidateEmail;
		
	@Transient
	@JsonProperty
	private String candidatePhoneNumber;
	
	@Transient
	@JsonProperty
	private String rescheduleComment;
	
	@Transient
	@JsonProperty
	private String timezone;
	
	@Transient
	@JsonProperty
	private String interviewSubmissionId;
	
	@Transient
	@JsonProperty
	private String isOwnOrgTemplate;
	
	@Transient
	@JsonProperty
	private String feedbackDate;
	
	@Transient
	@JsonProperty
	private String submissionStatus;
	
	@Transient
	@JsonProperty
	private String proposeTimeStatus;

	@Transient
	@JsonProperty
	private List<RequirementInterviewer> requirementInterviewerList;
	
	@Transient
	@JsonProperty
	private Long requirementIntervieweId;
	
	
	@Transient
	@JsonProperty
	private String interviewTemplateUUId;
	
	@Transient
	@JsonProperty
	private String interviewerUUID;
	
	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getCandidatePhoneNumber() {
		return candidatePhoneNumber;
	}

	public void setCandidatePhoneNumber(String candidatePhoneNumber) {
		this.candidatePhoneNumber = candidatePhoneNumber;
	}

	public String getDayOfweek() {
		return dayOfweek;
	}

	public void setDayOfweek(String dayOfweek) {
		this.dayOfweek = dayOfweek;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public List<RequirementInterviewHistory> getInterviewerList() {
		return interviewerList;
	}

	public void setInterviewerList(List<RequirementInterviewHistory> interviewerList) {
		this.interviewerList = interviewerList;
	}

	public List<ProposeTime> getProposeTimeList() {
		return proposeTimeList;
	}

	public void setProposeTimeList(List<ProposeTime> proposeTimeList) {
		this.proposeTimeList = proposeTimeList;
	}

	public List<Tasks> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Tasks> tasksList) {
		this.tasksList = tasksList;
	}

	public String getInterviewTimeZoneStartTimeStr() {
		return interviewTimeZoneStartTimeStr;
	}

	public void setInterviewTimeZoneStartTimeStr(String interviewTimeZoneStartTimeStr) {
		this.interviewTimeZoneStartTimeStr = interviewTimeZoneStartTimeStr;
	}

	public String getInterviewTimeZoneEndTimeStr() {
		return interviewTimeZoneEndTimeStr;
	}

	public void setInterviewTimeZoneEndTimeStr(String interviewTimeZoneEndTimeStr) {
		this.interviewTimeZoneEndTimeStr = interviewTimeZoneEndTimeStr;
	}



	public String getInterviewTimezoneDateStr() {
		return interviewTimezoneDateStr;
	}

	public void setInterviewTimezoneDateStr(String interviewTimezoneDateStr) {
		this.interviewTimezoneDateStr = interviewTimezoneDateStr;
	}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public Long getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(Long interviewerId) {
		this.interviewerId = interviewerId;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getRescheduleComment() {
		return rescheduleComment;
	}

	public void setRescheduleComment(String rescheduleComment) {
		this.rescheduleComment = rescheduleComment;
	}

	public String getIsOwnOrgTemplate() {
		return isOwnOrgTemplate;
	}

	public void setIsOwnOrgTemplate(String isOwnOrgTemplate) {
		this.isOwnOrgTemplate = isOwnOrgTemplate;
	}

	public String getInterviewSubmissionId() {
		return interviewSubmissionId;
	}

	public void setInterviewSubmissionId(String interviewSubmissionId) {
		this.interviewSubmissionId = interviewSubmissionId;
	}

	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getProposeTimeStatus() {
		return proposeTimeStatus;
	}

	public void setProposeTimeStatus(String proposeTimeStatus) {
		this.proposeTimeStatus = proposeTimeStatus;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public List<RequirementInterviewer> getRequirementInterviewerList() {
		return requirementInterviewerList;
	}

	public void setRequirementInterviewerList(List<RequirementInterviewer> requirementInterviewerList) {
		this.requirementInterviewerList = requirementInterviewerList;
	}

	public Long getRequirementIntervieweId() {
		return requirementIntervieweId;
	}

	public void setRequirementIntervieweId(Long requirementIntervieweId) {
		this.requirementIntervieweId = requirementIntervieweId;
	}

	public String getInterviewTemplateUUId() {
		return interviewTemplateUUId;
	}

	public void setInterviewTemplateUUId(String interviewTemplateUUId) {
		this.interviewTemplateUUId = interviewTemplateUUId;
	}

	public String getInterviewerUUID() {
		return interviewerUUID;
	}

	public void setInterviewerUUID(String interviewerUUID) {
		this.interviewerUUID = interviewerUUID;
	}
}
