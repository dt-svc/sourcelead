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
	@Table(name = "REQUIREMENT_INTERVIEWER")
	public class RequirementInterviewer implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INTERVIEW_ID", nullable=false )        
    private RequirementInterview requirementInterview;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INTERVIEWER_USER_ID" )        
    private User interviewerUser;
	
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
	
	@Column(name = "EXTERNAL_INTERVIEWER_NAME")
	@Type(type="text")
	private String externalInterviewerName;
	
	@Column(name = "EXTERNAL_INTERVIEWER_EMAIL")
	private String externalInterviewerEmail;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Transient
	@JsonProperty
	private String interviewerName;
	
	@Transient
	@JsonProperty
	private String feedbackDate;
	
	@Transient
	@JsonProperty
	private String feedbackStatus;
	
	@Transient
	@JsonProperty
	private String interviewStartTime;
	
	@Transient
	@JsonProperty
	private String interviewEndTime;
	
	@Transient
	@JsonProperty
	private String requirementTitle;
	
	
	@Transient
	@JsonProperty
	private String interviewType;

	@Transient
	@JsonProperty
	private List<RequirementInterviewer> interviewerList;
	
	@Transient
	@JsonProperty
	private List<RequirementInterview> applicantsList;
	
	
	public List<RequirementInterviewer> getInterviewerList() {
		return interviewerList;
	}

	public void setInterviewerList(List<RequirementInterviewer> interviewerList) {
		this.interviewerList = interviewerList;
	}

	public List<RequirementInterview> getApplicantsList() {
		return applicantsList;
	}

	public void setApplicantsList(List<RequirementInterview> applicantsList) {
		this.applicantsList = applicantsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementInterview getRequirementInterview() {
		return requirementInterview;
	}

	public void setRequirementInterview(RequirementInterview requirementInterview) {
		this.requirementInterview = requirementInterview;
	}

	public User getInterviewerUser() {
		return interviewerUser;
	}

	public void setInterviewerUser(User interviewerUser) {
		this.interviewerUser = interviewerUser;
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

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public String getInterviewStartTime() {
		return interviewStartTime;
	}

	public void setInterviewStartTime(String interviewStartTime) {
		this.interviewStartTime = interviewStartTime;
	}

	public String getInterviewEndTime() {
		return interviewEndTime;
	}

	public void setInterviewEndTime(String interviewEndTime) {
		this.interviewEndTime = interviewEndTime;
	}

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getExternalInterviewerName() {
		return externalInterviewerName;
	}

	public void setExternalInterviewerName(String externalInterviewerName) {
		this.externalInterviewerName = externalInterviewerName;
	}

	public String getExternalInterviewerEmail() {
		return externalInterviewerEmail;
	}

	public void setExternalInterviewerEmail(String externalInterviewerEmail) {
		this.externalInterviewerEmail = externalInterviewerEmail;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
